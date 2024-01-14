package web.app.engrivals.engrivals.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.app.engrivals.engrivals.persistance.entities.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer> {
}
