import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class Usuario {
    String nombre;
    String telefono;
    String tipoCliente;
    String corteSeleccionado;
    LocalDateTime fechaEntrada;
    LocalDateTime fechaSalida;

    Usuario(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.fechaEntrada = LocalDateTime.now();
        this.fechaSalida = null;
        this.corteSeleccionado = "";
    }

    void salir() {
        this.fechaSalida = LocalDateTime.now();
    }

    @Override
    public String toString() {
        String salida = (fechaSalida == null) ? "En barbería" : fechaSalida.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return nombre + " | " + telefono + " | " + tipoCliente + " | Corte: " + corteSeleccionado + " | Entrada: "
                + fechaEntrada.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " | Salida: " + salida;
    }
}

class BarberiaExotica {
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private Usuario usuarioActual;
    private JTextArea mensajesArea;

    public BarberiaExotica() {
        mostrarVentanaInicio();
    }

    private void mostrarVentanaInicio() {
        JFrame frame = new JFrame("Barbería Exótica");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(245, 222, 179));

        JLabel lblBienvenida = new JLabel("<html><center>Bienvenidos a nuestra Barbería Exótica,<br>Dios los bendiga</center></html>", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Monotype Corsiva", Font.BOLD, 22));
        lblBienvenida.setForeground(new Color(139, 69, 19));
        lblBienvenida.setBorder(new EmptyBorder(20,10,20,10));
        frame.add(lblBienvenida, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(4,2,10,10));
        panel.setBackground(new Color(245, 222, 179));
        panel.setBorder(new EmptyBorder(50,100,50,100));

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();

        JLabel lblTelefono = new JLabel("Teléfono:");
        JTextField txtTelefono = new JTextField();

        JButton btnIniciar = new JButton("Iniciar Sesión");
        JButton btnMensajes = new JButton("Buzón de Mensajes");

        panel.add(lblNombre); panel.add(txtNombre);
        panel.add(lblTelefono); panel.add(txtTelefono);
        panel.add(btnIniciar); panel.add(btnMensajes);

        frame.add(panel, BorderLayout.CENTER);

        mensajesArea = new JTextArea();
        mensajesArea.setEditable(false);
        mensajesArea.setBackground(new Color(255, 228, 196));
        JScrollPane scrollMensajes = new JScrollPane(mensajesArea);
        scrollMensajes.setBorder(BorderFactory.createTitledBorder("Buzón de Mensajes"));
        frame.add(scrollMensajes, BorderLayout.SOUTH);

        btnIniciar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String telefono = txtTelefono.getText().trim();
            if(!nombre.isEmpty() && !telefono.isEmpty()) {
                usuarioActual = new Usuario(nombre, telefono);
                usuarios.add(usuarioActual);
                mensajesArea.append("Usuario ingresó: " + nombre + "\n");
                frame.dispose();
                mostrarMenuCortes();
            } else {
                JOptionPane.showMessageDialog(frame, "Ingrese nombre y teléfono.");
            }
        });

        btnMensajes.addActionListener(e -> JOptionPane.showMessageDialog(frame, mensajesArea.getText(), "Mensajes del propietario", JOptionPane.INFORMATION_MESSAGE));

        frame.setVisible(true);
    }

    private void mostrarMenuCortes() {
        JFrame frame = new JFrame("Menú de Cortes");
        frame.setSize(750, 550);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(224, 255, 255));

        JLabel lblTitulo = new JLabel("Seleccione su tipo de cliente y corte", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(0, 128, 128));
        lblTitulo.setBorder(new EmptyBorder(20,10,20,10));
        frame.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelMenu = new JPanel(new GridLayout(3,2,10,10));
        panelMenu.setBorder(new EmptyBorder(20,50,20,50));
        panelMenu.setBackground(new Color(224, 255, 255));

        JButton btnHombres = new JButton("Hombres");
        JButton btnMujeres = new JButton("Mujeres");
        JButton btnAdultos = new JButton("Adultos");
        JButton btnJovenes = new JButton("Jóvenes");
        JButton btnNinos = new JButton("Niños");
        JButton btnProductos = new JButton("Productos");

        panelMenu.add(btnHombres); panelMenu.add(btnMujeres);
        panelMenu.add(btnAdultos); panelMenu.add(btnJovenes);
        panelMenu.add(btnNinos); panelMenu.add(btnProductos);

        frame.add(panelMenu, BorderLayout.CENTER);


        JTextArea historialArea = new JTextArea();
        historialArea.setEditable(false);
        historialArea.setBackground(new Color(255, 240, 245));
        JScrollPane scrollHistorial = new JScrollPane(historialArea);
        scrollHistorial.setBorder(BorderFactory.createTitledBorder("Historial de Usuarios"));

        frame.add(scrollHistorial, BorderLayout.SOUTH);

        
        btnHombres.addActionListener(e -> seleccionarCorte("Hombres", historialArea));
        btnMujeres.addActionListener(e -> seleccionarCorte("Mujeres", historialArea));
        btnAdultos.addActionListener(e -> seleccionarCorte("Adultos", historialArea));
        btnJovenes.addActionListener(e -> seleccionarCorte("Jóvenes", historialArea));
        btnNinos.addActionListener(e -> seleccionarCorte("Niños", historialArea));
        btnProductos.addActionListener(e -> mostrarProductos());

        actualizarHistorial(historialArea);

        frame.setVisible(true);
    }

    private void seleccionarCorte(String tipoCliente, JTextArea historialArea) {
        String[] cortes;
        double[] precios;

        switch(tipoCliente) {
            case "Hombres":
                cortes = new String[]{"Corte Moderno", "Corte Clásico", "Barba"};
                precios = new double[]{20, 15, 10};
                break;
            case "Mujeres":
                cortes = new String[]{"Corte Moderno", "Corte Clásico", "Peinado"};
                precios = new double[]{25, 20, 15};
                break;
            case "Adultos":
            case "Jóvenes":
                cortes = new String[]{"Corte Moderno", "Corte Clásico"};
                precios = new double[]{20, 15};
                break;
            case "Niños":
                cortes = new String[]{"Corte Moderno", "Corte Clásico"};
                precios = new double[]{10, 8};
                break;
            default:
                cortes = new String[]{};
                precios = new double[]{};
        }

        StringBuilder opciones = new StringBuilder();
        for(int i=0;i<cortes.length;i++) {
            opciones.append(cortes[i]).append(" ($").append(precios[i]).append(")\n");
        }

        String corte = (String) JOptionPane.showInputDialog(null, "Seleccione corte para " + tipoCliente + ":\n" + opciones,
                "Cortes", JOptionPane.QUESTION_MESSAGE, null, cortes, cortes[0]);

        if(corte != null) {
            usuarioActual.tipoCliente = tipoCliente;
            usuarioActual.corteSeleccionado = corte;
            mensajesArea.append("Usuario " + usuarioActual.nombre + " seleccionó: " + corte + "\n");
            actualizarHistorial(historialArea);
        }
    }

    private void mostrarProductos() {
        String[] productos = {"Shampoo $5", "Perfume $10", "Crema de Peinar $7"};
        String producto = (String) JOptionPane.showInputDialog(null, "Seleccione producto a comprar",
                "Productos", JOptionPane.QUESTION_MESSAGE, null, productos, productos[0]);
        if(producto != null) {
            mensajesArea.append("Usuario " + usuarioActual.nombre + " compró: " + producto + "\n");
        }
    }

    private void actualizarHistorial(JTextArea historialArea) {
        historialArea.setText("");
        for(Usuario u : usuarios) {
            historialArea.append(u.toString() + "\n");
        }
        historialArea.append("\n¡Gracias por su atención! Saludos cordiales.\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BarberiaExotica::new);
    }
}
