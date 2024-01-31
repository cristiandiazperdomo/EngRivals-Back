package web.app.engrivals.engrivals.Dtos;

import java.time.LocalDateTime;
import java.util.List;
import web.app.engrivals.engrivals.persistance.entities.QuestionN;

public class ChallengeDTO {
    private String id;
    private String title;
    private String loggedUserId;
    private List
            <QuestionN> questions;
    private LocalDateTime creationTime;

    public ChallengeDTO() {
    }

    public ChallengeDTO(String id, String title, String loggedUserId, List<QuestionN> questions, LocalDateTime creationTime) {
        this.id = id;
        this.title = title;
        this.loggedUserId = loggedUserId;
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

    public String getLoggedUserId() {
        return loggedUserId;
    }

    public void setLoggedUserId(String loggedUserId) {
        this.loggedUserId = loggedUserId;
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
    
}
