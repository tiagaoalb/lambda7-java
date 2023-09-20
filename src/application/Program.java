package application;

import entities.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Program {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter full file path: ");
        String path = sc.nextLine();

        System.out.print("Enter salary: ");
        double salary = sc.nextDouble();

        System.out.print("Enter the first letter of a name: ");
        char initialLetter = sc.next().charAt(0);

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            List<Employee> list = new ArrayList<>();
            String line = br.readLine();

            while (line != null) {
                String[] fields = line.split(",");
                list.add(new Employee(fields[0], fields[1], Double.parseDouble(fields[2])));
                line = br.readLine();
            }

            List<String> emailList = list.stream()
                    .filter(e -> e.getSalary() > salary)
                    .map(Employee::getEmail).sorted()
                    .toList();

            System.out.println("Email of people whose salary is more than " + String.format("%.2f", salary) + ":");
            emailList.forEach(System.out::println);

            List<Employee> namesList = list.stream()
                    .filter(e -> e.getName().charAt(0) == initialLetter)
                    .toList();

            double salarySum = namesList.stream()
                    .map(Employee::getSalary)
                    .reduce(0.0, Double::sum);

            System.out.println("Sum of salaries of people whose names start with " + initialLetter + " : " + String.format("%.2f", salarySum));


        } catch (IOException e) {
            System.out.println("Error message: " + e.getMessage());
        }
    }
}
