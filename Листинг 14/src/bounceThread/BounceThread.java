package bounceThread;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**В этой программе выполняется анимация скачуших мячей
 * Created by Elvis on 25.07.2016.
 */
public class BounceThread {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new BounceFrame();
                frame.setTitle("BounceThread");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
//класс исполняемого потока для анимации скачущего мяча
    class BallRunnable implements Runnable{
    private Ball ball;
    private Component component;
    public static final int STEPS = 1000;
    public static final int DELAY = 5;
    //Конструирует объект для исполняемого потока


public BallRunnable (Ball aBall, Component aComponent){
    ball = aBall;
    component = aComponent;
}
public void run() {
    try {
        for (int i = 1; i <= STEPS; i++) {
            ball.move(component.getBounds());
            component.repaint();
            Thread.sleep(DELAY);
        }
    }
    catch (InterruptedException e) {
    }
}

}
    //Фрейм спанелью и кнопками
    class BounceFrame extends JFrame{
        private BallComponent comp;

public BounceFrame(){
    comp = new BallComponent();
    add(comp, BorderLayout.CENTER);
    JPanel buttonPanel = new JPanel();
    addButton(buttonPanel, "Start", new ActionListener(){
        public void actionPerformed(ActionEvent event){
            addBall();
        }
    });
    addButton(buttonPanel, "Close", new ActionListener(){
        public void actionPerformed(ActionEvent event){
            System.exit(0);
        }
    });
    add(buttonPanel, BorderLayout.SOUTH);
    pack();
}
public void addButton(Container c, String title, ActionListener listener){
    JButton button = new JButton(title);
    c.add(button);
    button.addActionListener(listener);
}
public void addBall(){
    Ball b = new Ball();
    comp.add(b);
    Runnable r = new BallRunnable(b, comp);
    Thread t = new Thread(r);
    t.start();
}
}