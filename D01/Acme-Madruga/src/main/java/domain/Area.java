package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Area {

    // Fields -----------------------------------------------------------------
    private String name;
    private List<String> pictures;
    // Relationships ----------------------------------------------------------
    private Brotherhood brotherhood;


    // Field access methods ---------------------------------------------------

    @NotBlank
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Optional
    @URL
    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }



    // Relationship access methods --------------------------------------------

    public Brotherhood getBrotherhood() {
        return brotherhood;
    }

    public void setBrotherhood(Brotherhood brotherhood) {
        this.brotherhood = brotherhood;
    }

}
