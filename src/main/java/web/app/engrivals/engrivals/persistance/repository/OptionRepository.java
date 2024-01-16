package web.app.engrivals.engrivals.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import web.app.engrivals.engrivals.persistance.entities.Option;

import java.util.List;

public interface OptionRepository extends CrudRepository<Option, Integer> {
    List<Option> findByQuestionIdQuestion(int idQuestion);
}
