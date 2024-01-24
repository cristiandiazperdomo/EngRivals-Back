package web.app.engrivals.engrivals.persistance.repository;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.app.engrivals.engrivals.persistance.entities.Challenge;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, String>{
    @Transactional
    @Modifying
    @Query("DELETE FROM Challenge c WHERE TIMESTAMPDIFF(MINUTE, c.creationTime, :time) > 20")
    void deleteByCreationTime(@Param("time") LocalDateTime time);
}
