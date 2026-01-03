package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UserRepository userRepository, UploadService uploadService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/admin/user/create")
    public String getUserCreationPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @GetMapping(value = "/admin/user")
    public String getUsersPage(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users1", users);
        return "admin/user/show";
    }

    @PostMapping(value = "/admin/user/create")
    public String createUserPage(Model model,
            @ModelAttribute("newUser") @Valid User user,
            BindingResult newUserBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        // Validate
        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>" + error.getField() + " - " +
                    error.getDefaultMessage());
        }
        if (newUserBindingResult.hasErrors()) {
            return "admin/user/create";
        }

        String avatar = uploadService.uploadFile(file, "avatar");
        String hassPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hassPassword);
        user.setAvatar(avatar);
        user.setRole(userService.getRoleByName(user.getRole().getName()));
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