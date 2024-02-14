package web.app.engrivals.engrivals.service;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import web.app.engrivals.engrivals.Dtos.ChallengeStatus;
import web.app.engrivals.engrivals.persistance.entities.Answer;
import web.app.engrivals.engrivals.persistance.entities.CategoryEntity;
import web.app.engrivals.engrivals.persistance.entities.Challenge;
import web.app.engrivals.engrivals.persistance.entities.EnglishLevel;
import web.app.engrivals.engrivals.persistance.entities.Option;
import web.app.engrivals.engrivals.persistance.entities.OptionN;
import web.app.engrivals.engrivals.persistance.entities.Player;
import web.app.engrivals.engrivals.persistance.entities.Question;
import web.app.engrivals.engrivals.persistance.entities.QuestionN;
import web.app.engrivals.engrivals.persistance.entities.UserEntity;
import web.app.engrivals.engrivals.persistance.repository.CategoryRepository;
import web.app.engrivals.engrivals.persistance.repository.ChallengeRepository;
import web.app.engrivals.engrivals.persistance.repository.LevelRepository;
import web.app.engrivals.engrivals.persistance.repository.OptionRepository;
import web.app.engrivals.engrivals.persistance.repository.QuestionRepository;
import web.app.engrivals.engrivals.persistance.repository.UserRepository;

@Service
public class ChallengeService {
    static List<ChallengeStatus> challenges = new ArrayList<>();
    
    @Autowired
    ChallengeRepository challengeRepository;
    
    @Autowired
    QuestionRepository questionRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    LevelRepository levelRepository;
    
    @Autowired
    CategoryRepository categoryRepository;
    
    public Challenge create(Integer categoryId, Integer levelId, String userId, Boolean isTheBrowserCompatibleWithAudio) {
        ArrayList<ChallengeStatus> prevUserChallenges = new ArrayList<>();
        
        for (ChallengeStatus cStatus : challenges) {
            if (cStatus.getCreatorId().equals(userId)) prevUserChallenges.add(cStatus);
        }
        for (ChallengeStatus cStatus : prevUserChallenges) challenges.remove(cStatus);
        
        Optional<CategoryEntity> category = categoryRepository.findById(categoryId);
        
        if (!category.isPresent()) {
            throw new EntityNotFoundException("No existe una categoria asociada a este id: " + categoryId);
        }

        Optional<EnglishLevel> level = levelRepository.findById(levelId);
        
        if (!level.isPresent()) {
            throw new EntityNotFoundException("No existe un nivel de inglés asociado a este id: " + levelId);
        }
        
        Challenge challenge = new Challenge();
        
        challenge.setTitle(level.get().getName() + ": " + category.get().getName());
        
        List<Question> questions;
        
        if (isTheBrowserCompatibleWithAudio) {
            questions = questionRepository.findByCategoryEnglishLevel(categoryId, levelId);
        } else {
            questions = questionRepository.findByCategoryEnglishLevelWithoutAudio(categoryId, levelId);
        }
        
        List<QuestionN> questionsN = new ArrayList<>();
        
        Player player = new Player();
        
        player.setUserId(userId);
        player.setPoints(0);
        
        challenge.getPlayers().add(player);
        
        for (Question question : questions) {
            QuestionN questionN = new QuestionN();
            
            Integer amountOfExercises = isTheBrowserCompatibleWithAudio ? 4 : 2;

            int randomNumber = getRandomNumber(amountOfExercises, 1);

            questionN.setTypeOfExercise(question.getTypeOfExercise());
            
            if (!questionN.getTypeOfExercise().equals("writing")) {
                if (randomNumber == 1) questionN.setTypeOfExercise("writing");
                if (randomNumber == 2 || randomNumber == 3) questionN.setTypeOfExercise("translation");
                if (randomNumber == 4) questionN.setTypeOfExercise("open question");
            }

            Option correctOption = question.getOptions().stream().filter(option -> option.getIsCorrect()).findFirst().get();

            if (questionN.getTypeOfExercise().equals("writing") && correctOption.getIsCorrect() && correctOption.getName().split(" ").length <= 1) {
                Integer otherRandomNumber = getRandomNumber(amountOfExercises - 1, 1);
                if (otherRandomNumber == 1) questionN.setTypeOfExercise("translation");
                if (otherRandomNumber == 2) questionN.setTypeOfExercise("open question");
            }
                
            questionN.setOriginQuestionId(question.getIdQuestion());
            
            List<OptionN> optionsN = new ArrayList<>();
                    
            for (Option option : question.getOptions()) {
                OptionN optionN = new OptionN();
                if (questionN.getTypeOfExercise().equals("writing")) {
                    if (option.getIsCorrect()) {
                        optionN.setIsCorrect(option.getIsCorrect());
                        optionN.setName(option.getName());
                        optionsN.add(optionN);
                    }
                } else if (questionN.getTypeOfExercise().equals("open question")) { 
                    optionN.setVisibleIsCorrect(option.getIsCorrect());
                    optionN.setIsCorrect(option.getIsCorrect());
                    optionN.setName(option.getName());
                    optionsN.add(optionN);
                } else if (questionN.getTypeOfExercise().equals("translation")) { 
                    optionN.setIsCorrect(option.getIsCorrect());
                    optionN.setName(option.getName());
                    optionsN.add(optionN);
                } else {
                    optionN.setIsCorrect(option.getIsCorrect());
                    optionN.setName(option.getName());
                    optionsN.add(optionN);
                }
                
            }
            
            questionN.setTitle(question.getTitle());
            
            questionN.setOptions(optionsN);
            
            questionsN.add(questionN);
        }
        
        challenge.setQuestions(questionsN);
        
        challengeRepository.save(challenge);
        
        ChallengeStatus challengeStatus = new ChallengeStatus();
        
        challengeStatus.setChallengeId(challenge.getId());
        challengeStatus.setTitle(challenge.getTitle().replace("A1-A2: ", ""));
        challengeStatus.setCreatorId(userId);
        
        challenges.add(challengeStatus);
        
        return challenge;
    }
    
