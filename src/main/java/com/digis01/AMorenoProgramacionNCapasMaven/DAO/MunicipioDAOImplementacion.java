package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.ML.Municipio;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOImplementacion {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public Result GetAll(int IdEstado){
        Result result = new Result();
        
        try{
            jdbcTemplate.execute("{CALL MunicipioGetAllByIdSP(?,?)}",
                    (CallableStatementCallback<Boolean>)  callableStatement ->{
                        callableStatement.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
                        callableStatement.setInt(2, IdEstado);
                        callableStatement.execute();
                
                        ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                        
                        result.objects = new ArrayList<>();
                        while (resultSet.next()){
                            Municipio municipio = new Municipio();
                            municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                            municipio.setNombre(resultSet.getString("Nombre"));
                            
                            result.objects.add(municipio);
                        }
                        result.correct = true;
                        return true;
                    });
        } catch (Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
        
    }
}
