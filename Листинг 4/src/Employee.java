import java.util.*;

/**
 * Created by Elvis on 03.06.2016.
 */
class Employee{
    public Employee(String n, double s){
        name = n;
        salary = s;
    }
    public Employee (double s){
        this("Employee#" + nextId, s);
    }
    public Employee(){}
    public String getName(){
        return name;
    }
    public double getSalary(){
        return salary;
    }
    public int getId(){
        return id;
    }
    private static int nextId;
    private int id;
    private String name = "";
    private double salary;

    static {
        Random generator = new Random();
        nextId = generator.nextInt(1000);
    }
    {
        id = nextId;
        nextId++;
    }
}