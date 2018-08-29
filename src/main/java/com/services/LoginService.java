package com.services;

import com.persistence.LoginRepository;

/**
 * Service de creation d'un utilisateur
 */
public class LoginService {
    private LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository ;
    }

    /**
     * Demande de creation d'un utilisateur
     *
     * @param userName Nom utilisateur
     * @param password Mot de passe chiffre
     */
    public void addUser(String userName, String password) {
        loginRepository.addUser(userName, password) ;
    }
}
