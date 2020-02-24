import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Part of Command Pattern.
 * Implements the "touch" command.
 * @author taner
 */
public class TOUCH implements Order{
    String path = null;
    
    /**
     * Sets the path of the new directory.
     * @param path
     */
    public TOUCH(String path) {
        this.path = path;
    }
    
    @Override
    public void execute(FileWriter out, FileWriter err) throws IOException {
        ArrayList<GenericFile> allPaths;
        GenericFile toAdd;
        
        String[] steps = path.split("/");
        String newPath;
        
        int limit;
        if (path.contains("/")) {
            limit = path.lastIndexOf("/");
            if (limit == 0) {
                newPath = "/";
            } else {
                newPath = path.substring(0, limit);
            }
        } else {
            newPath = ".";
        }

        allPaths = Main.getAllPaths(newPath);
        
        for (int i = 0; i < allPaths.size(); i++) {
            if (allPaths.get(i).getType().equals("File")) {
                allPaths.remove(i);
                i--;
            }
        }
        
        if (allPaths.isEmpty()){
            err.write("touch: " + newPath + ": No such directory\n");
            return;
        }
        
        for (int i = 0; i < allPaths.size(); i++) {
            toAdd = allPaths.get(i);
            
            String absolutePath = LS.getAbsolutePath(toAdd);
            if (toAdd != Main.root) {
                absolutePath = absolutePath + "/" + steps[steps.length - 1];
            } else {
                absolutePath = absolutePath + steps[steps.length - 1];
            }
        
            GenericFile duplicate = Main.findChild((Folder)toAdd, steps[steps.length - 1]);
            if (duplicate != null) {
                err.write("touch: cannot create file " + absolutePath + ": Node exists\n");
                continue;
            }
        
            GenericFile newFile = new File(steps[steps.length - 1], toAdd);
            ((Folder)toAdd).addFile(newFile);
        }
    }
}
