package org.example.modelo;

import java.util.HashMap;
import java.util.Map;

public class SistemaTurismo {

    private Map<String, Usuario> usuarios;
    private Map<String, LugarTuristico> lugares;


    public SistemaTurismo() {
        this.usuarios = new HashMap<>();
        this.lugares = new HashMap<>();
        inicializarLugares();
    }

    private void inicializarLugares() {
        lugares.put("Parque Arví", new LugarTuristico("Parque Arví", "Norte de Medellín"));
        lugares.put("Pueblito Paisa", new LugarTuristico("Pueblito Paisa", "Cerro Nutibara"));
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Map<String, LugarTuristico> getLugares() {
        return lugares;
    }

    public void setLugares(Map<String, LugarTuristico> lugares) {
        this.lugares = lugares;
    }

    public void registrarUsuario(String nombreCompleto, String username, String password, String email, String celular) {
        usuarios.put(username, new UsuarioRegistrado(nombreCompleto, username, password, email, celular));
    }

    public Usuario login(String username, String password) {
        Usuario usuario = usuarios.get(username);
        if(usuario != null && usuario.iniciarSesion(username, password)) {
            return usuario;
        }
        return null;
    }

    public LugarTuristico buscarLugar(String nombre) {
        return lugares.get(nombre);
    }
}
