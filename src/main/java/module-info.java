module se233.asteroids {
    requires javafx.controls;
    requires javafx.fxml;


    opens se233.asteroids to javafx.fxml;
    exports se233.asteroids;
}