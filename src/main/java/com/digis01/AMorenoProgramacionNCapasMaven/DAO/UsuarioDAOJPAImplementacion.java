package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Usuario;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Rol;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Colonia;
import com.digis01.AMorenoProgramacionNCapasMaven.JPA.Direccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioDAOJPAImplementacion implements IUsuarioJPA {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GetAll() {

        Result result = new Result();

        try {

            TypedQuery<Usuario> query
                    = entityManager.createQuery("FROM Usuario", Usuario.class);

            List<Usuario> usuarios = query.getResultList();

            List<com.digis01.AMorenoProgramacionNCapasMaven.ML.Usuario> usuariosML = usuarios.stream()
                    .map(usuario -> modelMapper.map(usuario,
                    com.digis01.AMorenoProgramacionNCapasMaven.ML.Usuario.class))
                    .collect(Collectors.toList());

            result.objects = usuariosML;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result<com.digis01.AMorenoProgramacionNCapasMaven.ML.Usuario> Add(com.digis01.AMorenoProgramacionNCapasMaven.ML.Usuario usuario) {

        Result<com.digis01.AMorenoProgramacionNCapasMaven.ML.Usuario> result = new Result<>();

        try {

            Usuario usuarioJPA = modelMapper.map(usuario, Usuario.class);

            Rol rol = entityManager.find(Rol.class,
                    usuario.getRol().getIdRol());
            usuarioJPA.setRol(rol);

            Direccion direccion = new Direccion();
            direccion.setCalle(usuario.getdireccion().getCalle());
            direccion.setNumeroInterior(usuario.getdireccion().getNumeroInterior());
            direccion.setNumeroExterior(usuario.getdireccion().getNumeroExterior());

            Colonia colonia = entityManager.find(Colonia.class,
                    usuario.getdireccion().getColonia().getIdColonia());

            direccion.setColonia(colonia);

            direccion.setUsuario(usuarioJPA);

            usuarioJPA.getDirecciones().add(direccion);

            entityManager.persist(usuarioJPA);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result Delete(int idDireccion) {

        Result result = new Result();

        try {

            Direccion direccion = entityManager.find(Direccion.class, idDireccion);

            if (direccion != null) {

                Usuario usuario = direccion.getUsuario();
                usuario.getDirecciones().remove(direccion);

                entityManager.remove(direccion);

                result.correct = true;

            } else {
                result.correct = false;
                result.errorMessage = "Dirección no encontrada";
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result DeleteUser(int idUsuario) {
        Result result = new Result();

        try {
            com.digis01.AMorenoProgramacionNCapasMaven.JPA.Usuario usuario
                    = entityManager.find(com.digis01.AMorenoProgramacionNCapasMaven.JPA.Usuario.class, idUsuario);

            if (usuario != null) {

                entityManager.remove(usuario);

                result.correct = true;

            } else {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }
}
