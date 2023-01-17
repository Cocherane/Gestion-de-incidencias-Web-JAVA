package Test;

import Comunications.Comunications;
import DAO.*;
import DataPersistence.DataRecovery;
import models.GestionAPP;
import models.Incidencia;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class testClass {

    public static void main(String[] args) {

//        String foo = "";
//        System.out.printf(foo.isBlank()?"Esta vacia" : "is no funtional\n");
//        foo = " ";
//        System.out.printf(foo.isBlank()?"Esta vacia" : "is no funtional\n");
//
//        // inicializar MVC
        GestionAPP app = new GestionAPP();
//
//        // agregar Users
//        app.mock();
//
//        System.out.println(app.buscarUsuariobyID(1121));
//        System.out.println( app.buscarIncidencia(1212));
//
//
//        try {
//            app.cierraIncidencia(1212,1212,"hoa");
//        }catch ( NullPointerException e){
//            System.out.println("Advertencia!!! Ingresa un numero no una letra!!!");
//        }
        /*GestionAPP app = new GestionAPP();

        app.isGuestUserActiveConfig()

        String path = app.getPathDataConfig();

        DataRecovery testDatalog = new DataRecovery( path );

        try {
            testDatalog.writeLogs("primera");
            testDatalog.writeLogs("segunda");
            testDatalog.writeLogs("tercera");
        }catch ( IOException e){
            e.printStackTrace();
        }*/

        /*try {

            testDatalog.recordLastLogin(123);
            testDatalog.recordLastLogin(1234);
        }catch (IOException e) {
            e.printStackTrace();
        }*/

       // Comunications testEmailAttach = new Comunications( "0000", "dam4@carlosprofe.es","Olivo.2022"  );

        //testEmailAttach.enviarMail( "arielcocherane@gmail.com", "soy un test", "test");

        //testEmailAttach.enviarMailAdjunto( "arielcocherane@gmail.com", "soy un test", "test", "./test.xlsx" );
        //testEmailAttach.funtionName("");


        /* coneccion a la base de datos */
        DAOManager dao = DAOManager.getSinglentonInstance();
        DAOIncidenciaSQL daoIncidenciaSQL = new DAOIncidenciaSQL( dao );
        DAOUsuariosSql daoUsuariosSql = new DAOUsuariosSql( dao);
        DAOTecnicosSql daoTecnicosSql = new DAOTecnicosSql(dao);
        Incidencia incidencia = new Incidencia( 12344,"testDEscipcion", 9, 12345 );

        /*if (daoIncidenciaSQL.inserta( incidencia )) {
            System.out.println("exitosa");
        }else System.out.println("Error de envio");

        if (daoIncidenciaSQL.existeID( 12344)) System.out.println("existe la id");
        else System.out.println("no existe");*/

        //daoIncidenciaSQL.getIncidenciasAbiertasbyUsuario( 1234 ).forEach(System.out::println);
        //System.out.println(daoIncidenciaSQL.getIsIncidenciasAsignadas(4));
        //System.out.println(daoIncidenciaSQL.getIsIncidenciasAsignadas(5));
        //System.out.println(daoIncidenciaSQL.getNumIncidenciasAbiertas());
        //System.out.println(daoIncidenciaSQL.getNumPrioridadmediaIncidecnias());
        //System.out.println(daoIncidenciaSQL.getNumIncidenciasAbiertasAsignadas());
        //System.out.println(daoIncidenciaSQL.getNumIncidenciasCerradas());
        //System.out.println(daoIncidenciaSQL.getIncidenciaById(1));
        //if (daoIncidenciaSQL.getIncidenciaById(4).getSolucion() == null)
        /*if (daoIncidenciaSQL.updateCierreIncidencia(0, "probado")) System.out.println("fue modificada");
        else System.out.println("No fue modificado");

        System.out.println("     ".isBlank());*/

        /*System.out.println(daoIncidenciaSQL.readNumIncidenciasCerradasByUser(1234));
        daoIncidenciaSQL.readIncidenciaSinAsignar().forEach(st -> System.out.println(st));
        System.out.println(daoIncidenciaSQL.readIsIncidenciasAsignadas(6));
        System.out.println(daoIncidenciaSQL.readIsIncidenciasAsignadas(8));*/

        //System.out.println(daoTecnicosSql.readAllTecnicos());
        //System.out.println(daoUsuariosSql.readUsuarioByIdIncidencia(4));

        //System.out.println(app.buscaIncidenciasAbiertasbyTecnico(8989));
        //System.out.println(daoIncidenciaSQL.readIncidenciasAllCerradas());
        //System.out.println(app.buscaIncidenciasCerradas());

        //Object temp = app.login( "arielcocherane@gmail.com", "1234" );

        //System.out.println(temp.toString());

        //System.out.println(daoIncidenciaSQL.readTodasIncidenciasAbiertas());




    }




}

