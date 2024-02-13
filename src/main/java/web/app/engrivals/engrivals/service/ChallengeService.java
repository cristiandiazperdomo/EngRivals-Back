package web.app.engrivals.engrivals.service;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import web.app.engrivals.engrivals.persistance.entities.Answer;
import web.app.engrivals.engrivals.persistance.entities.CategoryEntity;
import web.app.engrivals.engrivals.persistance.entities.Challenge;
import web.app.engrivals.engrivals.persistance.entities.EnglishLevel;
import web.app.engrivals.engrivals.persistance.entities.Option;
import web.app.engrivals.engrivals.persistance.entities.OptionN;
import web.app.engrivals.engrivals.persistance.entities.Points;
import web.app.engrivals.engrivals.persistance.entities.Question;
import web.app.engrivals.engrivals.persistance.entities.QuestionN;
import web.app.engrivals.engrivals.persistance.repository.CategoryRepository;
import web.app.engrivals.engrivals.persistance.repository.ChallengeRepository;
import web.app.engrivals.engrivals.persistance.repository.LevelRepository;
import web.app.engrivals.engrivals.persistance.repository.OptionRepository;
import web.app.engrivals.engrivals.persistance.repository.QuestionRepository;

@Service
public class ChallengeService {
    @Autowired
    ChallengeRepository challengeRepository;
    
    @Autowired
    QuestionRepository questionRepository;
    
    @Autowired
    OptionRepository optionRepository;
    
    @Autowired
    LevelRepository levelRepository;
    
    @Autowired
    CategoryRepository categoryRepository;
    
    public Challenge create(Integer categoryId, Integer levelId, Boolean isTheBrowserCompatibleWithAudio) {
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
        
        Points points = new Points();
        
        points.setUserId("2");
        points.setPoints(0);
        
        challenge.getPoints().add(points);
        
        for (Question question : questions) {
            QuestionN questionN = new QuestionN();
            
            if (question.getTypeOfExercise().equals("multiple choice")) {
                Integer amountOfExercises = isTheBrowserCompatibleWithAudio ? 3 : 2;
                
                int randomNumber = getRandomNumber(amountOfExercises, 1);
                
                questionN.setTypeOfExercise(question.getTypeOfExercise());
                if (randomNumber == 1) questionN.setTypeOfExercise("writing");
                if (randomNumber == 2) questionN.setTypeOfExercise("translation");
                if (randomNumber == 3) questionN.setTypeOfExercise("open question");
                
                Option correctOption = question.getOptions().stream().filter(option -> option.getIsCorrect()).findFirst().get();
                
                if (questionN.getTypeOfExercise().equals("writing") && correctOption.getIsCorrect() && correctOption.getName().split(" ").length <= 1) {
                    Integer otherRandomNumber = getRandomNumber(amountOfExercises - 1, 1);
                    if (otherRandomNumber == 1) questionN.setTypeOfExercise("translation");
                    if (otherRandomNumber == 2) questionN.setTypeOfExercise("open question");
                }
            } else {
                questionN.setTypeOfExercise(question.getTypeOfExercise());
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
        
        return challenge;
    }
    
    public int getRandomNumber(Integer max, Integer min) {
        return (int) (Math.random() * max + 1);
    }
    
    public Challenge receiveAnswer(String challengeId, QuestionN questionN) {
        Optional<Challenge> response = challengeRepository.findById(challengeId);
        
        
        if (!response.isPresent()) {
            throw new EntityNotFoundException("No se encontro un desafío con ese ID: " + challengeId);
        }
        
        Challenge challenge = response.get();
        
        for (QuestionN question : challenge.getQuestions()) {
            if (question.getId().equals(questionN.getId())) {
                List<Answer> answers = question.getAnswers();
                
                if (answers.isEmpty()) {
                    Boolean isRight = false;
                    String correctAnswer = "";
         
                    for (OptionN optionN : question.getOptions()) {
                        if (optionN.getIsCorrect()) {
                            if (optionN.getName().equals(questionN.getAnswers().get(0).getAnswer())) {
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
                    
                    Answer userAnswer = questionN.getAnswers().get(0);
                    
                    Points points = new Points();
                    
                    points.setPoints(challenge.getPoints().get(0).getPoints());
                    
                    if (isRight) challenge.getPoints().get(0).setPoints(points.getPoints() + 2);
                    
                    userAnswer.setIsCorrect(isRight);
                    userAnswer.setCorrectAnswer(correctAnswer);
                    
                    answers.add(userAnswer);
                } else {
                    Boolean hasAlreadyAnswer = false;
                    Answer saveAnswer = null;
                    
                    for (Answer answer : answers) {
                        for (Answer newAnswer : questionN.getAnswers()) {
                            if (answer.getUserId().equals(newAnswer.getUserId())) {
                                hasAlreadyAnswer = true;
                                saveAnswer = newAnswer;
                                saveAnswer.setIsCorrect(answer.getIsCorrect());
                            }
                        }
                    }
                    
                    if (!hasAlreadyAnswer) answers.add(saveAnswer);
                }
                question.setAnswers(answers);
            }
        }
        
        challengeRepository.save(challenge);
        
        return challenge;
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
