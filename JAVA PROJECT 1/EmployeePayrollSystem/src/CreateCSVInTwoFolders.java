import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateCSVInTwoFolders {
    public CreateCSVInTwoFolders() {
    }

    public static void main(String[] args) {
        String payrollFolderPath = "C:\\Users\\yohan\\Documents\\payroll";
        String employeeFolderPath = "C:\\Users\\yohan\\Documents\\Employee";
        String importEmployeeFolderPath = "C:\\Users\\yohan\\Documents\\import_data_folder";
        String payrollFilePath = payrollFolderPath + "\\payroll.csv";
        String employeeFilePath = employeeFolderPath + "\\employee.csv";
        String importEmployeeFilePath = importEmployeeFolderPath + "\\importEmployee.csv";
        if (createFolder(payrollFolderPath)) {
            createEmptyCSVFile(payrollFilePath, "Payroll CSV file created successfully.");
        }

        if (createFolder(employeeFolderPath)) {
            createEmptyCSVFile(employeeFilePath, "Employee CSV file created successfully.");
        }

        if (createFolder(importEmployeeFolderPath)) {
            createEmptyCSVFile(importEmployeeFilePath, "importEmployee CSV file created successfully.");
        }

    }

    public static boolean createFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                System.out.println("Folder created successfully at: " + folderPath);
                return true;
            } else {
                System.out.println("Failed to create folder at: " + folderPath);
                return false;
            }
        } else {
            System.out.println("Folder already exists at: " + folderPath);
            return true;
        }
    }

    public static void createEmptyCSVFile(String filePath, String successMessage) {
        try {
            FileWriter writer = new FileWriter(filePath);

            try {
                System.out.println(successMessage);
            } catch (Throwable var6) {
                try {
                    writer.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            writer.close();
        } catch (IOException var7) {
            IOException e = var7;
            System.out.println("An error occurred while creating the CSV file: " + filePath);
            e.printStackTrace();
        }

    }
}