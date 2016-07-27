package inheritance;

/**
 * Created by Elvis on 03.06.2016.
 */

public class Manager extends Employee {
    private double bonus;
    /**
     * @param n Имя сотрудника
     * @param s Зарплата
     * @param year Год приема
     * @param mont месяц...
     * @param day день...
     */
    public Manager(String n, double s, int year, int mont, int day){
        super(n,s,year,mont,day);
        bonus = 0;
    }
    public double getSalary(){
        double baseSalary = super.getSalary();
        return baseSalary + bonus;
    }
    public void setBonus(double b){
        bonus = b;
    }
}
