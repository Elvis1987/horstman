package proxy;

import java.lang.reflect.*;
import java.util.*;

/**В этой программе демострируется применение прокси-объектов
 * Created by Elvis on 24.06.2016.
 */
public class ProxyTest {
    public static void main(String[] args) {
        Object[] elements = new Object[1000];
        //заполнить массив elements прокси-объектами целых цисел от 1до 1000
        for (int i = 0; i< elements.length; i++){
            Integer value = i + 1;
            InvocationHandler handler = new TraceHandler(value);
            Object proxy = Proxy.newProxyInstance(null, new Class[]{Comparable.class}, handler);
            elements[i] = proxy;
        }
        //Сформировать случайное целое число
        Integer key = new Random().nextInt(elements.length) + 1;
        //Выполнить поискпо критерию key
        int result = Arrays.binarySearch(elements, key);
        //Вывести совпавший элемент, если таковой найден
        if (result >=0) System.out.println(elements[result]);
    }
}

//Обработчик вызовов, выводящий сначала имя метода и его параметры, а затем вызывающий метод

class TraceHandler implements InvocationHandler {
    private Object target;

    //Коструирует объекты типа TraceHandler
    public TraceHandler(Object t) {
        target = t;
    }

    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        //Вывести неявный параметр
        System.out.print(target);
        //вывести имя метода
        System.out.print("." + m.getName() + "(");

        //Вывестиявные параметры
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                System.out.print(args[i]);
                if (i < args.length - 1) System.out.print(",");
            }
        }
        System.out.println(")");
        //вызвать конкретный метод
        return m.invoke(target, args);
    }
}