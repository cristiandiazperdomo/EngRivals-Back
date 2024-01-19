package web.app.engrivals.engrivals.persistance.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_id")
    private List<OptionN> options;
    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    public QuestionN() {
    }

    public QuestionN(String id, String title, List<OptionN> options) {
        this.id = id;
        this.title = title;
        this.options = options;
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

    public List<OptionN> getOptions() {
        return options;
    }

    public void setOptions(List<OptionN> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "QuestionN{" + "id=" + id + ", title=" + title + ", options=" + options + '}';
    }
    
    
}
