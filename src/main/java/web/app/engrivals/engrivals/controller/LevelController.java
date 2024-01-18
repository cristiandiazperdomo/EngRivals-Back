package web.app.engrivals.engrivals.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.app.engrivals.engrivals.persistance.entities.EnglishLevel;
import web.app.engrivals.engrivals.service.LevelService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/v1/api/levels")
public class LevelController {
    @Autowired
    private LevelService levelService;

    @GetMapping("")
    public ResponseEntity<List<EnglishLevel>> getAll(){
        return new ResponseEntity<>(levelService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<EnglishLevel>> getLevel(@PathVariable("id") int idLevel){
        return new ResponseEntity<>(levelService.getLevel(idLevel), HttpStatus.OK);
    }
}
