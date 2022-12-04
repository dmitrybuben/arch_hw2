package Architecture;

import java.util.Random;

public class Homework {

    public static void main(String[] args) {

        /*Задача №1 - получаем сотрудника определенного типа*/

        ConcreteEmployeeCreator concreteEmployeeCreator = new ConcreteEmployeeCreator();
        Employee employee = concreteEmployeeCreator.createEmployee(EmployeeType.Worker);
        System.out.println(employee);

        /*
        * Задача №2 Метод generateEmploeyee() должен быть без входных параметров, тип сотрудника,
        * фио и ставка генерируются автоматически.
        * */
        Employee[]employees = new Employee[10];
        for (int i = 0; i < employees.length; i++) {
            employees[i] = concreteEmployeeCreator.createEmployeeInstance(EmployeeType.Contractor);
        }

        for (Employee emp:employees){
            System.out.println(emp);
        }
    }
}
/*
* Базовый класс Работник
* */

abstract class Employee{
    protected String name;
    protected String surname;
    /*
    * Ставка заработной платы
    * */
    protected double salary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Employee(String name, String surname, double salary) {
        this.name = name;
        this.surname = surname;
        this.salary = salary;
    }

    public abstract double calculateSalary();
}

class Freelancer extends Employee{

    public Freelancer(String name, String surname, double salary) {
        super(name, surname, salary);
    }

    @Override
    public double calculateSalary() {
        return 15 * 6 * salary;
    }

    @Override
    public String toString() {
        return String.format("%s %s; Фрилансер; Среднемесячная заработная плата: %.2f (руб.); Почасовая ставка: %.2f (руб.)",
                name,surname, calculateSalary(), salary);
    }
}

class Worker extends Employee{

    public Worker(String name, String surname, double salary) {
        super(name, surname, salary);
    }

    @Override
    public double calculateSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return String.format("%s %s; Рабочий; Среднемесячная заработная плата (фиксированная месячная оплата): %.2f (руб.)",
                name,surname, calculateSalary(), salary);
    }
}

class Contractor extends Employee{

    public Contractor(String name, String surname, double salary) {
        super(name, surname, salary);
    }

    @Override
    public double calculateSalary() {
        return 8 * 2 * salary;
    }

    @Override
    public String toString() {
        return String.format("%s %s; Подрядчик; Среднемесячная заработная плата: %.2f (руб.); Ставка за сделку: %.2f (руб.)",
                name,surname, calculateSalary(), salary);
    }
}

enum EmployeeType{
    Worker,
    Freelancer,
    Contractor
}

abstract class BaseEmployeeCreator {

    public Employee createEmployee(EmployeeType employeeType){
        Employee employee = createEmployeeInstance(employeeType);
        return employee;
    }

    /*
    * FabricMethod для создания экземпляров типа Employee
    * */
    protected abstract Employee createEmployeeInstance(EmployeeType employeeType);

    public abstract void setDataSource(Object data);

    public abstract Object getDatasource();
}

class ConcreteEmployeeCreator extends BaseEmployeeCreator{

    @Override
    protected Employee createEmployeeInstance(EmployeeType employeeType) {
        return switch (employeeType){
            case Worker -> new Worker("Alex","Petrov",50000);
            case Contractor -> new Contractor("Ivan","Eliseev", 20000);
            case Freelancer -> new Freelancer("Jhon","Smith", 10000);
        };
    }

    @Override
    public void setDataSource(Object data) {

    }

    @Override
    public Object getDatasource() {
        return null;
    }
}