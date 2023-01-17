package models;

import java.io.Serializable;
import java.util.ArrayList;


 /**
 * <h2>La clase de los USUARIOS</h2>.
 * Contempla los usuarios normales dentro de la aplicacion.
 * <ul>
 * <li>Inserción de registros</li>
 * <li>Borrado y Modificación de registros</li>
 * </ul>
 *
 * @author Ariel Cocherane
 * @version v1.2022
 */
public class Usuario extends User implements Serializable {
     /**
      * Atributo apellido del empleado
      **/

    private String clave;
    private String email;
    private int token;
    private boolean tokenValidated;
    private ArrayList<Incidencia> incidencias;

    //Constructor

     /**
      * El contructor de las instancias Usuario
      *
      * @author Ariel Cocherane
      * @param id
      * @param nombre
      * @param apel
      * @param clave
      * @param email
      */
    public Usuario(int id, String nombre, String apel, String clave, String email) {
        super( id, nombre, apel);
        this.clave = clave;
        this.email = email;
        this.token = genNumLen(6,0,9);
        this.tokenValidated = false;
        this.incidencias = new ArrayList<>();
    }

     /**
      * El contructor para traer completa al usaurio des de la base de datos
      *
      * @author Gil Carlos Hermoso
      *
      * @param id
      * @param nombre
      * @param apel
      * @param clave
      * @param email
      * @param token
      * @param tokenValidated
      * @param incidencias
      */

     public Usuario(int id, String nombre, String apel, String clave, String email, int token, boolean tokenValidated, ArrayList<Incidencia> incidencias) {
         super( id, nombre, apel);
         this.clave = clave;
         this.email = email;
         this.token = token;
         this.tokenValidated = tokenValidated;
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

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public boolean isTokenValidated() {
        return tokenValidated;
    }

    public void setTokenValidated(boolean tokenValidated) {
        this.tokenValidated = tokenValidated;
    }

    public ArrayList<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(ArrayList<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    //Otros metodos
    /**
     * Se encarga de Insertar una incidencia al ArrayList delusuario
     *
     * @author Ariel Cocherane
     * @see ArrayList#add(Object) 
     * @param incidencia tiene como parametro la incidencia abierta pro el usuario
     */
    public void insertaIncidencia( Incidencia incidencia ){
        this.incidencias.add( incidencia );
    }

    /**
     *Se encarga de borrar una incidencia por su id.
     *
     *@author Gil Carlos
     *@return boolean deleteIncidencia si se ha boorado la incidecnia correctamente.
     */
    public boolean deleteIncidencia (int idIncidecnia){
        for (Incidencia i: incidencias) {
            if (i != null && idIncidecnia == i.getId()){
                incidencias.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     *Se encarga de buscar una incidencia por su id.
     *
     *@author Gil Carlos
     *@return Incidecnia buscaIncidenciabyId si se ha encontrado la incidenciae.
     */
    public Incidencia buscaIncidenciabyId(int idIncidecnia){
        for (Incidencia i: incidencias) {
            if (i != null && idIncidecnia == i.getId()) return  i;
        }
        return null;
    }

    /**
     *Se encarga de buscar un grupo de incidencias por un tema especifico y las recopila en un Array.
     *
     *@author Gil Carlos
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
     *Cuenta las incidencias que tiene el usuario en su array.
     *
     *@author Gil Carlos
     *@return int incidenciasAbiertas numero de incidencias.
     */
    public int incidenciasAbiertas(){
        return incidencias.size();
    }

    /**
     *Comprueba si el usuario se ha logueado.
     *
     *@author Gil Carlos & Ariel Cocherane
     *@return boolean login logueo correctamente.
     */
    public boolean login(String email,String clave){
        return email.equalsIgnoreCase(this.email) && clave.equals(this.clave);
    }

    /**
     *Comprueba el nivel medio de prioridad de las incidencias del usuario.
     *
     *@author Gil Carlos
     *@return float prioridadMediaUsuario media de prioridad de sus incidencias.
     */
    public float prioridadMediaUsuario(){
        int sumPioridadIncidencias = 0, contadorIncidencias = 0;
        for (Incidencia i: incidencias) {
            if (i != null ){
                sumPioridadIncidencias += i.getPrioridad();
                contadorIncidencias ++;
            }
        }
        return (float)sumPioridadIncidencias /(float) contadorIncidencias;

    }


    /**
     * El metodo se encargara de set el token si se ha comprobado
     *
     * @author Ariel Cocherane
     */
    public void tokenacept(){
        this.tokenValidated = true;
    }

    /**
     *Renovar el token cuando el usuario cambie de email
     *
     *@author Ariel Cocherane
     */
    public void resetToken( ){
        this.token = genNumLen(6,0,9);
        this.tokenValidated = false;
    }

    /**
     * El metodo se encargara de comprobar si el TOKEN introducido concuerda con el del Usuario
     *
     * @author Ariel Cocherane
     */
    public boolean tokenCheckUsuario(int token){
        return this.token == token;
    }


    /**
     * La funcion se encarga de generar numeros dentro de un rango de numeros, tienen que ser positivos.
     *
     * @author Ariel Cocherane
     * @see Math#random()
     * @param nuMin es el numero minido dentro de los rango de la funcion que genera numero aleatorios
     * @param nuMax es el numero maximo del rango del numero aleatorio
     * @return un numero aletorio dentro de los rango [nuMin - nuMax]
     */
    private static int genNum(int nuMin, int nuMax){
        return (int)(Math.random() * (nuMax-nuMin + 1)) + nuMin;
    }

    /**
     * El metodo se encarga de generar numeros aleatoreos de una longitud enteros.
     *
     * @author Ariel Cocherane
     * @see Usuario#genNum(int, int)
     * @param len Este parametro indica la longitud del numero
     * @param nuMin Indica el numero minimo del rango de los numeros aleatoreos individuales.
     * @param nuMax Indica el numero maximo del rango de los numeros aleatoreos individuales.
     * @return Integer numero que depende de la longitud introducida
     */
    private static int genNumLen(int len, int nuMin, int nuMax){
        int numOut = 0;
        for (int i = 0; i < len; i++) {
            int num ;
            // do es para controlar que si sale un numero 0 al final principio del numero random sea 0 lo vuelva a intentar
            do {
                num = genNum(nuMin,nuMax);
            }while(num == 0 && i == (len -1));
            // una vez pase la condicion se asigna a la posicion
            numOut += Math.pow(10,i) * num ;
        }
        return numOut;
    }

    /**
     * Set la nueva clave del Usuario
     *
     * @author Ariel Cocherane
     * @param password
     */
    public void cambiaClave(String password){
        //this.clave = password;
        resetToken();
    }





    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + getId() +
                ", nombre='" + getNombre() + '\'' +
                ", apel='" + getApel() + '\'' +
                ", clave='" + clave + '\'' +
                ", email='" + email + '\'' +
                ", incidencias=" + incidencias +
                '}';
    }
}
