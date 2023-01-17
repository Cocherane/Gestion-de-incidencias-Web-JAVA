package DAO;

import models.Tecnico;
import models.Usuario;

public interface DAOTecnicos {
    public Tecnico readByEmailClave(String email, String clave);
    public boolean updateClave(Tecnico t, String nuevaClave);
    public boolean insert(Tecnico t);
    public Tecnico readByEmail(String email);
    public Tecnico readById(int idTecnico);
    public boolean delete(int idTecnico);
}
