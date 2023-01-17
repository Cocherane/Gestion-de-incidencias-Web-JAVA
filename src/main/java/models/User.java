package models;

public class User {

    private int id;
    private String nombre;
    private String apel;

    public User(int id, String nombre, String apel) {
        this.id = id;
        this.nombre = nombre;
        this.apel = apel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApel() {
        return apel;
    }

    public void setApel(String apel) {
        this.apel = apel;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apel='" + apel + '\'' +
                '}';
    }
}
