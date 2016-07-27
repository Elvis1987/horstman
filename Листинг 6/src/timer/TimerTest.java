package timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;


/**
 * Created by Elvis on 24.06.2016.
 */
public class TimerTest {
    public static void main(String[] args) {
        ActionListener listener = new TimePrinter();
        //Сконструировать таймер,вызывающий обработчик событий
        //через каждые 10 секунд
        Timer t = new Timer(10000, listener);
        t.start();
        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

class TimePrinter implements ActionListener{
    public void actionPerformed(ActionEvent event){
        Date now = new Date();
        System.out.println("At the tone, the time is" + now);
        Toolkit.getDefaultToolkit().beep();
    }
}