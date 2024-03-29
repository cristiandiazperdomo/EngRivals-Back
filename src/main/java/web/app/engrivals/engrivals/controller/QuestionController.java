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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/v1/api/questions")
@CrossOrigin("*")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Question>> getQuestion(@PathVariable("id") int idQuestion){
        return new ResponseEntity<>(questionService.getQuestion(idQuestion), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Question> save(@RequestBody Question questionDTO) {
        return new ResponseEntity<>(questionService.save(questionDTO), HttpStatus.OK);
    }
    
    @GetMapping("/build")
    public void build() {
        questionService.buildingScript();
    }
    
    @PostMapping("/save-all")
    public ResponseEntity<List<Question>> saveAll(@RequestBody List<Question> questions) {
        return new ResponseEntity<>(questionService.saveAll(questions), HttpStatus.OK);
    }


}
