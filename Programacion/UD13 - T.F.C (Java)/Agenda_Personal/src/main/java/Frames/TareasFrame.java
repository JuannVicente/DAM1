package Frames;

import Conexion.Conexion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TareasFrame extends JFrame {
    private String username;

    public TareasFrame(String username) {
        this.username = username;
        setTitle("Tareas");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));// Agrega un margen al panel

        JButton crearButton = new JButton("Crear Tarea");
        JButton verButton = new JButton("Ver Tareas");
        JButton editarButton = new JButton("Editar Tarea");
        JButton imprimirButton = new JButton("Imprimir Tarea");
        JButton borrarButton = new JButton("Borrar Tarea");

        panel.add(crearButton);
        panel.add(verButton);
        panel.add(editarButton);
        panel.add(imprimirButton);
        panel.add(borrarButton);

        add(panel);

        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion conexion = new Conexion();
                conexion.insertarTarea(username);
            }
        });

        verButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion conexion = new Conexion();
                conexion.mostrarListaDeTareas(username);
            }
        });
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion conexion = new Conexion();
                conexion.editarListaTareasPorUsuario(username);
            }
        });

        imprimirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion conexion = new Conexion();
                ImprimirTareasFrame imprimirTareasFrame = new ImprimirTareasFrame(username);
                imprimirTareasFrame.setVisible(true);
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion conexion = new Conexion();
                conexion.borrarTarea(username);
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainMenuFrame mainMenuFrame = new MainMenuFrame(username);
                mainMenuFrame.setVisible(true);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
    }
}

