package ca.ualberta.smaccr.giftcarder;

import java.util.Date;

/**
 * Created by mrijlaar on 10/21/15.
 */
abstract public class Notification {

    private Date date = new Date();
    private Boolean seen = Boolean.FALSE;

    abstract public void printNoti();

    public void seen(){
        seen = Boolean.TRUE;
    }

    //getters and setters
    public Boolean getSeen() {
        return seen;
    }
    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}
    public void setSeen(Boolean seen) {this.seen = seen;}

}
