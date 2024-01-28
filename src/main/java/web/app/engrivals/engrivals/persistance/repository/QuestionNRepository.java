package web.app.engrivals.engrivals.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.app.engrivals.engrivals.persistance.entities.QuestionN;

@Repository
public interface QuestionNRepository extends JpaRepository<QuestionN, String> {
    
}
