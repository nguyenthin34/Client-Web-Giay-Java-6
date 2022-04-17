package com.assignment.Controller.Client;

import com.assignment.DTO.ColorsDTO;
import com.assignment.DTO.SizesDTO;
import com.assignment.domain.Commodity;
import com.assignment.domain.Product;
import com.assignment.model.product;
import com.assignment.repository.ProductRepository;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/site/product")
public class ProductSiteController {

    @Autowired
    StorageService sService;
    @Autowired
    ProductService productService;

    @Autowired
    CommodityService commodityService;

    @Autowired
    ColorService colorService;

    @Autowired
    SizeService sizeService;

    @Autowired
    ProductRepository productRepository;
    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename) {
        Resource file = sService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment: filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("{commodityId}")
    public String getCommodity(@PathVariable("commodityId") Optional<Integer> commodityId,
                               Model model, RedirectAttributes params){

        if(commodityId.isEmpty()){
            params.addAttribute("message", "Commodity ID is Empty");
            return "redirect:/home-shop-poly";
        }
        Optional<Commodity> commodity = commodityService.findById(commodityId.get());
        if(commodity.isEmpty()){
            params.addAttribute("message", "Commodity is Empty");
            return "redirect:/home-shop-poly";
        }
        List<Product> list = productService.findByProductByCommodityId(commodityId.get());
        if(list.size() <= 0 || list == null){
            params.addAttribute("message", "The product is temporarily out of stock, please come back later");
            return "redirect:/home-shop-poly";
        }else{
            model.addAttribute("products", list);
            model.addAttribute("product", list.get(0));
        }
        List<ColorsDTO> colors =  list.stream()
                .map(item -> {
                    ColorsDTO dto = new ColorsDTO();
                    BeanUtils.copyProperties(item.getColor(), dto);
                    dto.setColorId(item.getColor().getColor_id());
                    return dto;
                }).distinct().toList();
        model.addAttribute("colors", colors);
        List<SizesDTO> sizes =  list.stream()
                .map(item -> {
                    SizesDTO dto = new SizesDTO();
                    BeanUtils.copyProperties(item.getSize(), dto);
                    dto.setSizeId(item.getSize().getSize_id());
                    return dto;
                }).distinct().toList();
        model.addAttribute("sizes", sizes);
//        model.addAttribute("siteCart", new SiteCart());
        return "/client/product/detail";
    }

    @GetMapping("")
    public String getAllProduct(Model model,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size){
        Pageable pageableDefault = PageRequest.of(0, 6);

        int currentPage = page.orElse(1);
        int sizePage = size.orElse(6);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage);
        Page<Product> resultPage = null;
            resultPage = productRepository.getAllByCommodity(pageable);
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
        return "/client/ShowProduct";
    }


//    @GetMapping("update")
//    public String update(@RequestParam("commodityId") Optional<Integer> commodityId,
//                         @RequestParam("colorId") Optional<Integer> colorId,
//                         @RequestParam("sizeId") Optional<Integer> sizeId,
//                         RedirectAttributes params,
//                         Model model){
//        if(colorId.isEmpty() || sizeId.isEmpty() || commodityId.isEmpty()){
//            params.addAttribute("message", "Error! An error occurred. Please try again later");
//            return "redirect:/home-shop-poly";
//        }else if((colorId.isEmpty() || colorId.isEmpty()) && commodityId.isPresent()){
//            return  "redirect:/detail-product/" + commodityId.get();
//        }
//        List<Product> listcs =
//                productService.findByColor_ColorIdAndSize_SizeIdAndCommodity_CommodityIdOrderByQuantity(colorId.get(), sizeId.get(), commodityId.get());
//        List<Product> list = productService.findByCommodity_Commodity_id(commodityId.get());
//        if(listcs.size() <= 0){
//            params.addAttribute("message", "The product you selected is out of stock");
//            return "redirect:/detail-product/" + commodityId.get();
//        }else{
//            model.addAttribute("product", listcs.get(0));
//        }
//        List<ColorsDTO> colors =  list.stream()
//                .map(item -> {
//                    ColorsDTO dto = new ColorsDTO();
//                    BeanUtils.copyProperties(item.getColor(), dto);
//                    return dto;
//                }).distinct().toList();
//        model.addAttribute("colors", colors);
//        List<SizesDTO> sizes =  list.stream()
//                .map(item -> {
//                    SizesDTO dto = new SizesDTO();
//                    BeanUtils.copyProperties(item.getSize(), dto);
//                    return dto;
//                }).distinct().toList();
//        model.addAttribute("sizes", sizes);
////        model.addAttribute("siteCart", new product());
//        return "redirect:/site/product/view";
//    }


    @GetMapping("/view")
    public String getCartView(){
        return "/client/product/shopping-cart";
    }
}
