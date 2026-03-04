package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.ML.Municipio;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOJPAImplementacion implements IMunicipioJPA{

    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public Result<Municipio> GetAll(int idEstado) {
        Result<Municipio> result = new Result();
        
        try {
            TypedQuery<com.digis01.AMorenoProgramacionNCapasMaven.JPA.Municipio> query =
                entityManager.createQuery("FROM Municipio m WHERE m.Estado.IdEstado = :idEstado",
                    com.digis01.AMorenoProgramacionNCapasMaven.JPA.Municipio.class
                );

            query.setParameter("idEstado", idEstado);

            List<com.digis01.AMorenoProgramacionNCapasMaven.JPA.Municipio> estadoJPA = query.getResultList();

            List<Municipio> municipioML = estadoJPA.stream()
                    .map(estado -> modelMapper.map(estado, Municipio.class))
                    .collect(Collectors.toList());

            result.objects = municipioML;
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
}
