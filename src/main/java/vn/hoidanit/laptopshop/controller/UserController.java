package vn.hoidanit.laptopshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;





@Controller
public class UserController{
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @RequestMapping("/")
    public String getHomePage(){
        String test = this.userService.handleHello();
        return "hello";
    }
    
    @RequestMapping("/admin/user/create")
    public String getUserCreationPage(Model model){
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }
    
    @RequestMapping(value="/admin/user", method=RequestMethod.GET)
    public String getUserPage(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users1", users);
        return "admin/user/table-user";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User user){
        System.out.println("User: "+user);
        this.userRepository.save(user);
        return "redirect:/admin/user";
    }
    


    
}

