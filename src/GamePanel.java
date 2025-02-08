import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;



public class GamePanel extends JPanel implements ActionListener {
    private final int TILE_SIZE = 25;
    private Food food;
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;
    private Timer timer;
    private Timer tickCount;
    private Timer speedUp;
    private Snake snake;
    private String valg;
    private boolean gameOver = false;
    private boolean isSpeeding = false;
    private int score = 0;

    public GamePanel() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        SCREEN_WIDTH = (int) (screenWidth * 0.8);
        SCREEN_HEIGHT = (int) (screenHeight * 0.8);
        this.setBackground(Color.darkGray);
        this.setFocusable(true);
        this.addKeyListener(new KeyHandler());
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        snake = new Snake((SCREEN_WIDTH / 2) / TILE_SIZE * TILE_SIZE, (SCREEN_HEIGHT / 2) / TILE_SIZE * TILE_SIZE);
        food = new Food(TILE_SIZE, SCREEN_WIDTH, SCREEN_HEIGHT);
        timer = new Timer(16, e -> {
            repaint();
        });

        speedUp = new Timer(80, e -> {
            snake.move();
            checkFoodCollision();
            checkWallCollision();
        });

        tickCount = new Timer(100, e -> {
            snake.move();
            checkFoodCollision();
            checkWallCollision();
            checkSnakeCollision();
        });

        timer.start();
        tickCount.start();
    }

    public void updateSize(int width, int height) {
        this.SCREEN_WIDTH = width;
        this.SCREEN_HEIGHT = height;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.revalidate();
        repaint();


    }

    private void showScore(Graphics g) {

            g.setColor(Color.cyan);
            g.setFont(new Font("Times New Roman", Font.BOLD, 15));
            g.drawString("Score: " + (snake.getBody().size() - 1), 50, 50);
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
    private void checkSnakeCollision() {
        Point head = snake.getHead();
            for (int i = 1; i < snake.getBody().size() - 1; i++) {
                if (head.equals(snake.getBody().get(i))) {
                    gameOver();
                    return;
                }
            }

    }
    private void checkWallCollision(){
        Point head = snake.getHead();
        if (head.x < 0 || head.x >= SCREEN_WIDTH || head.y < 0 || head.y + TILE_SIZE > SCREEN_HEIGHT) {
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
                String currentDirection = snake.getDirection();

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        if (!currentDirection.equals("DOWN")) {
                            snake.setDirection("UP");
                        }
                        break;
                    case KeyEvent.VK_S:
                        if (!currentDirection.equals("UP")) {
                            snake.setDirection("DOWN");
                        }
                        break;
                    case KeyEvent.VK_A:
                        if (!currentDirection.equals("RIGHT")) {
                            snake.setDirection("LEFT");
                        }
                        break;
                    case KeyEvent.VK_D:
                        if (!currentDirection.equals("LEFT")) {
                            snake.setDirection("RIGHT");
                        }
                        break;
                    case KeyEvent.VK_SHIFT:
                        if (!isSpeeding) {
                            isSpeeding = true;
                            speedUp.start();
                        }
                        break;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_R) {
            redo();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
               if (isSpeeding) {
                   isSpeeding = false;
                   speedUp.stop();
               }
            }
        }
    }
}