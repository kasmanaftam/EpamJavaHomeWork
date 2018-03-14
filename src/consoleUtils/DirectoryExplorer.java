package consoleUtils;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.nio.file.Files.getLastModifiedTime;


public class DirectoryExplorer {
    public static void displayDir(Path directory) throws IOException {
        displayDir(directory, new FileNameComparator(), false);
    }

    public static void displayDir(Path directory, Comparator comparator, boolean reverseOrder) throws IOException {
        //check
        if (!Files.isDirectory(directory)) {
            System.out.println("Invalid directory");
            return;
        }
        DirectoryStream<Path> dirStream;
        try {
            dirStream = Files.newDirectoryStream(directory);
        } catch (Exception e) {
            System.out.println("Cannot access current folder");
            return;
        }

        List<Path> directoryFiles = new ArrayList<>();
        for (Path path : dirStream) {
            directoryFiles.add(path);
        }
        dirStream.close();
        //find largest name length
        Path longestNamePath = Collections.max(directoryFiles, (A, B) -> {
            int nameLengthA = A.getFileName().toString().length();
            int nameLengthB = B.getFileName().toString().length();
            return Integer.compare(nameLengthA, nameLengthB);
        });
        int longestNameLength = longestNamePath.getFileName().toString().length();
        if (longestNameLength < 10) longestNameLength = 10; //for correct header print
        int FILE_SIZE_POSITION_OFFSET = longestNameLength + 10;
        int FILE_TIME_POSITION_OFFSET = longestNameLength + 30;

        //sort collection
        Collections.sort(directoryFiles, comparator);
        if (reverseOrder) {
            Collections.reverse(directoryFiles);
        }
        //build header
        StringBuilder header = new StringBuilder();
        for(int i=0; i<longestNameLength+30;i++)  header.append(" ");
        header.setLength(longestNameLength + 30);

        header.insert(0, "file name");
        header.insert(FILE_SIZE_POSITION_OFFSET - 3, "file size");
        header.insert(FILE_TIME_POSITION_OFFSET, "last modify time");
        System.out.println(header + "\n");
        //display directory elements
        for (Path path : directoryFiles) {
            StringBuilder fileInfo = new StringBuilder();
            for(int i=0; i<longestNameLength+40;i++)  fileInfo.append(" ");
            FileTime lastModifyTime = Files.getLastModifiedTime(path);
            DateFormat df = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
            String fileName = path.getFileName().toString();
            String fileSize;
            if (Files.isDirectory(path))
                fileSize = "dir";
            else fileSize = String.valueOf(Files.size(path));
            String fileTime = df.format(lastModifyTime.toMillis());

            fileInfo.insert(0, fileName);
            fileInfo.insert(FILE_SIZE_POSITION_OFFSET, fileSize);
            fileInfo.insert(FILE_TIME_POSITION_OFFSET, fileTime);
            System.out.println(fileInfo.toString().trim());
        }
        System.out.println("");
    }

    public static long size(Path path) throws IOException {
        long size = 0;
        if (Files.isDirectory(path)) {
            DirectoryStream<Path> includedPaths = Files.newDirectoryStream(path);
            for (Path includePath : includedPaths) {
                if (Files.isDirectory(includePath)) {
                    size += size(includePath);
                } else {
                    try {
                        size += Files.size(includePath);
                    } catch (IOException e) {
                        throw (new IOException("Can't access file" + includePath));
                    }
                }
            }
            includedPaths.close();
        } else {
            try {
                size = Files.size(path);
            } catch (IOException e) {
                throw (new IOException("Can't access file" + path));
            }
        }
        return size;
    }
}

class FileNameComparator implements Comparator<Path> {
    @Override
    public int compare(Path A, Path B) {
        return A.toString().compareToIgnoreCase(B.toString());
    }
}

class FileSizeComparator implements Comparator<Path> {
    @Override
    public int compare(Path A, Path B) {
        try {
            return (int) (Files.size(A) - Files.size(B));
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}

class FileDateComparator implements Comparator<Path> {
    @Override
    public int compare(Path A, Path B) {
        try {
            FileTime timeA = getLastModifiedTime(A);
            FileTime timeB = getLastModifiedTime(B);
            return timeA.compareTo(timeB);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}