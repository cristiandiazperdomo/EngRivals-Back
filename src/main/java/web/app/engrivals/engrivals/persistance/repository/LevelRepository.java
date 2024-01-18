package web.app.engrivals.engrivals.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import web.app.engrivals.engrivals.persistance.entities.EnglishLevel;

public interface LevelRepository extends CrudRepository<EnglishLevel, Integer> {
}
