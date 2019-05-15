package com.example.beginneractivity;

import java.io.Serializable;

public class User implements Serializable {
    private String uname;
    private String upass;
    private String umail;

    public User(String umail, String upass) {
        this.upass = upass;
        this.umail = umail;
    }

    public User() {}

    public boolean equals(User obj) {
        return obj.umail.equals(umail) && obj.upass.equals(upass);
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpass() {
        return upass;
    }

    public void setUpass(String upass) {
        this.upass = upass;
    }

    public String getUmail() {
        return umail;
    }

    public void setUmail(String umail) {
        this.umail = umail;
    }
}
