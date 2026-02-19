package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.ML.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Usuario;

public interface IUsuario {
    Result GetAll();
    public Result GetById(int idUsuario);
    Result Update(Usuario usuario);
    Result Add(Usuario usuario);
    Result Delete(int idUsuario);
}
