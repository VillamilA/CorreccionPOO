import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Loginform extends JFrame {
    public JTextField campoUsuario;
    public JPasswordField campoContrasena;
    public JButton botonLogin;
    public JPanel panelLogin;

    public Loginform() {
        setTitle("Login");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelLogin);
        setLocationRelativeTo(null);

        botonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = campoUsuario.getText();
                String contrasena = new String(campoContrasena.getPassword());

                if (autenticarUsuario(usuario, contrasena)) {
                    new menu().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña incorrectos");
                }
            }
        });
    }

    private boolean autenticarUsuario(String usuario, String contrasena) {
        boolean autenticado = false;
        Connection connection = ConexionBaseDatos.getConnection();
        String query = "SELECT * FROM USUARIO WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, contrasena);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                autenticado = true;
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return autenticado;
    }

    public static void main(String[] args) {
        new Loginform().setVisible(true);
    }
}
