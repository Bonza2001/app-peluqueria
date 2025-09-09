import javax.swing.*;
import java.awt.*;

/**
 * Guarda este archivo como Main.java
 * Compilar: javac Main.java
 * Ejecutar:  java Main
 *
 * Usuario: admin
 * Contraseña: 1234
 */
public class Main {


    static DefaultListModel<String> historial = new DefaultListModel<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginVentana());
    }


    static class LoginVentana extends JFrame {
        JTextField usuarioField;
        JPasswordField passField;

        public LoginVentana() {
            setTitle("Peluquería Unisex - Inicio de Sesión");
            setSize(360, 200);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

            panel.add(new JLabel("Usuario:"));
            usuarioField = new JTextField();
            panel.add(usuarioField);

            panel.add(new JLabel("Contraseña:"));
            passField = new JPasswordField();
            panel.add(passField);

            panel.add(new JLabel("")); // espacio
            JButton loginBtn = new JButton("Ingresar");
            panel.add(loginBtn);

            loginBtn.addActionListener(e -> {
                String usuario = usuarioField.getText().trim();
                String pass = new String(passField.getPassword());
                if (usuario.equals("admin") && pass.equals("1234")) {
                    dispose();
                    new MenuPrincipal(usuario);
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
                }
            });

            add(panel);
            setVisible(true);
        }
    }


    static class MenuPrincipal extends JFrame {
        public MenuPrincipal(String usuario) {
            setTitle("Menú Principal - Bienvenido " + usuario);
            setSize(400, 300);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

            JButton serviciosBtn = new JButton("Servicios");
            JButton productosBtn = new JButton("Productos");
            JButton historialBtn = new JButton("Historial");

            panel.add(serviciosBtn);
            panel.add(productosBtn);
            panel.add(historialBtn);

            serviciosBtn.addActionListener(e -> new ServiciosVentana());
            productosBtn.addActionListener(e -> new ProductosVentana());
            historialBtn.addActionListener(e -> new HistorialVentana());

            add(panel);
            setVisible(true);
        }
    }


    static class ServiciosVentana extends JFrame {
        public ServiciosVentana() {
            setTitle("Servicios de Peluquería");
            setSize(320, 280);
            setLocationRelativeTo(null);

            String[] servicios = {"Corte Caballero", "Corte Dama", "Tintura", "Peinado", "Barba"};
            JList<String> lista = new JList<>(servicios);
            lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            JButton agregarBtn = new JButton("Agregar al historial");
            agregarBtn.addActionListener(e -> {
                String seleccion = lista.getSelectedValue();
                if (seleccion != null) {
                    historial.addElement("Servicio: " + seleccion);
                    JOptionPane.showMessageDialog(this, seleccion + " agregado al historial");
                } else {
                    JOptionPane.showMessageDialog(this, "Selecciona un servicio primero");
                }
            });

            add(new JScrollPane(lista), BorderLayout.CENTER);
            add(agregarBtn, BorderLayout.SOUTH);
            setVisible(true);
        }
    }


    static class ProductosVentana extends JFrame {
        public ProductosVentana() {
            setTitle("Productos a la Venta");
            setSize(320, 280);
            setLocationRelativeTo(null);

            String[] productos = {"Crema de peinar", "Shampoo", "Acondicionador", "Cera capilar", "Gel"};
            JList<String> lista = new JList<>(productos);
            lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            JButton agregarBtn = new JButton("Agregar al historial");
            agregarBtn.addActionListener(e -> {
                String seleccion = lista.getSelectedValue();
                if (seleccion != null) {
                    historial.addElement("Producto: " + seleccion);
                    JOptionPane.showMessageDialog(this, seleccion + " agregado al historial");
                } else {
                    JOptionPane.showMessageDialog(this, "Selecciona un producto primero");
                }
            });

            add(new JScrollPane(lista), BorderLayout.CENTER);
            add(agregarBtn, BorderLayout.SOUTH);
            setVisible(true);
        }
    }


    static class HistorialVentana extends JFrame {
        public HistorialVentana() {
            setTitle("Historial de Selecciones");
            setSize(360, 300);
            setLocationRelativeTo(null);

            JList<String> lista = new JList<>(historial); // se actualiza en tiempo real
            add(new JScrollPane(lista), BorderLayout.CENTER);

            JButton limpiarBtn = new JButton("Limpiar historial");
            limpiarBtn.addActionListener(e -> {
                int r = JOptionPane.showConfirmDialog(this, "¿Limpiar todo el historial?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (r == JOptionPane.YES_OPTION) historial.clear();
            });

            JPanel south = new JPanel();
            south.add(limpiarBtn);
            add(south, BorderLayout.SOUTH);

            setVisible(true);
        }
    }
}