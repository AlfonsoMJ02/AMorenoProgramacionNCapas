package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.ML.Direccion;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Usuario;

public interface IUsuarioJPA {
    Result GetAll();
    Result<Usuario> Add(Usuario usuario);
    Result Delete(int idDireccion);
    Result DeleteUser(int idUsuario);
    Result AddDireccion(Direccion direccion);
    Result GetByIdDireccion(int idDireccion);
}
