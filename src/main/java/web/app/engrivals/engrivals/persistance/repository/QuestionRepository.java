package web.app.engrivals.engrivals.persistance.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import web.app.engrivals.engrivals.persistance.entities.Question;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer> {
    @Query("SELECT q FROM Question q WHERE q.categoryEntity.id_category = :id_category AND q.englishLevel.idLevel = :id_level")
    List<Question> findByCategoryAndEnglishLevel(@Param("id_category") int id_category, @Param("id_level") int id_level);

}
