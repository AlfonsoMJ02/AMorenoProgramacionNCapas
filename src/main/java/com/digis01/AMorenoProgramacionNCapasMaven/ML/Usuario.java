package com.digis01.AMorenoProgramacionNCapasMaven.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class Usuario {
   private int IdUsuario;
    
    @Pattern(regexp = "^[\\p{L}]+$", message = "Solo puedes escribir letras en este campo")
    @NotEmpty(message = "Este campo no puede estar vacio")
    @Size(min = 2, max = 50, message = "Debe tener más de dos letras minimo")
    private String Nombre;
    
    @Pattern(regexp = "^[a-zá-úÁ-ÚA-Z\\s]+$", message = "Solo puedes escribir letras en este campo")
    @NotEmpty(message = "Este campo no puede estar vacio")
    @Size(min = 2, max = 50, message = "Debe tener más de dos letras minimo")
    private String ApellidoPaterno;
    
    @Pattern(regexp = "^[a-zá-úÁ-ÚA-Z\\s]+$", message = "Solo puedes escribir letras en este campo")
    @NotEmpty(message = "Este campo no puede estar vacio")
    @Size(min = 2, max = 50, message = "Debe tener más de dos letras minimo")
    private String ApellidoMaterno;
    
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Esta dirección de correo no es válida")
    @NotEmpty(message = "Este campo no puede estar vacío")
    @Size(min = 5, max = 100, message = "Debe tener entre 5 y 100 caracteres")
    private String Email;
    
    
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$", message = "Este campo debe contener al menos un número, una letra mayúscula, una mínuscula y un caracter especial")
    @NotEmpty(message = "Este campo no puede estar vacio")
    @Size(min = 2, max = 20, message = "Debe tener más de 2 Digitos minimo y maximo 20")
    private String Password;
    
    @NotNull(message = "Este campo no puede estar vacio, selecciona una opción")
    private String Sexo;
    
    @Pattern(regexp = "^[0-9]{10}$", message = "Este campo solo debe contener numeros")
    @NotEmpty(message = "Este campo no puede estar vacio")
    @Size(min = 10, max = 10, message = "Debe tener maximo 10 números")
    private String Telefono;
    
    //@Pattern(regexp = "^[0-9]{10}$", message = "Este campo solo debe contener numeros")
    //@NotEmpty(message = "Este campo no puede estar vacio")
    //@Size(min = 10, max = 10, message = "Debe tener maximo 10 números")
    private String Celular;
    
    @Pattern(regexp = "^[A-Z][AEIOU][A-Z]{2}\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])[HM](AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS)[A-Z]{3}[A-Z0-9]\\d$", message = "CURP inválida")
    @NotEmpty(message = "La CURP no puede estar vacía")
    @Size(min = 18, max = 18, message = "La CURP debe tener 18 caracteres")
    private String Curp;
    
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).+$", message = "Debes de al menos escribir un número, una letra mayúscula y una mínuscula")
    @NotEmpty(message = "Este campo no puede estar vacio")
    @Size(min = 5, max = 50, message = "Debe tener más de 5 Digitos minimo y maximo 20")
    private String UserName;
    
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha debe ser anterior al día de hoy")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate FechaNacimiento;
    
    
    public String Image;
    
    @Valid
    private Rol Rol;
    
    @Valid
    private Pais pais;
    
    @Valid
    private Estado estado;
    
    @Valid
    private Municipio municipio;
    
    @Valid
    private Colonia colonia;
    
    @Valid
    private Direccion direccion;
    
    private List<Direccion> Direcciones;
    
    public Colonia getColonia() {
        return colonia;
    }

    public void setColonia(Colonia colonia) {
        this.colonia = colonia;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Usuario() {
        Direcciones = new ArrayList<>();
        Rol = new com.digis01.AMorenoProgramacionNCapasMaven.ML.Rol();
    }
    
    public int getIdUsuario(){
       return IdUsuario;
    }
    
    public void setIdUsuario(int IdUsuario){
        this.IdUsuario = IdUsuario;
    }
    
    public String getNombre(){
        return Nombre;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
    
    public String getApellidoPaterno(){
        return ApellidoPaterno;
    }
    
    public void setApellidoPaterno(String ApellidoPaterno){
        this.ApellidoPaterno = ApellidoPaterno;
    }
    
    public String getApellidoMaterno(){
        return ApellidoMaterno;
    }
    
    public void setApellidoMaterno(String ApellidoMaterno){
        this.ApellidoMaterno = ApellidoMaterno;
    }
    
    public String getEmail(){
        return Email;
    }
    
    public void setEmail(String Email){
        this.Email = Email;
    }
    
    public String getPassword(){
        return Password;
    }
    
    public void setPassword(String Password){
        this.Password = Password;
    }
    
    public String getSexo(){
        return Sexo;
    }
    
    public void setSexo(String Sexo){
        this.Sexo = Sexo;
    }
    
    public String getTelefono(){
        return Telefono;
    }
    
    public void setTelefono(String Telefono){
        this.Telefono = Telefono;
    }
    
    public String getCelular(){
        return Celular;
    }
    
    public void setCelular(String Celular){
        this.Celular = Celular;
    }
    
    public String getCurp(){
        return Curp;
    }
    
    public void setCurp(String Curp){
        this.Curp = Curp;
    }
    
    public String getUserName(){
        return UserName;
    }
    
    public void setUserName(String UserName){
        this.UserName = UserName;
    }
    
    public LocalDate getFechaNacimiento(){
        return FechaNacimiento;
    }
    
    public void setFechaNacimiento(LocalDate FechaNacimiento){
        this.FechaNacimiento = FechaNacimiento;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }
    
    
    
    public Direccion getdireccion(){
        return direccion;
    }
    
    public void setdireccion(Direccion direccion){
        this.direccion = direccion;
    }

    public Rol getRol() {
        return Rol;
    }

    public void setRol(Rol Rol) {
        this.Rol = Rol;
    }

    public List<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(List<Direccion> Direcciones) {
        this.Direcciones = Direcciones;
    }
    
    
}
