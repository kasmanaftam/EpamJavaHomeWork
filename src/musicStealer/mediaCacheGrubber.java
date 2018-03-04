package musicStealer;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class mediaCacheGrubber {
    public static void main(String[] args) {
        List<Path> cacheDirs = getCacheDirectories();
        grubCache(cacheDirs.get(1).toString(), "h:\\getCacheTest\\");
    }
    public static List<Path> getCacheDirectories(){

        String[] caches = new String[3];
        String homePath = System.getProperty("user.home");

        caches[0] = "AppData\\Local\\Google\\Chrome\\User Data\\Default\\Media Cache";
        caches[1] = "AppData\\Local\\Opera Software\\Opera Stable\\Media Cache";
        caches[2] = "AppData\\Local\\Mozilla\\Firefox\\Profiles\\facpz2gi.default\\cache2\\entries";
        List<Path> cachePaths = new ArrayList<>();
        for(String cache : caches){
            Path cachePath = Paths.get(homePath, cache).toAbsolutePath();
            if(Files.exists(cachePath)){
                cachePaths.add(cachePath);
            }
        }
        return cachePaths;
    }
    public static void grubCache(String cacheDirectory, String destinationDirectory){
        grubCache(Paths.get(cacheDirectory), Paths.get(destinationDirectory));
    }
    public static void grubCache(Path cacheDirectory, Path destinationDirectory){
        DirectoryStream<Path> cacheFiles = null;
        try {
            cacheFiles = Files.newDirectoryStream(cacheDirectory);
        } catch (IOException e) {
            System.out.println("Can't access following cache folder!");
        }
        int copiedFiles = 0;
        Track track = null;
        for (Path cacheFile : cacheFiles) {
            //System.out.println(cacheFile.toString());                         //for debug purpose only
            //System.out.println("file size is: " + Files.size(cacheFile));     //for debug purpose only
            try {
                Mp3File mp3file = new Mp3File(cacheFile);   // create new mp3File
                if (mp3file.hasId3v2Tag()) {                // check for first fragment for ID3v2
                    ID3v2 tag = mp3file.getId3v2Tag();
                    track = new Track(tag.getArtist(), tag.getTitle()); //create new track with data from tags
                }
                if(track != null) {                         // if begin of mp3 file was found
                    track.addFragment(cacheFile);           // add fragment to track

                    //check for last fragment
                    if (mp3file.hasId3v1Tag() || Files.size(cacheFile)<1048576 || Files.size(cacheFile)>1048576) {
                        Path outputFile = track.createOutputFile(destinationDirectory); //create output file
                        track.copyFragmentsToFile(outputFile);                          //copy fragments to file
                        track = null;                                                   //delete track
                        copiedFiles++;
                        System.out.println("Created file: " + outputFile + " , size: " + Files.size(outputFile));
                    }
                }
            } catch (Exception e){
                continue;
            }

        }
        System.out.println("Totally " + copiedFiles + " mp3 files has been found in cache folder" +
                           " and copied to destination folder");
    }
}
