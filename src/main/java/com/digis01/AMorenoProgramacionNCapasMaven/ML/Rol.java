package com.digis01.AMorenoProgramacionNCapasMaven.ML;

import jakarta.validation.constraints.Min;

public class Rol {
    
    @Min(value = 1, message = "Selecciona un rol")
    private int IdRol;

    private String Nombre;
    
    public Rol(){
        
    }
    public int getIdRol(){
        return IdRol;
    }
    
    public void setIdRol(int IdRol){
        this.IdRol = IdRol;
    }
    
    public String getNombre(){
        return Nombre;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
}
