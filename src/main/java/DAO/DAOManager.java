package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOManager {
    private Connection conn;
    private final String URL;
    private final String USER;
    private final String PASS;
    private static DAOManager singlenton;

    private DAOManager() {
        this.conn = null;
        this.URL ="jdbc:mysql://127.0.0.1:3308/fernantickets";
        this.USER = "root";
        this.PASS = "root";
    }
    public static DAOManager getSinglentonInstance(){
        if (singlenton == null){
            singlenton = new DAOManager();
            return singlenton;
        }else return singlenton;
    }

    public Connection getConn() {
        return conn;
    }

    public void open() throws Exception{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
        }catch (Exception e){
            throw e;
        }
    }

    public void close() throws SQLException {
        try {
            if (this.conn != null) this.conn.close();
        }catch (Exception e){
            throw e;
        }
    }
}
