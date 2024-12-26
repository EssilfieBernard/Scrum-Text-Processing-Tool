module com.project.scrumtextprocessing {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.project.scrumtextprocessing to javafx.fxml;
    exports com.project.scrumtextprocessing;
    exports com.project.scrumtextprocessing.controller;
    opens com.project.scrumtextprocessing.controller to javafx.fxml;
    exports com.project.scrumtextprocessing.data_management;
    opens com.project.scrumtextprocessing.data_management to javafx.fxml;
}