import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Part of Command Pattern.
 * Singleton - type class.
 * Executes the orders.
 * @author taner
 */
public class OrderPlacer {
    
    private List<Order> commandList; 
    private static OrderPlacer ordPlace = null;
    
    private OrderPlacer() {
        this.commandList = new ArrayList<>();
    }
    
    /**
     * Creates an unique instance of this object.
     * @return the unique OrderPlacer object.
     */
    public static OrderPlacer getInstance() {
        if (ordPlace == null) {
            ordPlace = new OrderPlacer();
        }
        return ordPlace;
    }

    /**
     * Adds a new order to the queue.
     * @param order = the order to add.
     */
    public void takeOrder(Order order){
        commandList.add(order);		
    }

    /**
     * Takes all the commands from the queue and executes them.
     * @param out = the output file.
     * @param err = the errors output file.
     * @throws IOException
     */
    public void placeOrders(FileWriter out, FileWriter err) throws IOException{
        
        if (commandList.isEmpty()) {
            return;
        }
        
        for (int i = 0; i < commandList.size(); i++) {
            Order order = commandList.get(i);
            out.write((i+1) + "\n");
            err.write((i+1) + "\n");
            order.execute(out, err);
        }
        commandList.clear();
    }

}
