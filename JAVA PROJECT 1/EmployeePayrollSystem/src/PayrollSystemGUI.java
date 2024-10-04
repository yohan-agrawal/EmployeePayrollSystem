import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PayrollSystemGUI extends JFrame {

    private ArrayList<Employee> employeeList = new ArrayList<>();

    public PayrollSystemGUI() {
        setTitle("Employee Payroll System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Employee Payroll System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JButton addEmployeeButton = new JButton("Add Employee");
        JButton modifyEmployeeButton = new JButton("Modify Employee");
        JButton deleteEmployeeButton = new JButton("Delete Employee");
        JButton generatePayrollButton = new JButton("Generate Payroll");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        buttonPanel.add(addEmployeeButton);
        buttonPanel.add(modifyEmployeeButton);
        buttonPanel.add(deleteEmployeeButton);
        buttonPanel.add(generatePayrollButton);

        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        addEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addEmployee();
            }
        });

        modifyEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifyEmployee();
            }
        });

        deleteEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteEmployee();
            }
        });

        generatePayrollButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generatePayroll();
            }
        });

        setVisible(true);
    }

    private void addEmployee() {
        JTextField nameField = new JTextField();
        JTextField idField = new JTextField();
        JTextField positionField = new JTextField();
        JTextField departmentField = new JTextField();
        JTextField salaryField = new JTextField();
        JTextField overtimeField = new JTextField();
        JTextField bonusField = new JTextField();
        JTextField deductionField = new JTextField();
        JTextField taxField = new JTextField();

        Object[] fields = {
                "Name:", nameField,
                "Employee ID:", idField,
                "Position:", positionField,
                "Department:", departmentField,
                "Base Salary:", salaryField,
                "Overtime Hours:", overtimeField,
                "Bonuses:", bonusField,
                "Deductions:", deductionField,
                "Taxes:", taxField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add Employee", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Employee newEmployee = new Employee(
                    nameField.getText(),
                    idField.getText(),
                    positionField.getText(),
                    departmentField.getText(),
                    Double.parseDouble(salaryField.getText()),
                    Double.parseDouble(overtimeField.getText()),
                    Double.parseDouble(bonusField.getText()),
                    Double.parseDouble(deductionField.getText()),
                    Double.parseDouble(taxField.getText())
            );

            employeeList.add(newEmployee);
            JOptionPane.showMessageDialog(this, "Employee added successfully.");
        }
    }

    private void modifyEmployee() {
        String employeeID = JOptionPane.showInputDialog(this, "Enter Employee ID to Modify:");
        Employee employee = findEmployeeByID(employeeID);

        if (employee != null) {
            JTextField nameField = new JTextField(employee.getName());
            JTextField positionField = new JTextField(employee.getPosition());
            JTextField departmentField = new JTextField(employee.getDepartment());
            JTextField salaryField = new JTextField(String.valueOf(employee.getBaseSalary()));
            JTextField overtimeField = new JTextField(String.valueOf(employee.getOvertimepay()));
            JTextField bonusField = new JTextField(String.valueOf(employee.getBonuses()));
            JTextField deductionField = new JTextField(String.valueOf(employee.getDeductions()));
            JTextField taxField = new JTextField(String.valueOf(employee.getTaxes()));

            Object[] fields = {
                    "Name:", nameField,
                    "Position:", positionField,
                    "Department:", departmentField,
                    "Base Salary:", salaryField,
                    "Overtime Hours:", overtimeField,
                    "Bonuses:", bonusField,
                    "Deductions:", deductionField,
                    "Taxes:", taxField
            };

            int option = JOptionPane.showConfirmDialog(this, fields, "Modify Employee", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                employee.setName(nameField.getText());
                employee.setPosition(positionField.getText());
                employee.setDepartment(departmentField.getText());
                employee.setBaseSalary(Double.parseDouble(salaryField.getText()));
                employee.setOvertimepay(Double.parseDouble(overtimeField.getText()));
                employee.setBonuses(Double.parseDouble(bonusField.getText()));
                employee.setDeductions(Double.parseDouble(deductionField.getText()));
                employee.setTaxes(Double.parseDouble(taxField.getText()));
                JOptionPane.showMessageDialog(this, "Employee modified successfully.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Employee not found.");
        }
    }

    private void deleteEmployee() {
        String employeeID = JOptionPane.showInputDialog(this, "Enter Employee ID to Delete:");
        Employee employee = findEmployeeByID(employeeID);

        if (employee != null) {
            employeeList.remove(employee);
            JOptionPane.showMessageDialog(this, "Employee deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Employee not found.");
        }
    }

    private void generatePayroll() {
        StringBuilder payroll = new StringBuilder();

        for (Employee employee : employeeList) {
            payroll.append(employee.toString()).append("\n\n");
        }

        JTextArea textArea = new JTextArea(payroll.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(this, scrollPane, "Payroll Report", JOptionPane.INFORMATION_MESSAGE);
    }

    private Employee findEmployeeByID(String employeeID) {
        for (Employee emp : employeeList) {
            if (emp.getEmployeeID().equals(employeeID)) {
                return emp;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PayrollSystemGUI();
            }
        });
    }
}