    public Challenge joinARoom(String challengeId, String userId) {
        Challenge challenge = null;
        
        for (ChallengeStatus challengeStatus : challenges) {
            if (challengeId.equals(challengeStatus.getChallengeId())) {
                if (challengeStatus.getCreatorId().equals(userId)) return null;
                
                ArrayList<ChallengeStatus> prevUserChallenges = new ArrayList<>();
        
                for (ChallengeStatus cStatus : challenges) {
                    if (cStatus.getCreatorId().equals(userId)) prevUserChallenges.add(cStatus);
                }
                for (ChallengeStatus cStatus : prevUserChallenges) challenges.remove(cStatus);
                
                Optional<Challenge> response = challengeRepository.findById(challengeId);

                if (!response.isPresent()) {
                    throw new EntityNotFoundException("No se encontro un desafío con ese ID: " + challengeId);
                }
                
                challenge = response.get();
                
                if (challenge.getPlayers().size() == 2) return null;
                
                Player player = new Player();

                player.setUserId(userId);
                player.setPoints(0);

                challenge.getPlayers().add(player);
                
                LocalDateTime currentTime = LocalDateTime.now();
                
                System.out.println("CURRENT TIME: " + currentTime);
                
                challenge.setCreationTime(currentTime.plusSeconds(10));
                
                System.out.println("CURRENT TIME: " + currentTime.plusSeconds(10));
                
                challengeRepository.save(challenge);
                
                challenges.remove(challengeStatus);
                
                return challenge;
            }
        }
        
        return null;
    }
    
    public int getRandomNumber(Integer max, Integer min) {
        return (int) (Math.random() * max + 1);
    }
    
    public Challenge receiveAnswer(String challengeId, QuestionN questionN, String userId) {
        Optional<Challenge> response = challengeRepository.findById(challengeId);
        
        if (!response.isPresent()) {
            throw new EntityNotFoundException("No se encontro un desafío con ese ID: " + challengeId);
        }
        
        Challenge challenge = response.get();
        
        Boolean isAPlayer = challenge.getPlayers().stream().anyMatch(player -> player.getUserId().equals(userId));
        if (!isAPlayer) return null;
        
        for (QuestionN question : challenge.getQuestions()) {
            if (question.getId().equals(questionN.getId())) {
                List<Answer> answers = question.getAnswers();
                        
                if (question.getAnswers().isEmpty()) {
                    answers = new ArrayList<>();
                } else {
                    answers = question.getAnswers();
                    if (answers.size() == 2) return null;
                    Boolean hasAlreadyAnswer = question.getAnswers().stream().anyMatch(answer -> answer.getUserId().equals(userId));
                    if (hasAlreadyAnswer) return null;
                }
                
                Optional<Answer> responseAnswer = questionN.getAnswers().stream().filter(answer -> answer.getUserId().equals(userId)).findFirst();
                
                Boolean isRight = false;
                String correctAnswer = "";

                if (!responseAnswer.isPresent()) {
                    return null;
                }
                
                Answer userAnswer = responseAnswer.get();

                for (OptionN optionN : question.getOptions()) {
                    if (optionN.getIsCorrect()) {
                        if (optionN.getName().equals(userAnswer.getAnswer())) {
                            isRight = true;
                        }
                        correctAnswer = optionN.getName();
                    }
                }

                if (!isRight) {
                    for (OptionN optionN : question.getOptions()) {
                        if (optionN.getIsCorrect()) optionN.setVisibleIsCorrect(isRight);
                    }
                }
                
                Optional<Player> playerResponse = challenge.getPlayers().stream().filter(player -> player.getUserId().equals(userId)).findFirst();
                        
                if (!playerResponse.isPresent()) {
                    throw new EntityNotFoundException("No existe un jugador en esta partida con este id: " + userId);
                }
                
                Player player = playerResponse.get();
                if (isRight) player.setPoints(player.getPoints() + 2);

                userAnswer.setIsCorrect(isRight);
                userAnswer.setCorrectAnswer(correctAnswer);

                answers.add(userAnswer);
                question.setAnswers(answers);
                
                Boolean isChallengeCompleted = challenge.getQuestions().stream().allMatch(
                        q -> q.getAnswers().stream().anyMatch(a -> a.getUserId().equals(userId))
                );
                
                if (isChallengeCompleted) {
                    player.setFinishTime(LocalDateTime.now());
                    
                    Optional<UserEntity> userResponse = userRepository.findById(player.getUserId());
                    
                    if (!userResponse.isPresent()) {
                        throw new EntityNotFoundException("Este usuario no existe");
                    }
                    UserEntity user = userResponse.get();
                    
                    user.setScore(
                            user.getScore() 
                            + calculatePoints(challenge.getQuestions(), userId)
                    );
                    
                    userRepository.save(user);
                    
                    whoWin(challenge.getPlayers());
                }
            }
        }
        
        challengeRepository.save(challenge);
        
        return challenge;
    }
    
