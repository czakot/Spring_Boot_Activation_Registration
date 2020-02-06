package com.sec.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sec.entity.User;
import com.sec.service.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Qualifier("UserServiceImpl") // not nessessary, UserService unique here
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    @Autowired
    ApplicationContext ctx;
    

    @PostMapping("/adminreg")
//    @ResponseBody
    public String adminRegistration(@RequestParam Map<String, String> adminParams) throws Exception {
        log.info(adminParams.entrySet().toString());
        System.out.println("HttpSecurity exists: " + ctx.containsBean("httpSecurity"));
        System.out.println("AuthenticationManagerBuilder exists: " + ctx.containsBean("authenticationManagerBuilder"));
        System.out.println("ViewControllerRegistry exists: " + ctx.containsBean("viewControllerRegistry"));

        return "auth/login";
    }

    @RequestMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

//	@RequestMapping(value = "/reg", method = RequestMethod.POST)
    @PostMapping("/reg")
    public String reg(@ModelAttribute User userToRegister, Model model) {
        String result = userService.registerUser(userToRegister);
        if (result.equals("already_exists")) {
            User newUser = new User();
            newUser.setEmail(userToRegister.getEmail());
            model.addAttribute("user", newUser);
            model.addAttribute("result", result);
            return "registration";
        }
        if (result.equals("registered")) {
            model.addAttribute("result", result);
        }
        return "auth/login";
    }

    @RequestMapping(path = "/activation/{code}", method = RequestMethod.GET)
//	    public String activation(@PathVariable("code") String code, HttpServletResponse response) {
    public String activation(@PathVariable("code") String code, Model model) {
        String result = userService.userActivation(code);
        if (result.equals("activated")) {
            model.addAttribute("result", result);
        }
        return "auth/login";
    }

}
