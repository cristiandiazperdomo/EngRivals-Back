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
        Challenge challenge = new Challenge();
        
        Optional<CategoryEntity> category = categoryRepository.findById(categoryId);
        
        if (!category.isPresent()) {
            throw new EntityNotFoundException("No existe una categoria asociada a este id: " + categoryId);
        }

        Optional<EnglishLevel> level = levelRepository.findById(levelId);
        
        if (!level.isPresent()) {
            throw new EntityNotFoundException("No existe un nivel de inglés asociado a este id: " + levelId);
        }
        
        challenge.setTitle(level.get().getName() + ": " + category.get().getName());
        
        List<Question> questions;
        
        if (isTheBrowserCompatibleWithAudio) {
            questions = questionRepository.findByCategoryEnglishLevelAndNoAudio(categoryId, levelId);
        } else {
            questions = questionRepository.findByCategoryEnglishLevel(categoryId, levelId);
        }
       
        List<QuestionN> questionsN = new ArrayList<>();
        
        for (Question question : questions) {
            QuestionN questionN = new QuestionN();
            
            if (question.getTypeOfExercise().equals("translation")) {
                int randomNumber = (int) (Math.random() * 5 + 1);
                
                questionN.setTypeOfExercise(question.getTypeOfExercise());
                if (randomNumber == 3) questionN.setTypeOfExercise("writing");
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
    
    public Challenge receiveAnswer(String challengeId, QuestionN questionN) {
        Optional<Challenge> response = challengeRepository.findById(challengeId);
        
        
        if (!response.isPresent()) {
            throw new EntityNotFoundException("No se encontro un desafío con ese ID: " + challengeId);
        }
        
        Challenge challenge = response.get();
        
        for (QuestionN question : challenge.getQuestions()) {
            if (question.getId().equals(questionN.getId())) {
                List<Answer> answers = question.getAnswers();
                
                if (answers.size() == 0) {
                    Boolean isRight = false;
         
                    for (OptionN optionN : question.getOptions()) {
                        if (optionN.getIsCorrect()) {
                            if (optionN.getName().equals(questionN.getAnswers().get(0).getAnswer())) {
                                isRight = true;
                            }
                        }
                    }
                    
                    questionN.getAnswers().get(0).setIsCorrect(isRight);
                    
                    answers.add(questionN.getAnswers().get(0));
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
