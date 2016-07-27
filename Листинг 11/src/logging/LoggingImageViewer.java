package logging;

import eventTracer.EventTracer;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.*;

/**Это видоизмененный вариант программы просмотра, в которой
 * регистрируются различные события
 * Created by Elvis on 30.06.2016.
 */
public class LoggingImageViewer {
    public static void main(String[] args) {
        if (System.getProperty("java.util.logging.config.class") == null
                && System.getProperty("java.util.logging.config.file") == null){
            try {
                Logger.getLogger("logging.com").setLevel(Level.ALL);
                final int LOG_ROTATION_COUNT = 10;
                Handler handler = new FileHandler("%h/LoggingImageViewer.log", 0, LOG_ROTATION_COUNT);
                Logger.getLogger("logging.com").addHandler(handler);
            }
            catch (IOException e){
                Logger.getLogger("logging.com").log(Level.SEVERE,"Can`t create log file handler", e);
            }
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Handler windowHandler = new WindowHandler();
                windowHandler.setLevel(Level.ALL);
                Logger.getLogger("logging.com").addHandler(windowHandler);

                JFrame frame = new ImageViewerFrame();
                frame.setTitle("LoggingImageViewer");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                Logger.getLogger("logging.com").fine("Showing frame");
                frame.setVisible(true);
            }
        });

    }
}
//фрейм в котором показывается изображение
class ImageViewerFrame extends JFrame{
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 400;

    private JLabel label;
    private static Logger logger = Logger.getLogger("logging.com");

    public ImageViewerFrame(){
        logger.entering("ImageViewerFrame", "<init>");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        //установить строку меню
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem openItem = new JMenuItem("Open");
        menu.add(openItem);
        openItem.addActionListener(new FileOpenListener());
        JMenuItem exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                logger.fine("Exitin.");
                System.exit(0);
            }
        });
        //использовать метку для обозначения изображений
        label = new JLabel();
        add(label);
        logger.exiting("ImageViewerFrame", "<init>");

    }

    private class FileOpenListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            logger.entering("ImageViewerFrame.FileOpenListener", "actionPerformed", event);

            //установить компонент для выбора файлов
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));

            //принять все файлы с расширением gif
            chooser.setFileFilter(new javax.swing.filechooser.FileFilter(){
                public boolean accept(File f){
                    return f.getName().toLowerCase().endsWith(".gif") || f.isDirectory();
                }
                public String getDescription(){
                    return "GIF Images";
                }
            });
            //показать диалоговое окно для выбора файлов
            int r = chooser.showOpenDialog(ImageViewerFrame.this);
            //если файл изображения подходит, выбрать его в качестве пиктограммы  для метки
            if (r == JFileChooser.APPROVE_OPTION){
                String name = chooser.getSelectedFile().getPath();
                logger.log(Level.FINE, "Reading file {0}", name);
                label.setIcon(new ImageIcon(name));
            }
            else logger.fine("File open dialog canceled.");
            logger.exiting("ImageViewerFrame.FileOpenListener", "actionPerformed");
        }
    }
}

//обработчик для отображения протокольных записей в окне
class WindowHandler extends StreamHandler{
    private JFrame frame;
    public WindowHandler(){
        frame = new JFrame();
        final JTextArea output = new JTextArea();
        output.setEditable(false);
        frame.setSize(200, 200);
        frame.add(new JScrollPane(output));
        frame.setFocusableWindowState(false);
        frame.setVisible(true);
        setOutputStream(new OutputStream() {
            @Override
            public void write(int b){
            }//не вызывается!
            public void write(byte[] b, int off, int len){
                output.append(new String(b, off, len));
            }
        });
    }

    public void publish(LogRecord record){
        if (!frame.isVisible()) return;
        super.publish(record);
        flush();
       /** EventTracer tracer = new EventTracer();
        tracer.add(frame);
        */
    }
}