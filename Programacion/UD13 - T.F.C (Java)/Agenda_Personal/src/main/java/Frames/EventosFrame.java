package Frames;

import Conexion.Conexion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EventosFrame extends JFrame {

    private String username;

    public EventosFrame(String username) {

        this.username = username;
        setTitle("Eventos");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Agrega un margen al panel

        JButton crearButton = new JButton("Crear Evento");
        JButton verButton = new JButton("Ver Eventos");
        JButton editarButton = new JButton("Editar Evento");
        JButton imprimirButton = new JButton("Imprimir eventos");
        JButton borrarButton = new JButton("Borrar evento");


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
                conexion.insertarEvento(username);
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion conexion = new Conexion();
                conexion.editarListaEventosPorUsuario(username);
            }
        });


        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion conexion = new Conexion();
                conexion.borrarEvento(username);
            }
        });

        verButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion conexion = new Conexion();
                conexion.mostrarListaDeEventos(username);
            }
        });

        imprimirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImprimirEventosFrame imprimirEventosFrame = new ImprimirEventosFrame(username);
                imprimirEventosFrame.setVisible(true);

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


