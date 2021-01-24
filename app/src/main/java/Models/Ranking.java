package Models;

import java.util.List;

public class Ranking {
    private static Ranking Rankinginstance;
    private List<User> UserList;
    private Ranking() {
    }

    public static synchronized Ranking getInstance(){
        if(Rankinginstance == null)
            Rankinginstance = new Ranking();
        return Rankinginstance;
    }
    public void setList(List<User> newlist) {this.UserList = newlist;}
    public List<User> getList(){ return this.UserList; }

    public void closeInstance(){
        this.UserList.clear();
    }
}

