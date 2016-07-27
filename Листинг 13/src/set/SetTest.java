package set;
import java.util.*;

/**В этой программе выводятся все неповторяющиеся слова,
 * введенные из стандартного потока System.in во множество
 * Created by Elvis on 19.07.2016.
 */
public class SetTest {
    public static void main(String[] args) {
        Set<String> words = new HashSet<>(); //объект типа HashSet реализующий хеш-множества
        long totalTime = 0;

        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            String word = in.next();
            long callTime = System.currentTimeMillis();
            words.add(word);
            callTime = System.currentTimeMillis() - callTime;
            totalTime += callTime;
        }

        Iterator<String> iter = words.iterator();
        for (int i = 1; i <= 20 && iter.hasNext(); i++)
            System.out.println(iter.next());
        System.out.println("...");
        System.out.println(words.size() + " distinct words." + totalTime + "milliseconds");
    }
}
