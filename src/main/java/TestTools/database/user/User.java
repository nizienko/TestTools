package TestTools.database.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by def on 27.11.14.
 */
public class User {
    private Integer userId;
    private String login;
    private String passHash;
    private Integer userLevel;

    public User(){
        this.userLevel = 0;
    }

    public Integer getUserId() {
        return userId;
    }
    @Override
    public String toString(){
        return login;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (login.equals("")){
            throw new IllegalStateException("Empty Login");
        }
        this.login = login;
    }

    public String getPasswordHash() {
        return passHash;
    }

    public void setPasswordHash(String passHash){
        this.passHash = passHash;
    }

    public void setPassword(String passHash) {
        if (passHash.equals("")){
            throw new IllegalStateException("Empty password");
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(passHash.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        this.passHash = sb.toString();
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }
}
