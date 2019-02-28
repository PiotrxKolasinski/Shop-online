package com.onlineshop.controller;

import com.onlineshop.format.SupportFormat;
import com.onlineshop.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.logging.Logger;

@Controller
public class InformationController {
    private final EmailService emailService;
    private final Environment env;

    @Autowired
    public InformationController(EmailService emailService, Environment env) {
        this.emailService = emailService;
        this.env = env;
    }

    @RequestMapping(value = "/contact")
    public String showContact() {
        return "contact";
    }

    @RequestMapping(value = "/terms")
    public String showTerms() {
        return "terms";
    }

    @RequestMapping(value = "/support")
    public String showSupport(Model model) {

        model.addAttribute("supportFormat", new SupportFormat());

        return "support";
    }

    @RequestMapping(value = "/askForProduct/{name}", method = RequestMethod.POST)
    public String askForProduct(@PathVariable("name") String name, Model model) {

        SupportFormat supportFormat = new SupportFormat();
        supportFormat.setSubject(name);

        model.addAttribute("supportFormat", supportFormat);

        return "support";
    }

    @RequestMapping(value = "/emailSent", method = RequestMethod.POST)
    public String send(@ModelAttribute @Valid SupportFormat supportFormat, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "support";
        }

        emailService.sendEmail(env.getProperty("spring.mail.username"), supportFormat.getSubject(), "Email from "
                + supportFormat.getEmail() + "\n\nMessage:\n\n" + supportFormat.getMessage());

        emailService.sendEmail(supportFormat.getEmail(), supportFormat.getSubject(), "Your application has been accepted!");

        return "redirect:/support";
    }
}
