package edu.up.ui.forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import edu.up.controllers.LoginController;

/**
 * Formulario de login para autenticación de usuarios
 * Solo maneja la presentación, delega la lógica al controlador
 */
public class LoginForm extends JDialog {
    private static final long serialVersionUID = 1L;
    
    private JTextField usuarioField;
    private JPasswordField contrasenaField;
    private JButton loginButton;
    private JButton cancelButton;
    
    private LoginController loginController;
    private boolean loginExitoso = false;
    
    public LoginForm(JFrame parent, LoginController loginController) {
        super(parent, "Iniciar Sesión", true);
        this.loginController = loginController;
        initializeComponents();
        setupLayout();
        setupEventListeners();
        setupDialog();
    }
    
    private void initializeComponents() {
        // Campos de entrada
        usuarioField = new JTextField(20);
        contrasenaField = new JPasswordField(20);
        
        // Botones
        loginButton = new JButton("Iniciar Sesión");
        cancelButton = new JButton("Cancelar");
        
        // Configurar botón por defecto
        getRootPane().setDefaultButton(loginButton);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel principal con formulario
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel titleLabel = new JLabel("Sistema Turnero Médico");
        titleLabel.setFont(titleLabel.getFont().deriveFont(16f));
        mainPanel.add(titleLabel, gbc);
        
        // Espacio
        gbc.gridy = 1;
        mainPanel.add(new JLabel(" "), gbc);
        
        // Campo usuario
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Usuario:"), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(usuarioField, gbc);
        
        // Campo contraseña
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Contraseña:"), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(contrasenaField, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventListeners() {
        // Acción del botón login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
        
        // Acción del botón cancelar
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarDialogo();
            }
        });
        
        // Enter en los campos
        KeyAdapter enterListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    realizarLogin();
                }
            }
        };
        
        usuarioField.addKeyListener(enterListener);
        contrasenaField.addKeyListener(enterListener);
    }
    
    private void setupDialog() {
        setSize(350, 200);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        // Foco inicial en el campo usuario
        usuarioField.requestFocusInWindow();
    }
    
    private void realizarLogin() {
        String usuario = usuarioField.getText();
        String contrasena = new String(contrasenaField.getPassword());
        
        // Delegar toda la lógica al controlador
        LoginController.LoginResult resultado = loginController.validarCredenciales(usuario, contrasena);
        
        if (resultado.isExitoso()) {
            loginExitoso = true;
            dispose();
        } else {
            // Mostrar error y limpiar campos
            mostrarError(resultado.getMensaje());
            contrasenaField.setText("");
            
            // Foco en el campo apropiado según el error
            if (resultado.getMensaje().contains("usuario")) {
                usuarioField.requestFocus();
            } else {
                contrasenaField.requestFocus();
            }
        }
    }
    
    private void cerrarDialogo() {
        loginExitoso = false;
        dispose();
    }
    
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de Autenticación", 
                                    JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Verifica si el login fue exitoso
     * @return true si el login fue exitoso, false en caso contrario
     */
    public boolean isLoginExitoso() {
        return loginExitoso;
    }
    
    /**
     * Muestra el formulario de login de forma modal
     * @param parent Ventana padre
     * @param loginController Controlador de login
     * @return true si el login fue exitoso, false en caso contrario
     */
    public static boolean mostrarLogin(JFrame parent, LoginController loginController) {
        LoginForm loginForm = new LoginForm(parent, loginController);
        loginForm.setVisible(true);
        return loginForm.isLoginExitoso();
    }
} 