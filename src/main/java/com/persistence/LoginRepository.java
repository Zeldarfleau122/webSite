package com.persistence;

/**
 * Description des fonctions possible pour le Login
 */
public interface LoginRepository {
    /**
     * Ajout d'un utilisateur.
     *
     * @param userName Nom d'utilisateur
     * @param password Password encode.
     */
    void addUser(String userName, String password);
}
