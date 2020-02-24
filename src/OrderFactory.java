/**
 * Part of Factory Pattern.
 * Singleton - type class. It creates the orders.
 * @author taner
 */
public class OrderFactory {
    
    private static OrderFactory ordFact = null;
    private OrderFactory() {}
    
    /**
     * Gets the unique instance of this class.
     * @return unique OrderFactory object.
     */
    public static OrderFactory getInstance() {
        if (ordFact == null) {
            ordFact = new OrderFactory();
        }
        return ordFact;
    }
    
    /**
     * Reads the command and creates the correspondent Order object.
     * @param command = the command read from input.
     * @return an Order object.
     */
    public Order createOrder (String command) {
        Order newOrder = null;
        String[] arguments = command.split(" ");
        
        switch (arguments[0]) {
            case "ls":
                newOrder = new LS(arguments);
                break;
            case "pwd":
                newOrder = new PWD();
                break;
            case "cd":
                newOrder = new CD(arguments[1]);
                break;
            case "cp":
                newOrder = new CP(arguments[1], arguments[2]);
                break;
            case "mv":
                newOrder = new MV(arguments[1], arguments[2]);
                break;
            case "rm":
                newOrder = new RM(arguments[1]);
                break;
            case "touch":
                newOrder = new TOUCH(arguments[1]);
                break;
            case "mkdir":
                newOrder = new MKDIR(arguments[1]);
                break;
            default:
                break;
        }
        
        return newOrder;
    }
}
