import java.io.*;
import java.util.*;

/**
 * Part of Command Pattern.
 * Implements the "pwd" command.
 * @author taner
 */
public class PWD implements Order{
    
    @Override
    public void execute(FileWriter out, FileWriter err) throws IOException {
        ArrayList<String> parents = new ArrayList<>();
        
        GenericFile traveler = Main.currentDir;
        
        while (!traveler.getName().equals("root")) {
            parents.add(0, traveler.getName());
            traveler = traveler.getParent();
        }
        
        out.write("/");
        for (int i = 0; i < parents.size(); i++) {
            out.write(parents.get(i));
            if (i < parents.size() - 1) {
                out.write("/");
            }
        }
        out.write("\n");
    }
    
}
