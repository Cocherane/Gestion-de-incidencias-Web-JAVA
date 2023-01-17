package DataPersistence;

import models.Tecnico;
import models.Usuario;

import java.io.*;
import java.util.*;

public class DataRecovery {

    // path a cada parte de los ficheros externos de los documentos de datos
    private String pathRoot;
    private final String PATH_DATA;
    private final String PATH_LOGS;
    private final String PATH_SESSION;


    // path a cada nombre de los archivos
    private final String PATH_FILE_LOGS;
    private final String PATH_FILE_CONFIG;
    private final String PATH_FILE_LAST_LOGIN;



    public DataRecovery(String pathRoot) {
        // ponemos el path
        this.pathRoot = pathRoot;
        this.PATH_DATA = pathRoot + "\\data";
        this.PATH_LOGS = PATH_DATA + "\\logs";
        this.PATH_SESSION = PATH_DATA + "\\recordsession";



        // path a cada nombre de los archivos
        this.PATH_FILE_LOGS = "\\records.csv";
        this.PATH_FILE_CONFIG = ".\\properties.config";
        this.PATH_FILE_LAST_LOGIN = "\\lastLogin.bd";
        // inicializamos los folder que se necesiten
        creatingAllFolderPATH();
    }

    private void creatingAllFolderPATH( ){

        // creating path to data
        File pathData = new File( PATH_DATA );
        pathData.mkdir();

        // creating path to logs
        File pathLogs = new File( PATH_LOGS );
        pathLogs.mkdir();

        // creating path to
        File pathConfig = new File( PATH_SESSION );
        pathConfig.mkdir();




    }

    /* escribir en el log del sistema*/
    // se encarga de escribir los logs
    public void writeLogs(String saveLog ) throws IOException {
        // se escribe el log en la ruta \\user.dir\\data\\log\\records.csv
        File logFile = new File( PATH_LOGS, PATH_FILE_LOGS );

        // comprobamos si no existe el file, procedemos a crear el file
        if ( !logFile.exists() ){
            logFile.createNewFile();
            // diseñamos la cabecera del csv una vez creada
            final String HEADER = "Actividad,UserName,Email,TypeUser,Fecha,IdIncidencia";
            writeFile( logFile, HEADER );
        }
        // procedemos a ingresar el log si ya existe
        writeFile( logFile, saveLog );
    }


    private void writeFile( File path, String textSave ) throws IOException {
        // procedemos a ingresar el log
        FileWriter writeLog = new FileWriter( path , true );
        // asignamos el log a guardar
        writeLog.write( textSave + "\n" );
        // cerramos el flujo
        writeLog.close();
    }


    public void writeLastLogin( int idUser ) throws IOException {
        // set  la ruta del archivo
        File pathRecordLogin = new File( PATH_SESSION, PATH_FILE_LAST_LOGIN );
        // comprobamos is el fichero existe
        if ( !pathRecordLogin.exists() ){
            pathRecordLogin.createNewFile();
        }

        // set el archivo en memoria
        FileReader in = new FileReader( pathRecordLogin );
        // set la carga del archivo
        Properties properties = new Properties();
        // cargo el flugo
        properties.load( in );
        // cierro el flujo
        in.close();

        // set cargado ya a modificar
        FileWriter out = new FileWriter( pathRecordLogin );
        // actualizo las sesiones
        properties.setProperty( String.valueOf( idUser ), String.valueOf( new Date().getTime() ) );
        // guardo el archivo
        properties.store( out, "Sessions recording" );
        // cierro el flujo
        out.close();
    }


    public Date readLastLogin(int idUser ) throws IOException {
        // set el nombre de la ruta donde se encuentra el archivo
        File pathReadLogin = new File( PATH_SESSION, PATH_FILE_LAST_LOGIN );
        // comprobamos si existe
        if ( !pathReadLogin.exists() ) pathReadLogin.createNewFile();

        // set el file reading para cargarlo en el property
        FileReader pathRead = new FileReader( pathReadLogin );

        // buscamos si existe la idUser en el archivo, esto nos indica que es un usuario nuevo
        // set properties para cargar el archivo
        Properties in = new Properties();
         // cargamos el archivo al property
        in.load( pathRead );

         // cerramos el flujo del archivo
        pathRead.close();
         // retornamos una instancia de la clase Date para el último dia que se ha logiado y si no existe enviamos un null
        String segundos = in.getProperty( String.valueOf( idUser ) , null );
        if (segundos != null) return new Date( Long.parseLong( segundos ) );
        else return null;

    }

    /*  El  metodo se encarga de extarer laskey de las configuraciones del sistema */
    public String getValuesConfig(String keyData) throws IOException {
        String value = "", defaultValue = "";
        // comprobamos que este lowercase y sin espacios en blanco
        keyData = keyData.toLowerCase().trim();
        // apunto a el fichero
        File pathConfig = new File( PATH_FILE_CONFIG );

        // se abre el flujo para leer el archivo
        FileReader reader = new FileReader( pathConfig );

        // se abre la propiedades
        Properties in = new Properties();
        in.load( reader );
        // comprobamos que valor viene y asignamos el valor default
        if( keyData.equalsIgnoreCase("pathdata") ) defaultValue = ".";
        else if ( keyData.equalsIgnoreCase("userguest") ) defaultValue = "false";
        else if  ( keyData.equalsIgnoreCase("idsendtelegram") ) defaultValue = "00000000";
        else if  ( keyData.equalsIgnoreCase("bottelegram") ) defaultValue = "######## API TELEGRAM #######";
        // se obtiene el valor dado la key, si no se encuentra retorna un valor por defecto
        value = in.getProperty( keyData, defaultValue );
        // cerramos el flujo
        reader.close();


        return value.trim();
    }

    /* extrae en una lista todas las configuraciones del programa*/
    public Set<Map.Entry<Object, Object>> getPropertiesConfig( ) throws IOException {
        Set<Map.Entry<Object, Object>> setKeyValues = null;
        // abrimos la el archivo de configuracion del software
        File pathConfig = new File( PATH_FILE_CONFIG );

        // abrimos el flujo de leer en el archivo
        FileReader reader = new FileReader( pathConfig );

        // se realiza la instancia del properties
        Properties in = new Properties();
        // cargamos el properties con el archivo
        in.load( reader );

        setKeyValues = in.entrySet();

        // cerramos el flujo de archivo
        reader.close();
        // retornamos el mapa de set
        return setKeyValues;
    }

    /* extrae todas las ultimas sessiones de los usuarios */

    public Set< Map.Entry< Object, Object >> getLastSession(  ) throws IOException {
        Set< Map.Entry< Object, Object >> lastSession = null;
        // cargamos el la ruta del archivo
        File pathLastSession = new File( PATH_SESSION, PATH_FILE_LAST_LOGIN );

        // abrimos el flujo para leer el archivo
        FileReader reader = new FileReader( pathLastSession );

        // Abrimos el archivo con el properties
        Properties in = new Properties();
        in.load( reader );

        // hacemos un set de todas las sesiones
        lastSession = in.entrySet();

        // cerramos el flujo
        reader.close();

        return lastSession;
    }


    /*¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡GUARDADO EN DISCO!!!!!!!!!!!!!!!*/








}
