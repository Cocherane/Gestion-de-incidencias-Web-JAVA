package DAO;

import models.Admin;

public interface DAOAdmin {

    public Admin readByEmailClave(String email, String clave);
}
