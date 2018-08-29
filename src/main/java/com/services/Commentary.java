package com.services;

/**
 * Structure d'un commentaire.
 */
public class Commentary {
    public String content ;         // Contenu du commentaire
    public String author ;          // Auteur
    private int id_Note ;           // Note a laquel il se rapporte

    /**
     * Constructeur
     * @param content Contenu
     * @param author Auteur
     */
    public Commentary(String content, String author) {
        this.content = content ;
        this.author = author ;
    }

    /**
     * Constructeur
     *
     * @param content Contenu
     * @param author Auteur
     * @param id_Note Identifiant de la note
     */
    public Commentary(String content, String author, int id_Note) {
        this.content = content ;
        this.author = author ;
        this.id_Note = id_Note ;
    }


    public String getContent() {
        return this.content ;
    }

    public String getAuthor() {
        return this.author ;
    }

    public int getIdNote() {
        return this.id_Note ;
    }
}
