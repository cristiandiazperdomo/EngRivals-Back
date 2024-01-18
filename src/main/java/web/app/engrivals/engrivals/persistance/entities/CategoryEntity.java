package web.app.engrivals.engrivals.persistance.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class CategoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id_category;
  private String name;

  public CategoryEntity() {}
  
  public CategoryEntity(int id_category, String name) {
    this.id_category = id_category;
    this.name = name;
  }
  public int getId_category() {
    return id_category;
  }
  public void setId_category(int id_category) {
    this.id_category = id_category;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
}
