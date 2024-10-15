module se233.asteroids {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;

    opens se233.asteroids to javafx.fxml;
    exports se233.asteroids;
}