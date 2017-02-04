package org.nullpointer.doctor;

/**
 * Created by Deepanshu on 04-Feb-17.
 */

public class Appointment {
    private String mail_id;

    public Appointment(String mail) {
        this.setMail_id(mail);
    }

    public String getMail_id() {
        return mail_id;
    }

    public void setMail_id(String mail_id) {
        this.mail_id = mail_id;
    }
}
