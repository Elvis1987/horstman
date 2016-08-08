package treadPool;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Elvis on 08.08.2016.
 */
public class ThreadPoolTest {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter base directory: ");
        String directory = in.nextLine();
        System.out.print("Enter keyword: ");
        String keyword = in.nextLine();

        ExecutorService pool = Executors.newCachedThreadPool();

        MatchCounter counter = new MatchCounter(new File(directory), keyword, pool);
        Future<Integer> result = pool.submit(counter);

        try {
            System.out.println(result.get() + "matching files.");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
        }
        pool.shutdown();

        int largestPoolSize = ((ThreadPoolExecutor) pool).getLargestPoolSize();
        System.out.println("largest pool size= " + largestPoolSize);
    }
}
//Подсчитывает файлы ,содержащие заданное ключевое слово
class MatchCounter implements Callable<Integer>{
    private File directory;
    private String keyword;
    private ExecutorService pool;
    private int count;

    //Конструирует объект типа MatchCounter
    public MatchCounter(File directory, String keyword, ExecutorService pool){
        this.directory = directory;
        this.keyword = keyword;
        this.pool = pool;
    }
    public Integer call(){
        count = 0;
        try {
            File[] files = directory.listFiles();
            List<Future<Integer>> results = new ArrayList<>();

            for (File file : files)
                if (file.isDirectory()){
                    MatchCounter counter = new MatchCounter(file, keyword, pool);
                    Future<Integer> result = pool.submit(counter);
                    results.add(result);
                }
                else {
                    if (search(file)) count++;
                }
            for (Future<Integer> result : results)
                try {
                    count += result.get();
                }
                catch (ExecutionException e){
                    e.printStackTrace();
                }
        }
        catch (InterruptedException e){
        }
        return count;
    }
    //осуществляет поиск заданого слова
    public boolean search(File file){
        try {
            try (Scanner in = new Scanner(file)){
                boolean found = false;
                while (!found && in.hasNextLine()){
                    String line = in.nextLine();
                    if (line.contains(keyword)) found = true;
                }
                return found;
            }
        }
        catch (IOException e){
            return false;
        }
    }
}