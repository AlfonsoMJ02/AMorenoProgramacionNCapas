package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.ML.Estado;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOImplementacion {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    public Result GetAll(int IdPais) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("{CALL EstadoGetAllByIdSP(?,?)}",
                    (CallableStatementCallback<Boolean>) callableStatement -> {
                        callableStatement.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
                        callableStatement.setInt(2, IdPais);
                        callableStatement.execute();

                        ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                        result.objects = new ArrayList<>();
                        while (resultSet.next()) {
                            Estado estado = new Estado();
                            estado.setIdEstado(resultSet.getInt("IdEstado"));
                            estado.setNombre(resultSet.getString("Nombre"));

                            result.objects.add(estado);
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
