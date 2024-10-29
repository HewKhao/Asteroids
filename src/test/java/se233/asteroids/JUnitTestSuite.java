package se233.asteroids;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({PlayerShipTest.class,AsteroidTest.class, NormalEnemiesTest.class, EliteEnemiesTest.class, NormalAttackTest.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JUnitTestSuite {
    @BeforeAll
    public static void initJfxRuntime(){
        javafx.application.Platform.runLater(()->{});
    }
}
