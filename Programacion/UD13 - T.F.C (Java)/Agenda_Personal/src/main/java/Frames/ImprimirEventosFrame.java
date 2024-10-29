package Frames;

import Conexion.Conexion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ImprimirEventosFrame extends JFrame {
    private String username;
    private JSpinner fechaInicioSpinner;
    private JSpinner fechaFinSpinner;

    public ImprimirEventosFrame(String username) {
        this.username = username;
        setTitle("Imprimir Eventos");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 20, 10));

        JLabel fechaInicioLabel = new JLabel("Fecha de Inicio:");
        JLabel fechaFinLabel = new JLabel("Fecha de Fin:");

        SimpleDateFormat model = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date fechaHoy = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date fechaManana = calendar.getTime();

        SpinnerDateModel fechaInicioModel = new SpinnerDateModel(fechaHoy, null, null, Calendar.DAY_OF_MONTH);
        SpinnerDateModel fechaFinModel = new SpinnerDateModel(fechaManana, null, null, Calendar.DAY_OF_MONTH);

        fechaInicioSpinner = new JSpinner(fechaInicioModel);
        fechaFinSpinner = new JSpinner(fechaFinModel);

        JSpinner.DateEditor fechaInicioEditor = new JSpinner.DateEditor(fechaInicioSpinner, model.toPattern());
        JSpinner.DateEditor fechaFinEditor = new JSpinner.DateEditor(fechaFinSpinner, model.toPattern());

        fechaInicioSpinner.setEditor(fechaInicioEditor);
        fechaFinSpinner.setEditor(fechaFinEditor);

        JButton imprimirButton = new JButton("Imprimir");

        panel.add(fechaInicioLabel);
        panel.add(fechaInicioSpinner);
        panel.add(fechaFinLabel);
        panel.add(fechaFinSpinner);
        panel.add(imprimirButton);

        add(panel);

        imprimirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date fechaInicio = (Date) fechaInicioSpinner.getValue();
                Date fechaFin = (Date) fechaFinSpinner.getValue();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String fechaInicio1 = sdf.format(fechaInicio);
                String fechaFin1 = sdf.format(fechaFin);
                Conexion conexion = new Conexion();
                conexion.exportarEventosPorFechas(username, fechaInicio1, fechaFin1);
                dispose();
            }
        });
    }
}