    public void whoWin(List<Player> players) {
        Boolean everyoneIsDone = players.stream().allMatch(player -> player.getFinishTime() != null);
        if (everyoneIsDone) {
            Player player1 = players.get(0);
            Player player2 = players.get(1);
            
            if (player1.getPoints() > player2.getPoints()) {
                Optional<UserEntity> userResponse = userRepository.findById(player1.getUserId());
                    
                if (!userResponse.isPresent()) {
                    throw new EntityNotFoundException("Este usuario no existe");
                }
                
                UserEntity user = userResponse.get();
                
                user.setNumberOfGamesWon(
                    user.getNumberOfGamesWon() + 1
                );
                
                userRepository.save(user);
            } else if (player2.getPoints() > player1.getPoints()) {
                Optional<UserEntity> userResponse = userRepository.findById(player2.getUserId());
                    
                if (!userResponse.isPresent()) {
                    throw new EntityNotFoundException("Este usuario no existe");
                }
                
                UserEntity user = userResponse.get();
                
                user.setNumberOfGamesWon(
                    user.getNumberOfGamesWon() + 1
                );
                
                userRepository.save(user);
            }
        }
    }
    
    public Integer calculatePoints(List<QuestionN> questions, String userId) {
        Integer points = 0;
        
        for (QuestionN questionN : questions) {
            Optional<Answer> answer = questionN.getAnswers().stream().filter(a -> a.getUserId().equals(userId)).findFirst();
            if (answer.isPresent() && answer.get().getIsCorrect()) points += 2;
        }
        
        return points;
    }
    
    public Challenge endGame(String challengeId, String userId) {
        Optional<Challenge> response = challengeRepository.findById(challengeId);
        
        if (!response.isPresent()) {
            throw new EntityNotFoundException("No se encontro un desafío con ese ID: " + challengeId);
        }
        
        Challenge challenge = response.get();
        
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime creationTime = challenge.getCreationTime();
        
        creationTime.plusMinutes(1);
        creationTime.plusSeconds(10);
        
        Boolean isEqual = creationTime.equals(currentTime);
        Boolean isAfter = currentTime.isAfter(creationTime);
        
        if (isEqual || isAfter) {
            for (Player player : challenge.getPlayers()) {
                if (player.getFinishTime() == null) {
                    player.setFinishTime(LocalDateTime.now());
                    
                    Optional<UserEntity> userResponse = userRepository.findById(player.getUserId());
                    
                    if (!userResponse.isPresent()) {
                        throw new EntityNotFoundException("Este usuario no existe");
                    }
                    UserEntity user = userResponse.get();
                    
                    user.setScore(
                            user.getScore() 
                            + calculatePoints(challenge.getQuestions(), userId)
                    );
                    
                    userRepository.save(user);
                }
            }
        }
        
        return challengeRepository.save(challenge);
    }
    
    public List<ChallengeStatus> getChallenges() {
        return challenges;
    }
    
    public void deleteFromRooms(String challengeId) {
        challenges.remove(challengeId);
    }
    
    @Scheduled(fixedRate = 60000)
    public void deleteOldChallenge() {
        challengeRepository.deleteByCreationTime(LocalDateTime.now());
    }
    
    public Challenge findById(String id) {
        Optional<Challenge> response = challengeRepository.findById(id);
        
        if (!response.isPresent()) {
            throw new EntityNotFoundException("No se encontro un desafío con ese ID: " + id);
        }
        
        return response.get();
    }
    
}
