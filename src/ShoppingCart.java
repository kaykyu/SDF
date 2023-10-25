import java.io.Console;
import java.util.LinkedList;

public class ShoppingCart {

    public static Console cons = System.console();
    public static LinkedList<String> list = new LinkedList<>();

    public static void list(LinkedList<String> cart) {
         if (cart.size() == 0) {
            System.out.println("Your cart is empty");
        } else {
            for (int i = 0; i < cart.size(); i++) {
            System.out.println((i+1) + ". " + cart.get(i));
            } 
        }
    }       

    public static void add(String input) {
        String trim = input.trim();
        String allItems = trim.substring(4);
        String[] items = allItems.split(",");
        for (String item : items) {
            String trimmedItem = item.trim();
            if (list.contains(trimmedItem)) {
                System.out.println("You have " + trimmedItem + " in your cart");
            } else {
                list.add(trimmedItem);
                System.out.println(trimmedItem + " added to cart");
            }
        }
        }

    public static void delete(String input){
        String trim = input.trim();
        String num = trim.substring(7);
        int index = Integer.parseInt(num);
        if (list.size() < index) {
            System.out.println("Incorrect item index");
        } else {
            String item = list.get(index-1);
            list.remove(index-1);
            System.out.println(item + " removed from cart");
        }
    }

    public static void main (String[] args) {
        
        String input = "";
        System.out.println("""
            Welcome to your shopping cart.
            Type out the following commands to:
            list - list out items in cart.
            add - add items to cart.
            delete - remove items from cart.
            quit - exit shopping cart""");
        input = cons.readLine("> ");
        
        while (!(input.equals("quit"))) {

            if (input.equals("list")) {
                list(list);
                input = cons.readLine("> ");
            } else if (input.startsWith("add")) {
                add(input);
                input = cons.readLine("> ");
            } else if (input.startsWith("delete")) {
                delete(input);
                input = cons.readLine("> ");
            }          
        }
        System.out.println("Thank you for your patronage.");
    }
}
