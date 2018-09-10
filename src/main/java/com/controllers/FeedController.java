package com.controllers;


import com.services.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

/**
 * Controller contenant l'ensemble des actions possibles pour un utilisateur.
 */
@Controller
public class FeedController {
    private PublicationService publicationService;          // Service d'ecriture sur la base de donnee
    private FeedService feedService;                        // Service de lecture de la base de donnee
    private ChallengeService challengeService ;
    private MissionService missionService ;
    private boolean[] missionFinished ;

    /**
     * Constructeur
     *
     * @param publicationService Service d'ecriture sur la BD
     * @param feedService Service de lecture de la BD
     */
    public FeedController(PublicationService publicationService, FeedService feedService, ChallengeService challengeService) {
        this.publicationService = publicationService;
        this.feedService = feedService;
        this.challengeService = challengeService ;
    }

    private void getChallenge(Model model) {
        model.addAttribute("challenges", challengeService.fetchAll()) ;
    }

    @RequestMapping(value="/TaskSite/verifyLogin", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Boolean verifyLogin(String username, String password) {
        Boolean validate = false ;

        if ((username == "John Hurt") && (password == "Nostromo"))
            validate = true ;

        System.out.println((username == "John Hurt") && (password == "Nostromo")) ;

        return validate ;
    }

    @RequestMapping(value = "/TaskSite/index", method = RequestMethod.GET)
    public String index(Model model) {
        getChallenge(model) ;
        return "TaskSiteIndex";
    }

    /**
     * Demande de suppression d'une note a partir de son identifiant.
     *
     * @param id Identifiant de la note a supprimer.
     */
    @RequestMapping(value = "/TaskSite/task/remove", params="id", method = RequestMethod.GET)
    public String delete(@RequestParam("id") int id, Principal principal) {
        //publicationService.remove(id, principal.getName());
        return "redirect:/TaskSite/tasks";
    }

    /**
     * Demande de sauvegarde d'une note a partir d'un contenu saisi par l'utilisateur.
     *
     * @param content Contenu de la note.
     * @return Renvoie true si bon fonctionnement.
     */
    @RequestMapping(value = "/TaskSite/task/share", method = RequestMethod.POST)
    public String post(String title, String content, Principal principal) {
        publicationService.post(title, content, principal.getName());
        return "redirect:/TaskSite/tasks";
    }

    /**
     * Demande de modification d'une note avec un identifiant donne et le texte modifie.
     *
     * @param id Identifiant de la note a modifier.
     * @param content Nouveau contenu de la note.
     */
    @RequestMapping(value = "/TaskSite/task/update", params = { "id", "content" }, method = RequestMethod.POST)
    public String update(@RequestParam(value="id") int id,String content, Principal principal) {
        //publicationService.update(id, content, principal.getName());
        return "redirect:/TaskSite/tasks";
    }

    /**
     * Demande de modification d'une note avec un identifiant donne et le texte modifie.
     *
     * @param id Identifiant de la note a modifier.
     */
    @RequestMapping(value = "/TaskSite/task/modify", params = { "id"}, method = RequestMethod.GET)
    public String modify(@RequestParam(value="id") int id, Model model, Principal principal) {
        getChallenge(model) ;

        List<Note> notes = feedService.find(id) ;
        Note note = notes.get(0) ;

        model.addAttribute("toModifyContent", note.content) ;
        model.addAttribute("toModifyId", id) ;
        model.addAttribute("username", principal.getName()) ;
        return "TaskSiteTaskModification" ;
    }

    /**
     * Demande de recuperation de l'ensemble des notes.
     *
     * @return La page d'affichage des notes.
     */
    @RequestMapping(value = "/TaskSite/tasks", method = RequestMethod.GET)
    public String feed(Principal principal, Model model) {
        getChallenge(model) ;

        List<Note> notes = this.feedService.fetchAll(principal.getName());
        model.addAttribute("notes", notes);
        return "TaskSiteTasks";
    }

    @RequestMapping(value = "/TaskSite/tasks/addCollaborateur", method = RequestMethod.POST)
    public String addCollaborateur(@RequestParam(value="id") int id, String collaborateur, Principal principal, Model model) {
        //publicationService.addCollaborateur(id, collaborateur, principal.getName()) ;

        // HttpServletRequest request // System.out.println("IP : " + request.getRemoteAddr()) ;

        return "redirect:/TaskSite/task/modify?id="+id;
    }

    @RequestMapping(value = "/TaskSite/tasks/collaborationTasks", method = RequestMethod.GET)
    public String feedNotesInCollaboration(Principal principal, Model model) {
        getChallenge(model) ;

        List<Note> notesCollaboration = this.feedService.fetchAllCollaborationNote(principal.getName());
        model.addAttribute("notes", notesCollaboration);

        return "TaskSiteCollaborationTasks";
    }
}