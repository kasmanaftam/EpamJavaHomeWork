package consoleUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestFileUtils {
    public static void main(String[] args) throws IOException {
//        DirectoryExplorer.displayDir(Paths.get("H:\\games").toAbsolutePath());
        final Path source = Paths.get("H:\\test\\source");
        final Path destination = Paths.get("H:\\test\\dest");
        final Path destination2 = Paths.get("H:\\test\\destination\\source");
        utilCommands.copy(source, destination, false);
//        DirectoryExplorer.displayDir(destination2);
        utilCommands.delete(destination);
//        DirectoryExplorer.displayDir(destination2);
    }
}
