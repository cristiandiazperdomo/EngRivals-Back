package web.app.engrivals.engrivals.persistance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.UuidGenerator;

@Entity
public class Answer {
    @Id
    @UuidGenerator
    private String id;    
    private String answer;
    private String correctAnswer;
    @Column(columnDefinition = "TINYINT")
    @JsonProperty("isCorrect")
    private Boolean isCorrect;
    private String userId;
    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private QuestionN questionId;

    public Answer() {
    }

    public Answer(String id, String answer, String correctAnswer, Boolean isCorrect, String userId, QuestionN questionId) {
        this.id = id;
        this.answer = answer;
        this.correctAnswer = correctAnswer;
        this.isCorrect = isCorrect;
        this.userId = userId;
        this.questionId = questionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    
    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public QuestionN getQuestionId() {
        return questionId;
    }

    public void setQuestionId(QuestionN questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "Answer{" + "id=" + id + ", answer=" + answer + ", correctAnswer=" + correctAnswer + ", isCorrect=" + isCorrect + ", userId=" + userId + ", questionId=" + questionId + '}';
    }

}
