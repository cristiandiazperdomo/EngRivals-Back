package web.app.engrivals.engrivals.persistance.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.app.engrivals.engrivals.persistance.entities.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query("SELECT q FROM Question q WHERE q.categoryEntity.id_category = :id_category AND q.englishLevel.idLevel IN (:id_level, :id_level - 1, :id_level - 2) ORDER BY RAND() LIMIT 8")
    List<Question> findByCategoryEnglishLevel(@Param("id_category") Integer id_category, @Param("id_level") Integer id_level);
    
    @Query("SELECT q FROM Question q WHERE q.categoryEntity.id_category = :id_category AND q.typeOfExercise != 'open question' AND q.englishLevel.idLevel IN (:id_level, :id_level - 1, :id_level - 2) ORDER BY RAND() LIMIT 8")
    List<Question> findByCategoryEnglishLevelAndNoAudio(@Param("id_category") Integer id_category, @Param("id_level") Integer id_level);

}
