package DAO;

import models.Usuario;

public interface DAOUsuarios {
    public boolean insert(Usuario u);
    public Usuario readById(int idUsuario);
    public Usuario readByEmailClave(String email, String clave);
    public boolean updateClave(Usuario u, String nuevaClave);
    public boolean updateTokenValidado(Usuario u);
    public Usuario readByEmail(String email);

}
