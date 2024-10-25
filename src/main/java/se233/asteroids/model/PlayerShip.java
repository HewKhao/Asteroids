package se233.asteroids.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.asteroids.controller.GameStageController;
import se233.asteroids.util.SpriteUtil;

public class PlayerShip extends Character {
    private static final Logger logger = LogManager.getLogger(PlayerShip.class);

    private static final String IDLE_SPRITE = "/se233/asteroids/assets/playerShip/Idle.png";
    private static final String BOOST_SPRITE = "/se233/asteroids/assets/playerShip/Boost.png";
    private static final String SHOOT_SPRITE = "/se233/asteroids/assets/playerShip/Attack_1.png";
    private static final String FIRE_SPRITE = "/se233/asteroids/assets/playerShip/Burn.png";

    private final double shootCooldown = 0.5;
    private double timeSinceLastShot = 0.5;

    public Rectangle outline = new Rectangle();

    public PlayerShip(double x, double y, double initialSpeed, double maxSpeed, double acceleration, double rotationSpeed, double friction, int health, double width, double height) {
        super(IDLE_SPRITE,50 ,50, x, y, initialSpeed, maxSpeed, acceleration, rotationSpeed, friction, health, width, height);
        logger.info("PlayerShip created at X: {}, Y: {}", x, y);
        this.velocityX = 0;
        this.velocityY = 0;
        this.gameWidth = width;
        this.gameHeight = height;

        loadAnimations();

        currentAnimations.add("idle");
        this.imageView.setImage(animations.get("idle").getImage());
        this.imageView.setVisible(false);

        outline.setFill(Color.TRANSPARENT);
        outline.setStroke(Color.RED);
        outline.setStrokeWidth(2);
    }

    private void loadAnimations() {
        animations.put("idle", SpriteUtil.createAnimatedSprite(IDLE_SPRITE, 1, 1, 1, 192, 192, 100, 100));
        animations.put("boost", SpriteUtil.createAnimatedSprite(BOOST_SPRITE, 5, 5, 1, 192, 192, 100, 100));
        animations.put("shoot", SpriteUtil.createAnimatedSprite(SHOOT_SPRITE, 4, 4, 1, 192, 192, 100, 100));
        animations.put("fire", SpriteUtil.createAnimatedSprite(FIRE_SPRITE, 10, 1, 10, 128, 128, 100, 100));

        animations.get("shoot").setPlayOnce(true);
        animations.get("fire").setPlayOnce(true);

        animationOffsets.put("shoot", new double[]{0, -2.6});
        animationOffsets.put("fire", new double[]{0, 34});

        animationRotates.put("fire", -90.0);
    }

    public void shoot(GameStageController gameStageController) {
        if (timeSinceLastShot >= shootCooldown) {
            currentAnimations.add("shoot");
            currentAnimations.add("fire");
            AnimatedSprite shootSprite = animations.get("shoot");
            AnimatedSprite fireSprite = animations.get("fire");
            shootSprite.reset();
            fireSprite.reset();

            logger.info("PlayerShip is shooting!");

            timeSinceLastShot = 0;

            double playerX = this.getX();
            double playerY = this.getY();
            double rotation = this.getRotate();

            double offsetX = 50;
            double offsetY = 0;

            double radians = Math.toRadians(rotation);
            double spawnX = playerX + (offsetX * Math.cos(radians)) - (offsetY * Math.sin(radians));
            double spawnY = playerY + (offsetX * Math.sin(radians)) + (offsetY * Math.cos(radians));

            NormalAttack normalAttack = new NormalAttack(spawnX, spawnY, rotation, 10, 10, 1, 1, gameWidth, gameHeight);

            gameStageController.getNormalAttackController().addNormalAttack(normalAttack);
        }
    }

    @Override
    public void update() {
        super.update();

        if (timeSinceLastShot < shootCooldown) {
            timeSinceLastShot += 0.016;
        }

//        if (currentAnimations.contains("shoot")) {
//            AnimatedSprite shootSprite = animations.get("shoot");
//            if (shootSprite.getCurrentFrame() == shootSprite.getTotalFrames() - 1) {
//                currentAnimations.remove("shoot");
//            }
//        }
//
//        if (currentAnimations.contains("fire")) {
//            AnimatedSprite fireSprite = animations.get("fire");
//            if (fireSprite.getCurrentFrame() == fireSprite.getTotalFrames() - 1) {
//                currentAnimations.remove("fire");
//            }
//        }

        if (Math.abs(velocityX) > 0.1 || Math.abs(velocityY) > 0.1) {
            logger.info("PlayerShip Position - X: {}, Y: {}", getX(), getY());
        }
    }
}