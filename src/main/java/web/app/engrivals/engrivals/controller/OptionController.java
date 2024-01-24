package web.app.engrivals.engrivals.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.app.engrivals.engrivals.persistance.entities.Option;
import web.app.engrivals.engrivals.service.OptionService;

import java.util.List;

@RestController
@RequestMapping("/v1/api/options")
public class OptionController {
    @Autowired
    private OptionService optionService;

    @GetMapping("/option/{id}")
    public ResponseEntity<Boolean> confirmAnswer(@PathVariable("id") int idOption){
        return new ResponseEntity<>(optionService.confirmAnswer(idOption), HttpStatus.OK);
    }
}
