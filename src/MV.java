import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Part of Command Pattern.
 * Implements the "mv" command.
 * @author taner
 */
public class MV implements Order{
    String source, destination;

    /**
     * Sets the source and destination paths of the files / folders.
     * @param source = the path of the source file / folder.
     * @param destination = the path of the destination folder.
     */
    public MV(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }
    
    @Override
    public void execute(FileWriter out, FileWriter err) throws IOException {
        
        ArrayList<GenericFile> allPaths1;
        allPaths1 = Main.getAllPaths(source);
        ArrayList<GenericFile> allPaths2;
        allPaths2 = Main.getAllPaths(destination);
        
        if (allPaths1.isEmpty()) {
            err.write("mv: cannot move " + source + ": No such file or directory\n");
            return;
        }
        if (allPaths2.isEmpty()) {
            err.write("mv: cannot move into " + destination + ": No such directory\n");
            return;
        }
        
        GenericFile dst = allPaths2.get(0);
        String[] way = source.split("/");
        GenericFile duplicate = Main.findChild((Folder)dst, way[way.length - 1]);
        if (duplicate != null) {
            err.write("mv: cannot move " + source + ": Node exists at destination\n");
            return;
        }
        
        GenericFile src;
        
        src = allPaths1.get(0);
        GenericFile parent = src.getParent();
        ((Folder)parent).removeFile(src.getName());
        
        src.setParent(dst);
        ((Folder)dst).addFile(src);       
    }
}
