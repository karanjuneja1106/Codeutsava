package org.nullpointer.doctor;

/**
 * Created by Deepanshu on 04-Feb-17.
 */

public class Appointment {
    private String mail_id, name, contact, date;

    public Appointment(String name, String mail, String contact, String date) {
        this.setName(name);
        this.setMail_id(mail);
        this.setContact(contact);
        this.setDate(date);
    }

    public String getMail_id() {
        return mail_id;
    }

    public void setMail_id(String mail_id) {
        this.mail_id = mail_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
