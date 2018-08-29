package com.services;

import com.persistence.CommentaryRepository;

/**
 * Service d'ecriture des commentaires sur la base de donnée.
 */
public class CommentaryPublicationService {
    private CommentaryRepository commentaryRepository;

    public CommentaryPublicationService(CommentaryRepository commentaryRepository) {
        this.commentaryRepository = commentaryRepository;
    }

    /**
     * Demande d'ecriture d'un commentaire sur une note.
     *
     * @param id_Note Identifiant de la note.
     * @param content Contenu du commentaire.
     * @param userName Auteur du commentaire.
     */
    public void post(int id_Note, String content, String userName) {
        commentaryRepository.save(new Commentary(content, userName, id_Note), userName);
    }
}
