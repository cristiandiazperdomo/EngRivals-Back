package web.app.engrivals.engrivals.persistance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import org.hibernate.annotations.UuidGenerator;

@Entity
public class QuestionN {
    @Id
    @UuidGenerator
    private String id;
    private String title;
    private String typeOfExercise;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private List<OptionN> options;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private List<Answer> answers;
    private Integer originQuestionId;
    @ManyToOne
    @JoinColumn(name = "challenge_id")
    @JsonIgnore
    private Challenge challenge;

    public QuestionN() {
    }

    public QuestionN(String id, String title, String typeOfExercise, List<OptionN> options, List<Answer> answers, Integer originQuestionId, Challenge challenge) {
        this.id = id;
        this.title = title;
        this.typeOfExercise = typeOfExercise;
        this.options = options;
        this.answers = answers;
        this.originQuestionId = originQuestionId;
        this.challenge = challenge;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<OptionN> getOptions() {
        return options;
    }

    public void setOptions(List<OptionN> options) {
        this.options = options;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public Integer getOriginQuestionId() {
        return originQuestionId;
    }

    public void setOriginQuestionId(Integer originQuestionId) {
        this.originQuestionId = originQuestionId;
    }

    @Override
    public String toString() {
        return "QuestionN{" + "id=" + id + ", title=" + title + ", typeOfExercise=" + typeOfExercise + ", options=" + options + ", answers=" + answers + ", originQuestionId=" + originQuestionId + '}';
    }
    
}
