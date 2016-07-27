package linkedList;

import java.util.*;
/**В этой программе демонстрируются операции со связными списками
 * Created by Elvis on 19.07.2016.
 */
public class LinkedListTest {
    public static void main(String[] args) {
        List<String> a = new LinkedList<>();
        a.add("Amy");
        a.add("Carl");
        a.add("Erica");

        List<String> b = new LinkedList<>();
        b.add("Bob");
        b.add("Doug");
        b.add("Frances");
        b.add("Gloria");

        //объеденить слова изи связных списков b и a
        ListIterator<String> aIter = a.listIterator();
        Iterator<String> bIter = b.iterator();

        while (bIter.hasNext()){
            if (aIter.hasNext()) aIter.next();
            aIter.add(bIter.next());
        }
        System.out.println(a);

        //удалить каждое второе слово из связного списка b
        bIter = b.iterator();
        while (bIter.hasNext()){
            bIter.next(); //пропустить один элемент
            if (bIter.hasNext()){
                bIter.next(); //пройти следуюющий элемент
                bIter.remove(); //удалить этот элемент
            }
        }
        System.out.println(b);

        //групповая операция удаления из связного списка a
        //всех слов, составляющих связный список b
        a.removeAll(b);

        System.out.println(a);
    }
}
