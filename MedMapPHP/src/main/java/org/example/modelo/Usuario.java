package org.example.modelo;

public abstract class Usuario {
    private String nombreCompleto;
    private String username;
    private String password;
    private String email;
    private String celular;

    public Usuario() {
    }

    public Usuario(String nombreCompleto, String username, String password, String email, String celular) {
        this.nombreCompleto = nombreCompleto;
        this.username = username;
        this.password = password;
        this.email = email;
        this.celular = celular;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public boolean iniciarSesion(String usuario, String contraseña) {
        return this.username.equals(usuario) && this.password.equals(contraseña);
    }

    public void recuperarContraseña(String email) {
        System.out.println("Enviando enlace de recuperación a: " + email);
    }

    public void cambiarContraseña(String nuevaContraseña) {
        this.password = nuevaContraseña;
        System.out.println("Contraseña actualizada exitosamente");
    }

    public void cerrarSesion() {
        System.out.println("Cerrando sesión de: " + username);
    }

    public abstract void mostrarMenuPrincipal();



}
