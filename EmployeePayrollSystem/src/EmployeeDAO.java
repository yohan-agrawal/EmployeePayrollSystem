import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private Connection connect() {
        String url = "jdbc:mysql://localhost:3307/employeepayrollsys";
        String user = "root";
        String password = "password";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void addEmployee(Employee employee) {
        String query = "INSERT INTO employees (employeeID, name, position, department, baseSalary, overtimepay, bonuses, deductions, taxes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, employee.getEmployeeID());
            pstmt.setString(2, employee.getName());
            pstmt.setString(3, employee.getPosition());
            pstmt.setString(4, employee.getDepartment());
            pstmt.setDouble(5, employee.getBaseSalary());
            pstmt.setDouble(6, employee.getOvertimepay());
            pstmt.setDouble(7, employee.getBonuses());
            pstmt.setDouble(8, employee.getDeductions());
            pstmt.setDouble(9, employee.getTaxes());

            pstmt.executeUpdate();
            System.out.println("Employee added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(Employee employee) {
        String query = "UPDATE employees SET name = ?, position = ?, department = ?, baseSalary = ?, overtimepay = ?, bonuses = ?, deductions = ?, taxes = ? WHERE employeeID = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getPosition());
            pstmt.setString(3, employee.getDepartment());
            pstmt.setDouble(4, employee.getBaseSalary());
            pstmt.setDouble(5, employee.getOvertimepay());
            pstmt.setDouble(6, employee.getBonuses());
            pstmt.setDouble(7, employee.getDeductions());
            pstmt.setDouble(8, employee.getTaxes());
            pstmt.setString(9, employee.getEmployeeID());

            pstmt.executeUpdate();
            System.out.println("Employee updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(String employeeID) {
        String query = "DELETE FROM employees WHERE employeeID = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, employeeID);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Employee deleted successfully.");
            } else {
                System.out.println("Employee not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void generatePayroll() {
        String query = "SELECT name, employeeID, baseSalary, overtimepay, bonuses, deductions, taxes FROM employees";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String employeeID = rs.getString("employeeID");
                String name = rs.getString("name");
                double baseSalary = rs.getDouble("baseSalary");
                double overtimePay = rs.getDouble("overtimepay");
                double bonuses = rs.getDouble("bonuses");
                double deductions = rs.getDouble("deductions");
                double taxes = rs.getDouble("taxes");

                double grossSalary = baseSalary + overtimePay + bonuses;
                double netSalary = grossSalary - (deductions + taxes);

                System.out.println("Payroll for Employee: " + name + " (ID: " + employeeID + ")");
                System.out.println("Gross Salary: $" + String.format("%.2f", grossSalary));
                System.out.println("Deductions: $" + String.format("%.2f", deductions));
                System.out.println("Taxes: $" + String.format("%.2f", taxes));
                System.out.println("Net Salary: $" + String.format("%.2f", netSalary));
                System.out.println("-------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Employee findEmployeeByID(String employeeID) {
        String query = "SELECT * FROM employees WHERE employeeID = ?";
        Employee employee = null;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, employeeID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                employee = new Employee(
                        rs.getString("name"),
                        rs.getString("employeeID"),
                        rs.getString("position"),
                        rs.getString("department"),
                        rs.getDouble("baseSalary"),
                        rs.getDouble("overtimepay"),
                        rs.getDouble("bonuses"),
                        rs.getDouble("deductions"),
                        rs.getDouble("taxes")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }

    public List<Employee> getAllEmployees() {
        String query = "SELECT * FROM employees";
        List<Employee> employeeList = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getString("employeeID"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getString("department"),
                        rs.getDouble("baseSalary"),
                        rs.getDouble("overtimepay"),
                        rs.getDouble("bonuses"),
                        rs.getDouble("deductions"),
                        rs.getDouble("taxes")
                );
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }
}