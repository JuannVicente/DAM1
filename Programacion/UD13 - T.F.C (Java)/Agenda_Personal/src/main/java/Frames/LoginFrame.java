package Frames;

import Conexion.Conexion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton; // Nuevo botón para crear una nueva cuenta

    public LoginFrame() {
        setTitle("Agenda");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false); // Evita que la ventana sea redimensionable

        // Panel principal
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255)); // Color de fondo claro
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Etiqueta y campo de texto para el nombre de usuario
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Usuario:"), gbc);

        gbc.gridx = 1;
        userTextField = new JTextField(15);
        panel.add(userTextField, gbc);

        // Etiqueta y campo de texto para la contraseña
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // Botón de inicio de sesión
        gbc.gridx = 1;
        gbc.gridy = 2;
        loginButton = new JButton("Iniciar sesión");
        loginButton.setBackground(new Color(100, 150, 200)); // Color de fondo del botón
        panel.add(loginButton, gbc);
        // Nuevo botón para crear una nueva cuenta
        gbc.gridy = 3;
        createAccountButton = new JButton("Crear nueva cuenta");
        createAccountButton.setBackground(new Color(100, 150, 200)); // Color de fondo del botón
        panel.add(createAccountButton, gbc);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comprobarLogin();
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Instancia y muestra la interfaz para crear una nueva cuenta
                dispose();
                CrearCuentaFrame crearCuentaFrame = new CrearCuentaFrame();
                crearCuentaFrame.setVisible(true);
            }
        });
    }

    private void comprobarLogin() {
        String username = userTextField.getText();
        String password = new String(passwordField.getPassword());
        Conexion conexion = new Conexion();
        if (conexion.autenticar(username, password)) {
            JOptionPane.showMessageDialog(this, "¡Login correcto!");
            dispose(); // Cierra la ventana actual del inicio de sesión
            MainMenuFrame mainMenu = new MainMenuFrame(username);
            mainMenu.setVisible(true); // Abre la nueva interfaz
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
