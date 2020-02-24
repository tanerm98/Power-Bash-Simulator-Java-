import java.io.FileWriter;
import java.io.IOException;

/**
 * Part of Command Pattern.
 * Generic order.
 * @author taner
 */
public interface Order {
    
    /**
     * Executes the command.
     * @param out = the output file.
     * @param err = the errors output file.
     * @throws IOException
     */
    public void execute(FileWriter out, FileWriter err) throws IOException;
    
}
