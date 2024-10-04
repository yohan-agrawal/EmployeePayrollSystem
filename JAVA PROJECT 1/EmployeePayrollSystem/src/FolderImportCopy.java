import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FolderImportCopy {
    public static void main(String[] args) {
        Path sourceFolder = Paths.get("C:\\Users\\yohan\\Documents\\import_data.csv");
        Path destinationFolder = Paths.get("C:\\Users\\yohan\\Documents\\import_data.csv\\importEmployee.csv");

        try {
            if (Files.notExists(destinationFolder)) {
                Files.createDirectories(destinationFolder);
            }

            Files.walkFileTree(sourceFolder, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path destinationPath = destinationFolder.resolve(sourceFolder.relativize(file));
                    Files.copy(file, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path destinationPath = destinationFolder.resolve(sourceFolder.relativize(dir));
                    if (Files.notExists(destinationPath)) {
                        Files.createDirectory(destinationPath);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });

            System.out.println("Folder copied successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}