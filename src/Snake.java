import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private final int TILE_SIZE = 25;
    private String direction = "RIGHT";
    private String pendingDirection = null;
    private List<Point> body;

    public Snake(int startX, int startY) {
        body = new ArrayList<>();
        body.add(new Point(startX, startY));
    }

    public String getDirection() {
        return direction;
    }


    public void move() {
        Point head = body.get(0);
        Point newHead;

        switch (direction) {
            case "RIGHT":
                newHead = new Point(head.x + TILE_SIZE, head.y);
                break;
            case "LEFT":
                newHead = new Point(head.x - TILE_SIZE, head.y);
                break;
            case "UP":
                newHead = new Point(head.x, head.y - TILE_SIZE);
                break;
            case "DOWN":
                newHead = new Point(head.x, head.y + TILE_SIZE);
                break;
            default:
                newHead = new Point(head.x + TILE_SIZE, head.y);
                break;
        }
        body.add(0, newHead);
        body.remove(body.size() - 1);

    }

    public void grow() {
        body.add(new Point(body.get(body.size() - 1)));
    }

    public void setDirection(String newDirection) {
        if      ((direction.equals("UP") && !newDirection.equals("DOWN")) ||
                (direction.equals("DOWN") && !newDirection.equals("UP")) ||
                (direction.equals("LEFT") && !newDirection.equals("RIGHT")) ||
                (direction.equals("RIGHT") && !newDirection.equals("LEFT"))) {
                direction = newDirection;
        }
    }
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        for (Point p : body) {
            g.fillRect(p.x, p.y, TILE_SIZE, TILE_SIZE);
        }
    }
    public Point getHead() {
        return body.get(0);
    }
    public List<Point> getBody() {
        return body;
    }
}
