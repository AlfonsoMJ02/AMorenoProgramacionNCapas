package com.digis01.AMorenoProgramacionNCapasMaven.ML;

import jakarta.validation.constraints.Min;

public class Pais {
    
    @Min(value = 0, message = "Selecciona un pais")
    private Integer IdPais;
    
    private String Nombre;
    
    public Pais(){
    
    }
    public Integer getIdPais(){
        return IdPais;
    }
    
    public void setIdPais(Integer IdPais){
        this.IdPais = IdPais;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
}
