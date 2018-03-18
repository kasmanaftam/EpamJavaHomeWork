package consoleUtils;

import java.io.IOException;
import java.util.Scanner;

public class FileUtil {

    public static void main(String[] args) {
        System.out.println("Welcome to simple console file manager!");
        System.out.println("Type help to get command list.");

        Scanner console = new Scanner(System.in);
        while(true){
            System.out.println(FilesCommands.getCurrentDirectory() + "\\");
            String command = console.nextLine();
            try {
                FilesCommands.execute(command);
            } catch (IOException e) {
                System.err.println("Something goes wrong while execute: " + command);
            }
        }
    }
}
