package Models;

public class Objetos {
    String id;
    String userId;
    String nombre;
    int coste;
    int puntoSaludRecuperados;
    int puntosDefensAdd;


    public Objetos() {
    }


    public Objetos(String nombre, int coste, String id, String userId, int puntoSaludRecuperados, int puntosDefensAdd) {
        this.nombre = nombre;
        this.coste = coste;
        this.userId = userId;
        this.id= id;
        this.puntoSaludRecuperados = puntoSaludRecuperados;
        this.puntosDefensAdd = puntosDefensAdd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCoste() {
        return coste;
    }

    public void setCoste(int coste) {
        this.coste = coste;
    }

    public int getPuntoSaludRecuperados() {
        return puntoSaludRecuperados;
    }

    public void setPuntoSaludRecuperados(int puntoSaludRecuperados) {
        this.puntoSaludRecuperados = puntoSaludRecuperados;
    }

    public int getPuntosDefensAdd() {
        return puntosDefensAdd;
    }

    public void setPuntosDefensAdd(int puntosDefensAdd) {
        this.puntosDefensAdd = puntosDefensAdd;
    }

    @Override
    public String toString() {
        return "Objetos{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", nombre='" + nombre + '\'' +
                ", coste=" + coste +
                ", puntoSaludRecuperados=" + puntoSaludRecuperados +
                ", puntosDefensAdd=" + puntosDefensAdd +
                '}';
    }
}