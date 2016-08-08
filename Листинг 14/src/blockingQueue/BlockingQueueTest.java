package blockingQueue;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Elvis on 08.08.2016.
 */
public class BlockingQueueTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter base directory (e.g. /usr/local/jdk.6.0/src): ");
        String directory = in.nextLine();
        System.out.print("Enter keyword (e.g. volatile): ");
        String keyword = in.nextLine();

        final int FILE_QUEUE_SIZE = 10;
        final int SEARCH_THREADS = 100;

        BlockingQueue<File> queue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

        FileEnumerationTask enumerator = new FileEnumerationTask(queue, new File(directory));
        new Thread(enumerator).start();
        for (int i = 1; i <= SEARCH_THREADS; i++) new Thread(new SearchTask(queue, keyword)).start();
    }
}
//Перечисляет все файлы в каталоге и его подкаталогах
class FileEnumerationTask implements Runnable{
    public static File DUMMY = new File("");
    private BlockingQueue<File> queue;
    private File startingDirectory;

    //Конструирует объект типа FileEnumerationTask
    //@param gueue Блокирующая очередь, в которую вводятся перечисляемые файлы
    //@param startingDirectory Каталог, с которого начинается перечисление файлов
    public FileEnumerationTask(BlockingQueue<File> queue, File startingDirectory){
        this.queue = queue;
        this.startingDirectory = startingDirectory;
    }

    @Override
    public void run() {
        try {
            enumerate(startingDirectory);
            queue.put(DUMMY);
        }
        catch (InterruptedException e){
    }
}
//Рекурсивно перечисляет все файлы в данном каталоге и его подкаталогах
public void enumerate (File directory) throws InterruptedException{
File[] files = directory.listFiles();
    for (File file : files){
        if (file.isDirectory()) enumerate(file);
        else queue.put(file);
    }
}
}
//Осуществляет поиск заданого ключевого слова в файлах
class SearchTask implements Runnable{
    private BlockingQueue<File> queue;
    private String keyword;
    /**Конструирует объект типа SearchTask
     * @param queue Очередь, из которой извлекаются файлы
     * @param keyword Искомое ключевое слово
     */
    public SearchTask(BlockingQueue<File> queue, String keyword){
        this.queue = queue;
        this.keyword = keyword;
    }

    @Override
    public void run() {
        try {
            boolean done = false;
            while (!done){
                File file = queue.take();
                if (file == FileEnumerationTask.DUMMY){
                    queue.put(file);
                    done = true;
                }
                else search(file);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (InterruptedException e){
    }
}
/**Осуществляет поиск в файлах заданого ключевого
 * слова и выводит все совпадающте строки
 * @param file Файл для поиска ключевого слова
 */
public void search(File file) throws IOException{
    try (Scanner in = new Scanner(file)){
        int lineNumber = 0;
        while (in.hasNextLine()){
            lineNumber++;
            String line = in.nextLine();
            if (line.contains(keyword))
                System.out.printf("%s:%d:%s%n", file.getPath(), lineNumber, line);
        }
    }
}
}
