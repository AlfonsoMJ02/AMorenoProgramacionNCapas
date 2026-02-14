package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.ML.Colonia;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOImplementacion {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Result GetAll(int IdMunicipio) {
        Result result = new Result();
        
            try{
            jdbcTemplate.execute("{CALL ColoniaGetAllByIdSP(?,?)}",
                    (CallableStatementCallback<Boolean>) callableStatment ->{
                        callableStatment.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
                        callableStatment.setInt(2, IdMunicipio);
                        callableStatment.execute();
                        
                        ResultSet resultSet = (ResultSet) callableStatment.getObject(1);
                        
                        result.objects = new ArrayList<>();
                        while(resultSet.next()){
                            Colonia colonia = new Colonia();
                            colonia.setIdColonia(resultSet.getInt("IdColonia"));
                            colonia.setNombre(resultSet.getString("Nombre"));
                            
                            result.objects.add(colonia);
                        }
                        result.correct = true;
                        return true;
                });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
