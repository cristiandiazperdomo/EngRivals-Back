package web.app.engrivals.engrivals.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.engrivals.engrivals.persistance.entities.EnglishLevel;
import web.app.engrivals.engrivals.persistance.repository.LevelRepository;

import java.util.List;


@Service
public class LevelService {
    @Autowired
    private LevelRepository levelRepository;

    public List<EnglishLevel> getAll(){
        return (List<EnglishLevel>) levelRepository.findAll();
    }
}
