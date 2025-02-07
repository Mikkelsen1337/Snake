import java.awt.*;
import java.util.Random;

public class Food {
    private int x, y;
    private final int TILE_SIZE;
    private final int SCREEN_WIDTH, SCREEN_HEIGHT;
    private Random random;

    public Food(int tileSize, int screenWidth, int screenHeight) {
        this.TILE_SIZE = tileSize;
        this.SCREEN_WIDTH = screenWidth;
        this.SCREEN_HEIGHT = screenHeight;
        this.random = new Random();
        spawnNewFood();
    }
    public void spawnNewFood() {
        int gridX = SCREEN_WIDTH / TILE_SIZE;
        int gridY = SCREEN_HEIGHT / TILE_SIZE;
        x = random.nextInt(gridX) * TILE_SIZE;
        y = random.nextInt(gridY) * TILE_SIZE;
    }
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
    }

    public Point getPosition() {
        return new Point(x, y);
    }
}
