package consoleUtils;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DirectoryExplorer {
    public static void displayDir(Path directory, Comparator comparator, boolean reverseOrder) throws IOException {
        if(!Files.isDirectory(directory)){
            System.out.println("Invalid directory");
            return;
        }
        DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory);
        List<Path>  directoryFiles = new ArrayList<>();
        for(Path path : dirStream){
            directoryFiles.add(path);
        }
        Collections.sort(directoryFiles, comparator);
        if(reverseOrder){
            Collections.reverse(directoryFiles);
        }
        for(Path path : directoryFiles){
            System.out.println(path.getName(path.getNameCount()-1) + "\t\t" + Files.size(path) + "\t\t" + Files.getLastModifiedTime(path));
        }
    }
    public static long size(Path path) throws IOException {
        long size=0;
        if(Files.isDirectory(path)){
            DirectoryStream<Path> includedPaths = Files.newDirectoryStream(path);
            for(Path includePath : includedPaths){
                if(Files.isDirectory(includePath)){
                    size+=size(includePath);
                }
                else{
                    try {
                        size+=Files.size(includePath);
                    }
                    catch(IOException e){
                        throw(new IOException("Can't access file" + includePath));
                    }
                }
            }
        }
        else{
            try {
                size = Files.size(path);
            }
            catch(IOException e){
                throw(new IOException("Can't access file" + path));
            }
        }
        return size;
    }
}
class FileNameComparator implements Comparator<Path>{
    @Override
    public int compare(Path A, Path B) {
        return A.toString().compareTo(B.toString());
    }
}
class FileSizeComparator implements Comparator<Path>{
    @Override
    public int compare(Path A, Path B) {
        try {
            return (int) (DirectoryExplorer.size(A)-DirectoryExplorer.size(B));
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
class FileDateComparator implements Comparator<Path>{
    @Override
    public int compare(Path A, Path B) {
        try {
            FileTime timeA = Files.getLastModifiedTime(A);
            FileTime timeB = Files.getLastModifiedTime(B);
            return timeA.compareTo(timeB);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}