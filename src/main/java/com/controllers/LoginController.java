package com.controllers;

import com.services.FeedService;
import com.services.LoginService;
import com.services.PublicationService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller du profil
 */
@Controller
public class LoginController {
    private LoginService loginService;                        // Service de lecture de la base de donnee

    public LoginController(LoginService loginService) {
        this.loginService = loginService ;
    }

    /**
     * Page de login personnalisé
     *
     * @return page du login
     */
    @RequestMapping(value = "/TaskSite/login", method = RequestMethod.GET)
    public String loginPage() {
        return "TaskSiteLogin";
    }

    /**
     * Page de register personnalisé
     *
     * @return page du register
     */
    @RequestMapping(value = "/TaskSite/register", method = RequestMethod.GET)
    public String registerPage() {
        return "TaskSiteRegister";
    }

    /**
     * Rajout d'un utilisateur ( register )
     */
    @RequestMapping(value = "/TaskSite/register/addUser", method = RequestMethod.POST)
    public String addUser(String username, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        loginService.addUser(username, hashedPassword) ;
        return "redirect:/TaskSite/login" ;
    }
}
