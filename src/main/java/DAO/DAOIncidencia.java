package DAO;

import models.Incidencia;

import java.util.ArrayList;

public interface DAOIncidencia {


    public boolean inserta( Incidencia incidencia );

    /* comprueba si ya existe la id generada */
    public boolean existeID( int numIdIncidencia );

    /* Searching todas las Incidecnias abiertas de un USUARIO pasando un id de usuario [ ArrayList<Incidencia> buscaIncidenciasAbiertasbyUsuario(int idUsuario)]*/
    public ArrayList<Incidencia> readIncidenciasAbiertasbyUsuario(int idUsuario);

    /* Searching todas las Incidecnias cerradas de un USUARIO pasando un id de usuario [ ArrayList<Incidencia> buscaIncidenciasCerradasbyUsuario(int idUsuario)]*/
    public ArrayList<Incidencia> readIncidenciasCerradasbyUsuario(int idUsuario);

    /* Searching todas las Incidecnias abiertas asignadas a un TECNICO pasando un id de usuario [ ArrayList<Incidencia> buscaIncidenciasAbiertasbyTecnico(int idTecnico)]*/
    public ArrayList<Incidencia> readIncidenciasAbiertasbyTecnico(int idTecnico);

    /* Searching todas las Incidecnias Cerradas asignadas a un TECNICO pasando un id de usuario [ ArrayList<Incidencia> buscaIncidenciasCerradasbyTecnico(int idTecnico)]*/
    public ArrayList<Incidencia> readIncidenciasCerradasbyTecnico(int idTecnico);

    /* busca si ya la incidecnia esta asignada a un tecnico*/
    public boolean readIsIncidenciasAsignadas( int idIncidencia);

    /* retorna todas las incidecnias abiertas  sin cerrar*/
    public ArrayList<Incidencia> readTodasIncidenciasAbiertas();

    /* get el numero de incidecnias abiertas totales un numero integer*/
    public int readNumIncidenciasAbiertas();

    /* devuelve la prioridad media de las */
    public float readNumPrioridadmediaIncidecnias();

    /* devuelve un integer de el numero de incidecnias abiertas asignadas*/
    public int readNumIncidenciasAbiertasAsignadas();

    /* devuelve un integer el numero de incidecnias cerradas*/
    public int getNumIncidenciasCerradas();

    /* busca una incidecnia si existe*/
    public Incidencia readIncidenciaById(int idIncidencia);

    /* Cierra la incidencia pasandole el  mensaje de cierre y la id de la Incidecnia*/

    public boolean updateCierreIncidencia( int idIncidencia, String mensajeCierre );

    /* se encaga de buscar un aincidecnia por termino */
    public ArrayList<Incidencia> readIncidenciasByTerm( String termino);

}
