package cliente;

import modelo.Documental;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class VentanaDocumentales extends JFrame {

    private DefaultTableModel modeloTabla;
    private JTable tabla;

    public VentanaDocumentales() {
        setTitle("Gestión de Documentales - Fideflix");
        setSize(800, 400);
        setLocationRelativeTo(null);

        // --- Tabla ---
        String[] columnas = {"ID", "Título", "Tema", "Duración", "Año", "Calificación", "Director"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        // --- Botones ---
        JButton btnAgregar = new JButton("Agregar Documental");
        JButton btnEditar = new JButton("Editar Seleccionado");
        JButton btnEliminar = new JButton("Eliminar Seleccionado");

        btnAgregar.addActionListener(e -> mostrarDialogoAgregar());
        btnEditar.addActionListener(e -> mostrarDialogoEditar());
        btnEliminar.addActionListener(e -> eliminarSeleccionado());

        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            boolean haySeleccion = tabla.getSelectedRow() != -1;
            btnEditar.setEnabled(haySeleccion);
            btnEliminar.setEnabled(haySeleccion);
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarDocumentales();
    }

    // Cargar documentales
    private void cargarDocumentales() {
        modeloTabla.setRowCount(0);
        List<Documental> lista = ClienteSocket.obtenerDocumentales();
        if (lista != null) {
            for (Documental d : lista) {
                Vector<Object> fila = new Vector<>();
                fila.add(d.getId());
                fila.add(d.getTitulo());
                fila.add(d.getTema());
                fila.add(d.getDuracion());
                fila.add(d.getAnioEstreno());
                fila.add(d.getCalificacion());
                fila.add(d.getDirector());
                modeloTabla.addRow(fila);
            }
        }
    }

    private void mostrarDialogoAgregar() {
        JTextField tfTitulo = new JTextField();
        JTextField tfTema = new JTextField();
        JTextField tfDuracion = new JTextField();
        JTextField tfAnio = new JTextField();
        JTextField tfCalificacion = new JTextField();
        JTextField tfDirector = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Título:"));       panel.add(tfTitulo);
        panel.add(new JLabel("Tema:"));         panel.add(tfTema);
        panel.add(new JLabel("Duración:"));     panel.add(tfDuracion);
        panel.add(new JLabel("Año de estreno:"));panel.add(tfAnio);
        panel.add(new JLabel("Calificación:")); panel.add(tfCalificacion);
        panel.add(new JLabel("Director:"));     panel.add(tfDirector);

        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Documental",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String titulo = tfTitulo.getText().trim();
                String tema = tfTema.getText().trim();
                int duracion = Integer.parseInt(tfDuracion.getText().trim());
                int anio = Integer.parseInt(tfAnio.getText().trim());
                double calif = Double.parseDouble(tfCalificacion.getText().trim());
                String director = tfDirector.getText().trim();

                Documental nuevo = new Documental(titulo, tema, duracion, anio, calif, director);
                boolean exito = ClienteSocket.insertarDocumental(nuevo);

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Documental agregado correctamente.");
                    cargarDocumentales();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar documental (duplicado o datos inválidos).");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos, revise los campos.");
            }
        }
    }

    private void mostrarDialogoEditar() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) return;

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String titulo = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        String tema = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
        int duracion = (int) modeloTabla.getValueAt(filaSeleccionada, 3);
        int anio = (int) modeloTabla.getValueAt(filaSeleccionada, 4);
        double calif = (double) modeloTabla.getValueAt(filaSeleccionada, 5);
        String director = (String) modeloTabla.getValueAt(filaSeleccionada, 6);

        JTextField tfTitulo = new JTextField(titulo);
        JTextField tfTema = new JTextField(tema);
        JTextField tfDuracion = new JTextField(String.valueOf(duracion));
        JTextField tfAnio = new JTextField(String.valueOf(anio));
        JTextField tfCalificacion = new JTextField(String.valueOf(calif));
        JTextField tfDirector = new JTextField(director);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Título:"));        panel.add(tfTitulo);
        panel.add(new JLabel("Tema:"));          panel.add(tfTema);
        panel.add(new JLabel("Duración:"));      panel.add(tfDuracion);
        panel.add(new JLabel("Año de estreno:")) ; panel.add(tfAnio);
        panel.add(new JLabel("Calificación:"));  panel.add(tfCalificacion);
        panel.add(new JLabel("Director:"));      panel.add(tfDirector);

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Documental",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String nuevoTitulo = tfTitulo.getText().trim();
                String nuevoTema = tfTema.getText().trim();
                int nuevaDuracion = Integer.parseInt(tfDuracion.getText().trim());
                int nuevoAnio = Integer.parseInt(tfAnio.getText().trim());
                double nuevaCalif = Double.parseDouble(tfCalificacion.getText().trim());
                String nuevoDirector = tfDirector.getText().trim();

                Documental actualizado = new Documental(nuevoTitulo, nuevoTema, nuevaDuracion, nuevoAnio, nuevaCalif, nuevoDirector);
                actualizado.setId(id);
                boolean exito = ClienteSocket.actualizarDocumental(actualizado);

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Documental editado correctamente.");
                    cargarDocumentales();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al editar documental.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos.");
            }
        }
    }

    private void eliminarSeleccionado() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) return;
        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro de eliminar el documental seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean exito = ClienteSocket.eliminarDocumental(id);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Documental eliminado.");
                cargarDocumentales();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar documental.");
            }
        }
    }
}
