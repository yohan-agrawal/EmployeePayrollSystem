import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDataImport {

    public static List<Employee> importFromCSV(String filePath) {
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",");

                String name = data[1];
                String EmployeeID = data[0];
                String position = data[2];
                String department = data[3];
                double baseSalary = Double.parseDouble(data[4]);
                double overtimeHours = Double.parseDouble(data[5]);
                double bonuses = Double.parseDouble(data[6]);
                double deductions = Double.parseDouble(data[7]);
                double taxes = Double.parseDouble(data[8]);

                Employee employee = new Employee(name,EmployeeID, position, department, baseSalary, overtimeHours, bonuses, deductions, taxes);

                employee.calculateNetSalary();

                employees.add(employee);
            }

            System.out.println("Employee data imported successfully from CSV!");

        } catch (IOException e) {
            System.out.println("Error while importing data from CSV.");
            e.printStackTrace();
        }

        return employees;
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\yohan\\Documents\\payroll.csv";

        List<Employee> employees = importFromCSV(filePath);

        for (Employee emp : employees) {
            System.out.println("Imported Employee: " + emp.getName() + ", ID: " + emp.getEmployeeID());
        }
    }
}