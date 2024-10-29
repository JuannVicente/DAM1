package Conexion;

import Clases.Evento;
import Clases.Notas;
import Clases.Tarea;
import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class Conexion {

    String url = "jdbc:mysql://localhost:3306/agenda_personal";
    String user = "root";
    String password = "1234";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public boolean autenticar(String username, String password) {
        try (Connection connection = connect()) {
            String sql = "SELECT * FROM users WHERE nombre_usuario = ? AND contrasena = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            boolean isAuthenticated = resultSet.next();
            resultSet.close();
            preparedStatement.close();

            return isAuthenticated;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean crearCuenta(String nombre, String apellido, String username, String password) {
        try (Connection connection = connect()) {
            String sql = "INSERT INTO usuarios(nombre,apellido) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.executeUpdate();

            String sql1 = "SELECT id_usuario FROM usuarios ORDER BY id_usuario DESC LIMIT 1";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            ResultSet resultSet = preparedStatement1.executeQuery();
            int id = 0;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }

            String sql2 = "INSERT INTO users VALUES(?,?,?)";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setInt(1, id);
            preparedStatement2.setString(2, username);
            preparedStatement2.setString(3, password);
            preparedStatement2.executeUpdate();

            preparedStatement.close();
            preparedStatement1.close();
            resultSet.close();
            preparedStatement2.close();

            return true; // La cuenta se creó correctamente
        } catch (SQLIntegrityConstraintViolationException e) {
            // Si se produce una violación de la restricción UNIQUE, devuelve false indicando que la cuenta no se creó correctamente
            return false;
        } catch (SQLException e) {
            // Si se produce algún otro error, lanza una RuntimeException
            throw new RuntimeException(e);
        }
    }

    public void insertarEvento(String username) {
        try (Connection connection = connect()) {
            String id_usuario = "select id_usuario FROM usuarios us JOIN users usr ON us.id_usuario = usr.id WHERE usr.nombre_usuario = '" + username + "'";
            PreparedStatement preparedStatement1 = connection.prepareStatement(id_usuario);
            ResultSet resultSet = preparedStatement1.executeQuery();
            int id = 0;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }


            String sql = "INSERT INTO eventos (nombre_evento, descripcion_evento, fecha_evento, estado, creador_de_evento) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            String nombreEvento = JOptionPane.showInputDialog("Dime el nombre del evento");
            String descripcionEvento = JOptionPane.showInputDialog("Dime una descripción de este evento");
            String fechaEvento = JOptionPane.showInputDialog("Indica la fecha y hora de la tarea en formato: año/mes/día HH:MM:SS");
            String estado = JOptionPane.showInputDialog("¿Este evento en que Estado está? Escribir exactamente igual que los ejemplos: Completado, Pendiente o No_asistido");
            int creadorEvento = (id);

            preparedStatement.setString(1, nombreEvento);
            preparedStatement.setString(2, descripcionEvento);
            preparedStatement.setString(3, fechaEvento);
            preparedStatement.setString(4, estado);
            preparedStatement.setInt(5, creadorEvento);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertarTarea(String username) {
        try (Connection connection = connect()) {
            String id_usuario = "select id_usuario FROM usuarios us JOIN users usr ON us.id_usuario = usr.id WHERE usr.nombre_usuario = '" + username + "'";
            PreparedStatement preparedStatement1 = connection.prepareStatement(id_usuario);
            ResultSet resultSet = preparedStatement1.executeQuery();
            int id = 0;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }


            String sql = "INSERT INTO tareas (nombre_tarea, descripcion_tarea, fecha_tarea, estado, creador_de_tarea) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            String nombreTarea = JOptionPane.showInputDialog("Dime el nombre de la tarea");
            String descripcionTarea = JOptionPane.showInputDialog("Dime una descripción de esta tarea");
            String fechaTarea = JOptionPane.showInputDialog("Indica la fecha y hora de la tarea en formato: año/mes/día HH:MM:SS");
            String estado = JOptionPane.showInputDialog("¿Esta tarea en que Estado está? Escribir exactamente igual que los ejemplos: Completado, Pendiente o No_completado ");
            int creadorTarea = (id);

            preparedStatement.setString(1, nombreTarea);
            preparedStatement.setString(2, descripcionTarea);
            preparedStatement.setString(3, fechaTarea);
            preparedStatement.setString(4, estado);
            preparedStatement.setInt(5, creadorTarea);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertarNota(String username) {
        try (Connection connection = connect()) {
            String id_usuario = "select id_usuario FROM usuarios us JOIN users usr ON us.id_usuario = usr.id WHERE usr.nombre_usuario = '" + username + "'";
            PreparedStatement preparedStatement1 = connection.prepareStatement(id_usuario);
            ResultSet resultSet = preparedStatement1.executeQuery();
            int id = 0;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }


            String sql = "INSERT INTO notas (nota,creador_de_la_nota) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            String nota = JOptionPane.showInputDialog("Dime la nota que quieres apuntar");
            int creadorNota = (id);
            preparedStatement.setString(1, nota);
            preparedStatement.setInt(2, creadorNota);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void mostrarListaDeEventos(String username) {
        List<Evento> eventos = obtenerEventosPorUsuario(username);
        if (eventos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tienes ningún evento creado.", "Información", JOptionPane.ERROR_MESSAGE);
        } else {
            JFrame frame = new JFrame("Lista de Eventos");
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (Evento evento : eventos) {
                listModel.addElement(evento.getNombre_evento() + " - " + evento.getDescripcion_evento() + " - " + evento.getFecha_evento() + " - " + evento.getEstado());
                listModel.addElement("------------------------------------------------------------------------------------------------------------------------------------------------");
            }
            JList<String> eventList = new JList<>(listModel);
            JScrollPane scrollPane = new JScrollPane(eventList);
            frame.add(scrollPane);
            frame.setSize(600, 300);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        }
    }

    public void mostrarListaDeTareas(String username) {
        List<Tarea> tareas = obtenerTareasPorUsuario(username);
        if (tareas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tienes ninguna tarea creada.", "Información", JOptionPane.ERROR_MESSAGE);
        } else {
            JFrame frame = new JFrame("Lista de Tareas");
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (Tarea tarea : tareas) {
                listModel.addElement(tarea.getNombre_tarea() + " - " + tarea.getDescripcion_tarea() + " - " + tarea.getFecha_tarea() + " - " + tarea.getEstado());
                listModel.addElement("------------------------------------------------------------------------------------------------------------------------------------------------");
            }
            JList<String> eventList = new JList<>(listModel);
            JScrollPane scrollPane = new JScrollPane(eventList);
            frame.add(scrollPane);
            frame.setSize(600, 300);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        }
    }

    public void mostrarListaDeNotas(String username) {
        List<Notas> notas = obtenerNotasPorUsuario(username);
        if (notas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tienes ninguna nota creada.", "Información", JOptionPane.ERROR_MESSAGE);
        } else {
            JFrame frame = new JFrame("Lista de Notas");
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (Notas notas1 : notas) {
                listModel.addElement(notas1.getNota() + " - " + notas1.getFecha_creacion_de_tarea());
                listModel.addElement("------------------------------------------------------------------------------------------------------------------------------------------------");
            }
            JList<String> eventList = new JList<>(listModel);
            JScrollPane scrollPane = new JScrollPane(eventList);
            frame.add(scrollPane);
            frame.setSize(600, 300);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        }
    }

    public List<Evento> obtenerEventosPorUsuario(String username) {
        List<Evento> eventos = new ArrayList<>();
        try (Connection connection = connect()) {
            String id_usuario = "select id_usuario FROM usuarios us JOIN users usr ON us.id_usuario = usr.id WHERE usr.nombre_usuario = '" + username + "'";
            PreparedStatement preparedStatement1 = connection.prepareStatement(id_usuario);
            ResultSet resultSet = preparedStatement1.executeQuery();
            int id = 0;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }

            String eventosQuery = "SELECT * FROM eventos WHERE creador_de_evento = ?";
            PreparedStatement eventosStatement = connection.prepareStatement(eventosQuery);
            eventosStatement.setInt(1, id);
            ResultSet eventosResult = eventosStatement.executeQuery();

            while (eventosResult.next()) {
                int idEvento = eventosResult.getInt("id_evento");
                String nombreEvento = eventosResult.getString("nombre_evento");
                String descripcionEvento = eventosResult.getString("descripcion_evento");
                String fechaEvento = eventosResult.getString("fecha_evento");
                String estadoEvento = eventosResult.getString("estado");
                int creadorEvento = eventosResult.getInt("creador_de_evento");

                Evento evento = new Evento(idEvento, nombreEvento, descripcionEvento, fechaEvento, estadoEvento, creadorEvento);
                eventos.add(evento);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return eventos;
    }

    public void editarListaEventosPorUsuario(String username) {
        List<Evento> eventos = obtenerEventosPorUsuario(username);
        if (eventos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tienes ningún evento creado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Evento seleccion_evento = (Evento) JOptionPane.showInputDialog(null, "Elige un evento",
                "Eventos", JOptionPane.PLAIN_MESSAGE, null, eventos.toArray(), null);
        if (seleccion_evento == null) {
            JOptionPane.showMessageDialog(null, "No has seleccionado ningún evento", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int id_evento = seleccion_evento.getId_evento();

            try (Connection connection = connect()) {
                String[] opciones = {"nombre_evento", "descripcion_evento", "fecha_evento", "estado"};
                int seleccion = JOptionPane.showOptionDialog(null,
                        "¿Qué quieres editar?",
                        "Seleccionar opción", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, opciones, null);
                String columna = null;
                switch (seleccion) {
                    case 0:
                        columna = "nombre_evento";
                        break;
                    case 1:
                        columna = "descripcion_evento";
                        break;
                    case 2:
                        columna = "fecha_evento";
                        break;
                    case 3:
                        String[] opciones1 = {"Completado", "Pendiente", "No_asistido"};
                        int seleccion1 = JOptionPane.showOptionDialog(null,
                                "¿Que estado le quieres poner?"
                                , "Seleccionar opción", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones1, null);
                        String nuevoEstado;
                        switch (seleccion1) {
                            case 0:
                                nuevoEstado = "Completado";
                                break;
                            case 1:
                                nuevoEstado = "Pendiente";
                                break;
                            case 2:
                                nuevoEstado = "No_asistido";
                                break;
                            default:
                                throw new IllegalArgumentException("Selección no válida");
                        }

                        //PROCEDIMIENTO
                        String sql = "call CambiarEstadoEvento(?,?)";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                            preparedStatement.setInt(1, id_evento);
                            preparedStatement.setString(2, nuevoEstado);
                            preparedStatement.executeUpdate();
                        }
                        return;
                }
                String variableValor = JOptionPane.showInputDialog("¿Qué valor quieres poner?");
                String sql = "UPDATE eventos SET " + columna + " = ? WHERE id_evento = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, variableValor);
                preparedStatement.setInt(2, id_evento);
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Tarea> obtenerTareasPorUsuario(String username) {
        List<Tarea> tareas = new ArrayList();
        try (Connection connection = connect()) {
            String id_usuario = "SELECT id_usuario FROM usuarios us JOIN users usr ON us.id_usuario = usr.id WHERE usr.nombre_usuario = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(id_usuario);
            preparedStatement1.setString(1, username);
            ResultSet resultSet = preparedStatement1.executeQuery();
            int id = 0;
            if (resultSet.next()) {
                id = resultSet.getInt(1);


                String tareasQuery = "SELECT * FROM tareas WHERE creador_de_tarea = ?";
                PreparedStatement tareasStatement = connection.prepareStatement(tareasQuery);
                tareasStatement.setInt(1, id);
                ResultSet tareasResult = tareasStatement.executeQuery();
                while (tareasResult.next()) {
                    int idTarea = tareasResult.getInt("id_tarea");
                    String nombreTarea = tareasResult.getString("nombre_tarea");
                    String descripcionTarea = tareasResult.getString("descripcion_tarea");
                    String fechaTarea = tareasResult.getString("fecha_tarea");
                    String estadoTarea = tareasResult.getString("estado");
                    int creadorTarea = tareasResult.getInt("creador_de_tarea");

                    Tarea tarea = new Tarea(idTarea, nombreTarea, descripcionTarea, fechaTarea, estadoTarea, creadorTarea);
                    tareas.add(tarea);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tareas;
    }

    public void editarListaTareasPorUsuario(String username) {
        List<Tarea> tareas = obtenerTareasPorUsuario(username);
        if (tareas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tienes ninguna tarea creada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Tarea seleccion_tarea = (Tarea) JOptionPane.showInputDialog(null, "Elige una tarea",
                "Tareas", JOptionPane.PLAIN_MESSAGE, null, tareas.toArray(), null);
        if (seleccion_tarea == null) {
            JOptionPane.showMessageDialog(null, "No has seleccionado ninguna tarea", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idTarea = seleccion_tarea.getId_tarea();

        try (Connection connection = connect()) {
            String[] opciones = {"nombre_tarea", "descripcion_tarea", "fecha_tarea", "estado"};
            int seleccion = JOptionPane.showOptionDialog(null,
                    "¿Qué quieres editar?",
                    "Seleccionar opción", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, opciones, null);
            String columna = null;
            switch (seleccion) {
                case 0:
                    columna = "nombre_tarea";
                    break;
                case 1:
                    columna = "descripcion_tarea";
                    break;
                case 2:
                    columna = "fecha_tarea";
                    break;
                case 3:
                    String[] opciones1 = {"Completado", "Pendiente", "No_completado"};
                    int seleccion1 = JOptionPane.showOptionDialog(null,
                            "¿Que estado le quieres poner?"
                            , "Seleccionar opción", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones1, null);
                    String nuevoEstado;
                    switch (seleccion1) {
                        case 0:
                            nuevoEstado = "Completado";
                            break;
                        case 1:
                            nuevoEstado = "Pendiente";
                            break;
                        case 2:
                            nuevoEstado = "No_completado";
                            break;
                        default:
                            throw new IllegalArgumentException("Selección no válida");
                    }

                    // PROCEDIMIENTO
                    String sql = "call CambiarEstadoTarea(?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, idTarea);
                    preparedStatement.setString(2, nuevoEstado);
                    preparedStatement.executeUpdate();

                    return;
            }
            String variableValor = JOptionPane.showInputDialog("¿Qué valor quieres poner?");
            String sql = "UPDATE tareas SET " + columna + " = ? WHERE id_tarea = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, variableValor);
            preparedStatement.setInt(2, idTarea);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Notas> obtenerNotasPorUsuario(String username) {
        List<Notas> notasList = new ArrayList();
        try (Connection connection = connect()) {
            String id_usuario = "SELECT id_usuario FROM usuarios us JOIN users usr ON us.id_usuario = usr.id WHERE usr.nombre_usuario = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(id_usuario);
            preparedStatement1.setString(1, username);
            ResultSet resultSet = preparedStatement1.executeQuery();
            int id = 0;
            if (resultSet.next()) {
                id = resultSet.getInt(1);


                String notasQuery = "SELECT * FROM notas WHERE creador_de_la_nota = ?";
                PreparedStatement notasStatement = connection.prepareStatement(notasQuery);
                notasStatement.setInt(1, id);
                ResultSet notasResult = notasStatement.executeQuery();
                while (notasResult.next()) {
                    int idNota = notasResult.getInt("id_nota");
                    String nota = notasResult.getString("nota");
                    String fecha_creacion = notasResult.getString("fecha_creacion_de_nota");
                    int creadorNota = notasResult.getInt("creador_de_la_nota");

                    Notas notas = new Notas(idNota, nota, fecha_creacion, creadorNota);
                    notasList.add(notas);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return notasList;
    }

    public void editarListaNotasPorUsuario(String username) {
        List<Notas> notasList = obtenerNotasPorUsuario(username);
        if (notasList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tienes ninguna nota creada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Notas seleccion_nota = (Notas) JOptionPane.showInputDialog(null, "Elige una nota",
                "Notas", JOptionPane.PLAIN_MESSAGE, null, notasList.toArray(), null);
        if (seleccion_nota == null) {
            JOptionPane.showMessageDialog(null, "No has seleccionado ninguna nota", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idNota = seleccion_nota.getId_nota();

        try (Connection connection = connect()) {
            String columna = "nota";
            String variableValor = JOptionPane.showInputDialog("¿Qué valor quieres poner?");

            if (variableValor != null && !variableValor.isEmpty()) {
                String sql = "UPDATE notas SET " + columna + " = ? WHERE id_nota = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, variableValor);
                preparedStatement.setInt(2, idNota);
                preparedStatement.executeUpdate();

            } else {
                JOptionPane.showMessageDialog(null, "El valor no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void borrarEvento(String username) {
        List<Evento> eventos = obtenerEventosPorUsuario(username);
        if (eventos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tienes ninguna evento creado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Evento seleccion_evento = (Evento) JOptionPane.showInputDialog(null, "Elige un evento para borrar",
                "Eventos", JOptionPane.PLAIN_MESSAGE, null, eventos.toArray(), null);
        if (seleccion_evento == null) {
            JOptionPane.showMessageDialog(null, "No has seleccionado ningún evento", "Error", JOptionPane.ERROR_MESSAGE);
            return;

        }
        int id_evento = seleccion_evento.getId_evento();
        try (Connection connection = connect()) {
            String sql = "DELETE FROM eventos WHERE id_evento = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_evento);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Evento borrado con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo borrar el evento.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void borrarTarea(String username) {
        List<Tarea> tareas = obtenerTareasPorUsuario(username);
        if (tareas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tienes ninguna tarea creada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Tarea seleccion_tarea = (Tarea) JOptionPane.showInputDialog(null, "Elige una tarea para borrar",
                "Tarea", JOptionPane.PLAIN_MESSAGE, null, tareas.toArray(), null);
        if (seleccion_tarea == null) {
            JOptionPane.showMessageDialog(null, "No has seleccionado ninguna tarea", "Error", JOptionPane.ERROR_MESSAGE);
            return;

        }
        int id_tarea = seleccion_tarea.getId_tarea();
        try (Connection connection = connect()) {
            String sql = "DELETE FROM tareas WHERE id_tarea = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_tarea);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Tarea borrada con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo borrar la tarea.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void borrarNota(String username) {
        List<Notas> notasList = obtenerNotasPorUsuario(username);
        if (notasList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tienes ninguna nota creada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Notas seleccion_nota = (Notas) JOptionPane.showInputDialog(null, "Elige una nota para borrar",
                "Nota", JOptionPane.PLAIN_MESSAGE, null, notasList.toArray(), null);
        if (seleccion_nota == null) {
            JOptionPane.showMessageDialog(null, "No has seleccionado ninguna nota", "Error", JOptionPane.ERROR_MESSAGE);
            return;

        }
        int id_nota = seleccion_nota.getId_nota();
        try (Connection connection = connect()) {
            String sql = "DELETE FROM notas WHERE id_nota = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_nota);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Nota borrada con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo borrar la nota.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportarEventosPorFechas(String username, String fechaInicio, String fechaFin) {
        List<Evento> eventos = new ArrayList<>();
        try (Connection connection = connect()) {
            String id_usuario_query = "SELECT id_usuario FROM usuarios us JOIN users usr ON us.id_usuario = usr.id WHERE usr.nombre_usuario = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(id_usuario_query);
            preparedStatement1.setString(1, username);
            ResultSet resultSet = preparedStatement1.executeQuery();
            int id = 0;
            if (resultSet.next()) {
                id = resultSet.getInt(1);

                String eventos_query = "SELECT * FROM eventos WHERE creador_de_evento = ? AND fecha_evento BETWEEN ? AND ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(eventos_query);
                preparedStatement2.setInt(1, id);
                preparedStatement2.setString(2, fechaInicio);
                preparedStatement2.setString(3, fechaFin);
                ResultSet eventosResult = preparedStatement2.executeQuery();
                while (eventosResult.next()) {
                    int idEvento = eventosResult.getInt("id_evento");
                    String nombreEvento = eventosResult.getString("nombre_evento");
                    String descripcionEvento = eventosResult.getString("descripcion_evento");
                    String fechaEvento = eventosResult.getString("fecha_evento");
                    String estadoEvento = eventosResult.getString("estado");
                    int creadorEvento = eventosResult.getInt("creador_de_evento");

                    Evento evento = new Evento(idEvento, nombreEvento, descripcionEvento, fechaEvento, estadoEvento, creadorEvento);
                    eventos.add(evento);
                }
            }
            if (eventos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No tienes ninguna evento creado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("eventos.txt", true))) {
            writer.write("Los Eventos de la fecha inicio " + fechaInicio + " a fecha fin " + fechaFin + " son: ");
            writer.newLine();
            for (Evento evento : eventos) {
                writer.write("Nombre Evento: " + evento.getNombre_evento());
                writer.newLine();
                writer.write("Descripción Evento: " + evento.getDescripcion_evento());
                writer.newLine();
                writer.write("Fecha Evento: " + evento.getFecha_evento());
                writer.newLine();
                writer.write("Estado Evento: " + evento.getEstado());
                writer.newLine();
                writer.write("-------------------------------------------------------------------");
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Creado correctamente el archivo eventos.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportarTareasPorFechas(String username, String fechaInicio, String fechaFin) {
        List<Tarea> tareas = new ArrayList<>();
        try (Connection connection = connect()) {
            // Obtener el ID de usuario
            String id_usuario = "SELECT id_usuario FROM usuarios us JOIN users usr ON us.id_usuario = usr.id WHERE usr.nombre_usuario = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(id_usuario);
            preparedStatement1.setString(1, username);
            ResultSet resultSet = preparedStatement1.executeQuery();
            int id = 0;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
                String tareas_query = "SELECT * FROM tareas WHERE creador_de_tarea = ? AND fecha_tarea BETWEEN ? AND ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(tareas_query);
                preparedStatement2.setInt(1, id);
                preparedStatement2.setString(2, fechaInicio);
                preparedStatement2.setString(3, fechaFin);
                ResultSet tareasResult = preparedStatement2.executeQuery();
                while (tareasResult.next()) {
                    int idTarea = tareasResult.getInt("id_tarea");
                    String nombreTarea = tareasResult.getString("nombre_tarea");
                    String descripcionTarea = tareasResult.getString("descripcion_tarea");
                    String fechaTarea = tareasResult.getString("fecha_tarea");
                    String estado = tareasResult.getString("estado");
                    int creadorTarea = tareasResult.getInt("creador_de_tarea");

                    Tarea tarea = new Tarea(idTarea, nombreTarea, descripcionTarea, fechaTarea, estado, creadorTarea);
                    tareas.add(tarea);
                }
            }
            if (tareas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No tienes ninguna tarea creada.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tareas.txt"))) {
            writer.write("Las Tareas de la fecha inicio " + fechaInicio + " a fecha fin " + fechaFin + " son: ");
            writer.newLine();
            for (Tarea tarea : tareas) {
                writer.write("Nombre Tarea: " + tarea.getNombre_tarea());
                writer.newLine();
                writer.write("Descripción Tarea: " + tarea.getDescripcion_tarea());
                writer.newLine();
                writer.write("Fecha Tarea: " + tarea.getFecha_tarea());
                writer.newLine();
                writer.write("Estado Tarea: " + tarea.getEstado());
                writer.newLine();
                writer.write("-------------------------------------------------------------------");
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Creado correctamente el archivo tareas.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportarNotasPorFechas(String username, String fechaInicio, String fechaFin) {
        List<Notas> notas = new ArrayList<>();

        try (Connection connection = connect()) {
            String id_usuario = "SELECT id_usuario FROM usuarios us JOIN users usr ON us.id_usuario = usr.id WHERE usr.nombre_usuario = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(id_usuario);
            preparedStatement1.setString(1, username);
            ResultSet resultSet = preparedStatement1.executeQuery();
            int id = 0;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
                String notas_query = "SELECT * FROM notas WHERE creador_de_la_nota = ? AND fecha_creacion_de_nota BETWEEN ? AND ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(notas_query);
                preparedStatement2.setInt(1, id);
                preparedStatement2.setString(2, fechaInicio);
                preparedStatement2.setString(3, fechaFin);
                ResultSet notasResult = preparedStatement2.executeQuery();
                while (notasResult.next()) {
                    int idNota = notasResult.getInt("id_nota");
                    String nota = notasResult.getString("nota");
                    String fechaNota = notasResult.getString("fecha_creacion_de_nota");
                    int creadorNota = notasResult.getInt("creador_de_la_nota");

                    Notas Notas = new Notas(idNota, nota, fechaNota, creadorNota);
                    notas.add(Notas);
                }
            }
            if (notas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No tienes ninguna nota creada.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("notas.txt"))) {
            writer.write("Las Notas de la fecha inicio " + fechaInicio + " a fecha fin " + fechaFin + " son: ");
            writer.newLine();
            for (Notas Notas : notas) {
                writer.write("Nota: " + Notas.getNota());
                writer.newLine();
                writer.write("Fecha Nota: " + Notas.getFecha_creacion_de_tarea());
                writer.newLine();
                writer.write("-------------------------------------------------------------------");
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Creado correctamente el archivo notas.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportarParaHoy(String username) {
        List<Evento> eventoList = new ArrayList<>();
        List<Tarea> tareaList = new ArrayList<>();
        try (Connection connection = connect()) {
            String id_usuario = "SELECT id_usuario FROM usuarios us JOIN users usr ON us.id_usuario = usr.id WHERE usr.nombre_usuario = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(id_usuario);
            preparedStatement1.setString(1, username);
            ResultSet resultSet = preparedStatement1.executeQuery();
            int id = 0;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            String eventosQuery = "SELECT nombre_evento, descripcion_evento, fecha_evento, estado FROM eventos WHERE creador_de_evento = ? AND DATE(fecha_evento) = CURDATE()";
            PreparedStatement preparedStatement = connection.prepareStatement(eventosQuery);
            preparedStatement.setInt(1, id);
            ResultSet resultSet1 = preparedStatement.executeQuery();

            while (resultSet1.next()) {
                Evento evento = new Evento();
                evento.setNombre_evento(resultSet1.getString(1));
                evento.setDescripcion_evento(resultSet1.getString(2));
                evento.setFecha_evento(resultSet1.getString(3));
                String estadoStr = resultSet1.getString(4);
                Evento.Estado estado = Evento.Estado.valueOf(estadoStr);
                evento.setEstado(estado);
                eventoList.add(evento);
            }
            String tareasQuery = "SELECT nombre_tarea, descripcion_tarea, fecha_tarea, estado FROM tareas WHERE creador_de_tarea = ? AND DATE(fecha_tarea) = CURDATE()";
            PreparedStatement tareasStatement = connection.prepareStatement(tareasQuery);
            tareasStatement.setInt(1, id);
            ResultSet resultSet2 = tareasStatement.executeQuery();
            while (resultSet2.next()) {
                Tarea tarea = new Tarea();
                tarea.setNombre_tarea(resultSet2.getString(1));
                tarea.setDescripcion_tarea(resultSet2.getString(2));
                tarea.setFecha_tarea(resultSet2.getString(3));
                String estadoStr = resultSet2.getString(4);
                Tarea.Estado estado1 = Tarea.Estado.valueOf(estadoStr);
                tarea.setEstado(estado1);
                tareaList.add(tarea);
            }
//            System.out.println(eventoList);
//            System.out.println(tareaList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (eventoList.isEmpty() && tareaList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tienes nada para hoy");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Eventos_y_Tareas_Para_Hoy.txt"))) {
            int contador = 0;
            if (!eventoList.isEmpty()) {
                writer.write("Hoy tienes que asistir a " + eventoList.size() + " Eventos.");
                writer.newLine();
                writer.write("-------------------------------------------------------------------");
                writer.newLine();
                writer.newLine();
                for (Evento evento : eventoList) {
                    contador++;
                    writer.write("Evento número " + contador + ": ");
                    writer.newLine();
                    writer.write("Nombre : " + evento.getNombre_evento());
                    writer.newLine();
                    writer.write("Descripción : " + evento.getDescripcion_evento());
                    writer.newLine();
                    writer.write("El estado de este evento es " + evento.getEstado());
                    writer.newLine();
                    writer.write("-------------------------------------------------------------------");
                    writer.newLine();
                }
            }
            if (!tareaList.isEmpty()) {
                int contador1 = 0;
                writer.write("-------------------------------------------------------------------");
                writer.newLine();
                writer.newLine();
                writer.write("Hoy tienes que hacer " + tareaList.size() + " Tareas.");
                writer.newLine();
                writer.write("-------------------------------------------------------------------");
                writer.newLine();
                writer.newLine();
                for (Tarea tareas : tareaList) {
                    contador1++;
                    writer.write("Tarea número " + contador1 + ": ");
                    writer.newLine();
                    writer.write("Nombre : " + tareas.getNombre_tarea());
                    writer.newLine();
                    writer.write("Descripción : " + tareas.getDescripcion_tarea());
                    writer.newLine();
                    writer.write("El estado de esta tarea es " + tareas.getEstado());
                    writer.newLine();
                    writer.write("-------------------------------------------------------------------");
                    writer.newLine();
                }
                JOptionPane.showMessageDialog(null, "Creado correctamente el archivo Eventos_y_Tareas_Para_Hoy.txt");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}











