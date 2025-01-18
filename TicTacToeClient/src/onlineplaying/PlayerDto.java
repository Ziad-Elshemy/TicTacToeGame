/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineplaying;

/**
 *
 * @author Ziad-Elshemy
 */
public class PlayerDto {
    
    private String userName;
    private String name;
    private String Password;
    private boolean isOnline;
    private boolean isPlaying;
    private int score;

    public PlayerDto(){};
    public PlayerDto (String un , String n , String pass , boolean  isO , boolean isP , int s)
    {
        userName=un;
        name=n;
        Password=pass;
        isOnline=isO;
        isPlaying=isP;
        score=s;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public boolean isIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public boolean isIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    
    
}
