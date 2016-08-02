package unsynch;

/**В этой программе демонстрируется нарушение данных при
 * произвольном доступе к структуре данных из многих пооков
 * Created by Elvis on 02.08.2016.
 */
public class UnsynchBankTest {
    public static final int NACCOUNTS = 100;
    public static final double INITIAL_BALANCE = 1000;

    public static void main(String[] args) {
        Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
        int i;
        for (i =0; i < NACCOUNTS; i++){
            TransferRunnable r = new TransferRunnable(b, i, INITIAL_BALANCE);
            Thread t = new Thread(r);
            t.start();
        }
    }
}
