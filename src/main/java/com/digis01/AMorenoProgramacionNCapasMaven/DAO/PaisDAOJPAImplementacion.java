package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.ML.Pais;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaisDAOJPAImplementacion implements IPaisJPA{
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public Result<Pais> GetAll() {
        Result<Pais> result = new Result();
        
        try {
            TypedQuery<com.digis01.AMorenoProgramacionNCapasMaven.JPA.Pais> query =
                    entityManager.createQuery("FROM Pais", com.digis01.AMorenoProgramacionNCapasMaven.JPA.Pais.class);
            
            List<com.digis01.AMorenoProgramacionNCapasMaven.JPA.Pais> paisJPA = query.getResultList();
            
            List<Pais> paisML = paisJPA.stream()
                    .map(pais -> modelMapper.map(pais, Pais.class))
                    .collect(Collectors.toList());
            
            result.objects = paisML;
            result.correct = true;
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
}
