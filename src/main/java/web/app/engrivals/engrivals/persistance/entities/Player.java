package web.app.engrivals.engrivals.persistance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import org.hibernate.annotations.UuidGenerator;

@Entity
public class Player {
    @Id
    @UuidGenerator
    private String id;
    private Integer points;
    private String userId;
    private LocalDateTime finishTime;
    @ManyToOne
    @JoinColumn(name = "challenge_id")
    @JsonIgnore
    private Challenge challenge;

    public Player() {
    }

    public Player(String id, Integer points, String userId, LocalDateTime finishTime, Challenge challenge) {
        this.id = id;
        this.points = points;
        this.userId = userId;
        this.finishTime = finishTime;
        this.challenge = challenge;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    @Override
    public String toString() {
        return "Player{" + "id=" + id + ", points=" + points + ", userId=" + userId + ", finishTime=" + finishTime + '}';
    }

}
