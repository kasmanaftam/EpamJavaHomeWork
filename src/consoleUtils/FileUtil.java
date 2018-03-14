package consoleUtils;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class FileUtil {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        while(true){
            System.out.println(FilesCommands.getCurrentDirectory() + "\\");
            String command = console.nextLine();
            try {
                FilesCommands.execute(command);
            } catch (IOException e) {
                System.err.println("Something goes wrong!");
                e.printStackTrace();
            }
        }
    }
}
