package clone;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Elvis on 16.06.2016.
 */
public class Employee implements Cloneable{
    private String name;
    private double salary;
    private Date hireDay;

    public Employee(String n,double s){
        name = n;
        salary = s;
        hireDay = new Date();
    }
    public Employee clone() throws CloneNotSupportedException{
        //Вызвать метод Object.clone()
        Employee cloned = (Employee) super.clone();
        //клонировать изменяемые поля
        cloned.hireDay = (Date) hireDay.clone();
        return cloned;
    }
    //Устанавливает заданную дату приема
    public void setHireDay(int year, int month, int day){
        Date newHireDay = new GregorianCalendar(year, month-1, day).getTime();
        //Пример изменения поля экземпляра
        hireDay.setTime(newHireDay.getTime());
    }
    public void raiseSalary(double byPercent){
        double raise = salary * byPercent/100;
        salary += raise;
    }
    public String toString(){
        return "Employee[name=" + name +",salary=" + salary + ",hireDay=" + hireDay + "]";
    }

}
