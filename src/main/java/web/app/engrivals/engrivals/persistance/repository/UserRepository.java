package web.app.engrivals.engrivals.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.app.engrivals.engrivals.persistance.entities.UserEntity;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
}
