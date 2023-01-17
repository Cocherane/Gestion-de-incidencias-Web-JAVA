package models;

import java.io.Serializable;
import java.util.Date;

public class Incidencia implements Comparable<Incidencia>, Serializable {
    //Atributos
    private int id;
    private String descripcion;
    private String solucion;
    private int prioridad;
    private boolean estaResuelta;
    private Date fechaInicio;
    private Date fechaFin;
    private int idUsuario;
    private int idTecnico;

    //Constructor
    public Incidencia(int id, String descripcion, int prioridad, int idUsuario) {
        this.id = id;
        this.descripcion = descripcion;
        this.solucion = null;
        this.prioridad = prioridad;
        this.estaResuelta = false;
        this.fechaInicio = new Date();
        this.fechaFin = null;
        this.idUsuario = idUsuario;
        this.idTecnico = 0;
    }

    //constructor crear objetos desde SQL
    public Incidencia(int id, String descripcion, String solucion, int prioridad, boolean estaResuelta, Date fechaInicio, Date fechaFin, int idUsuario, int idTecnico) {
        this.id = id;
        this.descripcion = descripcion;
        this.solucion = solucion;
        this.prioridad = prioridad;
        this.estaResuelta = estaResuelta;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idUsuario = idUsuario;
        this.idTecnico = idTecnico;
    }

    //Constructor Copia
    public Incidencia(Incidencia incidencia) {
        this.id = incidencia.getId();
        this.descripcion = incidencia.getDescripcion();
        this.solucion = incidencia.getSolucion();
        this.prioridad = incidencia.getPrioridad();
        this.estaResuelta = incidencia.estaResuelta();
        this.fechaInicio = incidencia.getFechaInicio();
        this.fechaFin = incidencia.getFechaFin();
        this.idUsuario = incidencia.getIdUsuario();
        this.idTecnico = incidencia.getIdTecnico();
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getSolucion() {
        return solucion;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public boolean estaResuelta() {
        return estaResuelta;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    //Setters
    private void setId(int id) {
        this.id = id;
    }

    private void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public void setEstaResuelta(boolean estaResuelta) {
        this.estaResuelta = estaResuelta;
        this.fechaFin = new Date();
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }

    //Methods
    /**
     *Se encarga de comprobar cuantos dias han pasado desde que estÃ¡ abierta la incidencia.
     *
     *@author Ariel Cocherane
     *@return int dias que han pasado desde que fue abierta la incidencia.
     */
    public int diasAbierta(){
        return (int)( ( new Date().getTime() - this.fechaInicio.getTime() ) / 86400000 );
    }

    /**
     *Se encarga de devolver el dias que paso desde que se abrio hasta que fue resuelta
     *
     *@author Ariel Cocherane
     *@return int dias que tomo resolver la incidencia desde que fue abierta.
     */
    public  int diasEnResolver(){
        if (fechaFin != null) {
            return (int)( ( this.fechaFin.getTime() - this.fechaInicio.getTime() ) / 86400000 );
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", solucion='" + solucion + '\'' +
                ", prioridad=" + prioridad +
                ", estaResuelta=" + estaResuelta +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", idUsuario=" + idUsuario +
                ", idTecnico=" + idTecnico +
                '}';
    }

    @Override
    public int compareTo(Incidencia o) {
        return this.fechaInicio.compareTo(o.getFechaInicio());
    }
}

