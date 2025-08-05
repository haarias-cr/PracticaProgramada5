package cliente;

import modelo.Pelicula;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class VentanaPeliculas extends JFrame {

    private DefaultTableModel modeloTabla;
    private JTable tabla;

    public VentanaPeliculas() {
        setTitle("Gestión de Películas - Fideflix");
        setSize(700, 400);
        setLocationRelativeTo(null);

        // --- Tabla ---
        String[] columnas = {"ID", "Título", "Género", "Duración", "Año", "Calificación", "Director"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false; // Para que el usuario no pueda editar directamente la tabla
            }
        };
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        // --- Botones ---
        JButton btnAgregar = new JButton("Agregar Película");
        JButton btnEditar = new JButton("Editar Seleccionada");
        JButton btnEliminar = new JButton("Eliminar Seleccionada");

        // Acción agregar
        btnAgregar.addActionListener(e -> mostrarDialogoAgregar());

        // Acción editar
        btnEditar.addActionListener(e -> mostrarDialogoEditar());

        // Acción eliminar
        btnEliminar.addActionListener(e -> eliminarSeleccionada());

        // --- Habilita/deshabilita Editar y Eliminar según selección ---
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            boolean haySeleccion = tabla.getSelectedRow() != -1;
            btnEditar.setEnabled(haySeleccion);
            btnEliminar.setEnabled(haySeleccion);
        });

        // --- Layout ---
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarPeliculas();
    }

    // Carga películas desde el servidor y muestra en la tabla
    private void cargarPeliculas() {
        modeloTabla.setRowCount(0);
        List<Pelicula> lista = ClienteSocket.obtenerPeliculas();
        if (lista != null) {
            for (Pelicula p : lista) {
                Vector<Object> fila = new Vector<>();
                fila.add(p.getId());
                fila.add(p.getTitulo());
                fila.add(p.getGenero());
                fila.add(p.getDuracion());
                fila.add(p.getAnioEstreno());
                fila.add(p.getCalificacion());
                fila.add(p.getDirector());
                modeloTabla.addRow(fila);
            }
        }
    }

    // Diálogo para agregar película
    private void mostrarDialogoAgregar() {
        JTextField tfTitulo = new JTextField();
        JTextField tfGenero = new JTextField();
        JTextField tfDuracion = new JTextField();
        JTextField tfAnio = new JTextField();
        JTextField tfCalificacion = new JTextField();
        JTextField tfDirector = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Título:"));        panel.add(tfTitulo);
        panel.add(new JLabel("Género:"));        panel.add(tfGenero);
        panel.add(new JLabel("Duración:"));      panel.add(tfDuracion);
        panel.add(new JLabel("Año de estreno:"));panel.add(tfAnio);
        panel.add(new JLabel("Calificación:"));  panel.add(tfCalificacion);
        panel.add(new JLabel("Director:"));      panel.add(tfDirector);

        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Película",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String titulo = tfTitulo.getText().trim();
                String genero = tfGenero.getText().trim();
                int duracion = Integer.parseInt(tfDuracion.getText().trim());
                int anio = Integer.parseInt(tfAnio.getText().trim());
                double calif = Double.parseDouble(tfCalificacion.getText().trim());
                String director = tfDirector.getText().trim();

                Pelicula nueva = new Pelicula(titulo, genero, duracion, anio, calif, director);
                boolean exito = ClienteSocket.insertarPelicula(nueva);

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Película agregada correctamente.");
                    cargarPeliculas();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar película (duplicado o datos inválidos).");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos, revise los campos.");
            }
        }
    }

    // Diálogo para editar película
    private void mostrarDialogoEditar() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) return;

        // Obtiene los datos actuales
        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String titulo = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        String genero = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
        int duracion = (int) modeloTabla.getValueAt(filaSeleccionada, 3);
        int anio = (int) modeloTabla.getValueAt(filaSeleccionada, 4);
        double calif = (double) modeloTabla.getValueAt(filaSeleccionada, 5);
        String director = (String) modeloTabla.getValueAt(filaSeleccionada, 6);

        JTextField tfTitulo = new JTextField(titulo);
        JTextField tfGenero = new JTextField(genero);
        JTextField tfDuracion = new JTextField(String.valueOf(duracion));
        JTextField tfAnio = new JTextField(String.valueOf(anio));
        JTextField tfCalificacion = new JTextField(String.valueOf(calif));
        JTextField tfDirector = new JTextField(director);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Título:"));        panel.add(tfTitulo);
        panel.add(new JLabel("Género:"));        panel.add(tfGenero);
        panel.add(new JLabel("Duración:"));      panel.add(tfDuracion);
        panel.add(new JLabel("Año de estreno:"));panel.add(tfAnio);
        panel.add(new JLabel("Calificación:"));  panel.add(tfCalificacion);
        panel.add(new JLabel("Director:"));      panel.add(tfDirector);

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Película",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String nuevoTitulo = tfTitulo.getText().trim();
                String nuevoGenero = tfGenero.getText().trim();
                int nuevaDuracion = Integer.parseInt(tfDuracion.getText().trim());
                int nuevoAnio = Integer.parseInt(tfAnio.getText().trim());
                double nuevaCalif = Double.parseDouble(tfCalificacion.getText().trim());
                String nuevoDirector = tfDirector.getText().trim();

                Pelicula actualizada = new Pelicula(nuevoTitulo, nuevoGenero, nuevaDuracion, nuevoAnio, nuevaCalif, nuevoDirector);
                actualizada.setId(id);
                boolean exito = ClienteSocket.actualizarPelicula(actualizada);

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Película editada correctamente.");
                    cargarPeliculas();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al editar película.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos.");
            }
        }
    }

    // Elimina la película seleccionada
    private void eliminarSeleccionada() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) return;
        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro de eliminar la película seleccionada?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean exito = ClienteSocket.eliminarPelicula(id);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Película eliminada.");
                cargarPeliculas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar película.");
            }
        }
    }
}
