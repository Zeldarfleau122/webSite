package com.services;

import com.persistence.NoteRepository;

import java.util.List;

/**
 * Service de lecture de l'espace de stockage.
 */
public class FeedService {
    private NoteRepository noteRepository;          // Interface de communication avec la couche Persistence.

    /**
     * Constructeur.
     *
     * @param noteRepository Interface de communication
     */
    public FeedService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    /**
     * Demande de recuperation de l'ensemble des notes sauvegardees.
     *
     * @return Liste des notes sauvegardees.
     */
    public List<Note> fetchAll(String userName) {
        return noteRepository.findAll(userName);
    }

    public List<Note> find(int id) {return noteRepository.find(id);}

    public List<Note> fetchAllCollaborationNote(String username) {
        return noteRepository.findAllCollaborationNote(username) ;
    }
}
