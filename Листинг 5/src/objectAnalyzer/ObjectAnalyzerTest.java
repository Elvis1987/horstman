package objectAnalyzer;
import java.util.ArrayList;

/**В этой программе рефлексия применяется для слежения за объектами
 * Created by Elvis on 14.06.2016.
 */
public class ObjectAnalyzerTest {
    public static void main(String[] args) {
        ArrayList<Integer> squares = new ArrayList<>();
        for (int i=1; i<=5; i++)
            squares.add(i * i);
        System.out.println(new ObjectAnalyzer().toString(squares));
    }
}
