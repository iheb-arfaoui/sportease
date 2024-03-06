module tn.esprit.sportease {
    requires javafx.controls;
    requires javafx.fxml;


    opens tn.esprit to javafx.fxml;
    opens tn.esprit.controllers to javafx.fxml;
    exports tn.esprit;
    exports tn.esprit.test;
    opens tn.esprit.test to javafx.fxml;
    opens tn.esprit.models to javafx.base;
//    exports tn.esprit.gui.terrain;
    requires java.sql;
    requires java.desktop;
    requires javafx.web;
    requires jdk.jsobject;
    requires itextpdf;
    requires jfreechart;
}