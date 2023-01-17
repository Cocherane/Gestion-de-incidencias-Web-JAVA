package DAO;

import models.Incidencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DAOIncidenciaSQL {

    /* se encarga de manejar las query de los objetos Incidencia en la base de datos*/
    // VARIABLES DE CLASE
    // DAOManager the bridge to SQL DB
    private DAOManager dao;

    public DAOIncidenciaSQL( DAOManager dao ) {
        this.dao =dao;
    }



    /* CONSULTAS A LA BASE DE DATOS SQL A LA TABLA |incidencias|
    * QUE continen las tuplas con las siguientes characteristics de composicion
    *
    *   id              int NOT NULL,
    *   descripcion     varchar(300) NOT NULL,
    *   solucion        varchar(300) NOT NULL,
    *   prioridad       int NOT NULL DEFAULT '5',
    *   estaResuelta    tinyint(1) NOT NULL DEFAULT '0',
    *   fechaInicio     datetime NOT NULL,
    *   fechaFin        datetime NULL,
    *   idUsuario       int NOT NULL,
    *   idTecnico       int DEFAULT NULL
    *
    *
    *  */


    /*  Insertar una incidecnias */
    public boolean inserta( Incidencia incidencia ){
        // set date => to dateTime
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
        String dateInicio = dateFormat.format( incidencia.getFechaInicio() );

        // query
        String querySql = String.format("""
                INSERT INTO incidencias( id, descripcion, solucion, prioridad, estaResuelta, fechaInicio, fechaFin, idUsuario, idTecnico )
                VALUES( %d, '%s', %s, %d, %s ,'%s', %s, %d, %s  )
                """
                , incidencia.getId()
                , incidencia.getDescripcion()
                , "default"
                , incidencia.getPrioridad()
                , incidencia.estaResuelta()
                , dateInicio
                , "default"
                , incidencia.getIdUsuario()
                , "default"

        );

        try {
            // open dao
            dao.open();
            // connection with query
            Statement stmt = dao.getConn().createStatement();
            // query set
            stmt.executeUpdate( querySql );
            System.out.println("sentencia enviada");
            // close dao
            dao.close();

        } catch (Exception e) {
            System.out.println(e);
            return false;

        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    /* comprueba si ya existe la id generada */
    public boolean existeID( int numIdIncidencia ){
        // variable si existe
        boolean existe = true;

        try {
            // open dao
            dao.open();
            // Set pre the query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                SELECT EXISTS(
                    SELECT * FROM incidencias
                    WHERE id = ? );
                """);
            // set valor to search
            stmt.setInt( 1, numIdIncidencia );
            // resultado
            ResultSet resultSet = stmt.executeQuery();


            // si existe procedo a leer el numero 1 existe o 0 no existe
            if ( resultSet != null && resultSet.first()  ) {
                if ( resultSet.getInt( 1 ) == 0 ) existe = false;
            }

            // dao close
            stmt.close();
            dao.close();

        // catch atrapar los errores
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                dao.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return existe;
    }

    /* Searching todas las Incidecnias abiertas de un USUARIO pasando un id de usuario [ ArrayList<Incidencia> buscaIncidenciasAbiertasbyUsuario(int idUsuario)]*/
    public ArrayList<Incidencia> readIncidenciasAbiertasbyUsuario(int idUsuario){
        // creamos las arrayIncidecnias
        ArrayList<Incidencia> incidenciaAbiertasUsuario = new ArrayList<>();

        try {
            // open dao
            dao.open();
            // prepare a query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT id, descripcion, solucion, prioridad, estaResuelta, fechaInicio, fechaFin, idUsuario, idTecnico
                    FROM incidencias
                    WHERE idUsuario = ? AND estaResuelta = FALSE
                    """ );
            // Se asigna los valores de busqueda
            stmt.setInt( 1, idUsuario );
            // Extraemos el resusltado
            ResultSet resultado = stmt.executeQuery();
            // contruimos desde los valores el array de incidencias
            while ( resultado.next() ){
                incidenciaAbiertasUsuario.add(
                        new Incidencia(
                                  resultado.getInt("id")
                                , resultado.getString( "descripcion")
                                , null  //resultado.getString( "solucion")
                                , resultado.getInt("prioridad")
                                , resultado.getBoolean("estaResuelta")
                                , resultado.getDate( "fechaInicio" )
                                , resultado.getDate( "fechaFin" )
                                , resultado.getInt( "idUsuario")
                                , resultado.getInt("idTecnico")
                                )
                );
            }

            // cerrar dao y la query
            dao.close();
            stmt.close();


        }catch (Exception e) {
            System.out.println(e);
            return null;

        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return incidenciaAbiertasUsuario;

    }

    /* Searching todas las Incidecnias cerradas de un USUARIO pasando un id de usuario [ ArrayList<Incidencia> buscaIncidenciasCerradasbyUsuario(int idUsuario)]*/
    public ArrayList<Incidencia> readIncidenciasCerradasbyUsuario(int idUsuario){
        // creamos las arrayIncidecnias
        ArrayList<Incidencia> incidenciaCerradasUsuario = new ArrayList<>();

        try {
            // open dao
            dao.open();
            // prepare a query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT id, descripcion, solucion, prioridad, estaResuelta, fechaInicio, fechaFin, idUsuario, idTecnico
                    FROM incidencias
                    WHERE idUsuario = ? AND estaResuelta = TRUE
                    """ );
            // Se asigna los valores de busqueda
            stmt.setInt( 1, idUsuario );
            // Extraemos el resusltado
            ResultSet resultado = stmt.executeQuery();
            // contruimos desde los valores el array de incidencias
            while ( resultado.next() ){
                incidenciaCerradasUsuario.add(
                        new Incidencia(
                                resultado.getInt("id")
                                , resultado.getString( "descripcion")
                                , resultado.getString( "solucion")
                                , resultado.getInt("prioridad")
                                , resultado.getBoolean("estaResuelta")
                                , resultado.getDate( "fechaInicio" )
                                , resultado.getDate( "fechaFin" )
                                , resultado.getInt( "idUsuario")
                                , resultado.getInt("idTecnico")
                        )
                );
            }

            // cerrar dao y la query
            dao.close();
            stmt.close();


        }catch (Exception e) {
            System.out.println(e);
            return null;

        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return incidenciaCerradasUsuario;

    }

    /* Searching todas las Incidecnias abiertas asignadas a un TECNICO pasando un id de usuario [ ArrayList<Incidencia> buscaIncidenciasAbiertasbyTecnico(int idTecnico)]*/
    public ArrayList<Incidencia> readIncidenciasAbiertasbyTecnico(int idTecnico){
        // creamos las arrayIncidecnias
        ArrayList<Incidencia> incidenciaAbiertasTecnico = new ArrayList<>();

        try {
            // open dao
            dao.open();
            // prepare a query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT id, descripcion, solucion, prioridad, estaResuelta, fechaInicio, fechaFin, idUsuario, idTecnico
                    FROM incidencias
                    WHERE idTecnico = ? AND estaResuelta = FALSE
                    """ );
            // Se asigna los valores de busqueda
            stmt.setInt( 1, idTecnico );
            // Extraemos el resusltado
            ResultSet resultado = stmt.executeQuery();
            // contruimos desde los valores el array de incidencias
            while ( resultado.next() ){
                incidenciaAbiertasTecnico.add(
                        new Incidencia(
                                resultado.getInt("id")
                                , resultado.getString( "descripcion")
                                , null //resultado.getString( "solucion")
                                , resultado.getInt("prioridad")
                                , resultado.getBoolean("estaResuelta")
                                , resultado.getDate( "fechaInicio" )
                                , resultado.getDate( "fechaFin" )
                                , resultado.getInt( "idUsuario")
                                , resultado.getInt("idTecnico")
                        )
                );
            }

            // cerrar dao y la query
            dao.close();
            stmt.close();

        }catch (Exception e) {
            System.out.println(e);
            return null;

        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // retorna el valor de las incidencia si no hay incidecnia retorna una array vacio
        return incidenciaAbiertasTecnico;

    }

    /* Searching todas las Incidecnias Cerradas asignadas a un TECNICO pasando un id de usuario [ ArrayList<Incidencia> buscaIncidenciasCerradasbyTecnico(int idTecnico)]*/
    public ArrayList<Incidencia> readIncidenciasCerradasbyTecnico(int idTecnico){
        // creamos las arrayIncidecnias
        ArrayList<Incidencia> incidenciaCerradasTecnico = new ArrayList<>();

        try {
            // open dao
            dao.open();
            // prepare a query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT id, descripcion, solucion, prioridad, estaResuelta, fechaInicio, fechaFin, idUsuario, idTecnico
                    FROM incidencias
                    WHERE idTecnico = ? AND estaResuelta = TRUE
                    """ );
            // Se asigna los valores de busqueda
            stmt.setInt( 1, idTecnico );
            // Extraemos el resusltado
            ResultSet resultado = stmt.executeQuery();
            // contruimos desde los valores el array de incidencias
            while ( resultado.next() ){
                incidenciaCerradasTecnico.add(
                        new Incidencia(
                                resultado.getInt("id")
                                , resultado.getString( "descripcion")
                                , resultado.getString( "solucion")
                                , resultado.getInt("prioridad")
                                , resultado.getBoolean("estaResuelta")
                                , resultado.getDate( "fechaInicio" )
                                , resultado.getDate( "fechaFin" )
                                , resultado.getInt( "idUsuario")
                                , resultado.getInt("idTecnico")
                        )
                );
            }

            // cerrar dao y la query
            dao.close();
            stmt.close();


        }catch (Exception e) {
            System.out.println(e);
        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // retorna el valor de las incidencia si no hay incidecnia retorna una array vacio
        return incidenciaCerradasTecnico;

    }

    /* busca si ya la incidecnia esta asignada a un tecnico*/
    public boolean readIsIncidenciasAsignadas( int idIncidencia) {
        // boolean result
        boolean isAsignada = false;
        try {
            // open el dao
            dao.open();
            // realizo la consulta
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                SELECT CASE WHEN EXISTS (
                    SELECT *
                    FROM incidencias
                    WHERE id = ? AND idTecnico IS NULL)
                THEN FALSE
                ELSE TRUE END ;
                """);

            // set  query value
            stmt.setInt( 1, idIncidencia );
            // Get query response
            ResultSet resultado = stmt.executeQuery();
            // Check if result
            if ( resultado.next() ){
                isAsignada = resultado.getBoolean(1);
            }

            // cerrar dao y la query
            dao.close();
            stmt.close();


        }catch (Exception e) {
            System.out.println(e);

        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // retorna el valor de las incidencia si no hay incidecnia retorna una array vacio
        return isAsignada;


    }

    /* retorna todas las incidecnias abiertas  sin cerrar*/
    public ArrayList<Incidencia> readTodasIncidenciasAbiertas(){
        // creamos las arrayIncidecnias
        ArrayList<Incidencia> incidenciaAbiertas = new ArrayList<>();

        try {
            // open dao
            dao.open();
            // prepare a query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT id, descripcion, solucion, prioridad, estaResuelta, fechaInicio, fechaFin, idUsuario, idTecnico
                    FROM incidencias
                    WHERE estaResuelta = false;
                    """ );

            // Extraemos el resusltado
            ResultSet resultado = stmt.executeQuery();
            // contruimos desde los valores el array de incidencias
            while ( resultado.next() ){
                incidenciaAbiertas.add(
                        new Incidencia(
                                resultado.getInt("id")
                                , resultado.getString( "descripcion")
                                , resultado.getString( "solucion")
                                , resultado.getInt("prioridad")
                                , resultado.getBoolean("estaResuelta")
                                , resultado.getDate( "fechaInicio" )
                                , resultado.getDate( "fechaFin" )
                                , resultado.getInt( "idUsuario")
                                , resultado.getInt("idTecnico")
                        )
                );
            }

            // cerrar dao y la query
            dao.close();
            stmt.close();


        }catch (Exception e) {
            System.out.println(e);
            return null;
        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // retorna el valor de las incidencia si no hay incidecnia retorna una array vacio
        return incidenciaAbiertas;

    }

    /* get el numero de incidecnias abiertas totales un numero integer*/
    public int readNumIncidenciasAbiertas() {
        int numIncidenciasAbiertas = -1;
        try {
            // open dao
            dao.open();
            // set a query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                SELECT COUNT( * )
                FROM incidencias
                WHERE estaResuelta = FALSE;
                """);
            // Get result
            ResultSet resultado = stmt.executeQuery();
            // check value integer
            if ( resultado.next() )
                numIncidenciasAbiertas = resultado.getInt( 1 );

            // close dao and query
            dao.close();
            stmt.close();
        }catch (Exception e) {
            System.out.println(e);
        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return numIncidenciasAbiertas;

    }

    /* devuelve la prioridad media de las */
    public float readNumPrioridadmediaIncidecnias() {
        float numPriodidadMedia = 0.0f;
        try {
            // open dao
            dao.open();
            // set a query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT TRUNCATE( AVG( prioridad ), 2 )
                    FROM incidencias
                    """);
            // get result
            ResultSet resultSet = stmt.executeQuery();
            // set result a un variable
            if ( resultSet.next() )
                numPriodidadMedia = resultSet.getFloat(1);

            // close dao and query
            dao.close();
            stmt.close();


        }catch (Exception e) {
            e.printStackTrace();

        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // retorno el media de las prioridades
        return numPriodidadMedia;
    }

    /* devuelve la prioridad media de las */
    public float readNumPrioridadmediaIncidecniasByTecnico( int idTecnico ) {
        float numPriodidadMedia = 0.0f;
        try {
            // open dao
            dao.open();
            // set a query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT TRUNCATE( AVG( prioridad ), 2 )
                    FROM incidencias
                    where idTecnico = ?
                    """);
            stmt.setInt( 1, idTecnico );
            // get result
            ResultSet resultSet = stmt.executeQuery();
            // set result a un variable
            if ( resultSet.next() )
                numPriodidadMedia = resultSet.getFloat(1);

            // close dao and query
            dao.close();
            stmt.close();


        }catch (Exception e) {
            e.printStackTrace();

        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // retorno el media de las prioridades
        return numPriodidadMedia;
    }

    /* devuelve un integer de el numero de incidecnias abiertas asignadas*/
    public int readNumIncidenciasAbiertasAsignadas() {
        // declare a variable out
        int numIncidenciasAbirtasAsignadas = 0;
        try {
            // open dao
            dao.open();
            // set a query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT COUNT( * )
                    FROM incidencias
                    WHERE estaResuelta = FALSE AND idTecnico IS NOT NULL
                    """);
            // get result
            ResultSet resultSet = stmt.executeQuery();
            // set result a un variable
            if ( resultSet.next() )
                numIncidenciasAbirtasAsignadas = resultSet.getInt(1);

            // close dao and query
            dao.close();
            stmt.close();


        }catch (Exception e) {
            e.printStackTrace();

        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // retorno el media de las prioridades
        return numIncidenciasAbirtasAsignadas;

    }

    /* devuelve un integer el numero de incidecnias cerradas*/
    public int readNumIncidenciasCerradas() {
        // declare a variable out
        int numIncidenciasCerradas = 0;
        try {
            // open dao
            dao.open();
            // set a query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT COUNT( * )
                    FROM incidencias
                    WHERE estaResuelta = TRUE
                    """);
            // get result
            ResultSet resultSet = stmt.executeQuery();
            // set result a un variable
            if ( resultSet.next() )
               numIncidenciasCerradas = resultSet.getInt(1);

            // close dao and query
            dao.close();
            stmt.close();


        }catch (Exception e) {
            e.printStackTrace();

        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // retorno el media de las prioridades
        return numIncidenciasCerradas;

    }

    /* busca una incidecnia si existe*/
    public Incidencia readIncidenciaById(int idIncidencia) {
        // set variable de salida
        Incidencia incidecniaById = null;

        try {
            // open dao
            dao.open();
            // set query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                SELECT *
                FROM incidencias
                WHERE id = ?
                """);
            // Set value in a query
            stmt.setInt(1, idIncidencia);
            // Set el resultado a una variable
            ResultSet resultSet = stmt.executeQuery();
            // extraer el valor de las tupla
            if ( resultSet.next() ){
                incidecniaById = new Incidencia(
                                resultSet.getInt("id")
                                , resultSet.getString( "descripcion")
                                , resultSet.getString( "solucion")
                                , resultSet.getInt("prioridad")
                                , resultSet.getBoolean("estaResuelta")
                                , resultSet.getDate( "fechaInicio" )
                                , resultSet.getDate( "fechaFin" )
                                , resultSet.getInt( "idUsuario")
                                , resultSet.getInt("idTecnico")
                );
            }


            // close dao y la query
            dao.close();
            stmt.close();
        } catch (Exception e) {
            System.out.printf("Error en la busqueda de una incidecnias : %s", e);
            e.printStackTrace();
        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return incidecniaById;


    }

    /* Cierra la incidencia pasandole el  mensaje de cierre y la id de la Incidecnia*/

    public boolean updateCierreIncidencia( int idIncidencia, String mensajeCierre ){
        // set date => to dateTime
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
        String dateFin = dateFormat.format( new Date() );

        int checkRowUpdate = 0;

        try {
            // open dao
            dao.open();
            // set query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    UPDATE incidencias
                        SET solucion = ?,
                            estaResuelta = TRUE,
                            fechaFin = ADDDATE(NOW(), INTERVAL 2 HOUR )
                    WHERE id = ? ;
                    """);
            // set vauel of query
            stmt.setString( 1, mensajeCierre );
            stmt.setInt(2, idIncidencia );
            // get result si la tupla fue modificada
            checkRowUpdate = stmt.executeUpdate();

            // close dao y query
            dao.close();
            stmt.close();
        } catch (Exception e) {
            System.out.printf("Error en la modificacion de las incidecnia al cerrar la : %s", e );
        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // conprobar pro medio del reorno de la execusion si el numero de modificaciones g=ha sido 1
        return checkRowUpdate == 1;
    }

    /* se encaga de buscar un aincidecnia por termino */
    public ArrayList<Incidencia> readIncidenciasByTerm( String termino) {
        // init de el arraylist de incidecnias
        ArrayList<Incidencia> incidecniasByTerm = new ArrayList<>();

        try {
            // open dao
            dao.open();
            // set the query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT *
                    FROM incidencias
                    WHERE descripcion LIKE ?
                    """);

            // set value to match
            stmt.setString( 1, String.format( "%%%s%%", termino.trim() ) );
            // set result a varible
            ResultSet resultSet = stmt.executeQuery();
            // interate over a array
            while( resultSet.next() ){
                incidecniasByTerm.add(
                        new Incidencia(
                                        resultSet.getInt("id")
                                        , resultSet.getString( "descripcion")
                                        , resultSet.getString( "solucion")
                                        , resultSet.getInt("prioridad")
                                        , resultSet.getBoolean("estaResuelta")
                                        , resultSet.getDate( "fechaInicio" )
                                        , resultSet.getDate( "fechaFin" )
                                        , resultSet.getInt( "idUsuario")
                                        , resultSet.getInt("idTecnico")
                        )
                );
            }

            // close dao and query
            dao.close();
            stmt.close();

        }catch (Exception e) {
            System.out.printf("Error: al buscar una incidecnia por termino : %s", e );
        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // retorna el array de incidecnias by termino
        return incidecniasByTerm;


    }

    /* te da el numero de idUsuario pasandole una incidecnia*/
    public int buscaIdUsuarioByIdIncidecnia( int idIncidencia ){
        // variable guarda el resultado
        int idUsuario = 0;
        try {
            // open dao
            dao.open();
            // set the query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT idUsuario
                    FROM incidencias
                    WHERE id = ?
                    """);

            // set value to match
            stmt.setInt( 1, idIncidencia );
            // set result a varible
            ResultSet resultSet = stmt.executeQuery();
            // interate over a array
            if( resultSet.next() ){
                idUsuario = resultSet.getInt( "idUsuario" );
            }

            // close dao and query
            dao.close();
            stmt.close();

        }catch (Exception e) {
            System.out.printf("Error: al buscar una incidecnia por termino : %s", e );
        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // retorna la id del usuario
        return idUsuario;

    }

    /* devuelve el las incidecnias sin asignar a ningun tecnico*/
    public  ArrayList<Incidencia> readIncidenciaSinAsignar() {

        // creamos las arrayIncidecnias
        ArrayList<Incidencia> incidenciaAbiertasSinAsignar = new ArrayList<>();

        try {
            // open dao
            dao.open();
            // prepare a query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT id, descripcion, solucion, prioridad, estaResuelta, fechaInicio, fechaFin, idUsuario, idTecnico
                    FROM incidencias
                    WHERE idTecnico IS NULL
                    """ );


            // Extraemos el resusltado
            ResultSet resultado = stmt.executeQuery();
            // contruimos desde los valores el array de incidencias
            while ( resultado.next() ){
                incidenciaAbiertasSinAsignar.add(
                        new Incidencia(
                                resultado.getInt("id")
                                , resultado.getString( "descripcion")
                                , resultado.getString( "solucion")
                                , resultado.getInt("prioridad")
                                , resultado.getBoolean("estaResuelta")
                                , resultado.getDate( "fechaInicio" )
                                , resultado.getDate( "fechaFin" )
                                , resultado.getInt( "idUsuario")
                                , resultado.getInt("idTecnico")
                        )
                );
            }

            // cerrar dao y la query
            dao.close();
            stmt.close();


        }catch (Exception e) {
            System.out.println(e);
        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // retorna el valor de las incidencia si no hay incidecnia retorna una array vacio
        return incidenciaAbiertasSinAsignar;


    }
    /* muestra el numero de incidecncias cerrados para el usuario que las creo */
    public int readNumIncidenciasCerradasByUser(int idUsuario) {
        // declare a variable out
        int numIncidenciasCerradasCreadasByUsuario = 0;
        try {
            // open dao
            dao.open();
            // set a query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT COUNT( * )
                    FROM incidencias
                    WHERE estaResuelta = TRUE AND idUsuario = ?
                    """);
            // set value query
            stmt.setInt(1, idUsuario);
            // get result
            ResultSet resultSet = stmt.executeQuery();
            // set result a un variable
            if ( resultSet.next() )
                numIncidenciasCerradasCreadasByUsuario = resultSet.getInt(1);

            // close dao and query
            dao.close();
            stmt.close();


        }catch (Exception e) {
            e.printStackTrace();

        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // retorno el media de las prioridades
        return numIncidenciasCerradasCreadasByUsuario;

    }


    /* se encarga de modificar la incidecnia para set la asignacion de una incidecnia a un tecnico*/
    public boolean updateAsignaIncidencia( int idTecnico, int idIncidencia ){
        int checkRowUpdate = 0;

        try {
            // open dao
            dao.open();
            // set query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    UPDATE incidencias
                        SET idTecnico = ?
                    WHERE id = ?
                    """);
            // set vauel of query
            stmt.setInt( 1, idTecnico );
            stmt.setInt(2, idIncidencia );
            // get result si la tupla fue modificada
            checkRowUpdate = stmt.executeUpdate();

            // close dao y query
            dao.close();
            stmt.close();
        } catch (Exception e) {
            System.out.printf("Error en la modificacion de las incidecnia al cerrar la : %s", e );
        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // conprobar pro medio del reorno de la execusion si el numero de modificaciones g=ha sido 1
        return checkRowUpdate == 1;

    }

    public int readNumIncidenciasAbiertasByTecnico(int idTecnico) {
        // declare a variable out
        int numIncidenciasAbirtasByTecnico = 0;
        try {
            // open dao
            dao.open();
            // set a query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT COUNT( * )
                    FROM incidencias
                    WHERE idTecnico = ? AND estaResuelta = FALSE
                    """);
            // SET VALUE IN QUERY
            stmt.setInt( 1, idTecnico );
            // get result
            ResultSet resultSet = stmt.executeQuery();
            // set result a un variable
            if ( resultSet.next() )
                numIncidenciasAbirtasByTecnico = resultSet.getInt(1);

            // close dao and query
            dao.close();
            stmt.close();


        }catch (Exception e) {
            e.printStackTrace();

        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // retorno el numero de incidecnias abiertas que han sido asignada a un tecnico
        return numIncidenciasAbirtasByTecnico;


    }

    public int readNumIncidenciasAbiertasAsignadasByUsuario(int idUsuario) {
        // declare a variable out
        int numIncidenciasAbirtasByTecnico = 0;
        try {
            // open dao
            dao.open();
            // set a query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT COUNT( * )
                    FROM incidencias
                    WHERE idUsuario = ? AND estaResuelta = FALSE AND idTecnico IS NOT NULL ;
                    """);
            // SET VALUE IN QUERY
            stmt.setInt( 1, idUsuario );
            // get result
            ResultSet resultSet = stmt.executeQuery();
            // set result a un variable
            if ( resultSet.next() )
                numIncidenciasAbirtasByTecnico = resultSet.getInt(1);

            // close dao and query
            dao.close();
            stmt.close();


        }catch (Exception e) {
            e.printStackTrace();

        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // retorno el numero de incidecnias abuertas asignada por un tecnico
        return numIncidenciasAbirtasByTecnico;
    }

    /* BUSCA TODAS LAS INCIDECNIAS CERRADAS POR LOS TECNICOS*/
    public  ArrayList<Incidencia> readIncidenciasAllCerradas() {
        // init ArrayList<Incidecnia>
        ArrayList<Incidencia> incidenciasAllCerradas = new ArrayList<>();

        try {
            // dao open
            dao.open();
            // set query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT *
                    FROM incidencias
                    WHERE estaResuelta = TRUE
                    """);
            // get result
            ResultSet resultado = stmt.executeQuery();
            while ( resultado.next() ) {
                incidenciasAllCerradas.add(
                        new Incidencia(
                                resultado.getInt("id")
                                , resultado.getString("descripcion")
                                , resultado.getString("solucion")
                                , resultado.getInt("prioridad")
                                , resultado.getBoolean("estaResuelta")
                                , resultado.getDate("fechaInicio")
                                , resultado.getDate("fechaFin")
                                , resultado.getInt("idUsuario")
                                , resultado.getInt("idTecnico")
                        )
                );
            }
            // dao close and  query
            dao.close();
            stmt.close();
        }catch (Exception e) {
            e.printStackTrace();

        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // retorno el numero de incidecnias abuertas asignada por un tecnico
        return incidenciasAllCerradas;
    }

    public int readNumIncidenciasSinAbiertasbyUsuario(int idUsuario) {
        // declare a variable out
        int numIncidenciasAbirtasSinByTecnico = 0;
        try {
            // open dao
            dao.open();
            // set a query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT COUNT( * )
                    FROM incidencias
                    WHERE idUsuario = ? AND estaResuelta = FALSE AND idTecnico IS NULL ;
                    """);
            // SET VALUE IN QUERY
            stmt.setInt( 1, idUsuario );
            // get result
            ResultSet resultSet = stmt.executeQuery();
            // set result a un variable
            if ( resultSet.next() )
                numIncidenciasAbirtasSinByTecnico = resultSet.getInt(1);

            // close dao and query
            dao.close();
            stmt.close();


        }catch (Exception e) {
            e.printStackTrace();

        }finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // retorno el numero de incidecnias abuertas asignada por un tecnico
        return numIncidenciasAbirtasSinByTecnico;
    }
}
