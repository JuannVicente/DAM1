package Frames;

import Conexion.Conexion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CrearCuentaFrame extends JFrame {
    private JTextField nombreTextField;
    private JTextField apellidoTextField;
    private JTextField usuarioTextField;
    private JPasswordField passwordField;
    private JButton finishAccountButton;

    public CrearCuentaFrame() {
        setTitle("Crear cuenta");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana al hacer clic en cerrar
        setLocationRelativeTo(null);
        setResizable(false); // Evita que la ventana sea redimensionable

        // Panel principal
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255)); // Color de fondo claro
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Etiqueta y campo de texto para el nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        nombreTextField = new JTextField(15);
        panel.add(nombreTextField, gbc);

        // Etiqueta y campo de texto para el apellido
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Apellido:"), gbc);

        gbc.gridx = 1;
        apellidoTextField = new JTextField(15);
        panel.add(apellidoTextField, gbc);

        // Etiqueta y campo de texto para el nombre de usuario
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Usuario:"), gbc);

        gbc.gridx = 1;
        usuarioTextField = new JTextField(15);
        panel.add(usuarioTextField, gbc);

        // Etiqueta y campo de texto para la contraseña
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        gbc.gridy = 4;
        finishAccountButton = new JButton("¡He acabado!");
        finishAccountButton.setBackground(new Color(100, 150, 200));
        panel.add(finishAccountButton, gbc);

        add(panel);

        finishAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usuarioTextField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                String nombre = nombreTextField.getText().trim();
                String apellido = apellidoTextField.getText().trim();

                // Verificar si algún campo está vacío
                if (username.isEmpty() || password.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Falta por rellenar algún campo.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Salir del ActionListener sin intentar crear la cuenta
                }

                Conexion conexion = new Conexion();
                boolean cuentaCreada = conexion.crearCuenta(nombre, apellido, username, password);
                if (cuentaCreada) {
                    JOptionPane.showMessageDialog(null, "Usuario creado correctamente");
                    dispose();
                    LoginFrame loginFrame = new LoginFrame();
                    loginFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });


    }
}

