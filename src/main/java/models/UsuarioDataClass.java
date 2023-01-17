package models;

import java.util.ArrayList;

public class UsuarioDataClass {

    private int id;
    private String nombre;
    private String apel;
    private String email;
    private int incidenciasAbiertas;


    public UsuarioDataClass(Usuario usuario) {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.apel = usuario.getApel();
        this.email = usuario.getEmail();
        this.incidenciasAbiertas = usuario.incidenciasAbiertas();
    }

    public UsuarioDataClass(int id, String nombre, String apel, String email, int incidenciasAbiertas) {
        this.id = id;
        this.nombre = nombre;
        this.apel = apel;
        this.email = email;
        this.incidenciasAbiertas = incidenciasAbiertas;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIncidenciasAbiertas() {
        return incidenciasAbiertas;
    }

    public void setIncidenciasAbiertas(int incidenciasAbiertas) {
        this.incidenciasAbiertas = incidenciasAbiertas;
    }

    @Override
    public String toString() {
        return String.format("""
                         ╔═════════════════════════════════════════════════════════════════════════════════╗
                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░       MENU DE ADMIN        ░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                         Perfil del Usuario
                                                                                                                                                                
                                  -------------------------------------------------------------
                                   Nombre: %15S
                                  -------------------------------------------------------------
                                   ID del Usuario: %15d
                                  -------------------------------------------------------------
                                   Incidencias pendientes por asignar a este Usuario: %d
                                  -------------------------------------------------------------
                                                                                                             
                         ╚═════════════════════════════════════════════════════════════════════════════════╝
                        """
                , getNombre() + " " + getApel()
                , getId()
                , getIncidenciasAbiertas()
        );
    }
}
