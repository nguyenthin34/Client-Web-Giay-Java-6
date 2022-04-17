package com.assignment.Controller;

import com.assignment.DTO.ClientUsersDTO;
import com.assignment.DTO.RolesDTO;
import com.assignment.domain.User;
import com.assignment.service.RoleService;
import com.assignment.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class UserController {
    @Autowired
    HttpSession session;

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    RoleService roleService;
    @ModelAttribute("roles")
    public List<RolesDTO> getRoles(){
        return roleService.findAll().stream().map(item -> {
            RolesDTO dto = new RolesDTO();
            BeanUtils.copyProperties(item, dto);
            dto.setRoleId(item.getRole_id());
            return dto;
        }).toList();
    }

    @GetMapping("/information")
    public ModelAndView getProfile(){
        ModelAndView mav = new ModelAndView("/profile");
        Optional<User> user = userService.findById(session.getAttribute("username").toString());
        mav.addObject("user", user.get());
        return mav;
    }


    @PostMapping("")
    public String updateProfile(@Valid @ModelAttribute("user") ClientUsersDTO dto,
                                BindingResult bResult, RedirectAttributes params, ModelMap model){
        if (bResult.hasErrors()) {
            params.addAttribute("messaege", "Update Errors");
            return "redirect:/profile/information";
        }
        User checkEmail = userService.findByEmailContaining(dto.getEmail());
        Optional<User> checkUser = userService.findById(dto.getUsername());
        if(!dto.getEmail().equals(checkUser.get().getEmail()) && checkEmail != null){
            params.addAttribute("user", dto);
            params.addAttribute("message", "Email already exists");
            return "redirect:/profile/information";
        }
        User entity = new User();
        User oldUser = userService.findById(dto.getUsername()).get();
        BeanUtils.copyProperties(dto, entity);
        if(dto.getPassword().equals("") || dto.getPassword() == null){
            entity.setPassword(oldUser.getPassword());
        }else{
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        entity.setRole(oldUser.getRole());
        entity.setStatus(oldUser.getStatus());
        userService.update(entity);
        params.addAttribute("messaege", "Update Success");
        return "redirect:/profile/information";
    }
}
