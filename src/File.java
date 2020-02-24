/**
 * Implements a file.
 * @author taner
 */
public class File implements Cloneable, GenericFile{
    
    private String name;
    private GenericFile parent;

    /**
     * Sets the name and the parent folder of the file.
     * @param name = the new name.
     * @param parent = the nparent folder.
     */
    public File(String name, GenericFile parent) {
        this.name = name;
        this.parent = parent;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getType() {
        return "File";
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
