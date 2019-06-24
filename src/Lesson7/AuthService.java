package Lesson7;

import java.util.List;

public class AuthService {
    private List<String> nicksLst;

    public String checkNick(String nick) {
        return (nicksLst.contains(nick)?null:nick);
    }
    public void start(List<String> nicksLst){
        this.nicksLst = nicksLst;
    }
    public void stop(){

    }
}
