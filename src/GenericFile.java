/**
 * Sets the generic methods for both File and Folder objects.
 * @author taner
 */
public interface GenericFile {
    
    /**
     * Gets the name of the file / folder.
     * @return a string with the name.
     */
    public String getName();

    /**
     * Gets the type of the file / folder.
     * @return a string with the type ("File" / "Folder")
     */
    public String getType();

    /**
     * Gets the parrent of the file / folder. Null if it is the root folder.
     * @return a GenericFile.
     */
    public GenericFile getParent();

    /**
     * Clones the attributes of a file / folder. It doesn't clone the ArrayList 
     * that a folder contains, it only makes a new pointer to it.
     * @return the cloned file or folder.
     * @throws CloneNotSupportedException
     */
    public Object clone2()throws CloneNotSupportedException;

    /**
     * Sets the parent of a file / folder.
     * @param par = the parent folder.
     */
    public void setParent(GenericFile par);
}
