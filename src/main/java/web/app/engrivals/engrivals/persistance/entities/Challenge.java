package web.app.engrivals.engrivals.persistance.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.UuidGenerator;

@Entity
public class Challenge {
    @Id
    @UuidGenerator
    private String id;
    private String title;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "challenge_id")
    private List<Player> players;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "challenge_id")
    private List<QuestionN> questions;
    private LocalDateTime creationTime;

    public Challenge() {
        this.creationTime = LocalDateTime.now();
        this.players = new ArrayList<>();
    }

    public Challenge(String id, String title, List<Player> players, List<QuestionN> questions, LocalDateTime creationTime) {
        this.id = id;
        this.title = title;
        this.players = players;
        this.questions = questions;
        this.creationTime = creationTime;
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
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
