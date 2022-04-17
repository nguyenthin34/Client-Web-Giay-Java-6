package com.assignment.Controller;

import com.assignment.domain.Order;
import com.assignment.domain.Orderdetail;
import com.assignment.service.OrderDetailService;
import com.assignment.service.OrderService;
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

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    HttpSession session;

    @Autowired
    StorageService sService;



    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename) {
        Resource file = sService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment: filename=\"" + file.getFilename() + "\"").body(file);
    }


    @GetMapping("/checkout")
    public String checkout(){
        return "order/checkout";
    }

    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage);
        Page<Order> resultPage = orderService.findByUser_Username(pageable, session.getAttribute("username").toString());
        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);

            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("orderPage", resultPage);
        return "/client/order/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model,
                         @RequestParam("page") Optional<Integer> page,
                         @RequestParam("size") Optional<Integer> size ){


        model.addAttribute("order", orderService.findById(id).get());

        Pageable pageableDefault = PageRequest.of(0, 5);

        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage);
        Page<Order> resultPage = orderService.findByUser_Username(pageable, session.getAttribute("username").toString());
        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);

            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("orderPage", resultPage);
        return "/client/order/detail";
    }

}
