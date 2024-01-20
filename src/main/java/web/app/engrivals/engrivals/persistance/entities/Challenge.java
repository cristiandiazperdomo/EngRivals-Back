package web.app.engrivals.engrivals.persistance.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.UuidGenerator;

@Entity
public class Challenge {
    @Id
    @UuidGenerator
    private String id;
    private String title;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "challenge_id")
    private List<QuestionN> questions;
    private LocalDateTime creationTime;

    public Challenge() {
        this.creationTime = LocalDateTime.now();
    }

    public Challenge(String title, List<QuestionN> questions) {
        this.title = title;
        this.questions = questions;
        this.creationTime = LocalDateTime.now();
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

    public List<QuestionN> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionN> questions) {
        this.questions = questions;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "Challenge{" + "id=" + id + ", title=" + title + ", questions=" + questions + ", creationDate=" + creationTime + '}';
    }
    
}
