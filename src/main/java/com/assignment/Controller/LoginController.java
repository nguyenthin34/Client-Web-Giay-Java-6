package com.assignment.Controller;

import com.assignment.domain.User;
import com.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    HttpSession session;

    @Autowired
    UserService service;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/home/index")
    public String index(Model model){
        return "/client/home";
    }

    @GetMapping("/home/login")
    public ModelAndView login(ModelMap model){
        return new ModelAndView("/client/signin", model);
    }

    @GetMapping("/auth/login/success")
    public String success(RedirectAttributes params){
        String username = session.getAttribute("username").toString();
        User user = service.findById(username).get();
        if(user.getRole().getName().equals("ADMIN") || user.getRole().getName().equals("EMPLOYER") ){
            return "redirect:/home/login";
        }else{
            return "redirect:/site/product";
        }
    }
    @GetMapping("/auth/login/failed")
    public String failed(RedirectAttributes params, ModelMap model){
        params.addAttribute("message", "Login Failed");
        return "redirect:/home/index";
    }

    @GetMapping("/auth/logout/success")
    public String logout(){
        session.setAttribute("username", null);
        return "redirect:/home/index";
    }
    @GetMapping("/auth/access/denied")
    public String fai(RedirectAttributes params){
        String username = session.getAttribute("username").toString();
        User user = service.findById(username).get();
        if(user.getRole().getName().equals("ADMIN") || user.getRole().getName().equals("EMPLOYER") ){
            params.addAttribute("message", "You can't Login here");
            return "redirect:/home/login";
        }else{
            params.addAttribute("message", "You can't Login here");
            return "redirect:/home/login";
        }
    }
}
