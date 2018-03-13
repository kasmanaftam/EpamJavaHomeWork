package musicStealer;

import jdk.management.resource.internal.inst.FileInputStreamRMHooks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Track {
    String artist = "untitled";
    String song = "untitled";
    private List<Path> fragments = new ArrayList<>();
    Track(String artist, String song){
        this.artist = artist;
        this.song = song;
    }
    public void addFragment(Path path){
        this.fragments.add(path);
    }
    public String getSongName(){
        return artist + " - " + song+".mp3";
    }
    public String getSongName(int index){
        if(index>0) {
            return artist + "-" + song + "_" + index + ".mp3";
        } else {
            return artist + "-" + song +".mp3";
        }
    }
    public void copyFragmentsToFile(String destination) throws IOException {
        copyFragmentsToFile(Paths.get(destination));
    }
    public void copyFragmentsToFile(Path targetPath) throws IOException {
        OutputStream targetStream = Files.newOutputStream(targetPath);
        for(Path source : fragments){
            InputStream sourceStream = Files.newInputStream(source);
            byte[] buffer = new byte[sourceStream.available()];
            sourceStream.read(buffer);
            targetStream.write(buffer);
        }
    }
    public Path createOutputFile(String targetDirectory){
        return createOutputFile(Paths.get(targetDirectory));
    }
    public Path createOutputFile(Path targetDirectory){
        if(!Files.isDirectory(targetDirectory)){
            try {
                Files.createDirectories(targetDirectory);
            } catch (IOException e) {
                System.out.println("Can't create selected destination folder!");
                return null;
            }
        }
        int titleCount=0;
        Path targetFile = Paths.get(targetDirectory.toString(), this.getSongName(titleCount));
        while(Files.exists(targetFile)){
            targetFile = Paths.get(targetDirectory.toString(), this.getSongName(++titleCount));
        }
        try{
            Files.createFile(targetFile);
            return targetFile;
        } catch (FileAlreadyExistsException e){
            System.out.println("File " + this.getSongName() + " already exist");
            return null;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
