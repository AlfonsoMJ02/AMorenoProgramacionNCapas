package com.digis01.AMorenoProgramacionNCapasMaven.ML;

import jakarta.validation.constraints.NotEmpty;

public class Colonia {
    
    private int IdColonia;
    private String Nombre;
    
    //@NotEmpty(message = "Este campo no puede estar vacio")
    private String CodigoPostal;
    
    public Colonia(){
        
    }
    private Municipio Municipio;

    public Municipio getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(Municipio Municipio) {
        this.Municipio = Municipio;
    }
    
    
    
    public int getIdColonia(){
        return IdColonia;
    }
    
    public void setIdColonia(int IdColonia){
        this.IdColonia = IdColonia;
    }
    
    public String getNombre(){
        return Nombre;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
    
    public String getCodigoPostal(){
        return CodigoPostal;
    }
    
    public void setCodigoPostal(String CodigoPostal){
        this.CodigoPostal = CodigoPostal;
    }
}
