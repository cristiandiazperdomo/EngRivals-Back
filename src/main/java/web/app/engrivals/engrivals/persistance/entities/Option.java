package web.app.engrivals.engrivals.persistance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_options")
    private Integer idOption;
    private String name;
    @Column(name = "is_correct", columnDefinition = "TINYINT")
    @JsonProperty("isCorrect")
    private Boolean isCorrect;
    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private Question question;

    public Option() {
    }

    public Option(Integer idOption, String name, Boolean isCorrect, Question question) {
        this.idOption = idOption;
        this.name = name;
        this.isCorrect = isCorrect;
        this.question = question;
    }

    public Integer getIdOption() {
        return idOption;
    }

    public void setIdOption(Integer idOption) {
        this.idOption = idOption;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

}
