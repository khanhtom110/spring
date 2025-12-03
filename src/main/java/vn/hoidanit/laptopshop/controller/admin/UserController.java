package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @RequestMapping("/")
    public String getHomePage() {
        String test = this.userService.handleHello();
        return "hello";
    }

    @RequestMapping("/admin/user/create")
    public String getUserCreationPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user", method = RequestMethod.GET)
    public String getUsersPage(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users1", users);
        return "admin/user/show";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User user) {
        this.userRepository.save(user);
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/admin/user/{id}", method = RequestMethod.GET)
    public String getUserDetailPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id); // Gan id vao view
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }

    @RequestMapping("/admin/user/update/{id}")
    public String userUpdatingPage(Model model, @PathVariable long id) {
        User user = userService.getUser(id);
        model.addAttribute("newUser", user);
        return "admin/user/update";
    }

    @RequestMapping(value = "/admin/user/update", method = RequestMethod.POST)
    public String userUpdatingPage(Model model, @ModelAttribute("newUser") User user) {
        userService.updateUser(user);
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/admin/user/delete/{id}", method = RequestMethod.GET)
    public String userDeletePage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        return "admin/user/delete";
    }

    @GetMapping("/admin/user/delete/delete/{id}")
    public String deleteUser(Model model, @PathVariable long id) {
        userService.deleteUser(id);
        return "redirect:/admin/user";
    }

}
