package unsynch;

/**Имитируемый банк с целым рядом счетов
 * Created by Elvis on 01.08.2016.
 */
public class Bank {
    private final double[] accounts;
    //конструирует объект банка
    public Bank(int n, double initialBalance){
        accounts = new double[n];
        for (int i = 0; i< accounts.length; i++) accounts[i] = initialBalance;
    }

    //Переводит деньги с одного счета на другой
    public void transfer(int from, int to, double amount){
        if (accounts[from]< amount) return;
        System.out.print(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf("Total balance: %10.2f%n", getTotalBalance());
    }
    //получает сумму остатков на всех счетах
    public double getTotalBalance(){
        double sum = 0;
        for (double a : accounts) sum += a;
    }
    //Получает количество счетов
    public int size(){
        return accounts.length;
    }
}
