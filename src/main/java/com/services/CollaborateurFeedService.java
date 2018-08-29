package com.services;

import com.persistence.CollaborateurRepository;
import com.services.Collaborateur;

import java.util.List;

/**
 * Service de lecture des collaborateurs.
 */
public class CollaborateurFeedService {
    private CollaborateurRepository collaborateurRepository; // Lien avec la couche persistence des données.

    /**
     * Constructeur
     *
     * @param collaborateurRepository Lien avec la BD
     */
    public CollaborateurFeedService(CollaborateurRepository collaborateurRepository) {
        this.collaborateurRepository = collaborateurRepository;
    }

    /**
     * Demande de recuperation de l'ensemble des collaborateurs d'un utilisateur.
     *
     * @param username Utilisateur a l'origine de la demande.
     *
     * @return Renvoie une liste de l'ensemble des collaborateurs de l'utilisateur.
     */
    public List<Collaborateur> fetchAll(String username) {
        return collaborateurRepository.findAll(username);
    }
}
