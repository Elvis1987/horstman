package shuffle;
import java.util.*;
/**В этой программе демострируется алгоритмы произвольной перестановки и сортировки
 * Created by Elvis on 21.07.2016.
 */
public class ShuffleTest {
    public static void main(String[] args) {
        List<Integer> numbers =new ArrayList<>();
        for (int i = 1; i <=49; i++) numbers.add(i);
        Collections.shuffle(numbers);
        List<Integer> winningCombination = numbers.subList(0, 6);
        Collections.sort(winningCombination);
        System.out.println(winningCombination);
    }
}
