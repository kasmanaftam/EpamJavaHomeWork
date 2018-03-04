package musicStealer;

import com.mpatric.mp3agic.*;
import sun.misc.JavaIOAccess;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class id3analizer {
    public  static void main(String[] args) throws IOException {
//        final String cachePath = "C:\\Users\\kasmanaftam\\AppData\\Local\\Mozilla\\Firefox\\Profiles\\facpz2gi.default\\cache2\\entries";
//        final String destinationDirectory = "h:\\getMozillaCache";
        final String cachePath = "C:\\Users\\kasmanaftam\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Media Cache";
        final String destinationDirectory = "h:\\getCacheChrome";
        Path cache = Paths.get(cachePath);
        DirectoryStream<Path> cacheFiles = Files.newDirectoryStream(cache);
        int copiedFiles = 0;
        Track track = null;
        for (Path cacheFile : cacheFiles) {
            //System.out.println(cacheFile.toString()); //for debug purpose only
            //System.out.println("file size is: " + Files.size(cacheFile));
            try {
                Mp3File mp3file = new Mp3File(cacheFile); // create new mp3File
                if (mp3file.hasId3v2Tag()) {
                    ID3v2 tag = mp3file.getId3v2Tag();
                    track = new Track(tag.getArtist(), tag.getTitle());
                }
                if(track != null) {
                    track.addFragment(cacheFile);
                    if (mp3file.hasId3v1Tag() || Files.size(cacheFile)<1048576 || Files.size(cacheFile)>1048576) {
                        Path outputFile = track.createOutputFile(destinationDirectory);
                        track.copyFragmentsToFile(outputFile);
                        track = null;
                        copiedFiles++;
                        System.out.println("Created file: " + outputFile + " , size: " + Files.size(outputFile));
                        if(copiedFiles%10==0){
                            System.out.println("found " + copiedFiles + " files");
                        }
                    }
                }
            } catch (Exception e){
                continue;
            }

        }
        System.out.println("Totally " + copiedFiles + " mp3 files has been copied");
    }
}