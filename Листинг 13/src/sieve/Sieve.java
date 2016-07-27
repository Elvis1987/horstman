package sieve;
import java.util.*;
/**В этой программе выполняется тест по алгоритму "Решето Эратосфена"
 * для нахождения всех простых чисел вплоть до 2000000
 * Created by Elvis on 21.07.2016.
 */
public class Sieve {
    public static void main(String[] args) {
        int n = 2000000;
        Long start = System.currentTimeMillis();
        BitSet b = new BitSet(n + 1);
        int count = 0;
        int i;
        for (i = 2; i <= n; i++) b.set(i);
        i = 2;
        while (i * i <= n){
            if (b.get(i)){
                count++;
                int k = 2 * i;
                while (k <= n){
                    b.clear(k);
                    k += i;
                }
            }
            i++;
        }
        while (i <= n){
            if (b.get(i))count++;
            i++;
        }
        long end = System.currentTimeMillis();
        System.out.println(count + " primes");
        System.out.println((end - start) + "milliseconds");
    }
}
