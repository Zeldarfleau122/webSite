package com.controllers;

import com.services.ChallengeService;
import com.services.Collaborateur;
import com.services.CollaborateurFeedService;
import com.services.CollaborateurPublicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

/**
 * Controller lié au profil.
 */
@Controller
public class ProfilController {

    private CollaborateurFeedService collaborateurFeedService ;
    private CollaborateurPublicationService collaborateurPublicationService ;
    private ChallengeService challengeService ;

    public ProfilController(CollaborateurFeedService collaborateurFeedService, CollaborateurPublicationService collaborateurPublicationService, ChallengeService challengeService) {
        this.collaborateurFeedService = collaborateurFeedService ;
        this.collaborateurPublicationService = collaborateurPublicationService ;
        this.challengeService = challengeService ;
    }

    private void getChallenge(Model model) {
        model.addAttribute("challenges", challengeService.fetchAll()) ;
    }

    @RequestMapping(value = "/TaskSite/profil", method = RequestMethod.GET)
    public String modify(Principal principal, Model model) {
        getChallenge(model) ;

        model.addAttribute("username", principal.getName()) ;

        List<Collaborateur> ensCollab = collaborateurFeedService.fetchAll(principal.getName()) ;

        model.addAttribute("collaborateurs", ensCollab) ;

        return "TaskSiteProfil" ;
    }

    @RequestMapping(value = "/TaskSite/profil/addCollaborateur", method = RequestMethod.POST)
    public String addCollaborateur(@RequestParam(value="demandeur") String demandeur, String collaborateur, Principal principal, Model model) {
        collaborateurPublicationService.post(principal.getName(), collaborateur) ;

        System.out.println(demandeur + " " + collaborateur) ;

        return "redirect:/TaskSite/profil" ;
    }
}
