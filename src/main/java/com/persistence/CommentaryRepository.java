package com.persistence;

import com.services.Commentary;
import com.services.Note;

import java.util.List;

/**
 * Interface décrivant l'ensemble des actions possibles concernant les commentaires.
 */
public interface CommentaryRepository {
        /**
         * Sauvegarde d'un commentaire dans la base de donnée.
         *
         * @param commentary Commentaire a ssauvegarder.
         * @param userName Autheur du commentaire.
         */
        void save(Commentary commentary, String userName);

        /**
         * Recuperation de l'ensemble des commentaires liées a une note.
         *
         * @param id_Note Identifiant de la note.
         * @return Une liste des commentaires sur cette note.
         */
        List<Commentary> findAll(int id_Note);
}