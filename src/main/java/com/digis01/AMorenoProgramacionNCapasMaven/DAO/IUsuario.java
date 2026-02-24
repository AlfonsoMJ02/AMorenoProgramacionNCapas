package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.ML.Direccion;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Usuario;

public interface IUsuario {
    Result GetAll();
    public Result GetById(int idUsuario);
    Result Update(Usuario usuario);
    Result Add(Usuario usuario);
    Result Delete(int idUsuario);
    Result GetByIdDireccion(int idDireccion);
    Result AddDireccion(Direccion direccion);
    Result UpdateImagen(int idUsuario, String imagenBase64);
    Result Search(String nombre, String apellidoPaterno, String apellidoMaterno, Integer idRol);
}
