package com.persistence;

import com.services.Commentary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Lien a la base de donnée, en lien avec les commentaires. ( a l'aide de JDBC )
 */
public class JdbcCommentaryRepository implements CommentaryRepository {
    JdbcTemplate jdbcTemplate ;                 // Connection a la BD.

    /**
     * Constructeur.
     *
     * @param jdbcTemplate Connection a la BD.
     */
    public JdbcCommentaryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate ;
    }

    /**
     * Ajout d'un commentaire sur une note, avec une requete INSERT sur la table COMMENTARY
     *
     * @param commentary Commentaire a ssauvegarder.
     * @param userName Autheur du commentaire.
     */
    @Override
    public void save(Commentary commentary, String userName) {
        String query = "INSERT INTO COMMENTARY (CONTENT, username, ID_NOTE)VALUES(?,?,?)" ;
        jdbcTemplate.update(query, commentary.getContent(), userName, commentary.getIdNote());
    }

    /**
     * Recherche de l'ensemble des commentaires pour une note donnée, requete SELECT.
     *
     * @param id_Note Identifiant de la note.
     *
     * @return Renvoie une liste de l'ensemble des commentaires sur cette note.
     */
    @Override
    public List<Commentary> findAll(int id_Note) {
        String query = "SELECT * FROM COMMENTARY WHERE ID_NOTE = '" + id_Note + "' ;";
        return jdbcTemplate.query(query, new NoteMapper());
    }

    /**
     * Creation des commentaires à partir du resultat d'une requete SELECT.
     */
    class NoteMapper implements RowMapper<Commentary> {

        @Override
        public Commentary mapRow(ResultSet rs, int rowNum) throws SQLException {
            String content = rs.getString("CONTENT");
            String username = rs.getString("username") ;
            return new Commentary(content, username);
        }
    }
}
