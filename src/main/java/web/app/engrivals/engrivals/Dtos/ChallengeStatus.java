package web.app.engrivals.engrivals.Dtos;

public class ChallengeStatus {
    private String title;
    private String challengeId;
    private Integer amountOfPeople;
    private String creatorId;
    private Boolean isFull;

    public ChallengeStatus() {
        this.amountOfPeople = 1;
        this.isFull = false;
    }

    public ChallengeStatus(String title, String challengeId, Integer amountOfPeople, String creatorId, Boolean isFull) {
        this.title = title;
        this.challengeId = challengeId;
        this.amountOfPeople = amountOfPeople;
        this.creatorId = creatorId;
        this.isFull = isFull;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public Integer getAmountOfPeople() {
        return amountOfPeople;
    }

    public void setAmountOfPeople(Integer amountOfPeople) {
        this.amountOfPeople = amountOfPeople;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String userId) {
        this.creatorId = userId;
    }
    
    public Boolean getIsFull() {
        return isFull;
    }

    public void setIsFull(Boolean isFull) {
        this.isFull = isFull;
    }

    @Override
    public String toString() {
        return "ChallengeStatus{" + "title=" + title + ", challengeId=" + challengeId + ", amountOfPeople=" + amountOfPeople + ", creatorId=" + creatorId + ", isFull=" + isFull + '}';
    }

}
