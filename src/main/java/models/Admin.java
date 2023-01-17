package models;

import java.util.ArrayList;

public class Admin extends User{
    //Atributos

    private String clave;
    private String email;

    //Constructor

    public Admin(int id, String nombre, String apel, String clave, String email) {
        super( id, nombre, apel);
        this.clave = clave;
        this.email = email;
    }

    //Getters


    public String getClave() {
        return clave;
    }

    public String getEmail() {
        return email;
    }

    //Setters


    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *Comprueba si el admin se ha logueado.
     *
     *@author Ariel Cocherane
     *@return boolean login logueo correctamente.
     */
    public boolean login(String email,String clave){
        return email.equalsIgnoreCase(this.email) && clave.equals(this.clave);
    }

    //toString

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + getId() +
                ", nombre='" + getNombre() + '\'' +
                ", apel='" + getApel() + '\'' +
                ", clave='" + clave + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
