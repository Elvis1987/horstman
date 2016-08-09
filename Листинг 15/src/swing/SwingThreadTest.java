package swing;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**В этой программе демонстрируется, что поток, выполняющийся паралельно спотоком
 * диспечеризации событий, может вызвать ошибки в работе компонентов Swing
 * Created by Elvis on 09.08.2016.
 */
public class SwingThreadTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new SwingThreadFrame();
                frame.setTitle("SwingThreadTest");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**В этом фрейме имеется две кнопки для заполнеия комбинированого списка из отдельного потока.Кнопка Good
 * обновляет этот список через очередь событий, а кнопка Bad непосредственно
 */
class SwingThreadFrame extends JFrame{
    public SwingThreadFrame(){
        final JComboBox<Integer> combo = new JComboBox<>();
        combo.insertItemAt(Integer.MAX_VALUE, 0);
        combo.setPrototypeDisplayValue(combo.getItemAt(0));
        combo.setSelectedIndex(0);

        JPanel panel = new JPanel();

        JButton goodButton = new JButton("Good");
        goodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                new Thread(new GoodWorkerRunnable(combo)).start();
            }
        });
        panel.add(goodButton);
        JButton badButton = new JButton("Bad");
        badButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                new Thread(new BadWorkerRunnable(combo)).start();
            }
        });
        panel.add(badButton);

        panel.add(combo);
        add(panel);
        pack();
    }
}
/**В этом исполняемом потоке комбинированный список видоизменяется путем
 * произвольного ввода и удаления чисел. Это может привести к ошибкам, так как методы видоизменения комбинированого
 * списка не синхронизированы, и поэтому этот список доступен как из рабочего потока,
 * так из потока диспечеризации событий
 */
class BadWorkerRunnable implements Runnable {
    private JComboBox<Integer> combo;
    private Random generator;

    public BadWorkerRunnable(JComboBox<Integer> aCombo) {
        combo = aCombo;
        generator = new Random();
    }

    public void run() {
        try {
            while (true) {
                int i = Math.abs(generator.nextInt());
                if (i % 2 == 0) combo.insertItemAt(i, 0);
                else if (combo.getItemCount() > 0) combo.removeItemAt(i % combo.getItemCount());
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
        }
    }
}
    /**
     * Этот исполняемый поток видоизменяет комбинированый список,
     произвольно вводя и удаляя числа. Чтобы исключить содержимого этого списка ,
     редактирующие операции направляются в поток диспетчеризации событий
     *
     */
    class GoodWorkerRunnable implements Runnable {
        private JComboBox<Integer> combo;
        private Random generator;

        public GoodWorkerRunnable(JComboBox<Integer> aCombo) {
            combo = aCombo;
            generator = new Random();
        }

        @Override
        public void run() {
            try {
                while (true) {
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            int i = Math.abs(generator.nextInt());
                            if (i % 2 == 0) combo.insertItemAt(i, 0);
                            else if (combo.getItemCount() > 0)
                                combo.removeItemAt(i % combo.getItemCount());
                        }
                    });
                    Thread.sleep(1);
                }
            }
            catch (InterruptedException e) {
            }
        }
    }