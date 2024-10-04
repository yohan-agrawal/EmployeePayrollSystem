public class Employee {
    private String name;
    private String employeeID;
    private String position;
    private String department;
    private double baseSalary;
    private double overtimepay;
    private double bonuses;
    private double deductions;
    private double taxes;

    public Employee(String name, String employeeID, String position, String department, double baseSalary, double overtimepay, double bonuses, double deductions, double taxes) {
        this.name = name;
        this.employeeID = employeeID;
        this.position = position;
        this.department = department;
        this.baseSalary = baseSalary;
        this.overtimepay = overtimepay;
        this.bonuses = bonuses;
        this.deductions = deductions;
        this.taxes = taxes;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmployeeID() { return employeeID; }
    public void setEmployeeID(String employeeID) { this.employeeID = employeeID; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public double getBaseSalary() { return baseSalary; }
    public void setBaseSalary(double baseSalary) { this.baseSalary = baseSalary; }

    public double getOvertimepay() { return overtimepay; }
    public void setOvertimepay(double overtimepay) { this.overtimepay = overtimepay; }

    public double getBonuses() { return bonuses; }
    public void setBonuses(double bonuses) { this.bonuses = bonuses; }

    public double getDeductions() { return deductions; }
    public void setDeductions(double deductions) { this.deductions = deductions; }

    public double getTaxes() { return taxes; }
    public void setTaxes(double taxes) { this.taxes = taxes; }

    public double calculateNetSalary() {
        double overtimePay = overtimepay * (baseSalary / 160) * 1.5;
        double grossSalary = baseSalary + overtimePay + bonuses;
        double calculatedTaxes = grossSalary * 0.15;
        return grossSalary - calculatedTaxes - deductions;
    }

    @Override
    public String toString() {
        return "Employee ID: " + employeeID + "\nName: " + name + "\nPosition: " + position +
                "\nDepartment: " + department + "\nBase Salary: $" + baseSalary +
                "\nOvertime Hours: " + overtimepay + "\nBonuses: $" + bonuses +
                "\nDeductions: $" + deductions + "\nNet Salary: $" + calculateNetSalary();
    }
}