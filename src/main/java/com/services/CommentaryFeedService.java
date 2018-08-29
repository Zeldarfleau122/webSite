package com.services;

import com.persistence.CommentaryRepository;

import java.util.List;

/**
 * Service de lecture des commentaires.
 */
public class CommentaryFeedService {
    private CommentaryRepository commentaryRepository;

    public CommentaryFeedService(CommentaryRepository commentaryRepository) {
        this.commentaryRepository = commentaryRepository;
    }

    /**
     * Recuperation de l'ensemble des commentaires d'une note.
     *
     * @param id_Note Identifiant de la ntoe.
     *
     * @return Liste des commentaires sur cette note.
     */
    public List<Commentary> fetchAll(int id_Note) {
        return commentaryRepository.findAll(id_Note);
    }
}
