package com.config;

import com.AssemblerEmulator.CPU;
import com.controllers.AssemblerAController;
import com.controllers.CommentaryController;
import com.controllers.FeedController;
import com.controllers.ProfilController;
import com.persistence.*;
import com.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class AppConfig {

    @Bean
    public CPU cpu() {
        return new CPU() ;
    }

    @Bean
    JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public CommentaryRepository commentaryRepository(JdbcTemplate jdbcTemplate) throws SQLException {
        return new JdbcCommentaryRepository(jdbcTemplate);
    }

    @Bean
    public NoteRepository storyRepository(JdbcTemplate jdbcTemplate) throws SQLException {
        return new JdbcNoteRepository(jdbcTemplate);
    }

    @Bean
    public CollaborateurRepository collaborateurRepository(JdbcTemplate jdbcTemplate) throws SQLException {
        return new JdbcCollaborateurRepository(jdbcTemplate);
    }

    @Bean
    public LoginRepository loginRepository(JdbcTemplate jdbcTemplate) throws SQLException {
        return new JdbcNoteRepository(jdbcTemplate);
    }

    @Bean
    public LoginService loginService(LoginRepository loginRepository) {
        return new LoginService(loginRepository);
    }

    @Bean
    public FeedService feedService(NoteRepository storyRepository) {
        return new FeedService(storyRepository);
    }

    @Bean
    public PublicationService publicationService(NoteRepository storyRepository) {
        return new PublicationService(storyRepository);
    }

    @Bean
    public ChallengeService challengeService(JdbcChallengeRepository jdbcChallengeRepository) {
        return new ChallengeService(jdbcChallengeRepository) ;
    }

    @Bean
    public FeedController feedController(PublicationService publicationService, FeedService feedService, ChallengeService challengeService) {
        return new FeedController(publicationService, feedService, challengeService);
    }

    @Bean
    public CommentaryFeedService commentaryFeedService(CommentaryRepository commentaryRepository) {
        return new CommentaryFeedService(commentaryRepository);
    }

    @Bean
    public CommentaryPublicationService commentaryPublicationService(CommentaryRepository commentaryRepository) {
        return new CommentaryPublicationService(commentaryRepository);
    }

    @Bean
    public CommentaryController commentaryController(CommentaryFeedService commentaryFeedService, CommentaryPublicationService commentaryPublicationService, FeedService feedService, ChallengeService challengeService) {
        return new CommentaryController(commentaryFeedService, commentaryPublicationService, feedService, challengeService);
    }

    @Bean
    public CollaborateurFeedService collaborateurFeedService(CollaborateurRepository collaborateurRepository) {
        return new CollaborateurFeedService(collaborateurRepository);
    }

    @Bean
    public CollaborateurPublicationService collaborateurPublicationService(CollaborateurRepository collaborateurRepository) {
        return new CollaborateurPublicationService(collaborateurRepository);
    }

    @Bean
    public ProfilController profilController(CollaborateurFeedService collaborateurFeedService, CollaborateurPublicationService collaborateurPublicationService, ChallengeService challengeService) {
        return new ProfilController(collaborateurFeedService, collaborateurPublicationService, challengeService) ;
    }

    @Bean
    public JdbcChallengeRepository jdbcChallengeRepository(JdbcTemplate jdbcTemplate) throws SQLException {
        return new JdbcChallengeRepository(jdbcTemplate) ;
    }

    @Bean
    public JdbcNoteRepository jdbcStoryRepository(JdbcTemplate jdbcTemplate) throws SQLException {
        return new JdbcNoteRepository(jdbcTemplate);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                //.addScripts("schema.sql")
                .build();
    }

    @Bean
    public AssemblerAController assemblerAController() {
        return new AssemblerAController() ;
    }
}
