package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import java.util.Map;
import java.util.HashMap;
import java.math.MathContext;

public class SavingsCalculatorApplication extends Application {

    @Override
    public void start(Stage window) {

//        Creating the layout for the Scene;
        BorderPane layout = new BorderPane();
        BorderPane savinglayout = new BorderPane();
        BorderPane intrestlayout = new BorderPane();

//        Creaing the Vertcial Scetion of the Applcation;
        VBox box = new VBox();
        box.setSpacing(10);

//        Adding borderPane layout to vbox;
        box.getChildren().add(savinglayout);
        box.getChildren().add(intrestlayout);

//        Adding the Silder into the Apllcaition;
        Slider savingSlider = new Slider(25, 250, 25);
        savingSlider.setShowTickLabels(true);
        savingSlider.setShowTickMarks(true);
        savingSlider.setMinorTickCount(25);
        savingSlider.setBlockIncrement(25.0);

        savingSlider.setMajorTickUnit(25);
        savingSlider.setMinorTickCount(5);

        Slider intrestRateSlider = new Slider(0, 10, 0);
        intrestRateSlider.setShowTickLabels(true);
        intrestRateSlider.setShowTickMarks(true);

        intrestRateSlider.setMajorTickUnit(25);
        intrestRateSlider.setMinorTickCount(5);

// Creating the label for the Silder's 
        Label monthlySaveing = new Label("Monthly savings");
        Label intrestRate = new Label("Yearly interest rate");
        Label textMountly = new Label("25.0");
        Label textIntrest = new Label("0.0");

//        Adding elemts to the savingPane;
        savinglayout.setLeft(monthlySaveing);
        savinglayout.setRight(textMountly);
        savinglayout.setCenter(savingSlider);

//      Adding elements to the Intersetpane;
        intrestlayout.setLeft(intrestRate);
        intrestlayout.setRight(textIntrest);

        intrestlayout.setCenter(intrestRateSlider);

//        X and Y axis for thr lineChart;
        NumberAxis x = new NumberAxis(0, 30, 1);
        NumberAxis y = new NumberAxis();

//        Creating and Adding the lineChart in the Layout;
        LineChart lineChart = new LineChart(x, y);
        layout.setCenter(lineChart);

//Now adding the VBox at the top of the layout of Apllcaitionl;
        layout.setTop(box);

//        Now form here we will be working on the chart data fecting part of the program;
        XYChart.Series data = new XYChart.Series<>();
        XYChart.Series data1 = new XYChart.Series<>();
        double value = savingSlider.getValue();

        for (int i = 0; i <= 30; i++) {

            data.getData().add(new XYChart.Data<>(i, i * 12 * 25));

        }

        savingSlider.valueProperty().addListener((obv, olVal, newVal) -> {

            data.getData().clear();
            int valuex = (int) (Math.floor(newVal.intValue() / 10) * 10);
            textMountly.setText(String.valueOf(Double.valueOf(valuex)));
            for (int i = 0; i <= 30; i++) {

                data.getData().add(new XYChart.Data<>(i, i * 12 * valuex));

            }

            intrestRateSlider.valueProperty().addListener((obv1, oldVal1, newVal1) -> {

                data1.getData().clear();
                double valueOfIntrest = newVal1.doubleValue();
                textIntrest.setText(String.valueOf(Double.valueOf(valueOfIntrest)));
                int j = 0;
                
                double previousIntrest = 0;
                double previousAmount = 0;
                int rate =0 ;
                while (j <= 30) {

                    previousAmount =  (12 * valuex +previousAmount)*(1+rate);
                    previousIntrest = (previousAmount * valueOfIntrest) / 100;
                    previousAmount = previousAmount+previousIntrest;

                    data1.getData().add(new XYChart.Data<>(j, previousAmount ));

                    j++;
                
                }
            });

        });

         intrestRateSlider.valueProperty().addListener((obv1, oldVal1, newVal1) -> {

                data1.getData().clear();
                double valueOfIntrest = newVal1.doubleValue();
                textIntrest.setText(String.valueOf(Double.valueOf(valueOfIntrest)));
                int j = 0;
                
                double previousIntrest = 0;
                double previousAmount = 0;
                int rate =0;
                while (j <= 30) {

                    previousAmount =  (12 * savingSlider.getValue() +previousAmount)*(1+rate);
                    previousIntrest = (previousAmount * valueOfIntrest) / 100;
                    previousAmount = previousAmount+previousIntrest;

                    data1.getData().add(new XYChart.Data<>(j, previousAmount ));

                    j++;
                
                }
            });

        lineChart.getData().add(data);
        lineChart.getData().add(data1);

//        Creating,adding the Scene and intialsing the window too;
        Scene view = new Scene(layout);
        window.setScene(view);
        window.show();

    }

    public static void main(String[] args) {
        launch(SavingsCalculatorApplication.class);
    }

    private void updatingChart(Slider slider, LineChart chart) {

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {

            XYChart.Series data = new XYChart.Series<>();

            data.getData().add(newValue.intValue());
            chart.getData().add(data);

        });

    }

}
