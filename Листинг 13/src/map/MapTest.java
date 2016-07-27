package map;
import java.util.*;

/**В этой программе демонстрируется применение отображения
 * с ключами типа String и значениями типа Employee
 * Created by Elvis on 19.07.2016.
 */
public class MapTest {
    public static void main(String[] args) {
        Map<String, Employee> staff = new HashMap<>();
        staff.put("144-25-5464", new Employee("Amy Lee"));
        staff.put("567-24-2546", new Employee("Harry Hacker"));
        staff.put("157-62-7935", new Employee("Gary Cooper"));
        staff.put("456-62-5527", new Employee("Francesca Cruz"));

        //вывести все элементы отображения
        System.out.println(staff);

        //удалить элемент из отображения
        staff.remove("567-24-2546");

        //заменить элемент отображени
        staff.put("456-62-5527", new Employee("Francesca Miller"));

        //найти значение
        System.out.println(staff.get("157-62-7935"));

        //перебрать все элементы отображения
        for (Map.Entry<String, Employee> entry : staff.entrySet()){
            String key = entry.getKey();
            Employee value = entry.getValue();
            System.out.println("key= " + key + ", value=" + value);
        }
    }
}
