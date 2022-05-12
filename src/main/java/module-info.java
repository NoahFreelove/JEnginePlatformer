module com.jengineplatformer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.jengineplatformer to javafx.fxml;
    exports com.jengineplatformer;
}