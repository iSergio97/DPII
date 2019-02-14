package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class Procession {

    // Fields -----------------------------------------------------------------
    private String title;
    private String description;
    private Date moment;
    private String ticker;

    // Relationships ----------------------------------------------------------
    private Brotherhood brotherhood;


    // Field access methods ---------------------------------------------------

    @NotBlank
    @NotNull
    public String getTitle(){
        return this.getTitle();
    }

    public void setTitle(String title){
        this.title = title;
    }

    @NotBlank
    @NotNull
    public String getDescription(){
        return this.getDescription();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    public Date getMoment() {
        return this.moment;
    }

    public void setMoment(Date moment){
        this.moment = moment;
    }

    @NotBlank
    @NotNull
    //Falta añadir patron YYMMDD-XXXXX donde XXXXX son 5 letras en mayúsculas
    public String getTicker(){
        return this.ticker;
    }


    // Relationship access methods --------------------------------------------

    //Falta añadir anotación XToY
    public Brotherhood getBrotherhood() {
        return this.brotherhood;
    }

    public void setBrotherhood(Brotherhood brotherhood) {
        this.brotherhood = brotherhood;
    }
}
