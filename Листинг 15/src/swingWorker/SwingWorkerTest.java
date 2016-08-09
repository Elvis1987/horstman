package swingWorker;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import javax.swing.*;


/**В этой программе демострируется рабочий поток, в котором
 * выполняется потенциально продолжительная задача
 * Created by Elvis on 09.08.2016.
 */
public class SwingWorkerTest {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new SwingWorkerFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
/**
 * Этот фрейм содержит текстовую область для отображения содержимого
 * текстового файла, меню для открытия и отмены его открытия,
 * а также строку состояния для отображения процесса загрузки файла
 */
class SwingWorkerFrame extends JFrame {
    private JFileChooser chooser;
    private JTextArea textArea;
    private JLabel statusLine;
    private JMenuItem openItem;
    private JMenuItem cancelItem;
    private SwingWorker<StringBuilder, ProgressData> textReader;
    public static final int TEXT_ROWS = 20;
    public static final int TEXT_COLUMNS = 60;

    public SwingWorkerFrame() {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));

        textArea = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        add(new JScrollPane(textArea));

        statusLine = new JLabel(" ");
        add(statusLine, BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        openItem = new JMenuItem("Open");
        menu.add(openItem);

        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //показать диалоговое окно для выбора файлов
                int result = chooser.showOpenDialog(null);

                //если файл выбран, выбрать его в качестве
                //пиктограммы для метки
                if (result == JFileChooser.APPROVE_OPTION) {
                    textArea.setText("");
                    openItem.setEnabled(false);
                    textReader = new TextReader(chooser.getSelectedFile());
                    textReader.execute();
                    cancelItem.setEnabled(true);
                }
            }
        });

        cancelItem = new JMenuItem("Cancel");
        menu.add(cancelItem);
        cancelItem.setEnabled(false);
        cancelItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                textReader.cancel(true);
            }
        });
        pack();
    }

    private class ProgressData {
        public int number;
        public String line;
    }

    private class TextReader extends SwingWorker<StringBuilder, ProgressData> {
        private File file;
        private StringBuilder text = new StringBuilder();

        public TextReader(File file) {
            this.file = file;
        }

        //Следующий метод выполняется в рабочем потоке не затрагивая компоненты Swing

        @Override
        public StringBuilder doInBackground() throws IOException, InterruptedException {
            int lineNumber = 0;
            try (Scanner in = new Scanner(new FileInputStream(file))) {
                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    lineNumber++;
                    text.append(line);
                    text.append("\n");
                    ProgressData data = new ProgressData();
                    data.number = lineNumber;
                    data.line = line;
                    publish(data);
                    Thread.sleep(1);//только для проверки отмены
                }
            }
            return text;
        }

        //следующие методы выполняются в потоке диспетчеризации событий
        @Override
        public void process(List<ProgressData> data) {
            if (isCancelled()) return;
            StringBuilder b = new StringBuilder();
            statusLine.setText("" + data.get(data.size() - 1).number);
            for (ProgressData d : data) {
                b.append(d.line);
                b.append("\n");
            }
            textArea.append(b.toString());
        }

        @Override
        public void done() {
            try {
                StringBuilder result = get();
                textArea.setText(result.toString());
                statusLine.setText("Done");
            }
            catch (InterruptedException ex) {
            }
            catch (CancellationException ex) {
                textArea.setText("");
                statusLine.setText("Canceled");
            }
            catch (ExecutionException ex) {
                statusLine.setText("" + ex.getCause());
            }

            cancelItem.setEnabled(false);
            openItem.setEnabled(true);
        }
    };
}