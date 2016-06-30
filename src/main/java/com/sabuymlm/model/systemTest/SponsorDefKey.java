package com.sabuymlm.model.systemTest;
   
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Embeddable 
public class SponsorDefKey implements Serializable {
    @NotNull(message = "กำหนดชั้น(Level)"  ) 
    @Column(name = "level_index", columnDefinition = "int" )
    private Integer levelIndex; 
    @NotNull(message = "กำหนดตำแหน่ง"  ) 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", referencedColumnName = "position_id")
    private Position position ;   

    public SponsorDefKey(){}
    public SponsorDefKey(Integer levelIndex , Position position){
        this.levelIndex = levelIndex ;
        this.position = position ;
    }

    public Integer getLevelIndex() {
        return levelIndex;
    }

    public void setLevelIndex(Integer levelIndex) {
        this.levelIndex = levelIndex;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.levelIndex != null ? this.levelIndex.hashCode() : 0);
        hash = 97 * hash + (this.position != null ? this.position.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SponsorDefKey other = (SponsorDefKey) obj;
        if (this.levelIndex != other.levelIndex && (this.levelIndex == null || !this.levelIndex.equals(other.levelIndex))) {
            return false;
        }
        if (this.position != other.position && (this.position == null || !this.position.equals(other.position))) {
            return false;
        }
        return true;
    } 
     
}
