package eu.autorank.carsales.controller;

import eu.autorank.carsales.dto.RegistrationForm;
import eu.autorank.carsales.model.User;
import eu.autorank.carsales.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showRegistrationForm(@ModelAttribute("form") RegistrationForm form, @AuthenticationPrincipal User user){
        if(user != null){
            return "redirect:/cars";
        }

        return "register-user";
    }

    @PostMapping
    public String processRegistrationForm(@ModelAttribute("form") @Validated RegistrationForm form, BindingResult bindingResult, RedirectAttributes redAttrs){
        if(bindingResult.hasErrors()){
            return "register-user";
        }

        if(userRepository.findUserByUsername(form.getUsername()) != null){
            redAttrs.addFlashAttribute("form", form);
            redAttrs.addFlashAttribute("userError", "Username already exists");

            return "redirect:/register";
        }

        userRepository.save(form.toUser(passwordEncoder));

        return "redirect:/login";
    }
}
