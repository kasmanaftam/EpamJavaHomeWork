package consoleUtils;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;

public class utilCommands {
    private static Path currentDirectory;

    public static Path getCurrentDirectory() {
        return currentDirectory;
    }

    public static void setCurrentDirectory(Path currentDirectory) {
        if (Files.isDirectory(currentDirectory)) {
            utilCommands.currentDirectory = currentDirectory;
        }
    }

    public static void setCurrentDirectory(String targetDirectory) {
        Path targetPath;
        if (targetDirectory.matches("[A-Za-z]:*")) {
            targetPath = Paths.get(targetDirectory);
        } else {
            targetPath = Paths.get(currentDirectory.toString(), targetDirectory).normalize();
        }
        if (Files.isDirectory(targetPath)) {
            utilCommands.currentDirectory = targetPath;
        } else {
            System.out.println("Invalid directory");
            return;
        }
    }

    public static void dir(Comparator comparator, boolean reverseSort) throws IOException {
        DirectoryExplorer.displayDir(currentDirectory, comparator, reverseSort);
    }

    public static void move(Path sourcePath, Path destinationPath, boolean replaceExisting) throws IOException {
        if (!Files.isDirectory(destinationPath)) {
            try {
                Files.createDirectories(destinationPath);
            } catch (IOException e) {
                System.out.println("Can't create target directory");
                return;
            }
        }
        int rootIndex = sourcePath.getNameCount() - 1;
        copyRecursive(sourcePath, destinationPath, replaceExisting, rootIndex);
        delete(sourcePath);
    }

    public static void copy(Path sourcePath, Path destinationPath, boolean replaceExisting) throws IOException {
        if (!Files.isDirectory(destinationPath)) {
            try {
                Files.createDirectories(destinationPath);
            } catch (IOException e) {
                System.out.println("Can't create target directory");
                return;
            }
        }
        int rootIndex = sourcePath.getNameCount() - 1;
        copyRecursive(sourcePath, destinationPath, replaceExisting, rootIndex);
    }

    private static void copyRecursive(Path sourcePath, Path destinationPath, boolean replaceExisting, int rootIndex) throws IOException {
        if (Files.isDirectory(sourcePath)) {
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(sourcePath);
            for (Path path : directoryStream) {
                copyRecursive(path, destinationPath, replaceExisting, rootIndex);
            }
        } else {
            String fileRelativePath = sourcePath.subpath(rootIndex, sourcePath.getNameCount()).toString();
            Path copyFileAbsolutePath = Paths.get(destinationPath.toString(), fileRelativePath);
            Path copyFileParentalDirectory = copyFileAbsolutePath.getParent();
            if (!Files.isDirectory(copyFileParentalDirectory)) {
                try {
                    Files.createDirectories(copyFileParentalDirectory);
                } catch (IOException e) {
                    System.out.println("Can't create folder: " + copyFileParentalDirectory);
                    return;
                }
            }
            if (replaceExisting) {
                Files.copy(sourcePath, copyFileAbsolutePath, StandardCopyOption.REPLACE_EXISTING);
            } else {
                try {
                    Files.copy(sourcePath, copyFileAbsolutePath);
                } catch (FileAlreadyExistsException e) {
                    System.out.println("file " + copyFileAbsolutePath + " already exists");
                    return;
                }
            }
        }
    }

    public static void delete(Path target) throws IOException {
        if (Files.isDirectory(target)) {
            DirectoryStream<Path> targetDirectories = Files.newDirectoryStream(target);
            for (Path path : targetDirectories) {
                delete(path);
            }
            try {
                Files.delete(target);
                return;
            } catch (IOException e){
                System.out.println("Can't delete folder: " + target);
            }

        } else {
            try {
                Files.delete(target);
                return;
            } catch (IOException e) {
                System.out.println("Can't delete file " + target);
            }
        }


//        Files.walk(target, FileVisitOption.FOLLOW_LINKS)
//                .sorted(Comparator.reverseOrder())
//                .forEach(file -> {
//                            try {
//                                Files.deleteIfExists(file);
//                                while (Files.exists(file)) {
//                                }
//                                while (Files.isDirectory(file)) {
//                                }
//                                System.out.println("File: " + file + " successfully deleted");
//                            } catch (DirectoryNotEmptyException e) {
//                                System.out.println("Directory " + file + " is not empty!");
//                            } catch (IOException e) {
//                                System.out.println("Can't delete file: " + file);
//                            }
//                        }
//                );
    }
}
