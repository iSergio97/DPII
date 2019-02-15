package domain;

import java.util.List;

public class Member extends Actor{


    // Fields -----------------------------------------------------------------

    // Relationships ----------------------------------------------------------


    // Relationship access methods --------------------------------------------

    private List<Enrolment> enrolment;
    private List<Request> requests;
    private Finder finder;

    public List<Enrolment> getEnrolment() {
        return this.enrolment;
    }

    public void setEnrolment(List<Enrolment> enrolment){
        this.enrolment = enrolment;
    }

    public List<Request> getRequests (){
        return this.requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public Finder getFinder() {
        return finder;
    }

    public void setFinder(Finder finder) {
        this.finder = finder;
    }
}
