package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Tecnico extends User implements Serializable {
    //Atributos

    private String clave;
    private String email;
    private ArrayList<Incidencia> incidencias;

    //Constructor
    public Tecnico(int id, String nombre, String apel, String clave, String email) {
        super(id, nombre, apel);
        this.clave = clave;
        this.email = email;
        this.incidencias = new ArrayList<>();
    }

    public Tecnico(int id, String nombre, String apel, String clave, String email, ArrayList<Incidencia> incidencias) {
        super(id, nombre, apel);
        this.clave = clave;
        this.email = email;
        this.incidencias = incidencias;
    }

    //Getters y Setters


    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(ArrayList<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    //Otros metodos
    /**
     *Se encarga de buscar una incidencia por su id.
     *
     *@author Gil Carlos
     *@return Incidecnia buscaIncidenciabyId si se ha encontrado la incidenciae.
     */
    public Incidencia buscaIncidenciabyId(int idIncidecnia){
        for (Incidencia i: incidencias) {
            if ( idIncidecnia == i.getId()) return  i;
        }
        return null;
    }

    /**
     *Se encarga de buscar un grupo de incidencias por un tema especifico y las recopila en un Array.
     *
     *@author Ariel Cocherane
     *@return Incidecnia buscaIncidecniasbyTerm si se ha encontrado incidencias por ese tema.
     */
    public ArrayList<Incidencia> buscaIncidecniasbyTerm(String term){
        ArrayList<Incidencia> incidenciasbyTerm = new ArrayList<>();
        for (Incidencia incidencia: incidencias) {
            if ( incidencia.getDescripcion().toLowerCase().contains( term.toLowerCase() )) {
                incidenciasbyTerm.add( incidencia );
            }
        }
        return incidenciasbyTerm;
    }
    /**
     *Cuenta las incidencias que tiene el tecnico cerradas en su array.
     *
     *@author Gil Carlos
     *@return int incidenciasCerradas numero de incidencias cerradas.
     */
    public int incidecniasCerradas(){
        int cont = 0;
        for (Incidencia i:
             incidencias) {
            if (i != null && i.estaResuelta()) cont++;
        }
        return cont;
    }
    /**
     *Cuenta las incidencias que tiene el tecnico abiertas en su array.
     *
     *@author Gil Carlos
     *@return int incidenciasAbiertas numero de incidencias abiertas.
     */
    public int incidecniasAbiertas(){
        int cont = 0;
        for (Incidencia i: incidencias) {
            if ( i != null && !i.estaResuelta() ) cont++;
        }
        return cont;
    }
    /**
     *Comprueba si el tecnico se ha logueado.
     *
     *@author Gil Carlos
     *@return boolean login logueo correctamente.
     */
    public boolean login(String email,String clave){
        return email.equalsIgnoreCase(this.email) && clave.equals(this.clave);

    }
    /**
     *Comprueba el nivel medio de prioridad de las incidencias del tecnico.
     *
     *@author Gil Carlos
     *@return float prioridadMediaUsuario media de prioridad de sus incidencias.
     */
    public float prioridadMediaTecnica() {
        int sumPioridadIncidencias = 0, contadorIncidencias = 0;
        for (Incidencia i :
                incidencias) {
            if (i != null ) {
                sumPioridadIncidencias += i.getPrioridad();
                contadorIncidencias++;
            }
        }
        return (float) sumPioridadIncidencias / contadorIncidencias;
    }

    /**
     * Se encarga de anadir al tecnico cuando se asigna la incidecnia
     *
     *@author Ariel Cocherane
     *@param incidenciaAsignada
     *@return RETURN
     */
    public void asignaIncidencia(Incidencia incidenciaAsignada){
        incidencias.add( new Incidencia( incidenciaAsignada ));
    }

    /**
     *Da como resuelta la incidecnia a traves de su id y añadiendole una solucion.
     *
     *@author Gil Carlos
     *@return boolean cierraIncidencia true = cierra correctamente la incidencia.
     */
    public boolean cierraIncidencia(int idIncidencia, String solucion){
        if ( solucion.isBlank() ) return false;
        for (Incidencia i: incidencias) {
            if (i != null && idIncidencia == i.getId()){
                i.setEstaResuelta(true);
                i.setSolucion(solucion);
                return true;
            }
        }
        return false;
    }

    /**
     * Desasigna una incidenia de un tecnico, removiendola el ArrayList<Incidencias>
     *
     * @author Ariel Cocherane
     * @param idIncidencia
     * @return Incidencia borrada
     */
    public Incidencia desAsignarIncidencia( int idIncidencia ){
        //buscamos la incidencias y la remosvemos del tecnico
        for (int i = 0; i < incidencias.size(); i++) {
            if (  incidencias.get(i).getId() == idIncidencia ){
                return incidencias.remove( i );
            }
        }
        return null;
    }

    public void cambiaClave(String password){
        this.clave = password;
    }

    @Override
    public String toString() {
        return "Nombre " + getNombre();
    }
}
