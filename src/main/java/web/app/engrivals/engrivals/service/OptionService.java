package web.app.engrivals.engrivals.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.engrivals.engrivals.persistance.entities.Option;
import web.app.engrivals.engrivals.persistance.repository.OptionRepository;

import java.util.List;

@Service
public class OptionService {
    @Autowired
    private OptionRepository optionRepository;

    public List<Option> getOptions(int idOption){
        return optionRepository.findByQuestionIdQuestion(idOption);
    }

    public Boolean confirmAnswer(int idOption){
        return optionRepository.findById(idOption).get().getCorrect();
    }

    public Option save(Option option){
        return optionRepository.save(option);
    }
}
