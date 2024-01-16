package web.app.engrivals.engrivals.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.engrivals.engrivals.persistance.entities.Question;
import web.app.engrivals.engrivals.persistance.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getQuestions(int id_category, int id_level){
        return questionRepository.findByCategoryAndEnglishLevel(id_category, id_level);
    }

    public Optional<Question> getQuestion(int idQuestion){
        return questionRepository.findById(idQuestion);
    }

    public Question save (Question question){
        return questionRepository.save(question);
    }
}
