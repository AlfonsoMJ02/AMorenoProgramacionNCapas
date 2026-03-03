package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Usuario;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class UsuarioDAOJPAImplementacion implements IUsuarioJPA{

    @Autowired
    private EntityManager entityManager;
    

    @Override
    public Result GetAll() {

        Result<Usuario> result = new Result<>();

        try {
//            TypedQuery<Usuario> query = entityManager.createQuery(
//                    "SELECT DISTINCT usuario FROM Usuario usuario " +
//                    "LEFT JOIN FETCH usuario.Rol " +
//                    "LEFT JOIN FETCH usuario.Direcciones direccion " +
//                    "LEFT JOIN FETCH direccion.Colonia colonia " +
//                    "LEFT JOIN FETCH colonia.Municipio municipio " +
//                    "LEFT JOIN FETCH municipio.Estado estado " +
//                    "LEFT JOIN FETCH estado.Pais " +
//                    "ORDER BY usuario.IdUsuario",
//                    Usuario.class
//            );

            TypedQuery<Usuario> query = entityManager.createQuery("FROM Usuario", Usuario.class);

            List<Usuario> usuarios = query.getResultList();
            
            //List<com.digis01.AMorenoProgramacionNCapasMaven.ML.Usuario> usuariosML = ModelMap usuarios;

            result.objects = usuarios;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }
}