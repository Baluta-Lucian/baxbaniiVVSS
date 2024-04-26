module tasks {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires log4j;


    exports tasks.model;
    opens tasks.view to javafx.fxml;
    exports tasks.view;
    opens tasks.controller to javafx.fxml;
    exports tasks.controller;
    exports tasks.utils;
    opens tasks.utils to javafx.base;
    exports tasks.persistence;
    exports tasks.validator;
    exports tasks.services;
    opens tasks.persistence to javafx.base;
    opens tasks.model;
}
