package se233.asteroids.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.asteroids.controller.GameStageController;
import se233.asteroids.util.SpriteUtil;

public class PlayerShip extends Character {
    private static final Logger logger = LogManager.getLogger(PlayerShip.class);

    private static final String IDLE_SPRITE = "/se233/asteroids/assets/playerShip/Idle.png";
    private static final String BOOST_SPRITE = "/se233/asteroids/assets/playerShip/Boost.png";
    private static final String SHOOT_SPRITE = "/se233/asteroids/assets/playerShip/Attack_1.png";

    private final double shootCooldown = 0.5;
    private double timeSinceLastShot = 0.5;

    public PlayerShip(double x, double y, double MAX_SPEED, double ACCELERATION, double ROTATION_SPEED, double FRICTION, int health, double width, double height) {
        super(IDLE_SPRITE,100 ,100, x, y, MAX_SPEED, ACCELERATION, ROTATION_SPEED, FRICTION, health, width, height);
        logger.info("PlayerShip created at X: {}, Y: {}", x, y);
        this.velocityX = 0;
        this.velocityY = 0;
        this.gameWidth = width;
        this.gameHeight = height;


        loadAnimations();

        currentAnimations.add("idle");
        this.imageView.setImage(animations.get("idle").getImage());
    }

    private void loadAnimations() {
        animations.put("idle", SpriteUtil.createAnimatedSprite(IDLE_SPRITE, 1, 1, 1, 192, 192));
        animations.put("boost", SpriteUtil.createAnimatedSprite(BOOST_SPRITE, 5, 5, 1, 192, 192));
        animations.put("shoot", SpriteUtil.createAnimatedSprite(SHOOT_SPRITE, 4, 4, 1, 192, 192));

        animationOffsets.put("shoot", new double[]{-2.6, 0});
    }

    public void shoot(GameStageController gameStageController) {
        if (timeSinceLastShot >= shootCooldown) {
            currentAnimations.add("shoot");
            AnimatedSprite shootSprite = animations.get("shoot");
            shootSprite.reset();

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

            NormalAttack normalAttack = new NormalAttack(spawnX, spawnY, rotation, 10, 10, 0.5, 1, gameWidth, gameHeight);

            gameStageController.addNormalAttack(normalAttack);
        }
    }

    @Override
    public void update() {
        super.update();

        if (timeSinceLastShot < shootCooldown) {
            timeSinceLastShot += 0.016;
        }

        if (currentAnimations.contains("shoot")) {
            AnimatedSprite shootSprite = animations.get("shoot");
            if (shootSprite.getCurrentFrame() == shootSprite.getTotalFrames() - 1) {
                currentAnimations.remove("shoot");
            }
        }

        if (Math.abs(velocityX) > 0.1 || Math.abs(velocityY) > 0.1) {
            logger.info("PlayerShip Position - X: {}, Y: {}", getX(), getY());
        }
    }
}