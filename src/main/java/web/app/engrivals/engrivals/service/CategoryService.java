package web.app.engrivals.engrivals.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import web.app.engrivals.engrivals.persistance.entities.CategoryEntity;
import web.app.engrivals.engrivals.persistance.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
  private final CategoryRepository categoryRepo;

  public CategoryService(CategoryRepository categoryRepo) {
    this.categoryRepo = categoryRepo;
  }

  public CategoryEntity create(CategoryEntity category){
    return this.categoryRepo.save(category);
  }
  public List<CategoryEntity> getAll(){
    return this.categoryRepo.findAll();
  }

  public CategoryEntity update(CategoryEntity category){
    boolean exist = this.categoryRepo.existsById(category.getId_category());
    if(exist){
      return this.categoryRepo.save(category);
    }
    throw new EntityNotFoundException("No se encontró la entidad.");
  }
  public void delete(int id){
    boolean exist = this.categoryRepo.existsById(id);
    if(exist){
      this.categoryRepo.deleteById(id);
    }
    throw new EntityNotFoundException("No se encontro la entidad");
  }
}
