package Frames;

import Conexion.Conexion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuFrame extends JFrame {


    private String username;

    public MainMenuFrame(String username) {
        this.username = username;
        setTitle("Menú Principal");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Agrega un margen al panel

        JButton eventosButton = new JButton("Eventos");
        JButton tareasButton = new JButton("Tareas");
        JButton notasButton = new JButton("Notas");
        JButton imprimirButton = new JButton("¿Qué tengo que hacer hoy?");

        panel.add(eventosButton);
        panel.add(tareasButton);
        panel.add(notasButton);
        panel.add(imprimirButton);

        add(panel);
        eventosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainMenuFrame.this, "Acceso a la sección de Eventos");
                dispose(); // Cierra la ventana actual
                EventosFrame eventosFrame = new EventosFrame(username);
                eventosFrame.setVisible(true);

            }
        });

        tareasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainMenuFrame.this, "Acceso a la sección de Tareas");
                dispose();
                TareasFrame tareasFrame = new TareasFrame(username);
                tareasFrame.setVisible(true);
            }
        });

        notasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainMenuFrame.this, "Acceso a la sección de Notas");
                dispose();
                NotasFrame notasFrame = new NotasFrame(username);
                notasFrame.setVisible(true);
            }
        });

        imprimirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainMenuFrame.this, "Acceso a lo que tienes que hacer hoy");
                Conexion conexion = new Conexion();
                conexion.exportarParaHoy(username);
            }
        });


    }
}

