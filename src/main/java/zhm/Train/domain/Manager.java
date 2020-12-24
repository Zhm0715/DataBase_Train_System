package zhm.Train.domain;

import java.io.Serializable;

public class Manager implements Serializable {

    private Integer ID;
    private String Username;
    private String Passwd;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPasswd() {
        return Passwd;
    }

    public void setPasswd(String passwd) {
        Passwd = passwd;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "ID=" + ID +
                ", Username='" + Username + '\'' +
                ", Passwd='" + Passwd + '\'' +
                '}';
    }
}
