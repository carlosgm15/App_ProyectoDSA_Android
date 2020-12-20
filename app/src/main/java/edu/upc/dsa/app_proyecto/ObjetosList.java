package edu.upc.dsa.app_proyecto;
import java.util.List;
public class ObjetosList {
    private static ObjetosList objetolistinstance;
    private List<Objetos> objetolist;
    private ObjetosList() {
    }

    public static synchronized ObjetosList getInstance(){
        if(objetolistinstance == null)
            objetolistinstance = new ObjetosList();
        return objetolistinstance;
    }
    public void setList(List<Objetos> newlist) {this.objetolist = newlist;}
    public List<Objetos> getList(){ return this.objetolist; }

    public void closeInstance(){
        this.objetolist.clear();
    }
}

