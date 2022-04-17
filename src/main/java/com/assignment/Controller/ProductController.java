package com.assignment.Controller;

import com.assignment.DTO.*;
import com.assignment.domain.Color;
import com.assignment.domain.Commodity;
import com.assignment.domain.Product;
import com.assignment.domain.Size;
import com.assignment.service.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    AdminShoppingCartService service;

    @Autowired
    CommodityService commodityService;

    @Autowired
    ColorService colorService;

    @Autowired
    SizeService sizeService;

    @Autowired
    ProductService productService;

    @Autowired
    StorageService sService;

    @ModelAttribute("status")
    public Map<Integer, String> getStatus(){
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "Unconfimred");
        map.put(1, "confimred");
        map.put(2, "Delivered");
        map.put(3, "Cancelled");
        return map;
    }

    @RequestMapping("list")
    public String view(Model model) {
        Collection<AdminCartItem> cartItems = service.getItems();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", service.getAmount());
        model.addAttribute("NoOfItems", service.getCount());
        model.addAttribute("cartItem" , new AdminCartItem());
        return "/admin/product/listcart";
    }

    @ModelAttribute("colors")
    public List<ColorsDTO> getColor(){
        return colorService.findAll().stream()
                .map(item -> {
                    ColorsDTO dto = new ColorsDTO();
                    BeanUtils.copyProperties(item, dto);
                    dto.setColorId(item.getColor_id());
                    return dto;
                }).toList();
    }

    @ModelAttribute("sizes")
    public List<SizesDTO> getSizes(){
        return sizeService.findAll().stream().map(item ->{
            SizesDTO dto = new SizesDTO();
            BeanUtils.copyProperties(item, dto);
            dto.setSizeId(item.getSize_id());
            return dto;
        }).toList();
    }
    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename) {
        Resource file = sService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment: filename=\"" + file.getFilename() + "\"").body(file);
    }


    @ModelAttribute("commodities")
    public List<CommoditiesDTO> getCommodities(){
        return commodityService.findAll().stream().map(
                item -> {
                    CommoditiesDTO dto = new CommoditiesDTO();
                    BeanUtils.copyProperties(item, dto);
                    dto.setCommodityId(item.getCommodity_id());
                    return dto;
                }
        ).toList();
    }

    @GetMapping("add/{commodityId}")
    public String add(@PathVariable("commodityId")Integer commodityId,
                      RedirectAttributes params){
        Optional<Commodity> commodity = commodityService.findById(commodityId);
        if(commodity.isPresent()){
            AdminCartItem cartItem = new AdminCartItem();
            int ranNum = ThreadLocalRandom.current().nextInt(1,10000);
            cartItem.setId(ranNum);
            cartItem.setName(commodity.get().getName());
            cartItem.setCommodityId(commodity.get().getCommodity_id());
            cartItem.setQuantity(1);
            cartItem.setUnitPrice(0.0);
            cartItem.setImage(commodity.get().getImage());
            if(getColor().size() > 0){
                cartItem.setColorId(getColor().get(0).getColorId());
            }
            if(getSizes().size() > 0){
                cartItem.setSizeId(getSizes().get(0).getSizeId());
            }
            service.add(cartItem);

        }else{
            params.addAttribute("message","Commodity is not exists");
            return "redirect:/product/list";
        }
        return "redirect:/product/list";
    }

    @PostMapping("updateCart")
    public String update(@RequestParam("commodityId") Optional<Integer> commodityId,
                         @RequestParam("id") Optional<Integer> Id,
                         @RequestParam("unitPrice") Optional<Double> unitPrice,
                         @RequestParam("quantity") Optional<Integer> quantity,
                         @RequestParam("colorId") Optional<Integer> colorId,
                         @RequestParam("sizeId") Optional<Integer> sizeId,
                         RedirectAttributes params){
        if(Id.isEmpty() ||commodityId.isEmpty() || unitPrice.isEmpty() || quantity.isEmpty() || colorId.isEmpty() || sizeId.isEmpty()){
            params.addAttribute("message", "Error! An error occurred. Please try again later!");
            return "redirect:/product/list";
        }else if(!colorService.existsById(colorId.get())){
            params.addAttribute("message", "Error! An error occurred. Color ID is Not Exists");
            return "redirect:/product/list";
        }else if(!sizeService.existsById(sizeId.get())){
            params.addAttribute("message", "Error! An error occurred. Size ID is Not Exists");
            return "redirect:/product/list";
        }
        else if(!commodityService.existsById(commodityId.get())){
            params.addAttribute("message", "Error! An error occurred. Commodity ID is Not Exists");
            return "redirect:/product/list";
        }else{
            service.update(Id.get(), quantity.get(), unitPrice.get(), colorId.get(), sizeId.get());
            return "redirect:/product/list";
        }

    }

    @GetMapping("clear")
    public String clear() {
        return "redirect:/product/list";
    }


    @PostMapping("saveCart")
    public String save(RedirectAttributes params,
                       Model model) {
        for (int i = 0; i < service.getCount(); i++) {
            Product product = new Product();
            List<AdminCartItem> list = service.getItems().stream().toList();
            Size size = sizeService.findById(list.get(i).getSizeId()).get();
            Color color = colorService.findById(list.get(i).getColorId()).get();
            Commodity commodity = commodityService.findById(list.get(i).getCommodityId()).get();
            product.setSize(size);
            product.setColor(color);
            product.setCommodity(commodity);
            product.setQuantity(list.get(i).getQuantity());
            product.setUnitPrice(list.get(i).getUnitPrice());
            product.setPrice(0.0);
            product.setStatus(0);
            productService.save(product);
        }
        service.clear();
        params.addAttribute("message", "Add success");
        return "redirect:/product";
    }



    @GetMapping("")
    public String list(Model model,
                       @RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage);
        Page<Product> resultPage = null;
        resultPage = productService.findAll(pageable);
        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("productPage", resultPage);
        return "/admin/product/list";
    }

    @GetMapping("update")
    public String edit(
            @RequestParam("productId") Optional<Integer> productId,
            @RequestParam("price") Optional<Double> price,
            @RequestParam("status") Optional<Integer> status,
            ModelMap model, RedirectAttributes params) {
        ProductsDTO dto = new ProductsDTO();
        if(productId.isEmpty()){
            params.addAttribute("message", "Product ID is Empty");
        }else if(price.isEmpty()){
            params.addAttribute("message", "Price is Empty");
        }else if(status.isEmpty()){
            params.addAttribute("message", "Status ID is Empty");
        }else{
            Product entity = productService.getById(productId.get());

            if(entity == null){
                params.addAttribute("message", "Product is Empty");
            }else{
                if(price.get() < 0){
                    entity.setPrice(0.0);
                }
                entity.setPrice(price.get());
                entity.setStatus(status.get());
                productService.save(entity);
            }
        }
        return "redirect:/product";
    }


    @GetMapping("paginated")
    public String search(Model model,
                         @RequestParam(name = "commodityId", required = false) Optional<Integer> commodityId,
                         @RequestParam("page") Optional<Integer> page,
                         @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage);
        Page<Product> resultPage = null;
        if(commodityId.isEmpty()){
            resultPage = productService.findAll(pageable);
        }else if(commodityId.isPresent()){
            resultPage = productService.findByCommodity_Commodity_id(commodityId.get(), pageable);
        }else {
            resultPage = productService.findAll(pageable);
        }
        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);

            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("productPage", resultPage);
        return "/admin/product/list";
    }
}
