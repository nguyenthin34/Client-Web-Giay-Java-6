package com.assignment.Controller;

import com.assignment.DTO.CategoriesDTO;
import com.assignment.DTO.CommoditiesDTO;
import com.assignment.domain.Category;
import com.assignment.domain.Commodity;
import com.assignment.service.CategoryService;
import com.assignment.service.CommodityService;
import com.assignment.service.StorageService;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/commodity")
public class CommodityController {
    @Autowired
    CommodityService service;

    @Autowired
    CategoryService cService;

    @Autowired
    StorageService sService;

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return cService.findAll();
    }

    @GetMapping("add")
    public String add(Model model) {
        CommoditiesDTO dto = new CommoditiesDTO();
        model.addAttribute("commodity", dto);
        return "/admin/commodity/add";
    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename) {
        Resource file = sService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment: filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("save")
    public String saveOrupdate(RedirectAttributes params,
                               @Valid @ModelAttribute("commodity") CommoditiesDTO dto,
                               BindingResult bResult) {
        if (bResult.hasErrors()) {
            params.addAttribute("message", "errors");
            return "/admin/commodity/add";
        }
        Commodity entity = new Commodity();
        BeanUtils.copyProperties(dto, entity);
        entity.setCommodity_id(dto.getCommodityId());
        Category category = new Category();
        category.setCategory_id(dto.getCategory_id());
        entity.setCategory(category);
        if (!dto.getImageFile().isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();
            entity.setImage(sService.getStoredFileName(dto.getImageFile(), uuString));
            sService.store(dto.getImageFile(), entity.getImage());
        } else if (dto.getImageFile().isEmpty() && dto.getImage() != null) {
            entity.setImage(dto.getImage());
        } else {
            params.addAttribute("message", "You cannot leave the photo blank");
            return "redirect:/commodity";
        }
        service.save(entity);
        params.addAttribute("message", "Save is Success");
        return "redirect:/commodity/add";
    }

    @GetMapping("")
    public String list(Model model,
                       @RequestParam(name = "name", required = false) String name,
                       @RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size) {
        Pageable pageableDefault = PageRequest.of(0, 5, Sort.by("name"));

        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage, Sort.by("name"));
        Page<Commodity> resultPage = null;
        Page<Commodity> resultPageDefault = null;
        if (StringUtils.hasText(name)) {
            resultPage = service.findByNameContaining(name, pageable);
            resultPageDefault = service.findByNameContaining(name, pageableDefault);
        } else {
            resultPage = service.findAll(pageable);
            resultPageDefault = service.findAll(pageableDefault);
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
        model.addAttribute("commodityPage", resultPage);
        model.addAttribute("commodities", resultPageDefault.getContent());
        return "/admin/commodity/list";
    }

    @GetMapping("edit/{commodityId}")
    public ModelAndView edit(@PathVariable("commodityId") Integer commodityId,
                             ModelMap model, RedirectAttributes params) {
        CommoditiesDTO dto = new CommoditiesDTO();
        Optional<Commodity> optional = service.findById(commodityId);
        if (optional.isPresent()) {
            Commodity entity = optional.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setCommodityId(entity.getCommodity_id());
            dto.setCategory_id(entity.getCategory().getCategory_id());
            dto.setImage(entity.getImage());
            model.addAttribute("commodity", dto);
            return new ModelAndView("/admin/commodity/add", model);
        }
        params.addAttribute("message", "commodity is not exists");
        return new ModelAndView("redirect:/commodity", model);
    }

    @GetMapping("setStatus/{commodityId}")
    public String setStatus(@PathVariable("commodityId") Integer commodityId,
                            ModelMap model, RedirectAttributes params) {
        Optional<Commodity> optional = service.findById(commodityId);
        if (optional.isPresent()) {
            Commodity entity = optional.get();
            if (entity.getStatus()) {
                entity.setStatus(false);
            } else {
                entity.setStatus(true);
            }
            service.save(entity);
        } else {
            params.addAttribute("message", "Entity is not found");
        }
        return "redirect:/admin/commodities";
    }

    @GetMapping("paginated")
    public String search(Model model,
                         @RequestParam(name = "name", required = false) String name,
                         @RequestParam("page") Optional<Integer> page,
                         @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage, Sort.by("name"));
        Page<Commodity> resultPage = null;
        if (StringUtils.hasText(name)) {
            resultPage = service.findByNameContaining(name, pageable);
        } else {
            resultPage = service.findAll(pageable);
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
        model.addAttribute("commodityPage", resultPage);
        return "/admin/commodity/list";
    }

    @GetMapping("commodityAddCart")
    public String getlist(Model model,
                          @RequestParam(name = "name", required = false) String name,
                          @RequestParam("page") Optional<Integer> page,
                          @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(6);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage, Sort.by("name"));
        Page<Commodity> resultPage = null;
        Page<Commodity> resultPageDefault = null;
        if (StringUtils.hasText(name)) {
            resultPage = service.findByNameContaining(name, pageable);
        } else {
            resultPage = service.findAll(pageable);
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
        model.addAttribute("commodityPage", resultPage);
        return "/admin/shoppingcart/listcommodities";
    }

    @GetMapping("pagiatedcart")
    public String searchCart(Model model,
                             @RequestParam(name = "name", required = false) String name,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(6);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage, Sort.by("name"));
        Page<Commodity> resultPage = null;
        if (StringUtils.hasText(name)) {
            resultPage = service.findByNameContaining(name, pageable);
        } else {
            resultPage = service.findAll(pageable);
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
        model.addAttribute("commodityPage", resultPage);
        return "/admin/product/commodities";
    }
}
