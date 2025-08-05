package cliente;

import modelo.Serie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class VentanaSeries extends JFrame {

    private DefaultTableModel modeloTabla;
    private JTable tabla;

    public VentanaSeries() {
        setTitle("Gestión de Series - Fideflix");
        setSize(800, 400);
        setLocationRelativeTo(null);

        // --- Tabla ---
        String[] columnas = {"ID", "Título", "Clasificación", "Duración", "Temporadas", "Año", "Calificación", "Creador"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false; // El usuario no puede editar directamente la tabla
            }
        };
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        // --- Botones ---
        JButton btnAgregar = new JButton("Agregar Serie");
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

        cargarSeries();
    }

    // Carga series desde el servidor y muestra en la tabla
    private void cargarSeries() {
        modeloTabla.setRowCount(0);
        List<Serie> lista = ClienteSocket.obtenerSeries();
        if (lista != null) {
            for (Serie s : lista) {
                Vector<Object> fila = new Vector<>();
                fila.add(s.getId());
                fila.add(s.getTitulo());
                fila.add(s.getClasificacion());
                fila.add(s.getDuracion());
                fila.add(s.getTemporadas());
                fila.add(s.getAnioEstreno());
                fila.add(s.getCalificacion());
                fila.add(s.getCreador());
                modeloTabla.addRow(fila);
            }
        }
    }

    // Diálogo para agregar serie
    private void mostrarDialogoAgregar() {
        JTextField tfTitulo = new JTextField();
        JTextField tfClasificacion = new JTextField();
        JTextField tfDuracion = new JTextField();
        JTextField tfTemporadas = new JTextField();
        JTextField tfAnio = new JTextField();
        JTextField tfCalificacion = new JTextField();
        JTextField tfCreador = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Título:"));         panel.add(tfTitulo);
        panel.add(new JLabel("Clasificación:"));  panel.add(tfClasificacion);
        panel.add(new JLabel("Duración (min):")); panel.add(tfDuracion);
        panel.add(new JLabel("Temporadas:"));     panel.add(tfTemporadas);
        panel.add(new JLabel("Año de estreno:")); panel.add(tfAnio);
        panel.add(new JLabel("Calificación:"));   panel.add(tfCalificacion);
        panel.add(new JLabel("Creador:"));        panel.add(tfCreador);

        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Serie",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String titulo = tfTitulo.getText().trim();
                String clasificacion = tfClasificacion.getText().trim();
                int duracion = Integer.parseInt(tfDuracion.getText().trim());
                int temporadas = Integer.parseInt(tfTemporadas.getText().trim());
                int anio = Integer.parseInt(tfAnio.getText().trim());
                double calif = Double.parseDouble(tfCalificacion.getText().trim());
                String creador = tfCreador.getText().trim();

                Serie nueva = new Serie(titulo, clasificacion, duracion, temporadas, anio, calif, creador);
                boolean exito = ClienteSocket.insertarSerie(nueva);

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Serie agregada correctamente.");
                    cargarSeries();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar serie (duplicado o datos inválidos).");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos, revise los campos.");
            }
        }
    }

    // Diálogo para editar serie
    private void mostrarDialogoEditar() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) return;

        // Obtiene los datos actuales
        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String titulo = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        String clasificacion = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
        int duracion = (int) modeloTabla.getValueAt(filaSeleccionada, 3);
        int temporadas = (int) modeloTabla.getValueAt(filaSeleccionada, 4);
        int anio = (int) modeloTabla.getValueAt(filaSeleccionada, 5);
        double calif = (double) modeloTabla.getValueAt(filaSeleccionada, 6);
        String creador = (String) modeloTabla.getValueAt(filaSeleccionada, 7);

        JTextField tfTitulo = new JTextField(titulo);
        JTextField tfClasificacion = new JTextField(clasificacion);
        JTextField tfDuracion = new JTextField(String.valueOf(duracion));
        JTextField tfTemporadas = new JTextField(String.valueOf(temporadas));
        JTextField tfAnio = new JTextField(String.valueOf(anio));
        JTextField tfCalificacion = new JTextField(String.valueOf(calif));
        JTextField tfCreador = new JTextField(creador);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Título:"));         panel.add(tfTitulo);
        panel.add(new JLabel("Clasificación:"));  panel.add(tfClasificacion);
        panel.add(new JLabel("Duración (min):")); panel.add(tfDuracion);
        panel.add(new JLabel("Temporadas:"));     panel.add(tfTemporadas);
        panel.add(new JLabel("Año de estreno:")); panel.add(tfAnio);
        panel.add(new JLabel("Calificación:"));   panel.add(tfCalificacion);
        panel.add(new JLabel("Creador:"));        panel.add(tfCreador);

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Serie",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String nuevoTitulo = tfTitulo.getText().trim();
                String nuevaClasif = tfClasificacion.getText().trim();
                int nuevaDuracion = Integer.parseInt(tfDuracion.getText().trim());
                int nuevasTemporadas = Integer.parseInt(tfTemporadas.getText().trim());
                int nuevoAnio = Integer.parseInt(tfAnio.getText().trim());
                double nuevaCalif = Double.parseDouble(tfCalificacion.getText().trim());
                String nuevoCreador = tfCreador.getText().trim();

                Serie actualizada = new Serie(nuevoTitulo, nuevaClasif, nuevaDuracion, nuevasTemporadas, nuevoAnio, nuevaCalif, nuevoCreador);
                actualizada.setId(id);
                boolean exito = ClienteSocket.actualizarSerie(actualizada);

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Serie editada correctamente.");
                    cargarSeries();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al editar serie.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos.");
            }
        }
    }

    // Elimina la serie seleccionada
    private void eliminarSeleccionada() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) return;
        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro de eliminar la serie seleccionada?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean exito = ClienteSocket.eliminarSerie(id);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Serie eliminada.");
                cargarSeries();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar serie.");
            }
        }
    }
}
