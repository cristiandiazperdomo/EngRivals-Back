package web.app.engrivals.engrivals.persistance.entities;

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
    @Column(columnDefinition = "TINYINT")
    private Boolean isCorrect;
    private String userId;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionN questionId;

    public Answer() {
    }

    public Answer(String id, String answer, String userId) {
        this.id = id;
        this.answer = answer;
        this.userId = userId;
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
        return "Answer{" + "id=" + id + ", answer=" + answer + ", isCorrect=" + isCorrect + ", userId=" + userId + ", questionId=" + questionId + '}';
    }

}
