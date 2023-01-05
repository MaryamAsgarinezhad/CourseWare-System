module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires gson;
    requires json.simple;
    requires log4j;
    requires jackson.core.lgpl;
    requires jackson.mapper.lgpl;
    requires java.desktop;
    requires pdfbox;

    opens com.example.demo to javafx.fxml;
    exports client.util;
    exports server.request;
    exports server.response;
    exports server.database;
    exports com.example.demo;
    exports guiController;
    opens guiController to javafx.fxml;
    exports Logic;
    opens Logic to javafx.fxml;
}