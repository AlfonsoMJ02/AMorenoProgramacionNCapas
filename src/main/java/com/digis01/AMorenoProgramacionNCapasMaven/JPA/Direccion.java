package com.digis01.AMorenoProgramacionNCapasMaven.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Direccion {

    @Id
    @Column(name = "iddireccion")
    private int IdDireccion;

    @Column(name = "calle")
    private String Calle;

    @Column(name = "numerointerior")
    private String NumeroInterior;

    @Column(name = "numeroexterior")
    private String NumeroExterior;

    @ManyToOne
    @JoinColumn(name = "idcolonia")
    private Colonia Colonia;

    @ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario Usuario;


    public Colonia getColonia() {
        return Colonia;
    }

    public void setColonia(Colonia Colonia) {
        this.Colonia = Colonia;
    }

    public Usuario getUsuario() {
        return Usuario;
    }

    public void setUsuario(Usuario Usuario) {
        this.Usuario = Usuario;
    }

    
    public int getIdDireccion() {
        return IdDireccion;
    }

    public void setIdDireccion(int IdDireccion) {
        this.IdDireccion = IdDireccion;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String Calle) {
        this.Calle = Calle;
    }

    public String getNumeroInterior() {
        return NumeroInterior;
    }

    public void setNumeroInterior(String NumeroInterior) {
        this.NumeroInterior = NumeroInterior;
    }

    public String getNumeroExterior() {
        return NumeroExterior;
    }

    public void setNumeroExterior(String NumeroExterior) {
        this.NumeroExterior = NumeroExterior;
    }  
    
}
    
    