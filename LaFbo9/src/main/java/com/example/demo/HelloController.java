package com.example.demo;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController{
    @FXML
    private Spinner<Integer>SpinnerPapa;
    @FXML
    private Spinner<Integer>SpinnerCarne;
    @FXML
    private Spinner<Integer>spinnerPollo;
    @FXML
    private Spinner<Integer>spinnerVegetales;
    @FXML
    private TextField tfInputNombre;
    @FXML
    private Button btnComprar, btnLimpiar;
    @FXML
    private RadioButton rbEmpleado, rbEstudiante, rbEfectivo, rbTarjeta;
    @FXML
    private Label lblCliente, lblPago, lblSubtotalPapa, lblSubtotalCarne, lblSubtotalPollo, lblSubtotalVegetales, lblTotal;

    double precioPapa = 1.25;
    double precioCarne = 2.25;
    double precioPollo = 1.75;
    double precioVegetales = 0.75;
    private double TotalPrecio;

    @FXML
    public void initialize() {
        ToggleGroup options = new ToggleGroup();
        ToggleGroup options1 = new ToggleGroup();
        rbEmpleado.setToggleGroup(options);
        rbEstudiante.setToggleGroup(options);
        rbEfectivo.setToggleGroup(options1);
        rbTarjeta.setToggleGroup(options1);

        SpinnerValueFactory<Integer> valueFactoryPapa = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        SpinnerPapa.setValueFactory(valueFactoryPapa);

        SpinnerValueFactory<Integer> valueFactoryCarne = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        SpinnerCarne.setValueFactory(valueFactoryCarne);

        SpinnerValueFactory<Integer> valueFactoryPollo = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        spinnerPollo.setValueFactory(valueFactoryPollo);

        SpinnerValueFactory<Integer> valueFactoryVegetales = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        spinnerVegetales.setValueFactory(valueFactoryVegetales);

        //ChangeListener
        SpinnerPapa.valueProperty().addListener(new ChangeListener<Integer>(){

            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                lblSubtotalPapa.setText("$" + Double.toString(precioPapa * t1));
            }
        });

        SpinnerCarne.valueProperty().addListener(new ChangeListener<Integer>(){

            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                lblSubtotalCarne.setText("$" + Double.toString(precioCarne * t1));
            }
        });

        spinnerPollo.valueProperty().addListener(new ChangeListener<Integer>(){

            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                lblSubtotalPollo.setText("$" + Double.toString(precioPollo * t1));
            }
        });

        spinnerVegetales.valueProperty().addListener(new ChangeListener<Integer>(){

            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                lblSubtotalVegetales.setText("$" + Double.toString(precioVegetales * t1));
            }
        });
    }

    @FXML
    public void rbEmpleadoAction(){
        if(rbEmpleado.isSelected()){
            lblCliente.setText(rbEmpleado.getText());
        }
    }

    @FXML
    public void rbEstudianteAction(){
        if(rbEstudiante.isSelected()){
            lblCliente.setText(rbEstudiante.getText());
        }
    }

    @FXML
    public void rbEfectivoAction(){
        if(rbEfectivo.isSelected()){
            lblPago.setText(rbEfectivo.getText());
        }
    }

    @FXML
    public void rbTarjetaAction(){
        if(rbTarjeta.isSelected()){
            lblPago.setText(rbTarjeta.getText());
        }
    }

    @FXML
    private void handlebtnLimpiarAction() {
        SpinnerPapa.getValueFactory().setValue(0);
        SpinnerCarne.getValueFactory().setValue(0);
        spinnerPollo.getValueFactory().setValue(0);
        spinnerVegetales.getValueFactory().setValue(0);
        lblTotal.setText("0.00");
        tfInputNombre.clear();
        rbEfectivo.isSelected();
        lblCliente.setText("");
        lblPago.setText("");
        rbTarjeta.setSelected(false);
        rbEfectivo.setSelected(false);
        rbEstudiante.setSelected(false);
        rbEmpleado.setSelected(false);
    }

    @FXML
    private void handlebtnComprarAction() {
        if (tfInputNombre.getText().length() <= 6) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nombre del cliente inválido");
            alert.setContentText("El nombre del cliente debe tener más de 6 caracteres.");
            alert.show();
            return;
        }

        double subtotalPapaValue = SpinnerPapa.getValue() * precioPapa;
        double subtotalCarneValue = SpinnerCarne.getValue() * precioCarne;
        double subtotalPolloValue = spinnerPollo.getValue() * precioPollo;
        double subtotalVegetalesValue = spinnerVegetales.getValue() * precioVegetales;

        if (subtotalPapaValue == 0 && subtotalCarneValue == 0 && subtotalPolloValue == 0 && subtotalVegetalesValue == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Ningún producto seleccionado");
            alert.setContentText("Debe seleccionar al menos un producto para realizar una compra.");
            alert.show();
            return;
        }

        if (!rbEfectivo.isSelected() && !rbTarjeta.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No ha seleccionado metodo de pago");
            alert.setContentText("Debe seleccionar un metodo de pago para continuar.");
            alert.show();
            return;
        }

        if (!rbEmpleado.isSelected() && !rbEstudiante.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No ha seleccionado el tipo de Cliente");
            alert.setContentText("Debe seleccionar un tipo de cliente para continuar.");
            alert.show();
            return;
        }

        /*lblSubtotalPapa.setText(String.format("$%.2f", subtotalPapaValue));
        lblSubtotalCarne.setText(String.format("$%.2f", subtotalCarneValue));
        lblSubtotalPollo.setText(String.format("$%.2f", subtotalPolloValue));
        lblSubtotalVegetales.setText(String.format("$%.2f", subtotalVegetalesValue));*/

        double total = subtotalPapaValue + subtotalCarneValue + subtotalPolloValue + subtotalVegetalesValue;
        lblTotal.setText(String.format("$%.2f", total));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Compra Realizada");
        alert.setHeaderText("Gracias por su compra");
        alert.setContentText("Su compra ha sido realizada exitosamente. Total: $" + total + "\n" + "Nombre: " + tfInputNombre.getText() + "\n" + "Forma de pago: " + lblPago.getText());
        alert.show();
    }

    public void lblClienteAction(MouseEvent mouseEvent) {
    }
    public void lblPagoAction(MouseEvent mouseEvent) {
    }
    public void tfInputNombreAction(ActionEvent actionEvent) {
    }

}