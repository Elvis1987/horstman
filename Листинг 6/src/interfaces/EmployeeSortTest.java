package interfaces;
import java.util.*;

/**В этой программе демонстрируется применение интерфейса Comparable
 * Created by Elvis on 16.06.2016.
 */
public class EmployeeSortTest {
    public static void main(String[] args) {
        Employee[] staff = new Employee[3];
        staff[0] = new Employee("Harry Hacker", 35000);
        staff[1] = new Employee("Carl Cracker", 75000);
        staff[2] = new Employee("Tony Tester", 38000);

        Arrays.sort(staff);
        //вывести данные обо всех объектах типа Employee
        for (Employee e : staff)
            System.out.println("name=" + e.getName() + "salary=" + e.getSalary());
    }
}
