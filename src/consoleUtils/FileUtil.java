package consoleUtils;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileUtil {
    public static void main(String[] args) {
        int argsLength = args.length;
        if(argsLength==0){
            callHelp();
        }

        String command = args[0];

        switch(command){
            case "help" : callHelp(); break;
            case "dir" : {
                break;
            }
            case "rename" : {

                break;
            }
            case "copy" : {

                break;
            }
            case "move" : {

                break;
            }
            case "pack" : {

                break;
            }
            case "unpack" : {

            }
            default : callHelp();
        }


    }
    static void displayDir(String directory, String sortComparator, String SortOrder){
        Path workingDirectory = Paths.get(directory);
        try {
            DirectoryStream<Path> dirStream = Files.newDirectoryStream(workingDirectory);
            for(Path path : dirStream){
                System.out.println(path.getName(path.getNameCount()-1));
            }
        }
        catch(IOException e){
            System.out.println("Can't access selected folder");
        }
    }
    static void callHelp(){
        System.out.println("Console file utils.");
        System.out.println("Works with the following keys:");
        System.out.println("dir [directory]: display all files in current directory");
        System.out.println("dir sort [name|date|size] [r] [directory] : display sorted list of files from current directory");
        System.out.println("cd : change current directory");
        System.out.println("copy [replace] [create_dir] source_path destination_path : copy selected file to selected directory");
        System.out.println("move [replace] [create_dir] source_path destination_path : move selected file to selected directory");
        System.out.println("unpack [create_dir] [destination_path] : unpack all files to selected directory");
        System.out.println("grub cache");
        System.out.println("Type \"q|quit\" to quit");
    }

}
