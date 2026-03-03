package com.digis01.AMorenoProgramacionNCapasMaven.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Rol {
    @Id
    @Column(name = "idrol")
    private int IdRol;
    
    @Column(name = "nombre")
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
