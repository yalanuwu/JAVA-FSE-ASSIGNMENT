package Exercise_4;

import java.lang.reflect.Array;

public class Employee {
    private int employeeId;
    private String name;
    private String position;
    private double salary;

    public Employee(){}

    public Employee(int id,String n,String p,double s){
        this.employeeId = id;
        this.name = n;
        this.position = p;
        this.salary = s;
    }

    public int getEmployeeID(){return this.employeeId;}
    public void setEmployeeID(int i){ this.employeeId = i;}

    public String getName(){return this.name;}
    public void setName(String n){this.name = n;}

    public String getPosition(){return this.position;}
    public void setPosition(String p){this.position = p;}
    
    public double getSalary(){return this.salary;}
    public void setSalary(double s){this.salary = s;}

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                '}';
    }

    public static void add(Employee[] employees,int index,  Employee employee){
        Array.set(employees, index, employee);
    }

    public static Employee searchEmployee(Employee[] employees, int employeeId){
        for (Employee i : employees){
            if (i.getEmployeeID() == employeeId) return i;
        }
        return null;
    }

    public static void traverseEmployees(Employee[] employees){
        for (Employee i : employees){
            System.out.println(i);
        }
    }

    public static void deleteEmployee(Employee[] employees, int employeeId){
        int index = -1;
        for (int i = 0 ; i < employees.length ; ++i){
            if (employees[i].getEmployeeID() == employeeId) {
                index = i;
                break;
            }
        }
        if (index != -1){
            for (int i = index; i < employees.length - 1 ; ++i){
                employees[i] = employees[i + 1];
            }
        }
        
    }
    
    public static void main(String[] args) {
        Employee[] employees = {
            new Employee(1, "John", "Manager", 400),
            new Employee(2, "Katy", "Secretary", 50),
            new Employee(3, "Lisa", "Accountant", 200),
            new Employee(5, "Jack", "Salesman", 100)
        };
        

        //Adding Employees
        Employee empl = new Employee(4, "Rahul", "Developer", 300);
        add(employees, 2, empl);

        //Deleting Employee By Id
        deleteEmployee(employees, 2);

        //Searching Employee
        Employee searchRes = searchEmployee(employees, 2);
        System.out.println("Search Result: " + searchRes);

        //Traverse employees
        traverseEmployees(employees);

    }

}
