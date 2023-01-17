package DAO;

import models.Admin;
import models.Incidencia;
import models.Tecnico;
import models.TecnicoDataClass;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOAdminSql implements DAOAdmin{

    private DAOManager dao;

    public DAOAdminSql( DAOManager dao ) {
        this.dao =dao;
    }

    @Override
    public Admin readByEmailClave(String email, String clave) {
        Admin admin = null;

        try {
            // open dao
            dao.open();
            // set query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                SELECT *
                FROM admins
                WHERE email = ? AND SHA1( clave ) = SHA1( ? )
                """);

            // Set value in a query
            stmt.setString(1, email.toLowerCase() );
            stmt.setString(2, clave);

            // Set el resultado a una variable
            ResultSet resultSet = stmt.executeQuery();
            // extraer el valor de las tupla

            if ( resultSet.next() ){
                admin = new Admin(
                        resultSet.getInt("id")
                        , resultSet.getString( "name")
                        , resultSet.getString( "apels")
                        , resultSet.getString("clave")
                        , resultSet.getString("email")
                );

            }




            // close dao y la query
            dao.close();
            stmt.close();
        } catch (Exception e) {
            System.out.printf("Error en la busqueda de un admin por email y password : %s", e);
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

        return admin;
    }


    public boolean updateClave( int id, String nuevaClave) {
        // check if was
        int isUpdatedPass = 0;
        try {
            // open dao
            dao.open();
            // set query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                UPDATE admins
                set  clave  =  ? 
                WHERE id = ?
                """);

            // Set value in a query
            stmt.setString(1, nuevaClave );
            stmt.setInt(2, id);

            // Set el resultado a una variable
            isUpdatedPass = stmt.executeUpdate();

            // close dao y la query
            dao.close();
            stmt.close();
        } catch (Exception e) {
            System.out.printf("Error en admind DAOAdmins update clave : %s", e);
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

        // it's updated  1 yes 0 no
        return isUpdatedPass == 1;

    }

    // buscar un admin por el email
    public Admin readByEmail(String email) {
        Admin admin = null;

        try {
            // open dao
            dao.open();
            // set query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                SELECT *
                FROM admins
                WHERE email = ? 
                """);

            // Set value in a query
            stmt.setString(1, email.toLowerCase() );

            // Set el resultado a una variable
            ResultSet resultSet = stmt.executeQuery();
            // extraer el valor de las tupla

            if ( resultSet.next() ){
                admin = new Admin(
                        resultSet.getInt("id")
                        , resultSet.getString( "name")
                        , resultSet.getString( "apels")
                        , resultSet.getString("clave")
                        , resultSet.getString("email")
                );

            }




            // close dao y la query
            dao.close();
            stmt.close();
        } catch (Exception e) {
            System.out.printf("Error en la busqueda de un admin por email : %s", e);
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

        return admin;
    }

    public Admin readById( int idAdmin ) {
        Admin admin = null;

        try {
            // open dao
            dao.open();
            // set query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                SELECT *
                FROM admins
                WHERE id = ? 
                """);

            // Set value in a query
            stmt.setInt(1, idAdmin );


            // Set el resultado a una variable
            ResultSet resultSet = stmt.executeQuery();
            // extraer el valor de las tupla

            if ( resultSet.next() ){
                admin = new Admin(
                        resultSet.getInt("id")
                        , resultSet.getString( "name")
                        , resultSet.getString( "apels")
                        , resultSet.getString("clave")
                        , resultSet.getString("email")
                );

            }




            // close dao y la query
            dao.close();
            stmt.close();
        } catch (Exception e) {
            System.out.printf("Error en la busqueda de un admin por id : %s", e);
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

        return admin;
    }


    public ArrayList<Admin> readAllAdmins() {

        // array de tecnicos dataclas
        ArrayList<Admin> admisData = new ArrayList<>();
        try {
            // open dao
            dao.open();
            // prepare query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT *
                    FROM admins
                    """);
            // get values
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()){
                admisData.add(
                        new Admin(
                                resultSet.getInt("id")
                                , resultSet.getString( "name")
                                , resultSet.getString( "apels")
                                , resultSet.getString("clave")
                                , resultSet.getString("email")
                        )
                );
            }
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
        return admisData;
    }
}
