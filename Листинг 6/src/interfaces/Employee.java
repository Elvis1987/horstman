package interfaces;

/**
 * Created by Elvis on 16.06.2016.
 */
public class Employee implements Comparable<Employee> {
    private String name;
    private double salary;

    public Employee(String n, double s){
        name = n;
        salary = s;
    }
    public String getName(){
        return name;
    }
    public double getSalary(){
        return salary;
    }
    public void raiseSalary(double byPercent){
        double raise = salary * byPercent / 100;
        salary += raise;
    }
    //Сравнивает зарплату сотрудников
    public int compareTo(Employee other){
        return Double.compare(salary, other.salary);
    }
}
