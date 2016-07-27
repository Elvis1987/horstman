package bounce;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**В этой программе осуществяется анимация скачущего мяча
 * Created by Elvis on 25.07.2016.
 */
public class Bounce {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new BounceFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

//Фрейм с компонентом мяча и кнопками
class BounceFrame extends JFrame {
    private BallComponent comp;
    public static final int STEPS = 1000;
    public static final int DELAY = 3;

    //Конструирует фрейм с компонентом, отображающим скачущий мяч,  а также кнопки Start и Close
    public BounceFrame() {
        setTitle("Bounce");

        comp = new BallComponent();
        add(comp, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        addButton(buttonPanel, "Start", new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                addBall();
            }
        });
        addButton(buttonPanel, "Close", new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }

    //Вводит кнопки в контейнер, c Контейнер, tittle надпись на кнопке, listener приемник действий кнопок
    public void addButton(Container c, String title, ActionListener listener) {
        JButton button = new JButton(title);
        c.add(button);
        button.addActionListener(listener);
    }

    //Вводит скачущий мяч на панели ипроизводит 1000 его отскоков
    public void addBall() {
        try {
            Ball ball = new Ball();
            comp.add(ball);

            for (int i = 1; i <= STEPS; i++) {
                ball.move(comp.getBounds());
                comp.paint((comp.getGraphics()));
                Thread.sleep(DELAY);
            }
        } catch (InterruptedException e) {
        }
    }
}
