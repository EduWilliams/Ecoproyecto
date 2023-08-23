package com.Torneo.TorneoDeLatas.entidades;

import com.Torneo.TorneoDeLatas.enumeraciones.Rol;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String email;
    @Column(name = "Dni")
    private Integer dni;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Apellido")
    private String apellido;
    private double ecopuntos;
    private String password;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @Temporal(TemporalType.DATE)
    private Date alta;
    @OneToOne
    private Imagen imagen;

    public Usuario() {
    }

    public Usuario(String id, String email, Integer dni, String nombre, String apellido, double ecopuntos, String password, Rol rol, Date alta, Imagen imagen) {
        this.id = id;
        this.email = email;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.ecopuntos = ecopuntos;
        this.password = password;
        this.rol = rol;
        this.alta = alta;
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public double getEcopuntos() {
        return ecopuntos;
    }

    public void setEcopuntos(double ecopuntos) {
        this.ecopuntos = ecopuntos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

   
}
