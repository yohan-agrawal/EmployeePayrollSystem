import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileManager {

    private EmployeeDAO employeeDAO;

    public FileManager(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public void importEmployeesFromCSV(File file) {
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] employeeData = line.split(csvSplitBy);

                if (employeeData.length == 9) {
                    Employee employee = new Employee(
                            employeeData[0],
                            employeeData[1],
                            employeeData[2],
                            employeeData[3],
                            Double.parseDouble(employeeData[4]),
                            Double.parseDouble(employeeData[5]),
                            Double.parseDouble(employeeData[6]),
                            Double.parseDouble(employeeData[7]),
                            Double.parseDouble(employeeData[8])
                    );

                    employeeDAO.addEmployee(employee);
                } else {
                    System.out.println("Invalid data format in CSV line: " + line);
                }
            }
            System.out.println("Employees imported successfully from CSV.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void exportEmployeesToCSV(String filePath) {
        List<Employee> employees = employeeDAO.getAllEmployees();
        File file = new File(filePath);

        if(!file.exists() || !file.canWrite()) {
            System.out.println("Error: Unable to write to the specified file path.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("EmployeeID,Name,Position,Department,BaseSalary,OvertimePay,Bonuses,Deductions,Taxes");
            writer.newLine();

            for (Employee employee : employees) {
                writer.write(employee.getEmployeeID() + "," + employee.getName() + "," + employee.getPosition() + "," +
                        employee.getDepartment() + "," + employee.getBaseSalary() + "," +
                        employee.getOvertimepay() + "," + employee.getBonuses() + "," +
                        employee.getDeductions() + "," + employee.getTaxes());
                writer.newLine();
            }
            System.out.println("Employees exported successfully to CSV.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportPayrollToCSV(String filePath) {
        List<Employee> employees = employeeDAO.getAllEmployees();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("EmployeeID,Name,BaseSalary,OvertimePay,Bonuses,Deductions,Taxes,NetSalary");
            writer.newLine();

            for (Employee employee : employees) {
                double grossSalary = employee.getBaseSalary() + employee.getOvertimepay() + employee.getBonuses();
                double netSalary = grossSalary - (employee.getDeductions() + employee.getTaxes());

                writer.write(employee.getEmployeeID() + "," + employee.getName() + "," + employee.getBaseSalary() + "," +
                        employee.getOvertimepay() + "," + employee.getBonuses() + "," +
                        employee.getDeductions() + "," + employee.getTaxes() + "," + netSalary);
                writer.newLine();
            }
            System.out.println("Payroll exported successfully to CSV.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}