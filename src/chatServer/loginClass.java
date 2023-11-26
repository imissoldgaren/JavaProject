package chatServer;

import java.util.HashMap;

public class loginClass {

    HashMap<String,String> logininfo = new HashMap<String,String>();
    Credentials credentials = new Credentials();
    String username;
    String password;
    loginClass(String username , String password){
        this.password = password;
        this.username = username;
        logininfo.put(password,username);
    }
    boolean login(String password ,String userName){
        if(logininfo.get(password).equals(credentials.getCrendtials().get(password)) && credentials.getCrendtials().get(password) != null ){
            System.out.println("welcome back: " + userName);
            return true;
        }else{
            System.out.println("failed to login please try again");
            return false;
        }
    }


    boolean checkIfUserExists(String username){
        if(credentials.getCrendtials().containsValue(username)){
            System.out.println("please choose another username");
            return false;
        }
        return true;
    }
    boolean signin(String username , String password){
        if(checkIfUserExists(username)){
            credentials.getCrendtials().put(password,username);
            return true;
        }else{
            return false;
        }


    }

    void deleatusername(){
        credentials.getCrendtials().remove(this.password,this.username);
        System.out.println("username has been deleated");
    }


    void run(){
        while(true){

        }
    }





}
