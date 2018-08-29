package com.services;

import com.persistence.JdbcChallengeRepository;

import java.util.List;

public class ChallengeService {
    private JdbcChallengeRepository challengeRepository; // Lien avec la couche persistence des données.

    /**
     * Constructeur
     *
     * @param challengeRepository Lien avec la BD
     */
    public ChallengeService(JdbcChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    /**
     * Demande de recuperation de l'ensemble des collaborateurs d'un utilisateur.
     *
     * @return Renvoie une liste de l'ensemble des collaborateurs de l'utilisateur.
     */
    public List<Challenge> fetchAll() {
        return challengeRepository.findAll();
    }
}
