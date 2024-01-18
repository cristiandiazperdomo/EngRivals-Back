package web.app.engrivals.engrivals.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.app.engrivals.engrivals.persistance.entities.Question;
import web.app.engrivals.engrivals.service.QuestionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/{id_category}/{id_level}")
    public ResponseEntity<List<Question>> getAll(@PathVariable("id_category") int idCategory,@PathVariable("id_level") int idLevel){
        return new ResponseEntity<>(questionService.getQuestions(idCategory,idLevel), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Question>> getQuestion(@PathVariable("id") int idQuestion){
        return new ResponseEntity<>(questionService.getQuestion(idQuestion), HttpStatus.OK);
    }


}
