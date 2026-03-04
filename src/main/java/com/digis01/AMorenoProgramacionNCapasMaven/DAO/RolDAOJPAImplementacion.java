package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.ML.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Rol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RolDAOJPAImplementacion implements IRolJPA {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result<Rol> GetAll() {

        Result<Rol> result = new Result<>();

        try {

            TypedQuery<com.digis01.AMorenoProgramacionNCapasMaven.JPA.Rol> query =
                    entityManager.createQuery("FROM Rol", com.digis01.AMorenoProgramacionNCapasMaven.JPA.Rol.class);

            List<com.digis01.AMorenoProgramacionNCapasMaven.JPA.Rol> rolesJPA = query.getResultList();

            List<Rol> rolesML = rolesJPA.stream()
                    .map(rol -> modelMapper.map(rol, Rol.class))
                    .collect(Collectors.toList());

            result.objects = rolesML;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }
}
