package web.app.engrivals.engrivals.persistance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;

@Entity
public class OptionN {
    @Id
    @UuidGenerator
    private String id;
    private String name;
    @Column(columnDefinition = "TINYINT")
    private Boolean visibleIsCorrect;
    @Column(columnDefinition = "TINYINT")
    @JsonIgnore
    private Boolean isCorrect;

    public OptionN() {
    }

    public OptionN(String id, String name, Boolean visibleIsCorrect, Boolean isCorrect) {
        this.id = id;
        this.name = name;
        this.visibleIsCorrect = visibleIsCorrect;
        this.isCorrect = isCorrect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getVisibleIsCorrect() {
        return visibleIsCorrect;
    }

    public void setVisibleIsCorrect(Boolean visibleIsCorrect) {
        this.visibleIsCorrect = visibleIsCorrect;
    }
    
    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    @Override
    public String toString() {
        return "OptionN{" + "id=" + id + ", name=" + name + ", visibleIsCorrect=" + visibleIsCorrect + ", isCorrect=" + isCorrect + '}';
    }

}
