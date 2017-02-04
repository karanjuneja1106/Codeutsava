package org.nullpointer.client;

/**
 * Created by karan1106 on 4/2/17.
 */

public class Doctor {
    private String mName,mContact;

    public Doctor(String name, String contact) {
        mName = name;
        mContact = contact;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getContact() {
        return mContact;
    }

    public void setContact(String contact) {
        mContact = contact;
    }
}
