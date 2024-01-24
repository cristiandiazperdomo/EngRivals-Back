package web.app.engrivals.engrivals.persistance.entities;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@Entity
@Table(name = "category")
@CrossOrigin("*")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_category;
    private String name;

    public CategoryEntity() {}

    public CategoryEntity(Integer id_category, String name) {
        this.id_category = id_category;
        this.name = name;
    }
    
    public Integer getId_category() {
        return id_category;
    }
    
    public void setId_category(Integer id_category) {
        this.id_category = id_category;
    }
    
    public String getName() {
      return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" + "id_category=" + id_category + ", name=" + name + '}';
    }
    
}
