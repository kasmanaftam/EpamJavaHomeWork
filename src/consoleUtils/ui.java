package consoleUtils;

import java.util.Arrays;

public class ui {

    public static void uiHandler(String[] args){
        int l = args.length;
        String[] commandArgs;
        if(l>1){
            commandArgs = Arrays.copyOfRange(args, 1, l);
        }
        switch (args[0]){
            case "cd" :
        }
    }
}
