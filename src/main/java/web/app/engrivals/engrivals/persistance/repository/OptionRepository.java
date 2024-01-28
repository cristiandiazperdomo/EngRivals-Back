package web.app.engrivals.engrivals.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import web.app.engrivals.engrivals.persistance.entities.Option;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends CrudRepository<Option, Integer> {
}
