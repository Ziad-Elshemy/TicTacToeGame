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
    private String password;
    private boolean isOnline;
    private boolean isPlaying;
    private int score;
    private String gender;

    public PlayerDto(){};

    public PlayerDto(String userName, String name, String password, boolean isOnline, boolean isPlaying, int score, String gender) {
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.isOnline = isOnline;
        this.isPlaying = isPlaying;
        this.score = score;
        this.gender = gender;
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
        return password;
    }

    public void setPassword(String Password) {
        this.password = Password;
    }

    public boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public boolean getIsPlaying() {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    
    
    
    @Override
    public String toString() {
        return "PlayerDto{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", isOnline=" + isOnline +
                ", isPlaying=" + isPlaying +
                ", score=" + score +
                '}';
    }

   
}