package synch;
import  java.util.concurrent.locks.*;

/**Программа, имитирующая банк со счетами и использующая блокировки для
 * организациипоследовательного доступа к остаткам на счетах
 * Created by Elvis on 02.08.2016.
 */
public class Bank {
    private final double[] accounts;
    private Lock bankLock;
    private Condition sufficientFunds;

    /**Конструирует объект банка
     * @param n Количество счетов
     * @param initialBalance Первоначальный остаток на счете
     */
    public Bank(int n, double initialBalance){
        accounts = new double[n];
        for (int i = 0; i < accounts.length; i++)
            accounts[i] = initialBalance;
        bankLock = new ReentrantLock();
        sufficientFunds = bankLock.newCondition();
    }
    //Переводит деньги с одного счета на другой
    public void transfer(int from, int to, double amount)
        throws InterruptedException{
        bankLock.lock();
        try {
            while (accounts[from] < amount)
                sufficientFunds.await();
            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf("%10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf("Total Balance: %10.2f%n", getTotalBalance());
            sufficientFunds.signalAll();
        }
        finally {
            bankLock.unlock();
        }
    }
    //Получает сумму остатков на всех счетах
    public double getTotalBalance(){
        bankLock.lock();
        try {
            double sum = 0;
            for (double a : accounts) sum += a;
            return sum;
        }
        finally {
            bankLock.unlock();
        }
    }
    //Получает количество счетов в банке
    public int size(){
        return accounts.length;
    }
}
//Проверка
длордопрдрлордшо