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
    
    public Challenge create(Integer categoryId, Integer levelId) {
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
        
        List<Question> questions = questionRepository.findByCategoryAndEnglishLevel(categoryId, levelId);
        List<QuestionN> questionWithOptions = new ArrayList<>();
        
        for (Question question : questions) {
            QuestionN questionN = new QuestionN();
            
            List<Option> options = optionRepository.findByQuestionIdQuestion(question.getIdQuestion());
            List<OptionN> optionsWithQuestionId = new ArrayList<>();
            
            for (Option option : options) {
                OptionN optionN = new OptionN();
                
                optionN.setIsCorrect(option.getCorrect());
                optionN.setName(option.getName());
                
                optionsWithQuestionId.add(optionN);
            }
            
            questionN.setTitle(question.getTitle());
            questionN.setOptions(optionsWithQuestionId);
            
            questionWithOptions.add(questionN);
        }
        
        challenge.setQuestions(questionWithOptions);
        
        challengeRepository.save(challenge);
        
        return challenge;
    }
    
    @Scheduled(fixedRate = 60000)
    public void deleteOldChallenge() {
        challengeRepository.deleteByCreationTime(LocalDateTime.now());
    }
}
