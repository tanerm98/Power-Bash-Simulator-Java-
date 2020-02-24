import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * This is the main class of a program which simulates the Linux CLI.
 * @author taner
 */
public class Main {
    
    public static GenericFile root = null;
    public static GenericFile currentDir = null;
    
    public static String[] treePath;

    /**
     * This is the main method. It outputs the results of the commands given
     * in the input file.
     * @param args the command line arguments = the names of I/O files
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
        root = new Folder("root", null);
        currentDir = root;
        
        OrderTaker com = OrderTaker.getInstance(args[0], args[1], args[2]);
        com.readOrders();
        
    }
    
    /**
     * Searches the child file or folder of a specific parent folder.
     * @param toSearch = the parent folder.
     * @param toFind = the name of the child to be searched.
     * @return the child. NULL if there is no match for the given name.
     */
    public static GenericFile findChild(Folder toSearch, String toFind) {
        
        boolean b;
        
        GenericFile searched;

        Iterator<GenericFile> iter = toSearch.childFiles.iterator();
        while (iter.hasNext()) {
            searched = iter.next();
            if (searched.getName().equals(toFind)) {
                return searched;
            }
        }
        
        return null;
    }
    
    /**
     * Finds all the files that the given path goes to. If the path contains "*"
     *  there may be multiple solutions. Otherwise, there is only one or none.
     * @param path = the path for which the search is made.
     * @return an ArrayList with all the files found.
     */
    public static ArrayList<GenericFile> getAllPaths(String path) {
        
        ArrayList<GenericFile> allPaths = new ArrayList<>();
        
        GenericFile traveler;
        
        path = path.replace("*", ".*");
        treePath = path.split("/");
        int start = 1;
        
        switch (path) {
            case "/":
                allPaths.add(Main.root);
                return allPaths;
            case ".":
                allPaths.add(Main.currentDir);
                return allPaths;
            case "..":
                if (Main.currentDir == Main.root) {
                    return allPaths;
                }
                allPaths.add(Main.currentDir.getParent());
                return allPaths;
            default:
                break;
        }
        
        
        if (path.charAt(0) == '/') {
            traveler = (Folder)root;
        } else if ((path.charAt(0) == '.') && (path.charAt(1) == '/')) {  
            traveler = (Folder)currentDir;
        } else if ((path.charAt(0) == '.') && (path.charAt(1) == '.')) {
            if (Main.currentDir == Main.root) {
                return allPaths;
            }
            traveler = (Folder)currentDir.getParent();
        } else {
            traveler = Main.currentDir;
            start = 0;
        }
        
        findAllChildren(start, traveler, allPaths);
        
        return allPaths;
    }
    
    /**
     * Recursive method which populates an ArrayList with all the files to which 
     * the path (global variable) goes to, starting from a parent folder.
     * @param i = the current position in the path.
     * @param peak = the parent folder from which the search is started.
     * @param allPaths = the ArrayList which stores all files found.
     */
    public static void findAllChildren(int i, GenericFile peak, ArrayList<GenericFile> allPaths) {
        
        Boolean b;
        
        if (i == treePath.length) {
            allPaths.add(peak);
            return;
        }
        
        if (peak.getType().equals("File")) {
            return;
        }
        
        if (treePath[i].equals(".")) {
            findAllChildren(i + 1, peak, allPaths);
            return;
        }
        if (treePath[i].equals("..")) {
            if (peak.equals(Main.root)) {return; }
            findAllChildren(i + 1, peak.getParent(), allPaths);
            return;
        }
        
        for (int j = 0; j < ((Folder)peak).childFiles.size(); j++) {
            b = Pattern.matches(treePath[i], ((Folder)peak).childFiles.get(j).getName());
            if (b == true) {
                findAllChildren(i + 1, ((Folder)peak).childFiles.get(j), allPaths);
            }
        }
    }
}
