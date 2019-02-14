package domain;

import java.util.Date;
import java.util.List;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

public class Brotherhood extends Actor{

    // Fields -----------------------------------------------------------------
    private String title;
    private Date establishmentDate;
    private List<String> pictures;

    // Relationships ----------------------------------------------------------
    private List<Procession> procession;
    private List<AcmeFloat> acmeFloats;
    private Area area;


    // Field access methods ---------------------------------------------------

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    public Date getEstablishmentDate(){
        return this.establishmentDate;
    }

    public void setEstablishmentDate(Date establishmentDate){
        this.establishmentDate = establishmentDate;
    }

    @URL
    public List<String> getPictures(){
        return this.pictures;
    }

    public void setPictures(List<String> pictures){
        this.pictures = pictures;
    }



    // Relationship access methods --------------------------------------------

    //Falta añadir anotación YToX
    public List<Procession> getProcession(){
        return this.procession;
    }

    public void setProcession(List<Procession> procession) {
        this.procession = procession;
    }

    public List<AcmeFloat> getAcmeFloats(){
        return this.acmeFloats;
    }

    public void setAcmeFloats(List<AcmeFloat> acmeFloats) {
        this.acmeFloats = acmeFloats;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
