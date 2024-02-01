package web.app.engrivals.engrivals.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.app.engrivals.engrivals.persistance.entities.Question;
import web.app.engrivals.engrivals.persistance.entities.QuestionN;
import web.app.engrivals.engrivals.service.QuestionNService;

@RestController
@RequestMapping("/v1/api/questionsN")
@CrossOrigin("*")
public class QuestionNController {
    @Autowired
    QuestionNService questionNService;
    
    @PutMapping
    public ResponseEntity<Question> editQuestion(@RequestBody QuestionN questionN) {
        return new ResponseEntity<>(questionNService.editQuestion(questionN), HttpStatus.OK);
    }
}
