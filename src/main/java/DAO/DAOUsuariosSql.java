package DAO;

import models.Incidencia;
import models.Usuario;
import models.UsuarioDataClass;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOUsuariosSql implements DAOUsuarios{
    private DAOManager dao;

    public DAOUsuariosSql( DAOManager dao ) {
        this.dao =dao;
    }


    @Override
    public boolean insert(Usuario u) {
        String sentencia;
        sentencia = "INSERT INTO usuarios VALUES("
                + u.getId() + ",'"
                + u.getNombre() + "','"
                + u.getApel() + "', '"
                + u.getClave() + "','"
                + u.getEmail() + "',"
                + u.getToken() + ","
                + u.isTokenValidated() + ");";
        open();

        try (Statement stmt = dao.getConn().createStatement()){
            stmt.executeUpdate(sentencia);
            close();
            return true;
        }catch (SQLException ex){

            close();
            return false;
        }
    }
    //¡¡¡¡¡¡¡¡¡¡¡¡¡¡Se le debe añadir daoIncidecnais para el array de incidencias!!!!!!!!!!!!!!!!
    @Override
    public Usuario readById(int idUsuario) {
        ArrayList<Incidencia> incidencias = new ArrayList<>();
        Usuario usuario = null;
        String sentencia;
        sentencia = "select * from usuarios where id = ?;";
            open();
            try {
                PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
                ps.setInt(1, idUsuario);

                try (ResultSet rs = ps.executeQuery()){
                    if (rs.next()){
                        usuario = new Usuario(idUsuario,
                                rs.getString("nombre"),
                                rs.getString("apel"),
                                rs.getString("clave"),
                                rs.getString("email"),
                                rs.getInt("token"),
                                rs.getBoolean("tokenValidated"),
                                incidencias);
                    }
                    close();
                }

            } catch (SQLException ex){
                close();
                ex.printStackTrace();
            }
            return usuario;
        }


    //¡¡¡¡¡¡¡¡¡¡¡¡¡¡Se le debe añadir daoIncidecnais para el array de incidencias!!!!!!!!!!!!!!!!
    @Override
    public Usuario readByEmailClave(String email, String clave) {
        ArrayList<Incidencia> incidencias = new ArrayList<>();
        Usuario usuario = null;
        String sentencia;
        sentencia = "select * from usuarios where email = ? and SHA1( clave ) = SHA1( ? ) ;";
        open();
        try {
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            ps.setString(1, email);
            ps.setString(2, clave);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    usuario = new Usuario(rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apel"),
                            rs.getString("clave"),
                            rs.getString("email"),
                            rs.getInt("token"),
                            rs.getBoolean("tokenValidated"),
                            incidencias);
                }
            }
            close();
        } catch (SQLException ex){
            close();
            ex.printStackTrace();
        }
        return usuario;
    }

    @Override
    public boolean updateClave(Usuario u, String nuevaClave) {
        String sentencia;
        sentencia = "UPDATE usuarios set clave = '"
                + nuevaClave + "',tokenValidated = false, token = "+ u.getToken() +" "+
                "  where id = "
                + u.getId() + ";";
        open();
        try (Statement stmt = dao.getConn().createStatement()){
            stmt.executeUpdate(sentencia);
            close();
            return true;
        }catch (SQLException ex){
            close();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateTokenValidado(Usuario u) {
        String sentencia;
        sentencia = "UPDATE usuarios set tokenValidated = "
                + u.isTokenValidated()  + " where id = "
                + u.getId() + ";";
        open();
        try (Statement stmt = dao.getConn().createStatement()){
            stmt.executeUpdate(sentencia);
            close();
            return true;
        }catch (SQLException ex){
            close();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Usuario readByEmail(String email) {
        ArrayList<Incidencia> incidencias = new ArrayList<>();
        Usuario usuario = null;
        String sentencia;
        sentencia = "select * from usuarios where email = ?;";
        open();
        try {
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    usuario = new Usuario(rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apel"),
                            rs.getString("clave"),
                            rs.getString("email"),
                            rs.getInt("token"),
                            rs.getBoolean("tokenValidated"),
                            incidencias);
                }
            }
            close();
        } catch (SQLException ex){
            close();
            ex.printStackTrace();
        }
        return usuario;
    }

    public void open(){
        try{
            dao.open();
        }catch (Exception e){
            System.out.println("ERROR");
        }
    }
    public void close(){
        try {
            dao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /* metodos hechos por ariel NO TOCAR*/

    /* Busca los usuarios y regresa un resumen de lso usuario en UsuarioDataClass*/
    public ArrayList<UsuarioDataClass> readAllUsuarios(  ){
        // SET ARRAYLIS usuarioDataClass
        ArrayList<UsuarioDataClass> usuarioDataClasses = new ArrayList<>();
        try {
            // dao open
            dao.open();
            // prepare Sentence
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                SELECT U.id, nombre, apel, email ,COUNT( I.id ) AS 'incidenciasAbiertas'
                FROM usuarios U LEFT JOIN incidencias I ON U.id = I.idUsuario
                GROUP BY U.id
                """);
            // get value return
            ResultSet resultSet = stmt.executeQuery();

            while ( resultSet.next( )){

                usuarioDataClasses.add(
                        new UsuarioDataClass(
                                  resultSet.getInt(1)
                                , resultSet.getString( 2)
                                , resultSet.getString(3)
                                , resultSet.getString(4)
                                , resultSet.getInt(5)
                        )
                );

            }

            // close dao and query
            dao.close();
            stmt.close();


        } catch (Exception e) {
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
        return usuarioDataClasses;


    }



    /* busca un usuario por una incidecnia */
    public Usuario readUsuarioByIdIncidencia(int idIncidencia) {
        // set a User
        Usuario usuario = null;

        try {
            // open dao
            dao.open();
            // prepared a query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT U.*
                    FROM usuarios U INNER JOIN incidencias I ON U.id = I.idUsuario
                    WHERE I.id = ?
                    """);
            // set value idIncidecnia
            stmt.setInt(1, idIncidencia );
            // get value of query
            ResultSet resultSet = stmt.executeQuery();
            if ( resultSet.next() ){
                usuario = new Usuario(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apel"),
                        resultSet.getString("clave"),
                        resultSet.getString("email"),
                        resultSet.getInt("token"),
                        resultSet.getBoolean("tokenValidated"),
                        null);
            }
            // dao close
            dao.close();
            stmt.close();

        } catch (Exception e) {
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
        return usuario;

    }

}
