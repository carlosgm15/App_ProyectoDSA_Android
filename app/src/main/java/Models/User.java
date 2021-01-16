package Models;

import java.util.List;

import Models.Objetos;

public class User {
    public List<Objetos> objetosList;
    String id; // id del usuario
    String username; // nombre del usuario
    String apellidos;
    String password; // password del usuario
    int vida; // Vida del usuario
    int defensa; // Defensa del usuario
    int dinero; //Dinero del usuario
    int tiempo; // Tiempo que tarda el usuario en pasarse la partida

    public User() {
    }

    public User(String username,String id, String password, int vida, int defensa, int dinero, int tiempo, List<Objetos> objetosList) {
        this();
        this.id=id;
        this.username = username;
        this.password = password;
        this.vida = vida;
        this.defensa = defensa;
        this.dinero = dinero;
        this.tiempo = tiempo;
        this.objetosList = objetosList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public List<Objetos> getObjetosList() {
        return objetosList;
    }

    public void setObjetosList(List<Objetos> objetosList) {
        this.objetosList = objetosList;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", vida=" + vida +
                ", defensa=" + defensa +
                ", dinero=" + dinero +
                ", tiempo=" + tiempo +
                ", objetosList=" + objetosList +
                '}';
    }
}
