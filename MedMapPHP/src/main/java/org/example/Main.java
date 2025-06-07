package org.example;

import org.example.accesoDatos.MedmapCRUD;
import org.example.modelo.LugarTuristico;
import org.example.modelo.Usuario;
import org.example.modelo.UsuarioRegistrado;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final MedmapCRUD crud = new MedmapCRUD();

    public static void main(String[] args) {
        try {
            mostrarMenuPrincipal();
        } catch (SQLException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void mostrarMenuPrincipal() throws SQLException {
        int opcion;
        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Registrar nuevo usuario");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Ver lugares turísticos");
            System.out.println("4. Agregar lugar turístico");
            System.out.println("5. Actualizar datos de usuario");
            System.out.println("6. Eliminar usuario");
            System.out.println("7. Salir");

            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    registrarUsuario();
                    break;
                case 2:
                    iniciarSesion();
                    break;
                case 3:
                    listarLugaresTuristicos();
                    break;
                case 4:
                    agregarLugarTuristico();
                    break;
                case 5:
                    actualizarDatosUsuario();
                    break;

                case 6:
                    eliminarUsuario();
                    break;
                case 7:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 7);
    }

    private static void registrarUsuario() throws SQLException {
        System.out.println("\n=== REGISTRO DE USUARIO ===");
        System.out.print("Nombre completo: ");
        String nombreCompleto = scanner.nextLine();

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Celular: ");
        String celular = scanner.nextLine();

        Usuario nuevoUsuario = new UsuarioRegistrado(nombreCompleto, username, password, email, celular);

        if (crud.registrarUsuario(nuevoUsuario)) {
            System.out.println("Usuario registrado exitosamente!");
        } else {
            System.out.println("Error al registrar el usuario.");
        }
    }

    private static void iniciarSesion() throws SQLException {
        System.out.println("\n=== INICIO DE SESIÓN ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        Usuario usuario = crud.login(username, password);

        if (usuario != null) {
            System.out.println("Bienvenido, " + usuario.getNombreCompleto() + "!");
            mostrarMenuUsuario(usuario);
        } else {
            System.out.println("Credenciales incorrectas.");
        }
    }

    private static void mostrarMenuUsuario(Usuario usuario) throws SQLException {
        int opcion;
        int usuarioId = crud.obtenerUsuarioId(usuario.getUsername());

        do {
            System.out.println("\n=== MENÚ DE USUARIO ===");
            System.out.println("1. Ver lugares visitados");
            System.out.println("2. Agregar lugar visitado");
            System.out.println("3. Calificar lugar");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    listarLugaresVisitados(usuarioId);
                    break;
                case 2:
                    agregarLugarVisitado(usuarioId);
                    break;
                case 3:
                    calificarLugar();
                    break;
                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 4);
    }

    private static void listarLugaresTuristicos() throws SQLException {
        System.out.println("\n=== LUGARES TURÍSTICOS ===");
        crud.obtenerTodosLugares().forEach(lugar -> {
            System.out.printf("- %s (%s) - Calificación: %.1f estrellas%n",
                    lugar.getNombre(),
                    lugar.getUbicacion(),
                    lugar.getCalificacion());
        });
    }

    private static void agregarLugarTuristico() throws SQLException {
        System.out.println("\n=== AGREGAR LUGAR TURÍSTICO ===");
        System.out.print("Nombre del lugar: ");
        String nombre = scanner.nextLine();

        System.out.print("Ubicación: ");
        String ubicacion = scanner.nextLine();

        LugarTuristico nuevoLugar = new LugarTuristico(nombre, ubicacion);

        if (crud.agregarLugarTuristico(nuevoLugar)) {
            System.out.println("Lugar turístico agregado exitosamente!");
        } else {
            System.out.println("Error al agregar el lugar turístico.");
        }
    }

    private static void listarLugaresVisitados(int usuarioId) throws SQLException {
        System.out.println("\n=== TUS LUGARES VISITADOS ===");
        List<LugarTuristico> lugares = crud.obtenerLugaresVisitados(usuarioId);

        if (lugares.isEmpty()) {
            System.out.println("Aún no has visitado ningún lugar.");
        } else {
            lugares.forEach(lugar -> {
                System.out.printf("- %s (%s)%n", lugar.getNombre(), lugar.getUbicacion());
            });
        }
    }

    private static void agregarLugarVisitado(int usuarioId) throws SQLException {
        System.out.println("\n=== AGREGAR LUGAR VISITADO ===");
        listarLugaresTuristicos();

        System.out.print("Ingrese el nombre del lugar que visitó: ");
        String nombreLugar = scanner.nextLine();

        int lugarId = crud.obtenerLugarId(nombreLugar);
        if (lugarId == -1) {
            System.out.println("Lugar no encontrado.");
            return;
        }

        if (crud.agregarLugarVisitado(usuarioId, lugarId)) {
            System.out.println("Lugar agregado a tus visitas!");
        } else {
            System.out.println("Error al agregar el lugar visitado.");
        }
    }

    private static void calificarLugar() throws SQLException {
        System.out.println("\n=== CALIFICAR LUGAR ===");
        listarLugaresTuristicos();

        System.out.print("Ingrese el nombre del lugar a calificar: ");
        String nombreLugar = scanner.nextLine();

        System.out.print("Ingrese la calificación (0.0 - 5.0): ");
        double calificacion = scanner.nextDouble();
        scanner.nextLine(); // Limpiar buffer

        if (calificacion < 0 || calificacion > 5) {
            System.out.println("La calificación debe estar entre 0.0 y 5.0");
            return;
        }

        if (crud.actualizarCalificacion(nombreLugar, calificacion)) {
            System.out.println("Lugar calificado exitosamente!");
        } else {
            System.out.println("Error al calificar el lugar.");
        }
    }

    private static void actualizarDatosUsuario() throws SQLException {
        System.out.println("\n--- Actualizar datos de usuario ---");
        System.out.print("Ingrese el username del usuario: ");
        String username = scanner.nextLine();

        System.out.print("Ingrese el nuevo nombre completo: ");
        String nuevoNombre = scanner.nextLine();

        System.out.print("Ingrese el nuevo email: ");
        String nuevoEmail = scanner.nextLine();

        System.out.print("Ingrese el nuevo número de celular: ");
        String nuevoCelular = scanner.nextLine();

        boolean actualizado = crud.actualizarDatosUsuario(username, nuevoNombre, nuevoEmail, nuevoCelular);
        if (actualizado) {
            System.out.println("Datos actualizados correctamente.");
        } else {
            System.out.println("No se pudo actualizar el usuario. Verifique que el username exista.");
        }
    }

    private static void eliminarUsuario() throws SQLException {
        System.out.println("\n--- Eliminar usuario ---");
        System.out.print("Ingrese el username del usuario que desea eliminar: ");
        String username = scanner.nextLine();

        boolean eliminado = crud.eliminarUsuario(username);
        if (eliminado) {
            System.out.println("Usuario eliminado correctamente.");
        } else {
            System.out.println("No se encontró el usuario con ese username.");
        }
    }


}