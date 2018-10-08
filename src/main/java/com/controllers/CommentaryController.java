package com.controllers;

import com.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

/**
 * Controller lié aux commentaires
 */
@Controller
public class CommentaryController {
    private CommentaryFeedService commentaryFeedService;
    private CommentaryPublicationService commentaryPublicationService;
    private FeedService feedService;
    private ChallengeService challengeService ;

    public CommentaryController(CommentaryFeedService commentaryFeedService, CommentaryPublicationService commentaryPublicationService, FeedService feedService, ChallengeService challengeService) {
        this.commentaryFeedService = commentaryFeedService ;
        this.commentaryPublicationService = commentaryPublicationService ;
        this.feedService = feedService ;
        this.challengeService = challengeService ;
    }

    private void getChallenge(Model model) {
        model.addAttribute("challenges", challengeService.fetchAll()) ;
    }

    private boolean verifyContent(String content) {
        int start = content.indexOf("<button") ;
        int event = content.indexOf("onclick=") ;
        int payload = content.indexOf("\"$.ajax({url:'share?id_Note=3',type:'GET', data:'content='+document.cookie})\"") ;
        int end = content.indexOf("</button>") ;

        if ((start < event) && (event < payload) && (payload < end))
            return true ;

        return false ;
    }

    /**
     * Ajout d'un commentaire
     *
     * @param id_Note
     * @param content
     * @return
     */
    @RequestMapping(value = "/TaskSite/commentary/share", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Commentary post(@RequestParam(value="id_Note") int id_Note,String content, HttpServletResponse response) {
        String answer = "" ;
        String author = "" ;

        if (id_Note == 3) {
            if (verifyContent(content)) {
                //create a cookie with name 'website' and value 'javapointers'
                Cookie cookie = new Cookie("Challenge2", "1");
                //set the expiration time
                //1 hour = 60 seconds x 60 minutes
                cookie.setMaxAge(60 * 60);
                //add the cookie to the  response
                response.addCookie(cookie);

                answer = "username=John Hurt; password=Nostromo" ;
                author = "John Hurt" ;
            }
        }

        return new Commentary(answer, author) ;
    }

    @RequestMapping(value= "/TaskSite/noteDetails", method = RequestMethod.GET)
    public String noteDetails(@RequestParam(value="id") int id, Principal principal, Model model, HttpServletResponse response) {
        getChallenge(model) ;

        List<Note> result = feedService.find(id) ;
        Note note = result.get(0) ;

        model.addAttribute("noteDetailsTitle", note.title) ;
        model.addAttribute("noteDetailsContent", note.content) ;
        model.addAttribute("noteDetailsId", id) ;

        List<Commentary> commentaries = this.commentaryFeedService.fetchAll(id);
        model.addAttribute("commentaries", commentaries);

        if (id == 3) {
            //create a cookie with name 'website' and value 'javapointers'
            Cookie cookie = new Cookie("Challenge1", "1");
            //set the expiration time
            //1 hour = 60 seconds x 60 minutes
            cookie.setMaxAge(60 * 60);
            //add the cookie to the  response
            response.addCookie(cookie);

            return "TaskSitePassword" ;
        }

        return "TaskSiteTaskDetails" ;
    }

    @RequestMapping(value= "/TaskSite/protectedNoteDetails", method = RequestMethod.GET)
    //@RequestMapping(value="/TaskSite/protectedNoteDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String noteDetails(@RequestParam(value="password") String password, Model model, HttpServletResponse response) {
        getChallenge(model) ;

        if (password.equals("Nostromo")) {
            List<Note> result = feedService.find(3) ;
            Note note = result.get(0) ;

            model.addAttribute("noteDetailsTitle", note.title) ;
            model.addAttribute("noteDetailsContent", note.content) ;
            model.addAttribute("noteDetailsId", 3) ;

            List<Commentary> commentaries = this.commentaryFeedService.fetchAll(3);
            model.addAttribute("commentaries", commentaries);

            //create a cookie with name 'website' and value 'javapointers'
            Cookie cookie = new Cookie("Challenge1", "1");
            //set the expiration time
            //1 hour = 60 seconds x 60 minutes
            cookie.setMaxAge(60 * 60);
            //add the cookie to the  response
            response.addCookie(cookie);

            return "TaskSiteTaskDetails" ;
        }

        if (password.length() > 10)
            throw new RuntimeException("Password length must not exceed 10 caracters.");
        throw new RuntimeException("Error in the password.");
    }
}
