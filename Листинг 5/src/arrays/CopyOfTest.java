package arrays;
import java.lang.reflect.*;
import java.util.*;

/**В этой программе демонстрируется применение рефлексии
 * для манипулирования массивами
 * Created by Elvis on 15.06.2016.
 */
public class CopyOfTest {
    public static void main(String[] args) {
        int[]a = {1, 2, 3};
        a = (int[]) goodCopyOf(a, 10);
        System.out.println(Arrays.toString(a));

        String[]b = {"Tom", "Dick", "Harry"};
        b = (String[])goodCopyOf(b, 10);
        System.out.println(Arrays.toString(b));
        System.out.println("The following call will generate an exception.");
        b= (String[]) badCopyOf(b, 10);
    }
    /**В этом методе предпринимается попытка нарастить массив путем
     * выделения нового массива и копирования в него всех прежних елементов
     * @param Наращиваемый массив
     * @param newLength новая длинна массива
     * @return Возвращаемый наращенный массив, содержит все элементы
     * массива а, но он относится к типу Object[], а нек типу массива а
     */
    public static Object[] badCopyOf(Object[] a,int newLength){ //бесполезно
        Object[]newArray = new Object[newLength];
        System.arraycopy(a, 0, newArray, 0, Math.min(a.length, newLength));
        return newArray;
    }
    /**Этот метод наращивает массив , выделяя новый массив того же типа
     * и копируя в него все прежние элементы
     * @param Наращиваемый массив.Может массивом объектов или же
     *                     масивом примитивных типов
     * @return Возвращает наращенный масив, содержащий всеэлементы массива а
     */
    public static Object goodCopyOf(Object a, int newLength){
        Class cl = a.getClass();
        if (!cl.isArray()) return null;
        Class componentType = cl.getComponentType();
        int length = Array.getLength(a);
        Object newArray = Array.newInstance(componentType, newLength);
        System.arraycopy(a, 0, newArray, 0, Math.min(length, newLength));
        return newArray;
    }
}
