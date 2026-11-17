package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.ConsultasProductos;
import modelo.Producto;
import vista.GestionProducto;
/**
 *
 * @author carol
 */

public class CtrTienda implements ActionListener {

    private GestionProducto vista;
    private Producto producto;
    private ConsultasProductos consultas;

    public CtrTienda(
            GestionProducto vista,
            Producto producto,
            ConsultasProductos consultas) {

        this.vista = vista;
        this.producto = producto;
        this.consultas = consultas;

        this.vista.getBtnGuardar().addActionListener(this);
        this.vista.getBtnModificar().addActionListener(this);
        this.vista.getBtnEliminar().addActionListener(this);
        this.vista.getBtnBuscar().addActionListener(this);
        this.vista.getBtnLimpiar().addActionListener(this);
        this.vista.getBtnListar().addActionListener(this);

        this.vista.getTblListaProductos().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                eleccion();
            }

        });
        this.vista.setLocationRelativeTo(null);
        this.vista.setVisible(true);

        listar();
    }

    @Override
    
    public void actionPerformed(ActionEvent e) {
     
        if (e.getSource() == vista.getBtnGuardar()) {

            try {

                String nombre = vista.getTxtNombre().getText();

                double precio = Double.parseDouble(
                        vista.getTxtPrecio().getText());

                int stock = Integer.parseInt(
                        vista.getTxtStock().getText());

                if (nombre.isEmpty()) {

                    JOptionPane.showMessageDialog(vista, "Ingresa el nombre del producto");

                    return;
                }

                producto.setNombre(nombre);
                producto.setPrecio(precio);
                producto.setStock(stock);

                if (consultas.insertar(producto)) {

                    JOptionPane.showMessageDialog(
                            vista,
                            "Producto guardado correctamente");

                    vista.limpiarCampos();
                    listar();

                } else {

                    JOptionPane.showMessageDialog(vista,"No se pudo guardar el producto");
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(vista, "Revisa que los datos sean correctos");
            }
        }

        // =========================
        // BUSCAR
        // =========================

        if (e.getSource() == vista.getBtnBuscar()) {

            try {

                int id = Integer.parseInt(vista.getTxtId().getText());

                producto.setIdProducto(id);

                if (consultas.buscar(producto)) {

                    vista.setTxtNombre(producto.getNombre());
                    vista.setTxtPrecio(String.valueOf(producto.getPrecio()));
                    
                    vista.setTxtStock( String.valueOf(producto.getStock()));

                } else {

                    JOptionPane.showMessageDialog(vista,"Producto no encontrado");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista,"Ingresa un ID válido");
            }
        }


        if (e.getSource() == vista.getBtnModificar()) {

            try {

                int id = Integer.parseInt(vista.getTxtId().getText());

                String nombre = vista.getTxtNombre().getText();
                double precio = Double.parseDouble(vista.getTxtPrecio().getText());
                int stock = Integer.parseInt(vista.getTxtStock().getText());

                producto.setIdProducto(id);
                producto.setNombre(nombre);
                producto.setPrecio(precio);
                producto.setStock(stock);

                if (consultas.modificar(producto)) {

                    JOptionPane.showMessageDialog(
                            vista,
                            "Producto modificado correctamente");

                    vista.limpiarCampos();
                    listar();

                } else {

                    JOptionPane.showMessageDialog(
                            vista,
                            "No se pudo modificar el producto");
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(vista,"Verifica los datos ingresados");
            }
        }


        if (e.getSource() == vista.getBtnEliminar()) {

            try {

                int id = Integer.parseInt(
                        vista.getTxtId().getText());

                int confirmacion = JOptionPane.showConfirmDialog(vista,
                        "¿Seguro que deseas eliminar este producto?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {

                    producto.setIdProducto(id);

                    if (consultas.eliminar(producto)) {

                        JOptionPane.showMessageDialog(
                                vista,
                                "Producto eliminado correctamente");

                        vista.limpiarCampos();
                        listar();

                    } else {

                        JOptionPane.showMessageDialog(vista,"No se encontró el producto");
                    }
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        vista,
                        "Ingresa un ID válido");
            }
        }


        if (e.getSource() == vista.getBtnLimpiar()) {

            vista.limpiarCampos();
        }


        if (e.getSource() == vista.getBtnListar()) {

            listar();
        }
    }


    private void listar() {

        DefaultTableModel modelo =
                (DefaultTableModel) vista
                        .getTblListaProductos()
                        .getModel();

        consultas.listar(modelo);
    }

    private void eleccion() {

        int fila = vista.getTblListaProductos()
                .getSelectedRow();

        if (fila >= 0) {

            // Obtener ID de la primera columna
            int id = Integer.parseInt(
                    vista.getTblListaProductos()
                            .getValueAt(fila, 0)
                            .toString());

            // Colocar ID en el campo de texto
            vista.setTxtId(String.valueOf(id));

            // Obtener nombre
            vista.setTxtNombre(
                    vista.getTblListaProductos()
                            .getValueAt(fila, 1)
                            .toString());

            // Obtener precio
            vista.setTxtPrecio(vista.getTblListaProductos().getValueAt(fila, 2)
                            .toString());

            // Obtener stock
            vista.setTxtStock(
                    vista.getTblListaProductos()
                            .getValueAt(fila, 3)
                            .toString());
        }
    }
    
    private boolean validarCampos(){
        int vacios = 0;
        
        JTextField[] datos = {this.vista.getTxtNombre(), this.vista.getTxtPrecio(), this.vista.getTxtStock()};
        
        for(JTextField dato : datos){
            if(dato.getText().trim().isEmpty() && dato.getText().trim().isBlank())
                vacios++;
            
            
            
            
        }
        return vacios == 0 ? true : false;
    }
}