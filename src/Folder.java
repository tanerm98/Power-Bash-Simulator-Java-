import java.util.*;

/**
 * Implements a folder.
 * @author taner
 */
public class Folder implements Cloneable, GenericFile {
    
    private String name;
    private GenericFile parent;
    public List<GenericFile> childFiles;

    /**
     * Sets the name and the parent folder of the folder.
     * @param name = the new name.
     * @param parent = the parent folder.
     */
    public Folder(String name, GenericFile parent) {
        this.name = name;
        this.parent = parent;
        this.childFiles = new LinkedList<>();
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    /**
     * Adds a new file to the folder and sorts the files alphabeticaly.
     * @param newFile = the file to add.
     */
    public void addFile(GenericFile newFile) {
        
        childFiles.add(newFile);
        
        Collections.sort(childFiles, (GenericFile f1, GenericFile f2) 
            -> f1.getName().compareTo(f2.getName()));

    }
    
    /**
     * Removes a file from the folder.
     * @param name = the name of the file to remove.
     */
    public void removeFile(String name) {
        
        GenericFile toRemove;
        
        Iterator<GenericFile> iter = childFiles.iterator();
        while (iter.hasNext()) {
            toRemove = iter.next();
            if (toRemove.getName().equals(name)) {
                iter.remove();
                return;
            }
        }
    }
    
    @Override
    public String getType() {
        return "Folder";
    }
    
    @Override
    public GenericFile getParent() {
        return parent;
    }
    
    @Override
    public void setParent(GenericFile newPar) {
        this.parent = newPar;
    }
    
    @Override
    public Object clone()throws CloneNotSupportedException{  
        return super.clone();  
    }
    
    @Override
    public Object clone2()throws CloneNotSupportedException{
        return clone();
    }
}
