package com.services;

import com.persistence.NoteRepository;

import java.security.Principal;

/**
 * Service d'ecriture sur la base de donnee.
 */
public class PublicationService {
    private NoteRepository noteRepository;          // Interface de communication avec la couche Persistence

    /**
     * Constructeur.
     *
     * @param noteRepository Interface de communication.
     */
    public PublicationService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    /**
     * Mise en forme et envoie de la demande de la sauvegarde d'une note.
     *
     * @param contenu Contenu de la note a sauvegarder.
     */
    public void post(String contenu, String userName) {
        this.noteRepository.save(new Note(contenu), userName) ;           // Creation d'une note et demande de sauvegarde.
    }

    /**
     * Demande de suppression d'une note a partir de son identifiant.
     *
     * @param ID Identifiant de la note a sauvegarder.
     */
    public void remove(int ID, String userName) {
        this.noteRepository.remove(ID, userName) ;
    }

    /**
     * Modification du contenu d'une note.
     *
     * @param id Identifiant de la note a modifier.
     * @param content Nouveau contenu de la note.
     */
    public void update(int id, String content, String userName) {
        noteRepository.update(id, content, userName);
    }

    /**
     * Envoie de la demande de la sauvegarde d'une note.
     *
     * @param note Note a sauvegarder.
     */
    public void post(Note note, String userName) {
        noteRepository.save(note, userName);
    }

    public void addCollaborateur(int id, String collaborateur, String demandeur) {
        noteRepository.addCollaborateur(id, new Collaborateur(collaborateur), demandeur) ;
    }
}
