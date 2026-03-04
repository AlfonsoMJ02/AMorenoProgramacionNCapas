package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.ML.Municipio;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Result;

public interface IMunicipioJPA {
    Result GetAll(int idEstado);
}
