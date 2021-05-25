package bussiness;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String type;
    private int userID;
    public User(){

    }
    public User(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) {
            return false;
        }
        User other = (User) o;
        return other.username.equals(this.username) && other.password.equals(this.password) && other.type.equals(this.type) && other.userID == this.userID;
    }
}
