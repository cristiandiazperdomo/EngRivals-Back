package web.app.engrivals.engrivals.persistance.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_question")
    private Integer idQuestion;
    private String title;
    private String typeOfExercise;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private List<Option> options;
    @ManyToOne
    @JoinColumn(name = "category_id_category")
    private CategoryEntity categoryEntity;
    @ManyToOne
    @JoinColumn(name = "level_id_level")
    private EnglishLevel englishLevel;

    public Question() {
    }

    public Question(Integer idQuestion, String title, String typeOfExercise, List<Option> options, CategoryEntity categoryEntity, EnglishLevel englishLevel) {
        this.idQuestion = idQuestion;
        this.title = title;
        this.typeOfExercise = typeOfExercise;
        this.options = options;
        this.categoryEntity = categoryEntity;
        this.englishLevel = englishLevel;
    }

    public Integer getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(Integer idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeOfExercise() {
        return typeOfExercise;
    }

    public void setTypeOfExercise(String typeOfExercise) {
        this.typeOfExercise = typeOfExercise;
    }
    
    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public EnglishLevel getEnglishLevel() {
        return englishLevel;
    }

    public void setEnglishLevel(EnglishLevel englishLevel) {
        this.englishLevel = englishLevel;
    }

    @Override
    public String toString() {
        return "Question{" + "idQuestion=" + idQuestion + ", title=" + title + ", typeOfExercise=" + typeOfExercise + ", options=" + options + ", categoryEntity=" + categoryEntity + ", englishLevel=" + englishLevel + '}';
    }


}
