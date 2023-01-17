package DAO;

import models.Incidencia;
import models.Message;
import models.User;
import models.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DAOMessageSql {

    /* se encarga de manejar las query de los objetos message en la base de datos*/
    // VARIABLES DE CLASE
    // DAOManager the bridge to SQL DB
    private DAOManager dao;

    public DAOMessageSql( DAOManager dao ) {
        this.dao =dao;
    }


    public boolean inserta( Message message ){
        // set date => to dateTime
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
        String dateSent = dateFormat.format( message.getDateSent() );

        // query
        String querySql = String.format("""
                INSERT INTO messages( idMessage, emitter, receptor, content, dateSent, dateRead, isDeleteEmitter, isDeleteReceptor )
                VALUES( %d, %d, %d, "%s", "%s" , %s ,%s, %s  )
                """
                , message.getId()
                , message.getEmitter().getId()
                , message.getReceptor().getId()
                , message.getContent()
                , dateSent
                , "default"
                , "default"
                , "default"

        );

        try {
            // open dao
            dao.open();
            // connection with query
            Statement stmt = dao.getConn().createStatement();
            // query set
            stmt.executeUpdate( querySql );
            System.out.println("sentencia enviada mensajeria");
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

    // set a message is read
    public boolean updateSetReadMessage( int idMessage ){
        // set date => to dateTime
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
        String dateFin = dateFormat.format( new Date() );

        int checkRowUpdate = 0;

        try {
            // open dao
            dao.open();
            // set query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    UPDATE messages
                        SET dateRead = ?
                    WHERE idMessage = ? 
                    """);
            // set vauel of query
            stmt.setString( 1, dateFin );
            stmt.setInt(2, idMessage );
            // get result si la tupla fue modificada
            checkRowUpdate = stmt.executeUpdate();

            // close dao y query
            dao.close();
            stmt.close();
        } catch (Exception e) {
            System.out.printf("Error en la modificacion de los mensajes al read la : %s", e );
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

    // set delete a message
    public boolean deleteMessage( int idMessage, int idUser ){

        int checkRowUpdate = 0;

        try {
            // open dao
            dao.open();
            // set query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    UPDATE messages
                    SET isDeleteEmitter = CASE WHEN idMessage = ? AND emitter = ?  THEN TRUE ELSE isDeleteEmitter END,
                    	isDeleteReceptor = CASE WHEN idMessage = ? AND receptor = ?  THEN TRUE ELSE isDeleteReceptor END;
                    """);
            // set vauel of query
            stmt.setInt( 1, idMessage );
            stmt.setInt(2, idUser );
            stmt.setInt( 3, idMessage );
            stmt.setInt(4, idUser );
            // get result si la tupla fue modificada
            checkRowUpdate = stmt.executeUpdate();

            // close dao y query
            dao.close();
            stmt.close();
        } catch (Exception e) {
            System.out.printf("Error en la modificacion de los mensajes al cerrar la : %s", e );
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



    /* Searching todas los mensajes del usuario pasando su id*/
    public ArrayList<Message> readMessagebyUser(int idUser){
        // creamos las arrayIncidecnias
        ArrayList<Message> messagesUser = new ArrayList<>();

        try {
            // open dao
            dao.open();
            // prepare a query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT *
                    FROM messages
                    WHERE ( emitter = ? AND isDeleteEmitter = FALSE ) OR ( receptor = ? AND isDeleteReceptor = FALSE )
                    """ );
            // Se asigna los valores de busqueda
            stmt.setInt( 1, idUser );
            stmt.setInt( 2, idUser );

            // Extraemos el resusltado
            ResultSet resultado = stmt.executeQuery();
            // contruimos desde los valores el array de incidencias
            while ( resultado.next() ){
                messagesUser.add(
                        new Message(
                                resultado.getInt("idMessage"),
                                new User( resultado.getInt("emitter"), "",""),
                                new User( resultado.getInt("receptor"), "",""),
                                resultado.getString("content"),
                                resultado.getTimestamp("dateSent"),
                                resultado.getTimestamp("dateRead"),
                                resultado.getBoolean("isDeleteEmitter"),
                                resultado.getBoolean("isDeleteReceptor")

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

        return messagesUser;

    }


    public boolean existeID( int idMessage ){
        // variable si existe
        boolean existe = true;

        try {
            // open dao
            dao.open();
            // Set pre the query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                SELECT EXISTS(
                    SELECT * FROM messages
                    WHERE idMessage = ? );
                """);
            // set valor to search
            stmt.setInt( 1, idMessage );
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




}
