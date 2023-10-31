import java.io.Console;
import java.io.File;
import java.util.LinkedList;
import java.nio.file.Path;

public class ShoppingCart {

    static Path pNoUser = Path.of("db");
    static File fNoUser = new File (pNoUser.toString());
    static Path pUser = Path.of("cartdb");
    static File fUser = new File (pUser.toString());
    public static Console cons = System.console();
    public static LinkedList<String> list = new LinkedList<>();
    private static boolean userLogin = false;
    private static String currentUser;

    public static File getfNoUser() {
        return fNoUser;
    }
    public static File getfUser() {
        return fUser;
    }

    public static boolean isUserLogin() {
        return userLogin;
    }

    public static void list(LinkedList<String> cart) {
        if (cart.size() == 0) {
            System.out.println("Your cart is empty");
        } else {
            for (int i = 0; i < cart.size(); i++) {
            System.out.println(String.format("%d. %s", i+1, cart.get(i)));
            } 
        }
    }       

    public static void add(String input) {
        String allItems = input.substring(4);
        String[] items = allItems.split(",");
        for (String item : items) {
            String trimmedItem = item.trim();
            if (list.contains(trimmedItem)) {
                System.out.println(String.format("You have %s in your cart", trimmedItem));
            } else {
                list.add(trimmedItem);
                System.out.println(String.format("%s added to cart", trimmedItem));
            }
        }
    }

    public static void delete(String input){
        String num = input.substring(7);
        int index = Integer.parseInt(num);
        if (list.size() < index) {
            System.out.println("Incorrect item index");
        } else {
            String item = list.get(index-1);
            list.remove(index-1);
            System.out.println(String.format("%s removed from cart", item));
        }
    }

    public static void users() {
        String[] userList = fUser.list();
        if (userList.length > 0) {
            System.out.println("The following users are registered");         
            for (String user : userList) {
                String name = user.substring(0, user.indexOf("."));
                System.out.println(name);
            }
        } else {
            System.out.println("There are no registered users");
        }
    }
    

    public static void main(String[] args) {

        String input = "";

        if (args.length != 0 && args[0].equals("cartdb")) {
            while (!fUser.exists()) {
                fUser.mkdir();
            }
            System.out.println("""
                Welcome to your shopping cart.
                Type out the following commands to:
                login - login to your cart.
                list - list out items in cart.
                add - add items to cart.
                delete - remove items from cart.
                quit - exit shopping cart
                save - save your cart.
                users - list all users""");
            } else { 
            while (!fNoUser.exists()) {
                fNoUser.mkdir();
            }
            System.out.println("""
                Welcome to your shopping cart.
                Type out the following commands to:
                list - list out items in cart.
                add - add items to cart.
                delete - remove items from cart.
                quit - exit shopping cart""");
        }    
        
        
        input = cons.readLine("> ");
        
        while (!(input.equals("quit"))) {

            if (input.equals("list")) {
                list(list);
                input = cons.readLine("> ");
            } else if (input.startsWith("add")) {
                add(input.trim());
                input = cons.readLine("> ");
            } else if (input.startsWith("delete")) {
                delete(input.trim());
                input = cons.readLine("> ");
            } else if (input.startsWith("login")) {
                userLogin = true;
                list.clear();
                currentUser = input.substring(6);
                ShoppingCartDB user = new ShoppingCartDB(currentUser);
                user.createFile();
                list = user.getCart();
                input = cons.readLine("> ");
            } else if (input.startsWith("save")) {
                ShoppingCartDB user = new ShoppingCartDB(currentUser);
                user.save();
                input = cons.readLine("> ");
            } else if (input.startsWith("users")) {
                users();
                input = cons.readLine("> ");
            } else {
                System.out.println("Invalid command");
                input = cons.readLine("> ");
            }
        }
        System.out.println("Thank you for using Shopping Cart.");
    }
}
