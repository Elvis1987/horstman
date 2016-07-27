package bounce;
import java.awt.*;
import java.util.*;
import javax.swing.*;

/**Компонент рисующий скачущий мяч
 * Created by Elvis on 25.07.2016.
 */
public class BallComponent  extends JPanel{
    private static final int DEFAULT_WIDTH = 450;
    private static final int DEFAULT_HEIGHT = 350;

    private java.util.List<Ball> balls = new ArrayList<>();
    //Вводит мяч в компонент, b Вводимый мяч
    public void add(Ball b){
        balls.add(b);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g); // очистить фон
        Graphics2D g2 = (Graphics2D) g;
        for (Ball b : balls){
            g2.fill(b.getShape());
        }
    }
    public  Dimension getPreferredSize(){
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}
