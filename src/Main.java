import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;


public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake");
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        int width = frame.getContentPane().getWidth();
        int height = frame.getContentPane().getHeight();

        gamePanel.updateSize(width, height);
    }
}
