package se233.asteroids.model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimatedSprite extends ImageView {
    int count, columns, rows, offsetX, offsetY, width, height, curIndex, curColumnIndex = 0, curRowIndex = 0;

    private boolean isPlayOnce = false;
    private int playFrameCount = 0;

    // gameLoop fps / fps that animation should run at
    private int frameDelay = 60/24;
    private int frameCounter = 0;

    public AnimatedSprite(Image image, int count, int columns, int rows, int offsetX, int offsetY, int width, int height) {
        this.setImage(image);
        this.count = count;
        this.columns = columns;
        this.rows = rows;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    }

    public void tick() {
        frameCounter++;

        if (frameCounter >= frameDelay) {
            curColumnIndex = curIndex % columns;
            curRowIndex = curIndex / columns;

            interpolate();

            curIndex = (curIndex + 1) % (columns * rows);
            curIndex = curIndex < count ? curIndex : 0;
            if (playFrameCount == count) {
                playFrameCount = 0;
            } else {
                playFrameCount++;
            }

            frameCounter = 0;
        }
    }

    public void reset() {
        curIndex = 0;
        playFrameCount = 0;
        interpolate();
    }

    protected void interpolate() {
        final int x = curColumnIndex * width + offsetX;
        final int y = curRowIndex * height + offsetY;
        this.setViewport(new Rectangle2D(x, y, width, height));
    }

    public void setCurrentFrame(int frame) {
       this.curIndex = frame;
       this.playFrameCount = frame;
    }

    public int getCurrentFrame() {
        return curIndex;
    }

    public int getTotalFrames() {
        return count;
    }

    public void setPlayOnce(boolean playOnce) {
        this.isPlayOnce = playOnce;
    }

    public boolean isPlayOnce() {
        return isPlayOnce;
    }

    public void setPlayFrameCount(int frameCount) {
        this.playFrameCount = frameCount;
    }

    public int getPlayFrameCount() {
        return playFrameCount;
    }
}