package com.persistence;

import com.services.Collaborateur;
import com.services.Note;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Communication avec une base de donnee SQL (via JDBC)
 */
public class JdbcNoteRepository implements NoteRepository, LoginRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcNoteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate ;
    }

    /**
     * Sauvegarde dans la base de donnee : insertion SQL
     *
     * @param note Note a sauvegarder.
     */
    public void save(Note note, String userName) {
        String query = "INSERT INTO NOTE (TITLE, CONTENT, username)VALUES(?,?, ?)" ;
        jdbcTemplate.update(query, note.getTitle(), note.getContent(), userName);
    }

    /**
     * Suppression d'une note de la base de donnee : DELETE sql
     *
     * @param ID Identifiant de la note a supprimer.
     */
    @Override
    public void remove(int ID, String userName) {
        String query = "DELETE FROM NOTE WHERE ID=? AND username=?";
        jdbcTemplate.update(query, ID, userName) ;
    }

    /**
     * Modification d'une note donnee : UPDATE sql
     *
     * @param id identification  de la note a modifier.
     * @param content Nouveau contenu de la note.
     */
    @Override
    public void update(int id, String content, String userName) {
        String query = "UPDATE NOTE SET CONTENT = ? WHERE ID = ? AND username = ? ;";
        jdbcTemplate.update(query, content, id, userName) ;
    }

    /**
     * Recupere l'ensemble des notes contenues dans la base de donnee via un simple "SELECT" sql.
     *
     * @return Liste des notes contenues dans la base de donne
     */
    public List<Note> findAll(String userName) {
        String query = "SELECT * FROM NOTE WHERE username = '" + userName + "' ;";
        List<Note> notes = jdbcTemplate.query(query, new NoteMapper());

        for (int i=0; i<notes.size(); i++) {
            Note n = notes.get(i) ;

            List<Collaborateur> collaborateurs ;

            query = "SELECT * FROM NOTEINCOLLAB WHERE ID_NOTE = '" + n.id + "' ;";
            collaborateurs = jdbcTemplate.query(query, new CollabMapper()) ;

            n.setCollaborateurs(collaborateurs);
        }

        return notes ;
    }

    class NoteMapper implements RowMapper<Note> {

        @Override
        public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
            String title = rs.getString("TITLE");
            String content = rs.getString("CONTENT");
            int id = rs.getInt("ID") ;
            return new Note(id, title, content);
        }
    }

    @Override
    public void addUser(String userName, String password) {
        String query;

        query = "INSERT INTO USERS VALUES(?, ?, true);";
        jdbcTemplate.update(query, userName, password) ;

        query = "INSERT INTO AUTHORITIES VALUES(?, 'USER');";
        jdbcTemplate.update(query, userName) ;
    }

    public List<Note> find(int id) {
        String query = "SELECT * FROM NOTE WHERE ID = '" + id + "' ;";
        List<Note> notes = jdbcTemplate.query(query, new NoteMapper());

        List<Collaborateur> collaborateurs ;
        for (Note n : notes) {
            query = "SELECT * FROM NOTEINCOLLAB WHERE ID_NOTE = '" + id + "' ;";
            collaborateurs = jdbcTemplate.query(query, new CollabMapper()) ;

            n.setCollaborateurs(collaborateurs);
        }

        return notes ;
    }

    @Override
    public void addCollaborateur(int id, Collaborateur collaborateur, String demandeur) {
        String query =  "SELECT count(*) FROM COLLABORATEUR WHERE (CollaborateurA = ? AND CollaborateurB = ?) OR (CollaborateurA = ? AND CollaborateurB = ?);" ;
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, collaborateur.name, demandeur, demandeur, collaborateur.name);

        boolean resulta = (count > 0) ? true : false ;

        query =  "SELECT count(*) FROM NOTEINCOLLAB WHERE (ID_NOTE=? AND Collaborateur=?) ;" ;
        count = jdbcTemplate.queryForObject(query, Integer.class, id, collaborateur.name);

        boolean resultb = (count > 0) ? false : true ;

        if (resulta && resultb) {
            query = "INSERT INTO NOTEINCOLLAB (ID_NOTE, Collaborateur)VALUES(?,?)";
            jdbcTemplate.update(query, id, collaborateur.name);
        }
    }

    @Override
    public List<Note> findAllCollaborationNote(String username) {
        String query = "SELECT NOTE.ID, NOTE.CONTENT FROM NOTE JOIN NOTEINCOLLAB ON NOTE.ID=NOTEINCOLLAB.ID_NOTE WHERE NOTEINCOLLAB.Collaborateur = '" + username + "' ;";
        List<Note> notes = jdbcTemplate.query(query, new NoteMapper());

        List<Collaborateur> collaborateurs ;
        for (Note n : notes) {
            query = "SELECT * FROM NOTEINCOLLAB WHERE ID_NOTE = '" + n.id + "' ;";
            collaborateurs = jdbcTemplate.query(query, new CollabMapper()) ;

            n.setCollaborateurs(collaborateurs);

            collaborateurs = null ;
        }

        return notes ;
    }

    class CollabMapper implements RowMapper<Collaborateur> {

        @Override
        public Collaborateur mapRow(ResultSet rs, int rowNum) throws SQLException {
            String name = rs.getString("Collaborateur");
            return new Collaborateur(name);
        }
    }
}
