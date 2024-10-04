import java.io.File;
import java.util.List;
import javax.swing.*;

public class EmployeeMain {

    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        FileManager fileManager = new FileManager(employeeDAO);

        while (true) {
            String[] options = {"Add Employee", "Modify Employee", "Delete Employee", "Get All Employees", "Generate Payroll", "Import Employees", "Export Employees", "Export Payroll", "Help", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "Select an option", "Employee Payroll System",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    addNewEmployee(employeeDAO);
                    break;
                case 1:
                    modifyEmployee(employeeDAO);
                    break;
                case 2:
                    deleteEmployee(employeeDAO);
                    break;
                case 3:
                    displayAllEmployees(employeeDAO);
                    break;
                case 4:
                    employeeDAO.generatePayroll();
                    break;
                case 5:
                    importEmployees(fileManager);
                    break;
                case 6:
                    exportEmployees(fileManager);
                    break;
                case 7:
                    exportPayroll(fileManager);
                    break;
                case 8:
                    displayHelp();
                    break;
                case 9:
                    confirmExit();
                    break;
                default:
                    break;
            }
        }
    }

    public static void addNewEmployee(EmployeeDAO employeeDAO) {
        try {
            String name = JOptionPane.showInputDialog("Enter Employee Name:");
            String employeeID = JOptionPane.showInputDialog("Enter Employee ID:");
            String position = JOptionPane.showInputDialog("Enter Position:");
            String department = JOptionPane.showInputDialog("Enter Department:");
            double baseSalary = Double.parseDouble(JOptionPane.showInputDialog("Enter Base Salary:"));
            double overtimePay = Double.parseDouble(JOptionPane.showInputDialog("Enter Overtime Pay:"));
            double bonuses = Double.parseDouble(JOptionPane.showInputDialog("Enter Bonuses:"));
            double deductions = Double.parseDouble(JOptionPane.showInputDialog("Enter Deductions:"));
            double taxes = Double.parseDouble(JOptionPane.showInputDialog("Enter Taxes:"));

            Employee employee = new Employee(name, employeeID, position, department, baseSalary, overtimePay, bonuses, deductions, taxes);
            employeeDAO.addEmployee(employee);
            JOptionPane.showMessageDialog(null, "Employee added successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter numeric values where appropriate.");
        }
    }

    public static void modifyEmployee(EmployeeDAO employeeDAO) {
        String employeeID = JOptionPane.showInputDialog("Enter Employee ID to Modify:");
        Employee employee = employeeDAO.findEmployeeByID(employeeID);

        if (employee != null) {
            try {
                String newName = JOptionPane.showInputDialog("Enter New Name:", employee.getName());
                employee.setName(newName);

                String newPosition = JOptionPane.showInputDialog("Enter New Position:", employee.getPosition());
                employee.setPosition(newPosition);

                String newDepartment = JOptionPane.showInputDialog("Enter New Department:", employee.getDepartment());
                employee.setDepartment(newDepartment);

                double newBaseSalary = Double.parseDouble(JOptionPane.showInputDialog("Enter New Base Salary:", employee.getBaseSalary()));
                employee.setBaseSalary(newBaseSalary);

                double newOvertimePay = Double.parseDouble(JOptionPane.showInputDialog("Enter New Overtime Pay:", employee.getOvertimepay()));
                employee.setOvertimepay(newOvertimePay);

                double newBonuses = Double.parseDouble(JOptionPane.showInputDialog("Enter New Bonuses:", employee.getBonuses()));
                employee.setBonuses(newBonuses);

                double newDeductions = Double.parseDouble(JOptionPane.showInputDialog("Enter New Deductions:", employee.getDeductions()));
                employee.setDeductions(newDeductions);

                double newTaxes = Double.parseDouble(JOptionPane.showInputDialog("Enter New Taxes:", employee.getTaxes()));
                employee.setTaxes(newTaxes);

                employeeDAO.updateEmployee(employee);
                JOptionPane.showMessageDialog(null, "Employee modified successfully.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter numeric values where appropriate.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Employee not found.");
        }
    }

    public static void deleteEmployee(EmployeeDAO employeeDAO) {
        String employeeID = JOptionPane.showInputDialog("Enter Employee ID to Delete:");
        employeeDAO.deleteEmployee(employeeID);
        JOptionPane.showMessageDialog(null, "Employee deleted successfully.");
    }

    public static void displayAllEmployees(EmployeeDAO employeeDAO) {
        List<Employee> employees = employeeDAO.getAllEmployees();
        if (employees.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No employees found.");
        } else {
            StringBuilder employeeDetails = new StringBuilder();
            for (Employee employee : employees) {
                employeeDetails.append("Employee ID: ").append(employee.getEmployeeID()).append("\n")
                        .append("Name: ").append(employee.getName()).append("\n")
                        .append("Position: ").append(employee.getPosition()).append("\n")
                        .append("Department: ").append(employee.getDepartment()).append("\n")
                        .append("Base Salary: ").append(employee.getBaseSalary()).append("\n")
                        .append("Overtime Pay: ").append(employee.getOvertimepay()).append("\n")
                        .append("Bonuses: ").append(employee.getBonuses()).append("\n")
                        .append("Deductions: ").append(employee.getDeductions()).append("\n")
                        .append("Taxes: ").append(employee.getTaxes()).append("\n")
                        .append("----------\n");
            }
            JOptionPane.showMessageDialog(null, employeeDetails.toString());
        }
    }

    public static void importEmployees(FileManager fileManager) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a CSV File");

        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            fileManager.importEmployeesFromCSV(file);
            JOptionPane.showMessageDialog(null, "Employees imported successfully.");
        }
    }

    public static void exportEmployees(FileManager fileManager) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save employees");

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            fileManager.exportEmployeesToCSV(fileToSave.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "Employees exported successfully.");
        }
    }

    public static void exportPayroll(FileManager fileManager) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save payroll");

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            fileManager.exportPayrollToCSV(fileToSave.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "Payroll exported successfully.");
        }
    }

    public static void displayHelp() {
        String userGuide = """
                Step 1: Introduction to the System
                Welcome to the "Java-Employee-Payroll-System," a tool designed to help manage employee data, calculate salaries, process payroll, and generate reports.

                Step 2: System Requirements
                - Java Development Kit (JDK) version 8 or higher
                - Database (MySQL/SQLite) configured for data storage
                - Operating System: Windows, macOS, or Linux.

                Step 3: Installation
                - Download and install Java Development Kit.
                - Set up the MySQL or SQLite database.
                - Run the EmployeeMain.java file.

                Step 4: Navigating the User Interface
                Main Menu options:
                - Add Employee
                - Modify Employee
                - Delete Employee
                - Process Payroll
                - Generate Pay Stub
                - Import/Export Employee Data

                Step 5: Adding Employee Data
                Select 'Add Employee' and input Name, Employee ID, Position, Department, and BaseSalary,
                        OverTimePay, deductions, Taxes, NetSalary.
                     -> Once all fields are filled, click Submit to add the employee. A confirmation
                        message will indicate that the employee has added successfully.
                        Now the data is stored in Database successfully.

                Step 6: Modifying Employee Details
                Select 'Modify Employee' and update the necessary fields.
                     -> In the "Modify Employee" section, you will be prompted to select an employee by their ID.
                        Modify any of the fields (name, position, salary, etc.) and click Update.
                        The system will display a success message confirming the update.

                Step 7: Deleting Employee Data
                Select 'Delete Employee' and input the Employee ID to delete.
                        (This will permanently delete the employee data)

                Step 8: Processing Payroll
                Select 'Process Payroll' to calculate salaries based on predefined rules.
                    -> This System will calculate salaries based on predefined rules
                       1. Base salary
                       2. OverTimePay
                       3. Deductions
                       4. Bonuses
                       5. Taxes
                       6. Net Salary (after deductions and tax)

                Step 9: Generating Payroll
                Select 'Generate Pay Stub' to view.

                Step 10: Importing/Exporting Employee Data
                Use 'Import/Export Employee Data' to handle CSV.
                  -> For this make sure that you had given proper "path" in Class - (CreateCSVInTwoFolders)
                     # For payrollFolderPath -> this File is for exporting payroll Data.
                     # For employeeFolderPath -> this file is for exporting Employee Data.
                     # For importEmployeeFolderPath -> this file for importing Data
    
                     For Export Employees Data :-
                     1. Click on the "Export Employees" option.
                     2. Select the file (e.g., CSV) where you want to store
                        employee information. (select the folder which you had given the path
                        in Class "CreateCSVInTwoFolders")
                     3. After this you will see that Data is stored in csv File
                
                     For Export payroll Data :-
                     1. Click on the "Export payroll" option.
                     2. Select the file (e.g., CSV) where you want to store
                        employee information. (select the folder which you had given the path
                        in Class "CreateCSVInTwoFolders")
                     3. After this you will see that Data is stored in csv File
                
                     For Import Employees data
                     1. Click on the "Import Data" option.
                     2. Select the file (e.g., CSV) that contains
                        employee information. (Where you stored Employee data)
                     3. The system will read the file and automatically
                        populate the employee database
                        After this you will see the data is imported in database as well as
                        you can see in Option "Get All Employees"

                Step 11: Error Handling and Validation
                The system validates inputs and alerts users on incorrect entries.
                     1. Required fields are left empty.
                     2. Invalid data (e.g., incorrect employee ID) is entered.
                     3. File import/export errors occur (e.g., incorrect file format).
                     # Ensure that the correct file formats are used (CSV for employee data
                       import/export).
                
                Step 12: Exiting the System
                     1. Click on "Exit" button to close the application safely
                     2. Ensure that all data has been saved before exiting.
                
                Step 12: Troubleshooting Tips
                Check database connection settings if errors occur.

                Step 13: Conclusion
                This step-by-step guide should provide users with clear instructions on how to use Employee payroll System
                effectively, from  adding employee data to generating payroll reports and handling payroll reports and
                handling data imports/exports.
                You've now learned how to use the system. For further help, contact the development team.
                """;


        JTextArea textArea = new JTextArea(userGuide);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 400));

        JOptionPane.showMessageDialog(null, scrollPane, "User Guide", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void confirmExit() {
        int confirmExit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (confirmExit == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}