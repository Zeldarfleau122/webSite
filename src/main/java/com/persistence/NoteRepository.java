package com.persistence;

import com.services.Collaborateur;
import com.services.Note;

import java.util.List;

/**
 * Interface de communication avec la base de donnee.
 */
public interface NoteRepository {
    /**
     * Recuperation de l'ensemble des notes contenues dans la BD.
     *
     * @return Renvoie la liste des notes connues.
     */
    List<Note> findAll(String userName);

    /**
     * Sauvegarde une note dans la base de donnee.
     *
     * @param note Note a sauvegarder.
     */
    void save(Note note, String userName);

    /**
     * Supprime une note a partir de son identifiant.
     *
     * @param ID Identifiant de la note a supprimer.
     */
    void remove(int ID, String userName) ;

    /**
     * Modification d'une note.
     *
     * @param id identification  de la note a modifier.
     * @param content Nouveau contenu de la note.
     */
    void update(int id, String content, String userName);

    List<Note> find(int id) ;

    void addCollaborateur(int id, Collaborateur collaborateur, String demandeur);

    List<Note> findAllCollaborationNote(String username);
}
