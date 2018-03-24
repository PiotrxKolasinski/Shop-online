package com.onlineshop.controller;

import com.onlineshop.entity.Address;
import com.onlineshop.entity.Authorities;
import com.onlineshop.format.CrmUser;
import com.onlineshop.entity.Customer;
import com.onlineshop.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

@Controller
public class SecurityController {

    private UserDetailsManager userDetailsManager;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private CustomerService customerService;

    @Autowired
    public SecurityController(UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @RequestMapping(value = "/login")
    public String showLogin(){
        return "login";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping(value = "/registration")
    public String showRegistration(Model model){
        model.addAttribute("CrmUser", new CrmUser());
        return "registration";
    }

    @RequestMapping(value = "/processRegistration", method = RequestMethod.POST)
    public String processRegistration(@Valid @ModelAttribute("CrmUser") CrmUser crmUser,
                                      BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "registration";
        }

        String username = crmUser.getUsername().toLowerCase();

        String email = crmUser.getEmail();

        boolean userExists = customerService.userExists(username);
        boolean emailExists = customerService.emailExists(email);
        boolean confirmPass = !crmUser.getPassword().equals(crmUser.getConfirmPassword());

        Map<String, Boolean> falseForm = new HashMap<>();

        falseForm.put("usernameExists", userExists);
        falseForm.put("emailExists", emailExists);
        falseForm.put("pass", confirmPass);

        if(userExists || emailExists || confirmPass){

            Set<String> key = falseForm.keySet();

            String redirectName = "redirect:/registration?";

            for(String temp : key){
                if(falseForm.get(temp)) redirectName = redirectName +temp +"&";
            }

            redirectName = redirectName.substring(0, redirectName.length()-1);
            System.out.println(redirectName);
            return redirectName;
        }

        String encodedPassword = passwordEncoder.encode(crmUser.getPassword());
        encodedPassword ="{bcrypt}"+encodedPassword;
        String auth1 = "ROLE_USER";

        Customer customer = new Customer(username, encodedPassword, 1, email);
        //Address address = new Address();

       // customer.setAddress(address);

        Authorities authorities = new Authorities(auth1);
        authorities.setCustomerId(customer);

        customerService.registerCustomer(authorities);

        logger.info("Add new user to database");

        return "redirect:/registration?success";
    }
}
