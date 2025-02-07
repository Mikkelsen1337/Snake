import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;


public class GamePanel extends JPanel implements ActionListener {
    private final int TILE_SIZE = 25;
    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 800;
    private Food food;
    private Timer timer;
    private Timer tickCount;
    private Snake snake;
    private String valg;
    private boolean gameOver = false;
    private int score = 0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.darkGray);
        this.setFocusable(true);
        this.addKeyListener(new KeyHandler());
        this.setDoubleBuffered(true);
        snake = new Snake((SCREEN_WIDTH / 2) / TILE_SIZE * TILE_SIZE, (SCREEN_HEIGHT / 2) / TILE_SIZE * TILE_SIZE);
        food = new Food(TILE_SIZE, SCREEN_WIDTH, SCREEN_HEIGHT);
        timer = new Timer(16, e -> {
            repaint();
        });

        tickCount = new Timer(100, e -> {
            snake.move();
            checkFoodCollision();
            checkWallCollision();
        });

        timer.start();
        tickCount.start();
    }

    private void showScore(Graphics g) {

            g.setColor(Color.cyan);
            g.setFont(new Font("Times New Roman", Font.BOLD, 15));
            g.drawString("Score: " + (snake.getBody().size() - 1), 50, 50);
            repaint();
        }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        food.draw(g);
        snake.draw(g);
        showScore(g);

        if (!tickCount.isRunning()) {
        g.setColor(Color.cyan);
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
        g.drawString("Game Over! Press R to start again", 200, SCREEN_HEIGHT / 2);

        }
    }


    private void redo() {
        snake = new Snake((SCREEN_WIDTH / 2) / TILE_SIZE * TILE_SIZE, (SCREEN_HEIGHT / 2) / TILE_SIZE * TILE_SIZE);
        food = new Food(TILE_SIZE, SCREEN_WIDTH, SCREEN_HEIGHT);
        tickCount.start();
        gameOver = false;
        repaint();
    }

    private void checkFoodCollision() {
        if (snake.getHead().equals(food.getPosition())) {
            snake.grow();
            food.spawnNewFood();
        }
    }
    private void checkWallCollision(){
        Point head = snake.getHead();
        if (head.x < 0 || head.x >= SCREEN_WIDTH || head.y < 0 || head.y >= SCREEN_HEIGHT) {
            gameOver();
        }
    }
    private void gameOver() {
        tickCount.stop();
        gameOver = true;
        repaint();
    }

    private void draw(Graphics g) {
    snake.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        snake.move();
        repaint();
    }
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!gameOver) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    snake.setDirection("UP");
                break;
                case KeyEvent.VK_S:
                    snake.setDirection("DOWN");
                break;
                case KeyEvent.VK_A:
                    snake.setDirection("LEFT");
                    break;
                case KeyEvent.VK_D:
                    snake.setDirection("RIGHT");
                break;
            }
            } else if (e.getKeyCode() == KeyEvent.VK_R) {
            redo();
            }
        }
    }
}