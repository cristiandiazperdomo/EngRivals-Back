package web.app.engrivals.engrivals.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.app.engrivals.engrivals.persistance.entities.CategoryEntity;
import web.app.engrivals.engrivals.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/v1/api/category")
public class CategoryController {
  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }
  @PostMapping
  public ResponseEntity<CategoryEntity> create(@RequestBody CategoryEntity category){
    return ResponseEntity.ok( this.categoryService.create(category));
  }
  @GetMapping
  public ResponseEntity<List<CategoryEntity>> getAll(){
    return ResponseEntity.ok(this.categoryService.getAll());
  }
  @PutMapping
  public ResponseEntity<CategoryEntity> update (@RequestBody CategoryEntity category){
    return ResponseEntity.ok(this.categoryService.update(category));
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable("id") int id){
    this.categoryService.delete(id);
    return ResponseEntity.ok("Eliminado con éxito");
  }
}
