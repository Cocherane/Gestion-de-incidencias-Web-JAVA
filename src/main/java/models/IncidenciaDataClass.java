package models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IncidenciaDataClass implements Comparable<IncidenciaDataClass> {
    //Atributos
    private int id;
    private String descripcion;
    private String solucion;
    private int prioridad;
    private boolean estaResuelta;
    private Date fechaInicio;
    private Date fechaFin;
    private int idUsuario;
    private int dias;
    private int diasResolver;
    private String nombreUsuario;
    private String emailUsuario;
    private int idTecnico;
    private String nombreTecnico;

    // Constructor
    // Aplicamos sobrecarga de métodos para las incidencias copias para mostrar al usuario
    // Este método se encarga de extraer información de las incidencias abiertas por el usuario.
    public IncidenciaDataClass(Incidencia incidencia, Usuario usuario) {
        this.id = incidencia.getId();
        this.descripcion = incidencia.getDescripcion();
        this.solucion = incidencia.getSolucion();
        this.prioridad = incidencia.getPrioridad();
        this.estaResuelta = incidencia.estaResuelta();
        this.fechaInicio = incidencia.getFechaInicio();
        this.fechaFin = incidencia.getFechaFin();
        this.idUsuario = incidencia.getIdUsuario();
        this.dias = incidencia.diasAbierta();
        this.diasResolver = incidencia.diasEnResolver();
        this.nombreUsuario = usuario.getNombre()+" "+ usuario.getApel();
        this.emailUsuario = usuario.getEmail();
        this.idTecnico = 0;
        this.nombreTecnico = null;
    }
    // Este método se encarga de extraer información de las incidencias asignadas ya al tecnico.
    public IncidenciaDataClass(Incidencia incidencia, Usuario usuario, Tecnico tecnico) {
        this.id = incidencia.getId();
        this.descripcion = incidencia.getDescripcion();
        this.solucion = incidencia.getSolucion();
        this.prioridad = incidencia.getPrioridad();
        this.estaResuelta = incidencia.estaResuelta();
        this.fechaInicio = incidencia.getFechaInicio();
        this.fechaFin = incidencia.getFechaFin();
        this.idUsuario = incidencia.getIdUsuario();
        this.dias = incidencia.diasAbierta();
        this.diasResolver = incidencia.diasEnResolver();
        this.nombreUsuario = usuario.getNombre()+" "+ usuario.getApel();
        this.emailUsuario = usuario.getEmail();
        this.idTecnico = ( tecnico!=null )? tecnico.getId() : 0;
        this.nombreTecnico = ( tecnico!=null )? tecnico.getNombre() : null ;
    }

    // Getters

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

    public int getDias() {
        return dias;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public String getNombreTecnico() {
        return nombreTecnico;
    }

    public int getDiasResolver() {
        return diasResolver;
    }

    public String getDateFormatteFechaInicio(){ return dateFormatter( fechaInicio ); }

    public String getDateFormatteFechaFin(){ return dateFormatter( fechaFin ); }

    public boolean isEstaResuelta() {
        return estaResuelta;
    }

    public boolean isAsignadaToTecnico(){
        return idTecnico != 0 && nombreTecnico != null ;
    }

    /* Trasforma la fecha a dd/MM/yyyy para legibilidad */
    public String dateFormatter(Date date ){
        SimpleDateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy" );
         return dateFormat.format( date );
    }

    /* resumen para crear el array*/
    public Object[] dataResume( ){
        return new Object[]{
                getId(),
                getDescripcion(),
                getPrioridad(),
                dateFormatter(getFechaInicio()) ,
                getDias(),
                getNombreUsuario(),
                getEmailUsuario()
        };
    }

    // Setters
    // como son datos para presentar al usuario, no se necesita setters


    @Override
    public String toString() {

        if (idTecnico == 0){
            return String.format("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░    INNCIDENCIA ABIERTA   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                              Incidencia con ID : %d    
                              Comentario : %s   
                              Prioridad : %d 
                              Fecha de la creacion : %s
                              Dias desde que se abrio : %d
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                    """, id, descripcion, prioridad, dateFormatter(fechaInicio), dias);
        }else if ( idTecnico != 0 && estaResuelta ){
            return String.format("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░    INNCIDENCIA CERRADA   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                              Incidencia con ID : %d  
                              RESUELTA  
                              Comentario del Usuario: %s  
                              Comentarios del tecnico : %s
                              Prioridad : %d 
                              
                              Fecha de la creacion : %s
                              Fecha de Resolucion : %s
                              Dias que tardo en resolver : %d
                              Usuario que la abrio : %s (%s)
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                    """, id, descripcion, solucion, prioridad, dateFormatter(fechaInicio), dateFormatter(fechaFin), diasResolver, nombreUsuario, emailUsuario);

        }else if ( idTecnico != 0 && !estaResuelta ){
            return String.format("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░    INNCIDENCIA ASIGNADA   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                              Incidencia con ID : %d  
                              NO RESUELTA  
                              Comentario del Usuario : %s   
                              Prioridad : %d 
                              Fecha de la creacion : %s
                              Dias desde que se abrio : %d
                              Usuario que la abrio : %s (%s)
                              Tecnico asignado : %s  %d
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                    """, id, descripcion, prioridad, dateFormatter(fechaInicio), dias, nombreUsuario, emailUsuario, nombreTecnico, idTecnico);
        }


        return "IncidenciaDataClass{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", solucion='" + solucion + '\'' +
                ", prioridad=" + prioridad +
                ", estaResuelta=" + estaResuelta +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", idUsuario=" + idUsuario +
                ", dias=" + dias +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", emailUsuario='" + emailUsuario + '\'' +
                ", idTecnico=" + idTecnico +
                ", nombreTecnico='" + nombreTecnico + '\'' +
                '}';
    }

    @Override
    public int compareTo(IncidenciaDataClass o) {
        return this.fechaInicio.compareTo( o.getFechaInicio() );
    }
}
