package com.proyectoSpring.fullstack.Model;

import java.util.ArrayList;
import java.util.List;

public class Administrador {

    // Atributos
    private String nombre;
    private String email;
    private List<String> permisos;

    // Constructor
    public Administrador(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
        this.permisos = new ArrayList<>();
    }

    // Métodos para gestión de cuentas de usuario
    public void crearCuenta(String usuario) {
        System.out.println("Cuenta creada para el usuario: " + usuario);
    }

    public void actualizarCuenta(String usuario) {
        System.out.println("Cuenta actualizada para el usuario: " + usuario);
    }

    public void desactivarCuenta(String usuario) {
        System.out.println("Cuenta desactivada para el usuario: " + usuario);
    }

    public void eliminarCuenta(String usuario) {
        System.out.println("Cuenta eliminada para el usuario: " + usuario);
    }

    // Métodos para asignar y modificar permisos
    public void asignarPermiso(String permiso) {
        permisos.add(permiso);
        System.out.println("Permiso asignado: " + permiso);
    }

    public void modificarPermiso(String permisoAntiguo, String permisoNuevo) {
        if (permisos.contains(permisoAntiguo)) {
            permisos.remove(permisoAntiguo);
            permisos.add(permisoNuevo);
            System.out.println("Permiso modificado de " + permisoAntiguo + " a " + permisoNuevo);
        } else {
            System.out.println("El permiso " + permisoAntiguo + " no existe.");
        }
    }

    // Métodos para monitoreo del sistema
    public void visualizarEstadoSistema() {
        System.out.println("Estado del sistema: Operativo");
    }

    public void recibirAlertas(String alerta) {
        System.out.println("Alerta recibida: " + alerta);
    }

    public void monitorizarRendimiento() {
        System.out.println("Monitorizando rendimiento del sistema...");
    }

    // Métodos para copias de seguridad
    public void realizarCopiaSeguridad() {
        System.out.println("Copia de seguridad realizada con éxito.");
    }

    public void restaurarDatos() {
        System.out.println("Datos restaurados con éxito.");
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<String> permisos) {
        this.permisos = permisos;
    }
}