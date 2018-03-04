package consoleUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

// recursive zipper must be modified. Currently it can zip only folders

public class Zipper {
    public static void zipFiles(Path inputFile, Path outputFile) throws IOException {
        Path outputFolder = outputFile.getParent();
        //check output directory existing
        if(!Files.exists(outputFolder)){
            try{
                Files.createDirectories(outputFolder);
            } catch(Exception e){
                System.out.println("Can't create target directory.");
                return;
            }
        }
        try {
            Files.createFile(outputFile);
        }
        catch (FileAlreadyExistsException e){
            System.out.println("File already exist. Try another name.");
            return;
        }
        catch (IOException e) {
            System.out.println("Can't create file!");
            return;
        }

        OutputStream targetStream = Files.newOutputStream(outputFile);
        ZipOutputStream compressedStream = new ZipOutputStream(targetStream);
        int rootIndex = inputFile.getNameCount()-1;
        zipRecursive(inputFile, rootIndex, compressedStream);
        compressedStream.close();
    }
    private static void zipRecursive(Path file, int rootIndex, ZipOutputStream outputStream) throws IOException {
        if(!Files.exists(file)){
            System.out.println("Can't access file: " + file);
            return;
        }
        String filename = file.subpath(rootIndex, file.getNameCount()).toString();
        if(Files.isDirectory(file)) {
            DirectoryStream<Path> zipFiles = Files.newDirectoryStream(file);
            System.out.println("Added directory: " + filename);
            int zippedFiles=0;
            for(Path path : zipFiles){
                zipRecursive(path, rootIndex, outputStream);
                zippedFiles++;
            }
            if(zippedFiles==0){
                outputStream.putNextEntry(new ZipEntry(file.subpath(rootIndex, file.getNameCount()).toString()));
            }
        } else {
            addFileToZipStream(file, filename, outputStream);
            System.out.println("Added file " + filename);
        }
    }
    private static void addFileToZipStream(Path file, String filename, ZipOutputStream zipStream) throws IOException {
        ZipEntry zipEntry = new ZipEntry(filename);
        zipStream.putNextEntry(zipEntry);
        long fileSize = Files.size(file);
        if(fileSize==0){
            return;
        }
        InputStream fileStream = Files.newInputStream(file);
        byte[] buffer = new byte[10*1024*1024];
        int bufferLength;
        while((bufferLength = fileStream.read(buffer))>=0){
            zipStream.write(buffer, 0, bufferLength);
        }
        fileStream.close();
    }
    public static void unzipFile(Path zipFile, Path targetDirectory) throws IOException {
        ZipInputStream zipStream;
        try {
            zipStream = new ZipInputStream(Files.newInputStream(zipFile));
        } catch (IOException e) {
            System.out.println("Invalid zip file");
            return;
        }
        ZipEntry zipEntry = zipStream.getNextEntry();
        byte[] buffer = new byte[10*1024*1024];
        while((zipEntry)!=null){
            Path targetFile = Paths.get(targetDirectory.toString(), zipEntry.getName());
            Path parentPath = targetFile.getParent();
            if(!Files.isDirectory(parentPath)){
                Files.createDirectories(parentPath);
                System.out.println("Created directory: " + parentPath);
            }
            Files.createFile(targetFile);
            System.out.println("Created file: " + targetFile);
            if(zipStream.available()==0){
                continue;
            }
            OutputStream targetStream = Files.newOutputStream(targetFile);
            int bytesAvailable;
            while((bytesAvailable = zipStream.read(buffer))>=0){
                targetStream.write(buffer, 0, bytesAvailable);
            }
            targetStream.close();
            zipEntry = zipStream.getNextEntry();
        }
        zipStream.closeEntry();
        zipStream.close();
    }

}
