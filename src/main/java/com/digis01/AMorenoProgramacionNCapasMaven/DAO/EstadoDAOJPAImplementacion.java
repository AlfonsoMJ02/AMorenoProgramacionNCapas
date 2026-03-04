package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.ML.Estado;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOJPAImplementacion implements IEstadoJPA {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result<Estado> GetAll(int idPais) {

        Result<Estado> result = new Result<>();

        try {

            TypedQuery<com.digis01.AMorenoProgramacionNCapasMaven.JPA.Estado> query =
                    entityManager.createQuery(
                            "FROM Estado e WHERE e.Pais.IdPais = :idPais",
                            com.digis01.AMorenoProgramacionNCapasMaven.JPA.Estado.class
                    );

            query.setParameter("idPais", idPais);

            List<com.digis01.AMorenoProgramacionNCapasMaven.JPA.Estado> estadoJPA =
                    query.getResultList();

            List<Estado> estadoML = estadoJPA.stream()
                    .map(estado -> modelMapper.map(estado, Estado.class))
                    .collect(Collectors.toList());

            result.objects = estadoML;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }
}
