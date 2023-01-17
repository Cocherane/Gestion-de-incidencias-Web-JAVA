package DAO;

import models.Incidencia;
import models.Tecnico;
import models.TecnicoDataClass;
import models.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOTecnicosSql implements DAOTecnicos{

    private DAOManager dao;

    public DAOTecnicosSql( DAOManager dao ) {
        this.dao =dao;
    }

    @Override
    public Tecnico readByEmailClave(String email, String clave) {
        ArrayList<Incidencia> incidencias = new ArrayList<>();
        Tecnico tecnico = null;
        String sentencia;
        sentencia = "select * from tecnicos where email = ? and SHA1( clave ) = SHA1( ? ) ;";
        open();
        try {
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            ps.setString(1, email);
            ps.setString(2, clave);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    tecnico = new Tecnico(rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apel"),
                            rs.getString("clave"),
                            rs.getString("email"),
                            incidencias);
                }
            }
            close();
        } catch (SQLException ex){
            close();
            ex.printStackTrace();
        }
        return tecnico;
    }

    @Override
    public boolean updateClave(Tecnico t, String nuevaClave) {
        String sentencia;
        sentencia = "UPDATE tecnicos set clave = '"
                + nuevaClave + "' where id = "
                + t.getId() + ";";
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
    public boolean insert(Tecnico t) {
        String sentencia;
        sentencia = "INSERT INTO tecnicos VALUES("
                + t.getId() + ",'"
                + t.getNombre() + "','"
                + t.getApel() + "','"
                + t.getClave() + "','"
                + t.getEmail() +  "');";
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

    @Override
    public Tecnico readByEmail(String email) {
        ArrayList<Incidencia> incidencias = new ArrayList<>();
        Tecnico tecnico = null;
        String sentencia;
        sentencia = "select * from tecnicos where email = ?;";
        open();
        try {
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    tecnico = new Tecnico(rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apel"),
                            rs.getString("clave"),
                            rs.getString("email"),
                            incidencias);
                }
            }
            close();
        } catch (SQLException ex){
            close();
            ex.printStackTrace();
        }
        return tecnico;
    }

    @Override
    public Tecnico readById(int idTecnico) {
        ArrayList<Incidencia> incidencias = new ArrayList<>();
        Tecnico tecnico = null;
        String sentencia;
        sentencia = "select * from tecnicos where id = ?;";
        open();
        try {
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            ps.setInt(1, idTecnico);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    tecnico = new Tecnico(idTecnico,
                            rs.getString("nombre"),
                            rs.getString("apel"),
                            rs.getString("clave"),
                            rs.getString("email"),
                            incidencias);
                }
                close();
            }

        } catch (SQLException ex){
            close();
            ex.printStackTrace();
        }
        return tecnico;
    }

    @Override
    public boolean delete(int idTecnico) {
        String sentencia;
        sentencia = "DELETE from tecnicos where id = "
                + idTecnico + ";";
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

    /* consultas hechas por ariel NO TOCAR*/
    public ArrayList<TecnicoDataClass> readAllTecnicos(){
        // array de tecnicos dataclas
        ArrayList<TecnicoDataClass> tecnicoDataClasses = new ArrayList<>();
        try {
            // open dao
            dao.open();
            // prepare query
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT T.id, T.nombre, T.apel, T.email,
                           SUM( CASE WHEN solucion IS NULL AND I.id IS NOT NULL THEN 1
                               ELSE 0 END
                               ) 'inAbierta',
                           SUM( CASE WHEN solucion IS NOT NULL THEN 1
                               ELSE 0 END
                               ) 'inCerrada',
                           IFNULL( AVG(prioridad), 0) 'prioridadMedia'
                    FROM tecnicos T LEFT JOIN incidencias I ON T.id = I.idTecnico
                    GROUP BY T.id;
                    """);
            // get values
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()){
                tecnicoDataClasses.add(
                        new TecnicoDataClass(
                                  resultSet.getInt(1)
                                , resultSet.getString(2)
                                , resultSet.getString(3)
                                , resultSet.getString(4)
                                , resultSet.getInt(5)
                                , resultSet.getInt( 6)
                                , resultSet.getInt(7)
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
        return tecnicoDataClasses;

    }
}
