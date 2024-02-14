package web.app.engrivals.engrivals.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import web.app.engrivals.engrivals.Dtos.ChallengeStatus;
import web.app.engrivals.engrivals.persistance.entities.Challenge;
import web.app.engrivals.engrivals.persistance.entities.Player;
import web.app.engrivals.engrivals.persistance.entities.QuestionN;
import web.app.engrivals.engrivals.service.ChallengeService;

@Controller
@CrossOrigin("*")
@RequestMapping("/v1/api/challenges")
public class ChallengeController {
    @Autowired
    ChallengeService challengeService;
    
    @Autowired
    private MessageSendingOperations<String> messagingTemplate;
    
    @GetMapping
    @ResponseBody
    public List<ChallengeStatus> getChallenges() {
        return challengeService.getChallenges();
    }
    
    @MessageMapping("/join/{challengeId}/{userId}")
    public void joinARoom(@DestinationVariable("challengeId") String challengeId,
            @DestinationVariable("userId") String userId) {
        Challenge challenge = challengeService.joinARoom(challengeId, userId);
        
        if (challenge == null) {
            messagingTemplate.convertAndSend("/rooms/join/" + userId, Collections.singletonMap("status", "La partida fue borrada o es tu partida."));
            return;
        }
        
        System.out.println("CHALLENGES: " + challengeService.getChallenges());
        messagingTemplate.convertAndSend("/rooms", challengeService.getChallenges());
        messagingTemplate.convertAndSend("/rooms/join/" + challenge.getPlayers().get(0).getUserId(), challenge);
        messagingTemplate.convertAndSend("/rooms/join/" + userId, challenge);
    }
    
    @MessageMapping("/create-room/{category_id}/{english_level_id}/{userId}")
    @SendTo("/rooms")
    public List<ChallengeStatus> createChallenge(@DestinationVariable("category_id") Integer category_id, 
                                    @DestinationVariable("english_level_id") Integer english_level_id, 
                                    @DestinationVariable("userId") String userId,
                                    @Payload HashMap<String, Boolean> conditions) {
        challengeService.create(category_id, english_level_id, userId, conditions.get("isTheBrowserCompatibleWithAudio"));
        System.out.println("CHALLENGES : " + challengeService.getChallenges());
        return challengeService.getChallenges();
    }
    
    @MessageMapping("/results/{challenge_id}")
    public void findById(@DestinationVariable("challenge_id") String id) {
        messagingTemplate.convertAndSend("/rooms/results/" + id, challengeService.findById(id));
    }
    
    @MessageMapping("/save-user-answer/{challengeId}/{userId}")
    public void receiveAnswer(@DestinationVariable("challengeId") String challengeId, @DestinationVariable("userId") String userId, @Payload QuestionN questionN) {
        Challenge challenge = challengeService.receiveAnswer(challengeId, questionN, userId);
        
        for (Player player : challenge.getPlayers()) {
            messagingTemplate.convertAndSend("/rooms/game/" + player.getUserId(), challenge);
        }
        messagingTemplate.convertAndSend("/rooms/results/" + challengeId, challenge);
    }
    
    @MessageMapping("/end-game/{challengeId}/{userId}")
    public void endGame(@DestinationVariable("challengeId") String challengeId, 
            @DestinationVariable("userId") String userId) {
        Challenge challenge = challengeService.endGame(challengeId, userId);
        messagingTemplate.convertAndSend("/rooms/game/" + userId, challenge);
        messagingTemplate.convertAndSend("/rooms/results/" + challengeId, challenge);
    }
    
    
    @GetMapping("/{challengeId}")
    @ResponseBody
    public Challenge findByChallengeId(@PathVariable("challengeId") String challengeId) {
        return challengeService.findById(challengeId);
    }
    
    /*
    @PutMapping("/{challenge_id}")
    public Challenge receiveAnswer(@PathVariable("challenge_id") String challengeId, @RequestBody QuestionN questionN) {
        return challengeService.receiveAnswer(challengeId, questionN);
    }
    
    @GetMapping
    public List<ChallengeStatus> getChallenges() {
        return challengeService.getChallenges();
    }
    */
}
