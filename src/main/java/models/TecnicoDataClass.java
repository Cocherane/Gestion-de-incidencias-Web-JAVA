package models;


public class TecnicoDataClass {

    //Atributos
    private int id;
    private String nombre;
    private String apel;
    private String email;
    private int incidenciasAbiertas;
    private int incidenciasCerradas;
    private float prioridadMediaIncidenciaTecnico;


    public TecnicoDataClass(Tecnico tecnico) {
        this.id = tecnico.getId();
        this.nombre = tecnico.getNombre();
        this.apel = tecnico.getApel();
        this.email = tecnico.getEmail();
        this.incidenciasAbiertas = tecnico.incidecniasAbiertas();
        this.incidenciasCerradas = tecnico.incidecniasCerradas();
        this.prioridadMediaIncidenciaTecnico = tecnico.prioridadMediaTecnica();

    }

    public TecnicoDataClass(int id, String nombre, String apel, String email, int incidenciasAbiertas, int incidenciasCerradas, float prioridadMediaIncidenciaTecnico) {
        this.id = id;
        this.nombre = nombre;
        this.apel = apel;
        this.email = email;
        this.incidenciasAbiertas = incidenciasAbiertas;
        this.incidenciasCerradas = incidenciasCerradas;
        this.prioridadMediaIncidenciaTecnico = prioridadMediaIncidenciaTecnico;
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

    public int getIncidenciasCerradas() {
        return incidenciasCerradas;
    }

    public void setIncidenciasCerradas(int incidenciasCerradas) {
        this.incidenciasCerradas = incidenciasCerradas;
    }

    public float getPrioridadMediaIncidenciaTecnico() {
        return prioridadMediaIncidenciaTecnico;
    }

    public void setPrioridadMediaIncidenciaTecnico(float prioridadMediaIncidenciaTecnico) {
        this.prioridadMediaIncidenciaTecnico = prioridadMediaIncidenciaTecnico;
    }

    @Override
    public String toString() {
        return "TecnicoDataClass{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apel='" + apel + '\'' +
                ", email='" + email + '\'' +
                ", incidenciasAbiertas=" + incidenciasAbiertas +
                ", incidenciasCerradas=" + incidenciasCerradas +
                ", prioridadMediaIncidenciaTecnico=" + prioridadMediaIncidenciaTecnico +
                '}';
    }
}


