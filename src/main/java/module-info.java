module ValidaSystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens app;
    opens view;
    opens model;
    opens service;
}
