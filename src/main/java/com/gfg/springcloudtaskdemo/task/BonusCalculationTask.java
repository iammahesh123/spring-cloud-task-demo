package com.gfg.springcloudtaskdemo.task;



import com.gfg.springcloudtaskdemo.model.Employee;
import com.gfg.springcloudtaskdemo.service.BonusCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class BonusCalculationTask implements CommandLineRunner {

    @Autowired
    private BonusCalculatorService bonusCalculatorService;

    @Override
    public void run(String... args) throws Exception {
        // Read input CSV file
        File inputFile = new File("C:/Users/Mahesh/Desktop/E-Commerce-Backend/SpringCloudTaskDemo/src/main/resources/employees.csv");
        List<Employee> employees = readEmployeesFromCSV(inputFile);

        // Process data (calculate bonus)
        calculateBonus(employees);

        // Write processed data to output CSV file
        File outputFile = new File("employees_with_bonus.csv");
        writeEmployeesToCSV(employees, outputFile);
    }

    private List<Employee> readEmployeesFromCSV(File file) throws IOException {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean headerSkipped = false; // Flag to track whether header row has been skipped
            while ((line = reader.readLine()) != null) {
                if (!headerSkipped) {
                    // Skip the header row
                    headerSkipped = true;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    double salary = Double.parseDouble(parts[1]);
                    employees.add(new Employee(name, salary));
                }
            }
        }
        return employees;
    }


    private void calculateBonus(List<Employee> employees) {
        for (Employee employee : employees) {
            double bonus = bonusCalculatorService.calculateBonus(employee.getSalary());
            employee.setSalary(employee.getSalary() + bonus);
        }
    }

    private void writeEmployeesToCSV(List<Employee> employees, File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Employee employee : employees) {
                writer.write(employee.getName() + "," + employee.getSalary());
                writer.newLine();
            }
        }
    }
}

