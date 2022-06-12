module com.github.permissiondog.tohokuim {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires com.google.gson;
    requires MaterialFX;


    opens com.github.permissiondog.tohokuim to javafx.fxml, com.google.gson;
    opens com.github.permissiondog.tohokuim.controller to javafx.fxml;
    opens com.github.permissiondog.tohokuim.entity to com.google.gson;
    opens com.github.permissiondog.tohokuim.net to com.google.gson;
    exports com.github.permissiondog.tohokuim;
}