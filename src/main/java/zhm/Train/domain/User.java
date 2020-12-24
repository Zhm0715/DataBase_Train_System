package zhm.Train.domain;

import java.io.Serializable;

public class User implements Serializable {

    private Integer ID;
    private String Username;
    private String Passwd;
    private Boolean BuyPower;

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

    public Boolean getBuyPower() {
        return BuyPower;
    }

    public void setBuyPower(Boolean buyPower) {
        BuyPower = buyPower;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", Username='" + Username + '\'' +
                ", Passwd='" + Passwd + '\'' +
                ", BugPower=" + BuyPower +
                '}';
    }
}
