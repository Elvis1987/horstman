package methods;
import java.lang.reflect.*;

/**В этой программе демострируется применение рефлексии для вызова методов
 * Created by Elvis on 15.06.2016.
 */
public class MethodTableTest {
    public static void main(String[] args) throws Exception {
        //Получить указатели на методы square() и sqrt()
        Method square = MethodTableTest.class.getMethod("square", double.class);
        Method sqrt = Math.class.getMethod("sqrt", double.class);
        //вывести значения x и y в табличном виде
        printTable(1, 10, 10, square);
        printTable(1, 10, 10, sqrt);
    }
    /**Возвращает квадрат числа
     * @param  x Число
     * @return x Квадрат числа
     */
    public static double square (double x){
        return x*x;
    }
    /**Выводит в табличном виде значения x и y указаного метода
     * @param Нижняя граница значений х
     * @param Верхняяя граница значений х
     * @param n Количество строк в таблице
     * @param f Метод, получающий и возвращающий значение типа double
     */
    public static void printTable(double from, double to, int n, Method f){
        //Вывести сигнатуру метода в заголовке таблицы
        System.out.println(f);
        double dx = (to - from)/(n - 1);
        for (double x = from; x<= to; x += dx){
            try {
                double y = (Double) f.invoke(null,x);
                System.out.printf("%10.4f | %10.4f%n", x, y);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
