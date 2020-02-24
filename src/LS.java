import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.*;

/**
 * Part of Command Pattern.
 * Implements the "ls" command. It can also simulate the "ls -R" functionality, 
 * in combination with "grep" command.
 * @author taner
 */
public class LS implements Order{
    
    String[] arguments;
    
    int grepPos = -1;
    String regex = null;
    boolean b = true;

    /**
     * Sets the arguments of the command.
     * @param arguments
     */
    public LS(String[] arguments) {
        this.arguments = arguments;
    }
    
    @Override
    public void execute(FileWriter out, FileWriter err) throws IOException {
        
    ArrayList<GenericFile> allPaths = new ArrayList<>();
    
    GenericFile toLs;
    int R = 0;
    String path = null;
    
    for (int i = 0; i < arguments.length; i++) {
        if (arguments[i].equals("grep")) {
            grepPos = i;
            break;
        }
    }
    
    String[] arguments2;
    if (grepPos != -1) {
        regex = arguments[grepPos + 1].substring(1, arguments[grepPos + 1].length() - 1);
        
        arguments2 = new String[grepPos - 1];
        System.arraycopy(arguments, 0, arguments2, 0, (grepPos - 1));
    } else {
        arguments2 = arguments;
    }
    
    
        switch (arguments2.length) {
            case 1:
                allPaths.add(Main.currentDir);
                break;
            case 2:
                if (arguments2[1].equals("-R")) {
                    R = 1;
                    allPaths.add(Main.currentDir);
                } else {
                    path = arguments2[1];
                    allPaths = Main.getAllPaths(path);
                }   break;
            case 3:
                R = 1;
                if (arguments2[1].equals("-R")) {
                    path = arguments2[2];
                    allPaths = Main.getAllPaths(path);
                } else {
                    path = arguments2[1];
                    allPaths = Main.getAllPaths(path);
                }   break;
            default:
                break;
        }
        
        for (int i = 0; i < allPaths.size(); i++) {
            if (allPaths.get(i).getType().equals("File")) {
                allPaths.remove(i);
                i--;
            }
        }
        
        if (allPaths.isEmpty()) {
            err.write("ls: " + path + ": No such directory\n");
            return;
        }
        
        for (int i = 0; i < allPaths.size(); i++) {
            toLs = allPaths.get(i);
        
            if (R == 0) {
                ls_simple(toLs, out, err);
            } else {
                ls_R(toLs, out, err);
            }
        }
    }
    
    /**
     * Prints the children of a given folder, non - recursively.
     * @param toLs = the folder to "ls".
     * @param out = the output file.
     * @param err = the error output file.
     * @throws IOException
     */
    public void ls_simple(GenericFile toLs, FileWriter out, FileWriter err) throws IOException {
        out.write(getAbsolutePath(toLs));
        out.write(":\n");
        
        int prec = 0;
        for (int i = 0; i < ((Folder)toLs).childFiles.size(); i++) {
            if (grepPos != -1) {
                b = Pattern.matches(regex, ((Folder)toLs).childFiles.get(i).getName());
                if (b == true) {
                    if (prec == 1) {
                        out.write(" ");
                    }
                    prec = 1;
                    out.write(getAbsolutePath(((Folder)toLs).childFiles.get(i)));
                }
            } else {
                out.write(getAbsolutePath(((Folder)toLs).childFiles.get(i)));
                if (i < ((Folder)toLs).childFiles.size() - 1) {
                    out.write(" ");
                }
            }
        }
        out.write("\n\n");
    }
    
    /**
     * Prints the children of a given folder, recursively.
     * @param toLs = the folder to "ls -R".
     * @param out = the output file.
     * @param err = the error output file.
     * @throws IOException
     */
    public void ls_R(GenericFile toLs, FileWriter out, FileWriter err) throws IOException {
        out.write(getAbsolutePath(toLs));
        out.write(":\n");
        
        int prec = 0;
        for (int i = 0; i < ((Folder)toLs).childFiles.size(); i++) {
            if (grepPos != -1) {
                b = Pattern.matches(regex, ((Folder)toLs).childFiles.get(i).getName());
                if (b == true) {
                    if (prec == 1) {
                        out.write(" ");
                    }
                    prec = 1;
                    out.write(getAbsolutePath(((Folder)toLs).childFiles.get(i)));
                }
            } else {
                out.write(getAbsolutePath(((Folder)toLs).childFiles.get(i)));
                if (i < ((Folder)toLs).childFiles.size() - 1) {
                    out.write(" ");
                }
            }
        }
        out.write("\n\n");
        
        for (int i = 0; i < ((Folder)toLs).childFiles.size(); i++) {
            if (((Folder)toLs).childFiles.get(i).getType().equals("Folder")) {
                ls_R(((Folder)toLs).childFiles.get(i), out, err);
            }
        }
    }
    
    /**
     * Gets the absolute path a given file / folder.
     * @param child = the file / folder for which the absolute path is requested.
     * @return a String with the absolute path. 
     * @throws IOException
     */
    public static String getAbsolutePath(GenericFile child) throws IOException {
        ArrayList<String> parents = new ArrayList<>();
        String absolutePath = "/";
        
        GenericFile traveler = child;
        
        while (!traveler.equals(Main.root)) {
            parents.add(0, traveler.getName());
            traveler = traveler.getParent();
        }
        
        for (int i = 0; i < parents.size(); i++) {
            
            absolutePath = absolutePath + parents.get(i);
            if (i < parents.size() - 1) {
                absolutePath = absolutePath + "/";
            }
            
        }
        
        return absolutePath;
    }
    
}
