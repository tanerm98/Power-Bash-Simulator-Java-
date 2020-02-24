import java.util.*;
import java.io.*;
/**
 * Part of Factory Pattern.
 * Singleton - type class.
 * Reads all the commands from input, transforms them in Order objects and 
 * executes them.
 * @author taner
 */
public class OrderTaker {
    
    Scanner in;
    FileWriter out, err;
    
    private static OrderTaker ordTake = null;
    private OrderTaker(String input, String output, String error) throws IOException {

        java.io.File inputFile = new java.io.File(input);
        this.in = new Scanner(inputFile);
        this.out = new FileWriter(output);
        this.err = new FileWriter(error);
    }
    
    /**
     * Creates an unique instance of this object.
     * @param in = the input file.
     * @param out = the output file.
     * @param err = the errors output file.
     * @return the unique OrderTaker Object.
     * @throws IOException
     */
    public static OrderTaker getInstance(String in, String out, String err) throws IOException {
        if (ordTake == null) {
            ordTake = new OrderTaker(in, out, err);
        }
        return ordTake;
    }
    
    /**
     * Reads all commands from input and executes them.
     * Closes the output files.
     * @throws IOException
     */
    public void readOrders() throws IOException {
        OrderFactory ordFact = OrderFactory.getInstance();
        OrderPlacer ordPlc = OrderPlacer.getInstance();
        
        while (in.hasNextLine()) {
            String command = in.nextLine();
            Order newOrder = ordFact.createOrder(command);
            ordPlc.takeOrder(newOrder);
        }
        
        ordPlc.placeOrders(out, err);
        
       
        out.flush();
        out.close();
        
        err.flush();
        err.close();
    }
    
    
}
