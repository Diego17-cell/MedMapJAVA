package org.example.modelo;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRegistrado extends Usuario{
    private List<String> lugaresVisitados;



    public UsuarioRegistrado() {
    }

    public UsuarioRegistrado(String nombreCompleto, String username, String password, String email, String celular) {
        super(nombreCompleto, username, password, email, celular);
        this.lugaresVisitados = new ArrayList<>();


    }

    public List<String> getLugaresVisitados() {
        return lugaresVisitados;
    }

    public void setLugaresVisitados(List<String> lugaresVisitados) {
        this.lugaresVisitados = lugaresVisitados;
    }


    public void agregarLugarVisitado(String lugar) {
        lugaresVisitados.add(lugar);
    }

    @Override
    public void mostrarMenuPrincipal() {
        System.out.println("\nMENÚ PRINCIPAL");
        System.out.println("1. Ver lugares visitados");
        System.out.println("2. Cerrar sesión");
    }

}
