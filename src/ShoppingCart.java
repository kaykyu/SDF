import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Scanner;
import java.nio.file.Path;

public class ShoppingCart {

    static Path pNoUser = Path.of("db");
    static File fNoUser = new File (pNoUser.toString());
    
    public static File getfNoUser() {
        return fNoUser;
    }

    static Path pUser = Path.of("cartdb");
    static File fUser = new File (pUser.toString());

    public static File getfUser() {
        return fUser;
    }

    public static Console cons = System.console();
    public static LinkedList<String> list = new LinkedList<>();
    public static boolean userLogin = false;
    public static String user;

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
                System.out.println("You have " + trimmedItem + " in your cart");
            } else {
                list.add(trimmedItem);
                System.out.println(trimmedItem + " added to cart");
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
            System.out.println(item + " removed from cart");
        }
    }

    public static void login(String input) {
        userLogin = true;
        list.clear();
        String name = input.substring(6);
        user = name;
        createFile(name);
    }

    public static void createFile(String name) {
        try {
            File db = new File (getfUser(), String.format("%s.db.txt", name));
            if (db.createNewFile()) {
                System.out.println(String.format("%s, your cart is empty", name));
            } else {
                list = load(name);
                System.out.println(String.format("%s, your cart contains the following items", name));
                list(list);
            }
        } catch (IOException e) {
            System.out.println("User cannot be created!");
        }
    }
    
    public static LinkedList<String> load(String name) {
        File db = new File (getfUser(), String.format("%s.db.txt", name));
        LinkedList<String> dbList = new LinkedList<>();
        try {
            Scanner reader = new Scanner(db);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                dbList.add(data);
            }
        reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("User cart cannot be loaded!");
        } return dbList;
    }

    public static void save() {
        if (userLogin) {
            try {
                File db = new File (getfUser(), String.format("%s.db.txt", user));
                PrintStream stdout = System.out;
                PrintStream stream = new PrintStream(db);
                System.out.println("Your cart has been saved");
                System.setOut(stream);
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(list.get(i));
                }                
                System.setOut(stdout);
            } catch (FileNotFoundException e) {
                System.out.println("User cart not found!");
            }
        } else {
            System.out.println("Please login first");
        }
    }

    public static void users() {
        String[] userList = fUser.list();
        System.out.println("The following users are registered");         
        for (String user : userList) {
            String name = user.substring(0, user.indexOf("."));
            System.out.println(name);
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
                login(input.trim());
                input = cons.readLine("> ");
            } else if (input.startsWith("save")) {
                save();
                input = cons.readLine("> ");
            } else if (input.startsWith("users")) {
                users();
                input= cons.readLine("> ");
            }
        }
        System.out.println("Thank you for using Shopping Cart.");
    }
}
