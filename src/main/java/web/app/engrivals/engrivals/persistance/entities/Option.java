package web.app.engrivals.engrivals.persistance.entities;

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
    private Boolean isCorrect;
    @ManyToOne
    @JoinColumn(name = "question_id_question", insertable = false, updatable = false)
    private Question question;

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

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
