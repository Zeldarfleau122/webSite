package com.services;

import java.util.List;
import java.util.Objects;

/**
 * Structure de donnee pour une note.
 */
public class Note {
    public int id ;                // Identifiant d'une note. ( Unique )
    public String content;         // Contenu.
    public List<Collaborateur> collaborateurs ;

    /**
     * Constructeur.
     *
     * @param content Contenu de la note.
     */
    public Note(String content) {
        this.content = content;
    }

    /**
     * Constructeur.
     *
     * @param content Contenu de la note.
     */
    public Note(String content, List<Collaborateur> collaborateurs) {
        this.content = content;
        this.collaborateurs = collaborateurs ;
    }

    /**
     * Constructeur a partir d'un identifiant et d'un contenu.
     *
     * @param ID Identifiant de la note.
     * @param content Contenu de la note.
     */
    public Note(int ID, String content) {
        this.id = ID ;
        this.content = content ;
    }

    /**
     * Redefinition de l'egalite de deux notes.
     *
     * @param o Note a comparer avec l'appelante.
     *
     * @return Renvoie 1 si egales, 0 sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(content, note.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    /**
     * Accesseur au contenu d'une note.
     *
     * @return Content
     */
    public String getContent() {
        return content;
    }

    /**
     * Setteur pour le contenu d'une note.
     *
     * @param content Nouveau contenu.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Accesseur pour l'identifiant d'une note.
     *
     * @return ID
     */
    public int getID() {
        return id;
    }

    public void setCollaborateurs(List<Collaborateur> collaborateurs) {
        this.collaborateurs = collaborateurs ;
    }
}
