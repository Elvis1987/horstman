package robot;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

/**
 * Created by Elvis on 14.07.2016.
 */
public class RobotTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //создать рейм с панелью кнопок
                ButtonFrame frame = new ButtonFrame();
                frame.setTitle("ButtonTest");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
        //присоеденить робот к устройству вывода на экран
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice screen = environment.getDefaultScreenDevice();

        try {
            final Robot robot = new Robot(screen);
            robot.waitForIdle();
            new Thread(){
                public void run(){
                    runTest(robot);
                };
            }.start();
        }
        catch (AWTException e){
            e.printStackTrace();
        }
    }
    public static void runTest(Robot robot){
        //сымитировать нажатие клавиши пробела
        robot.keyPress(' ');
        robot.keyRelease(' ');
        //сымитировать нажатие клавиш табуляции и пробела
        robot.delay(2000);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        robot.keyPress(' ');
        robot.keyRelease(' ');
        //сымитировать щелчок мыши
        robot.delay(2000);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        //захватить экран и показать полученное
        robot.delay(2000);
        BufferedImage image = robot.createScreenCapture(new Rectangle(0, 0, 400, 300 ));

        ImageFrame frame = new ImageFrame(image);
        frame.setVisible(true);
    }
}
//Фрейм для показа захваченого изображения экрана
class ImageFrame extends JFrame{
    private static final int DEFAULT_WIDTH = 450;
    private static final int DEFAULT_HEIGHT = 350;

    //Показываемое изображение
    public ImageFrame(Image image){
        setTitle("Capture");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        JLabel label = new JLabel(new ImageIcon(image));
        add(label);
    }
}
