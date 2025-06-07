package org.example.accesoDatos;

import org.example.modelo.LugarTuristico;
import org.example.modelo.Usuario;
import org.example.modelo.UsuarioRegistrado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedmapCRUD {
    private static final String URL = "jdbc:mysql://localhost:3306/dbmedmap";
    private static final String USER = "root"; // Cambiar por tu usuario
    private static final String PASSWORD = ""; // Cambiar por tu contraseña

    // Método para obtener la conexión
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC no encontrado", e);
        }
    }

    // CRUD para LugarTuristico
    public boolean agregarLugarTuristico(LugarTuristico lugar) throws SQLException {
        String sql = "INSERT INTO lugares_turisticos (nombre, ubicacion, calificacion) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, lugar.getNombre());
            pstmt.setString(2, lugar.getUbicacion());
            pstmt.setDouble(3, lugar.getCalificacion());

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<LugarTuristico> obtenerTodosLugares() throws SQLException {
        List<LugarTuristico> lugares = new ArrayList<>();
        String sql = "SELECT * FROM lugares_turisticos";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LugarTuristico lugar = new LugarTuristico(
                        rs.getString("nombre"),
                        rs.getString("ubicacion")
                );
                lugar.setCalificacion(rs.getDouble("calificacion"));
                lugares.add(lugar);
            }
        }

        return lugares;
    }

    public boolean actualizarCalificacion(String nombreLugar, double nuevaCalificacion) throws SQLException {
        String sql = "UPDATE lugares_turisticos SET calificacion = ? WHERE nombre = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, nuevaCalificacion);
            pstmt.setString(2, nombreLugar);

            return pstmt.executeUpdate() > 0;
        }
    }

    // CRUD para Usuario
    public boolean registrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre_completo, username, password, email, celular) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNombreCompleto());
            pstmt.setString(2, usuario.getUsername());
            pstmt.setString(3, usuario.getPassword());
            pstmt.setString(4, usuario.getEmail());
            pstmt.setString(5, usuario.getCelular());

            return pstmt.executeUpdate() > 0;
        }
    }

    public Usuario login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new UsuarioRegistrado(
                            rs.getString("nombre_completo"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email"),
                            rs.getString("celular")
                    );
                }
            }
        }

        return null;
    }

    // Obtener ID de usuario por username
    public int obtenerUsuarioId(String username) throws SQLException {
        String sql = "SELECT id FROM usuarios WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }

        return -1;
    }

    // Obtener ID de lugar por nombre
    public int obtenerLugarId(String nombreLugar) throws SQLException {
        String sql = "SELECT id FROM lugares_turisticos WHERE nombre = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombreLugar);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }

        return -1;
    }

    // Métodos para lugares visitados
    public boolean agregarLugarVisitado(int usuarioId, int lugarId) throws SQLException {
        String sql = "INSERT INTO lugares_visitados (usuario_id, lugar_id, fecha_visita) VALUES (?, ?, CURRENT_DATE)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            pstmt.setInt(2, lugarId);

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<LugarTuristico> obtenerLugaresVisitados(int usuarioId) throws SQLException {
        List<LugarTuristico> lugares = new ArrayList<>();
        String sql = "SELECT lt.* FROM lugares_turisticos lt " +
                "JOIN lugares_visitados lv ON lt.id = lv.lugar_id " +
                "WHERE lv.usuario_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LugarTuristico lugar = new LugarTuristico(
                            rs.getString("nombre"),
                            rs.getString("ubicacion")
                    );
                    lugar.setCalificacion(rs.getDouble("calificacion"));
                    lugares.add(lugar);
                }
            }
        }

        return lugares;
    }

    public boolean actualizarDatosUsuario(String username, String nuevoNombre, String nuevoEmail, String nuevoCelular) throws SQLException {
        String sql = "UPDATE usuarios SET nombre_completo = ?, email = ?, celular = ? WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nuevoNombre);
            pstmt.setString(2, nuevoEmail);
            pstmt.setString(3, nuevoCelular);
            pstmt.setString(4, username);

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean eliminarUsuario(String username) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            return pstmt.executeUpdate() > 0;
        }
    }


}