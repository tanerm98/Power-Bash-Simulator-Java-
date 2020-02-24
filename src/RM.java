import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Part of Command Pattern.
 * Implements the "rm" command.
 * @author taner
 */
public class RM implements Order{
    String path = null;
    
    /**
     * Sets the path of the file / folder to delete.
     * @param path = the path.
     */
    public RM(String path) {
        this.path = path;
    }
    
    @Override
    public void execute(FileWriter out, FileWriter err) throws IOException {
        
        ArrayList<GenericFile> allPaths;
        allPaths = Main.getAllPaths(this.path);
        
        if (allPaths.isEmpty()) {
            err.write("rm: cannot remove " + path + ": No such file or directory\n");
            return;
        }
        
        for (int i = 0; i < allPaths.size(); i++) {
            GenericFile traveler = Main.currentDir;
            int notPermitted = 0;
            while (traveler != Main.root) {
                if (traveler.equals(allPaths.get(i))) {
                    notPermitted = 1;
                    break;
                }
            traveler = traveler.getParent();
            }
            if (notPermitted == 1) {
                continue;
            }
            if (allPaths.get(i).equals(Main.root)) {
                continue;
            }
            GenericFile parent = allPaths.get(i).getParent();
            ((Folder)parent).removeFile(allPaths.get(i).getName());
        }
    }
}
