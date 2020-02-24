import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Part of Command Pattern.
 * Implements the "cp" command.
 * @author taner
 */
public class CP implements Order{
    String source, destination;

    /**
     * Sets the source and destination file paths.
     * @param source = the source file path.
     * @param destination = the destination file path.
     */
    public CP(String source, String destination) {
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
            err.write("cp: cannot copy " + source + ": No such file or directory\n");
            return;
        }
        if (allPaths2.isEmpty()) {
            err.write("cp: cannot copy into " + destination + ": No such directory\n");
            return;
        }
        
        GenericFile dst = allPaths2.get(0);
        String[] way = source.split("/");
        GenericFile duplicate = Main.findChild((Folder)dst, way[way.length - 1]);
        if (duplicate != null) {
            err.write("cp: cannot copy " + source + ": Node exists at destination\n");
            return;
        }
        
        GenericFile src;
        try {
            
            src = copy(allPaths1.get(0), dst);
            src.setParent(dst);
            ((Folder)dst).addFile(src);
            
        } catch (CloneNotSupportedException ex) {
            System.out.println("Cloning not suported!");
        }
        
    }
    
    /**
     * Clones the given file or folder and sets the new parent of the cloned.
     * @param toCopy = the file / folder to clone.
     * @param parent = the new parent of the cloned file / folder.
     * @return the clone.
     * @throws CloneNotSupportedException
     */
    public GenericFile copy (GenericFile toCopy, GenericFile parent) throws CloneNotSupportedException {
        GenericFile cloned;
        
        if (toCopy.getType().equals("File")) {
            cloned = (GenericFile)(toCopy.clone2());
            cloned.setParent(parent);
            return cloned;
        }
        
        cloned = new Folder(toCopy.getName(), null);
        cloned.setParent(parent);
        for (int i = 0; i < ((Folder)toCopy).childFiles.size(); i++) {
            ((Folder)cloned).addFile(copy(((Folder)toCopy).childFiles.get(i), cloned));
        }
        return cloned;
    }
}
