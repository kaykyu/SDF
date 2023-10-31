import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Scanner;

public class ShoppingCartDB extends ShoppingCart {

    private String user;
    private LinkedList<String> cart = new LinkedList<>();    
    private File userDB;
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LinkedList<String> getCart() {
        return cart;
    }

    public ShoppingCartDB(String name) {
        this.user = name;
        this.cart = new LinkedList<String>();
        this.userDB = new File (getfUser(), String.format("%s.db.txt", user));
    }

    public void createFile() {
        try {
            File db = userDB;
            if (db.createNewFile()) {
                System.out.println(String.format("%s, your cart is empty", user));
            } else {
                try {
                    Scanner reader = new Scanner(db);
                    while (reader.hasNextLine()) {
                        String data = reader.nextLine();
                        cart.add(data);
                    }
                    reader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("User cart cannot be loaded!");
                }
                if (cart.size() > 0 ) {
                    System.out.println(String.format("%s, your cart contains the following items", user));
                    list(cart);
                } else {
                    System.out.println(String.format("%s, your cart is empty", user));
                }
            }
        } catch (IOException e) {
            System.out.println("User cannot be created!");
        }
    }

    public void save() {
        if (isUserLogin()) {
            try {
                File db = userDB;
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
}

