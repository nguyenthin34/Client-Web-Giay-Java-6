package com.assignment.Controller.Client;

import com.assignment.domain.Commodity;
import com.assignment.service.CategoryService;
import com.assignment.service.CommodityService;
import com.assignment.service.StorageService;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/site/commodity")
public class CommoditySiteController {
    @Autowired
    CommodityService service;

    @Autowired
    CategoryService cService;

    @Autowired
    StorageService sService;

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename) {
        Resource file = sService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment: filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("")
    public String list(Model model,
                       @RequestParam(name = "name", required = false) String name,
                       @RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size) {
        Pageable pageableDefault = PageRequest.of(0, 5, Sort.by("name"));

        int currentPage = page.orElse(1);
        int sizePage = size.orElse(6);
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
        return "/client/ShowProduct";
    }
}
