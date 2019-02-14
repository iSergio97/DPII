package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Request {


    // Fields -----------------------------------------------------------------

    private String status;
    private int row;
    private int column;
    private String reason;
    // Relationships ----------------------------------------------------------

    private Procession procession;


    @NotNull
    @NotBlank
    @Pattern(regexp = "^APPROVED|REJECTED|PENDING$")
    public String getStatus(){
        return  this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @NotBlank
    @NotNull
    @Range(min=1)
    public int getRow() {
        return row;
    }

    @NotBlank
    @NotNull
    @Range(min=1)
    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    //Falta anotación XToY
    public Procession getProcession() {
        return procession;
    }

    public void setProcession(Procession procession) {
        this.procession = procession;
    }
}
