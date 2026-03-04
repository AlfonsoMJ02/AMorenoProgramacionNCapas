package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.ML.Colonia;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOJPAImplementacion implements IColoniaJPA{
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public Result<Colonia> GetAll(int idMunicipio) {
        Result result = new Result();
        
        try {
            TypedQuery<com.digis01.AMorenoProgramacionNCapasMaven.JPA.Colonia> query = 
                    entityManager.createQuery("FROM Colonia c WHERE c.Municipio.IdMunicipio = :idMunicipio",
                            com.digis01.AMorenoProgramacionNCapasMaven.JPA.Colonia.class);
            
            query.setParameter("idMunicipio", idMunicipio);
            
            List<com.digis01.AMorenoProgramacionNCapasMaven.JPA.Colonia> municipioJPA = query.getResultList();

            List<Colonia> coloniaML = municipioJPA.stream()
                    .map(estado -> modelMapper.map(estado, Colonia.class))
                    .collect(Collectors.toList());
            
            result.objects = coloniaML;
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
}
