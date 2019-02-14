package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AcmeFloat {


    // Fields -----------------------------------------------------------------
    private String title;
    private String description;
    private List<String> pictures;

    // Relationships ----------------------------------------------------------
    private Brotherhood brotherhood;

    //Posible cambio de Procession a List<Procession>
    private Procession procession;

    @NotNull
    @NotBlank
    public String getTitle() {
        return this.getTitle();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull
    @NotBlank
    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //Optional
    @URL
    public List<String> getPictures(){
        return this.pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public Brotherhood getBrotherhood() {
        return this.brotherhood;
    }

    public void setBrotherhood(Brotherhood brotherhood) {
        this.brotherhood = brotherhood;
    }

    public Procession getProcession() {
        return this.procession;
    }

    public void setProcession(Procession procession) {
        this.procession = procession;
    }
}
