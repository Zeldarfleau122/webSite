package com.persistence;

import com.services.Collaborateur;

import java.util.List;

/**
 * Interface decrivant les actions possibles concernant les Collaborateurs.
 */
public interface CollaborateurRepository {
    /**
     * Recuperation de l'ensemble des collaborateurs d'un utilisateur, par une requete sur la base de donnée
     *
     * @param username Nom d'utilisateur du demandeur.
     *
     * @return Renvoie une liste de l'ensmble des collaborateurs de l'utilisateur.
     */
    List<Collaborateur> findAll(String username);

    /**
     * Rajoute un collaborateur pour un utilisateur donnée, requete insert dans la base de odonnée.
     *
     * @param collaborateur Nom du collaborateur à rajouter.
     * @param demandeur Utilisateur a l'origine de la requete.
     */
    void save(Collaborateur collaborateur, String demandeur);
}
