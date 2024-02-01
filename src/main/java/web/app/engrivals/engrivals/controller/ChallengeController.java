package web.app.engrivals.engrivals.controller;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.app.engrivals.engrivals.persistance.entities.Challenge;
import web.app.engrivals.engrivals.persistance.entities.QuestionN;
import web.app.engrivals.engrivals.service.ChallengeService;

@RestController
@RequestMapping("/v1/api/challenges")
@CrossOrigin("*")
public class ChallengeController {
    @Autowired
    ChallengeService challengeService;
    
    @PostMapping("/{category_id}/{english_level_id}")
    public Challenge create(@PathVariable("category_id") Integer category_id, @PathVariable("english_level_id") Integer english_level_id, @RequestBody HashMap<String, Boolean> conditions) {
        return challengeService.create(category_id, english_level_id, conditions.get("isTheBrowserCompatibleWithAudio"));
    }
    
    @GetMapping("/{challenge_id}")
    public Challenge findById(@PathVariable("challenge_id") String id) {
        return challengeService.findById(id);
    }
    
    @PutMapping("/{challenge_id}")
    public Challenge receiveAnswer(@PathVariable("challenge_id") String challengeId, @RequestBody QuestionN questionN) {
        return challengeService.receiveAnswer(challengeId, questionN);
    }
}
