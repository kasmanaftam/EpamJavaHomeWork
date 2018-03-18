package consoleUtils;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilesCommands {

    private static Path currentDirectory = Paths.get("./").toAbsolutePath().normalize();

    static void execute(String commandRaw) throws IOException {
        final String WORD_OR_WORDS_IN_QUOTES_REGEX = "([^\"]\\S*|\".+?\")\\s*";
        List<String> commandSplited = new ArrayList<>();
        Matcher m = Pattern.compile(WORD_OR_WORDS_IN_QUOTES_REGEX).matcher(commandRaw);
        while (m.find()) {
            commandSplited.add(m.group(1));
        }
        int argNum = commandSplited.size();
        if (argNum == 0) {
            System.out.println("Invalid command");
            return;
        }
        String command = commandSplited.get(0);
        commandSplited.remove(0);
        String[] args = commandSplited.toArray(new String[commandSplited.size()]);
        switch (command) {
            case "help":
            case "-h":
                callHelp(args);
                break;
            case "copy":
                copy(args);
                break;
            case "delete":
                delete(args);
                break;
            case "move":
                move(args);
                break;
            case "pack":
                pack(args);
                break;
            case "unpack":
                unpack(args);
                break;
            case "cd":
                setCurrentDirectory(args);
                break;
            case "dir":
                dir(args);
                break;
            case "quit":
            case "-q":
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                callHelp(args);
        }

    }

    static Path getCurrentDirectory() {
        return currentDirectory;
    }

    public static void setCurrentDirectory(Path currentDirectory) {
        if (Files.isDirectory(currentDirectory)) {
            FilesCommands.currentDirectory = currentDirectory;
        }
    }

    private static void setCurrentDirectory(String[] args) {

        if (args.length == 0) {
            System.out.println("Invalid target folder");
            return;
        }

        Path targetPath = currentDirectory.resolve(args[0]);
        if (Files.isDirectory(targetPath)) {
            FilesCommands.currentDirectory = targetPath.toAbsolutePath().normalize();
        } else {
            System.out.println("Invalid directory");
        }
    }

    private static void dir(String[] args) throws IOException {
        Comparator<Path> fileComparator = new FileNameComparator();
        boolean reverseSortOrder = false;

        if (args.length >= 2 && args[0].matches("-r?s")) {
            switch (args[1].toLowerCase()) {
                case "name":
                    break;
                case "date":
                    fileComparator = new FileDateComparator();
                    break;
                case "size":
                    fileComparator = new FileSizeComparator();
                    break;
                default:
                    System.out.println("Invalid sort argument, you can use only: name, date, size");
                    return;
            }
            if (args[0].equals("-rs")) reverseSortOrder = true;
        }
        DirectoryExplorer.displayDir(currentDirectory, fileComparator, reverseSortOrder);
    }

    private static void move(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Invalid move arguments!");
            return;
        }
        Path source = currentDirectory.resolve(Paths.get(args[0]));
        if (Files.notExists(source)) {
            System.out.println("Invalid move source path!");
            return;
        }
        Path destination = currentDirectory.resolve(Paths.get(args[1]));
        boolean replaceExisting = false;
        if (args.length > 2 && args[2].equals("-r")) {
            replaceExisting = true;
        }
        move(source, destination, replaceExisting);
    }

    private static void move(Path sourcePath, Path destinationPath, boolean replaceExisting) throws IOException {
        if (!Files.isDirectory(destinationPath)) {
            try {
                Files.createDirectories(destinationPath);
            } catch (IOException e) {
                System.out.println("Can't create target directory");
                return;
            }
        }
        if (sourcePath.getRoot().equals(destinationPath.getRoot())) {
            try {
                if (replaceExisting) {
                    Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    Files.move(sourcePath, destinationPath);
                }
            } catch (FileAlreadyExistsException e) {
                System.out.println("Can't move file " + sourcePath +
                        ". File " + destinationPath + " already exists");
            } catch (DirectoryNotEmptyException e) {
            } catch (IOException e) {
                System.out.println("Can't move file " + sourcePath);
            }

        } else {
            copy(sourcePath, destinationPath, replaceExisting);
            delete(sourcePath);
        }

    }

    private static void copy(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Invalid copy arguments!");
            return;
        }
        Path source = currentDirectory.resolve(args[0]);
        if (Files.notExists(source)) {
            System.out.println("Invalid copy source path!");
            return;
        }
        Path destination = currentDirectory.resolve(args[1]);
        boolean replaceExisting = false;
        if (args.length > 2 && args[2].equals("-r")) {
            replaceExisting = true;
        }
        copy(source, destination, replaceExisting);
    }

    private static void copy(Path sourcePath, Path destinationPath, boolean replaceExisting) throws IOException {
        if (!Files.isDirectory(destinationPath)) {
            try {
                Files.createDirectories(destinationPath);
            } catch (IOException e) {
                System.out.println("Can't create target directory");
                return;
            }
        }

        Iterator<Path> pathsToCopy = Files
                .walk(sourcePath, FileVisitOption.FOLLOW_LINKS)
                .iterator();

        while (pathsToCopy.hasNext()) {
            Path pathToCopy = pathsToCopy.next();
            Path relativePath = sourcePath.getParent().relativize(pathToCopy);
            Path targetPath = destinationPath.resolve(relativePath);

            try {
                if (replaceExisting) {
                    Files.copy(pathToCopy, targetPath, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    Files.copy(pathToCopy, targetPath);
                }
            } catch (FileAlreadyExistsException e) {
                System.out.println("Can't copy file " + pathToCopy +
                        ". File " + targetPath + " already exists");
            } catch (DirectoryNotEmptyException e) {
                continue;
            } catch (IOException e) {
                System.out.println("Can't copy file " + pathToCopy);
                break;
            }
        }
    }

    private static void delete(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Invalid delete arguments!");
            return;
        }
        Path deleteTarget = currentDirectory.resolve(args[0]);
        if (Files.exists(deleteTarget)) {
            delete(deleteTarget);
        }
    }

    private static void delete(Path target) throws IOException {
        Iterator<Path> fileIterator = Files.walk(target, FileVisitOption.FOLLOW_LINKS)
                .sorted(Comparator.reverseOrder())
                .iterator();
        while (fileIterator.hasNext()) {
            Path pathToDelete = fileIterator.next();
            try {
                Files.delete(pathToDelete);
            } catch (IOException e) {
                System.out.println("Can't delete path " + pathToDelete);
                break;
            }
        }
    }

    private static void pack(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Invalid command arguments!");
            return;
        }
        Path source = currentDirectory.resolve(Paths.get(args[0]));
        if (Files.notExists(source)) {
            System.out.println("Invalid target file");
            return;
        }
        Path destination = Paths.get(currentDirectory.toString(), source.getFileName().toString() + ".zip");
        if (args.length > 1) {
            destination = currentDirectory.resolve(Paths.get(args[1]));
        }
        Zipper.zipFiles(source, destination);
    }

    private static void unpack(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Invalid command arguments!");
            return;
        }
        Path source = currentDirectory.resolve(Paths.get(args[0]));
        if (Files.notExists(source)) {
            System.out.println("Invalid target file");
            return;
        }
        Path destination = currentDirectory;
        if (args.length > 1) {
            destination = currentDirectory.resolve(Paths.get(args[1]));
        }
        Zipper.unzipFile(source, destination);
    }

    private static void callHelp(String[] args) {
        if (args.length == 0) {
            System.out.println("Simple console file manager");
            System.out.println("Works with the following commands: dir, cd, copy , move, delete, pack, unpack");
            System.out.println("use help <command> for additional info");
            System.out.println("quit : quit the program");
            return;
        }
        switch (args[0]) {
            case "dir":
                System.out.println("dir [-s|-rs <comp>] : Display current directory elements. " +
                        "Can sort files by following comparators: name, size, date. -s - sort, -rs - reverse sort");
                break;
            case "cd":
                System.out.println("cd <path> : change current directory");
                break;
            case "copy":
                System.out.println("copy <source_path> <destination path> [-r]:" +
                        " copy selected file or folder to selected directory. Key -r for replace");
                break;
            case "move":
                System.out.println("move <source_path> <destination path> [-r]:" +
                        " move selected file or folder to selected directory. Key -r for replace");
                break;
            case "delete":
                System.out.println("delete <path> : delete selected file or directory");
                break;
            case "pack":
                System.out.println("pack <target_file> [destination_path] " +
                        ": pack selected file or directory to zip archive");
                break;
            case "unpack":
                System.out.println("unpack <target_file> [destination_path]  : unpack selected zip archive");
                break;
            default:
                System.out.println("Invalid command name");
        }
        System.out.println();
    }
}
