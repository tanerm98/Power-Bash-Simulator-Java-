import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Part of Command Pattern.
 * Implements the "cd" command.
 * @author taner
 */
public class CD implements Order{

    String path = null;
    
    /**
     * Sets the path to which the "cd" command is made.
     * @param path = the requested path for "cd".
     */
    public CD(String path) {
        this.path = path;
    }
    
    @Override
    public void execute(FileWriter out, FileWriter err) throws IOException {
        ArrayList<GenericFile> allPaths;
        allPaths = Main.getAllPaths(path);
        
        GenericFile newCurDir;
        
        if (allPaths.isEmpty()) {
            err.write("cd: " + path + ": No such directory\n");
            return;
        }
        
        for (int i = 0; i < allPaths.size(); i++) {
            newCurDir = allPaths.get(i);
            Main.currentDir = newCurDir;
        }
    }
    
}
