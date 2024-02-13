package web.app.engrivals.engrivals.service;

import java.util.ArrayList;
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

    public Optional<Question> getQuestion(Integer idQuestion){
        return questionRepository.findById(idQuestion);
    }
    
    public List<Question> saveAll(List<Question> questions) {
        for (Question question : questions) {
            questionRepository.save(question);
        }
        return questions;
    }

    public Question save(Question questionDTO){
        return questionRepository.save(questionDTO);
    }
    
    public Integer eval(Boolean bool) {
        return bool ? 1 : 0;
    }
    
    public void buildingScript() {
        List<Question> questions = new ArrayList<>();
        
        questions = questionRepository.findAll();
        System.out.println("");
        System.out.println("SET foreign_key_checks = 0;");
        for (Question question : questions) {
            if (question.getOptions().isEmpty()) return;
            //if (question.getOptions().size() <= 2) return;
            
            
            
            if (question.getOptions().size() == 2) {
                System.out.println("");
                System.out.println("");
                String insertQuestion = """
            INSERT INTO question (title, type_of_exercise, category_id_category, level_id_level) 
            VALUES ("%s", "%s", %d, %d);
            """.formatted(question.getTitle(), question.getTypeOfExercise(), question.getCategoryEntity().getId_category(), question.getEnglishLevel().getIdLevel());
                System.out.println(insertQuestion);
                System.out.println("");
                System.out.println("-- Obtener el ID de la pregunta recién insertada");
                System.out.println("SET @question_id = LAST_INSERT_ID();");
                System.out.println("");
                String insertOption = """
            INSERT INTO options (is_correct, name, question_id) VALUES  (%s, "%s", @question_id), (%s, "%s", @question_id);
            """.formatted(
                    eval(question.getOptions().get(0).getIsCorrect()), question.getOptions().get(0).getName(),
                    eval(question.getOptions().get(1).getIsCorrect()), question.getOptions().get(1).getName()
            );
                System.out.println(insertOption);
                System.out.println("");

            } else if (question.getOptions().size() == 3) {
            
                System.out.println("");
                System.out.println("");
                String insertQuestion = """
            INSERT INTO question (title, type_of_exercise, category_id_category, level_id_level) 
            VALUES ("%s", "%s", %d, %d);
            """.formatted(question.getTitle(), question.getTypeOfExercise(), question.getCategoryEntity().getId_category(), question.getEnglishLevel().getIdLevel());
                System.out.println(insertQuestion);
                System.out.println("");
                System.out.println("-- Obtener el ID de la pregunta recién insertada");
                System.out.println("SET @question_id = LAST_INSERT_ID();");
                System.out.println("");
                String insertOption = """
            INSERT INTO options (is_correct, name, question_id) VALUES  (%s, "%s", @question_id), (%s, "%s", @question_id), (%s, "%s", @question_id);
            """.formatted(
                    eval(question.getOptions().get(0).getIsCorrect()), question.getOptions().get(0).getName(),
                    eval(question.getOptions().get(1).getIsCorrect()), question.getOptions().get(1).getName(),
                    eval(question.getOptions().get(2).getIsCorrect()), question.getOptions().get(2).getName()
        );
            System.out.println(insertOption);
            System.out.println("");
            } else {
                System.out.println("");
                System.out.println("");
                String insertQuestion = """
            INSERT INTO question (title, type_of_exercise, category_id_category, level_id_level) 
            VALUES ("%s", "%s", %d, %d);
            """.formatted(question.getTitle(), question.getTypeOfExercise(), question.getCategoryEntity().getId_category(), question.getEnglishLevel().getIdLevel());
                System.out.println(insertQuestion);
                System.out.println("");
                System.out.println("-- Obtener el ID de la pregunta recién insertada");
                System.out.println("SET @question_id = LAST_INSERT_ID();");
                System.out.println("");
                String insertOption = """
            INSERT INTO options (is_correct, name, question_id) VALUES  (%s, "%s", @question_id);
            """.formatted(
                    eval(question.getOptions().get(0).getIsCorrect()), question.getOptions().get(0).getName());
            System.out.println(insertOption);
            System.out.println("");
            }
            
            String getIds = """
            -- Obtener los IDs de las opciones recién insertadas
            SET @option_id_A = LAST_INSERT_ID(); 
            SET @option_id_B = @option_id_A - 1;
            SET @option_id_C = @option_id_A - 2;   

            -- Insertar la asociación en la tabla question_options
            INSERT INTO question_options (question_id_question, options_id_options) VALUES (@question_id, @option_id_A);
            INSERT INTO question_options (question_id_question, options_id_options) VALUES (@question_id, @option_id_B);
            INSERT INTO question_options (question_id_question, options_id_options) VALUES (@question_id, @option_id_C);
            """;
            System.out.println(getIds);
            System.out.println("");
            
        }
        System.out.println("");
        System.out.println("SET foreign_key_checks = 1;");
        System.out.println("");
        System.out.println("");
        
    }
        
}
