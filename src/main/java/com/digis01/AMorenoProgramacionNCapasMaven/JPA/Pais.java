package com.digis01.AMorenoProgramacionNCapasMaven.JPA;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pais {
    
    @Id
    @Column(name = "idpais")
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
