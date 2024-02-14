package web.app.engrivals.engrivals.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.engrivals.engrivals.persistance.entities.OptionN;
import web.app.engrivals.engrivals.persistance.entities.Question;
import web.app.engrivals.engrivals.persistance.entities.QuestionN;
import web.app.engrivals.engrivals.persistance.repository.QuestionNRepository;
import web.app.engrivals.engrivals.persistance.repository.QuestionRepository;

@Service
public class QuestionNService {
    @Autowired
    QuestionNRepository questionNRespository;
    
    @Autowired
    QuestionRepository questionRepository;
    
    public Question editQuestion(QuestionN questionN) {
        Optional<Question> response = questionRepository.findById(questionN.getOriginQuestionId());
        
        if (!response.isPresent()) {
            throw new EntityNotFoundException("La pregunta no fue encontrada");
        }
        
        Question question = response.get();
        
        if (question.getTypeOfExercise().equals("writing")) return null;
        
        question.setTypeOfExercise("open question");
        
        return questionRepository.save(question);
    }
    
    public Map<String, String> deleteQuestion(QuestionN questionN) {
        Optional<Question> response = questionRepository.findById(questionN.getOriginQuestionId());
        
        if (!response.isPresent()) {
            throw new EntityNotFoundException("La pregunta no fue encontrada");
        }
        
        questionRepository.deleteById(response.get().getIdQuestion());
        
        HashMap<String, String> status = new HashMap<>();
        
        status.put("status", "Eliminada");
        
        return status;
    }
}
