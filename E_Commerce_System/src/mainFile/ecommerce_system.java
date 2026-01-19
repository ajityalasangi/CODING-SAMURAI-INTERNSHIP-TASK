package mainFile;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;


public class ecommerce_system {
	public static void main(String[] args) {
		ArrayList<User> users = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		int choice =0,id=1;
		
		while(choice != 3) {
			System.out.println("\nWelcome to Ecommerce Console App");
	        System.out.println("1. Create Account");
	        System.out.println("2. Login");
	        System.out.println("3. Exit");
			choice = sc.nextInt();
			sc.nextLine();
			switch(choice) {
			case 1 :  
				System.out.println("Enter your name : ");
				String name = sc.nextLine();
				
				System.out.println("Enter your email : ");
				String email = sc.nextLine();
				
				System.out.println("Enter your password :");
				String password = sc.nextLine();
				
				User newUser = new User(id++,name, password, email);
				UserFileHandler.saveUser(newUser);

				System.out.println("Registration successful!");
				break;
			
			case 2 :
				System.out.println("Enter your name: ");
				String username = sc.nextLine();
				System.out.println("Enter you password: ");
				String userpassword = sc.nextLine();
				User loggedUser = AuthUserService.login(username, userpassword);
				if(loggedUser != null ) {
					System.out.println("Login successfull");
					Cart cart = new Cart();
					int option =0;
					
					while(option!=4) {
						ProductService.displayProducts();
						System.out.println("\n1.Add to Cart ");
						System.out.println("2.View Cart");
						System.out.println("3.Checkout");
						System.out.println("4.Logout");
						option = sc.nextInt();
						
						switch(option) {
						case 1 : 
							System.out.println("Enter product ID : ");
							int productId = sc.nextInt();
							System.out.println("Enter quantity : ");
							int quantity = sc.nextInt();
							if(quantity <= 0) {
							    System.out.println("Invalid quantity");
							    break;
							}
							
							Product myProduct = ProductService.getProductById(productId);
							
							if(myProduct != null) {
								cart.addItem(new CartItem(myProduct.productId,myProduct.productName,myProduct.price,quantity));
							}
							else {
								System.out.println("Product not found");
							}
							break;
							
						case 2 :
							cart.viewCart();
							break;
							
						case 3 : 
							cart.checkout(loggedUser);
							break;
							
						case 4 : 
							System.out.println("Thank you ! ");
							System.out.println("Logout successful");
							break;
						}
					}
					
					

				}
				else {
					System.out.println("Login unsuccessful");
				}
				
               break;
			case 3 :
				System.out.println("Thank you for using this app");
				break;	
			default : 
				System.out.println("Invalid choice");
			}
			
			
		}
	}
}

class AuthUserService {
	static int idCounter = 1;

	private static final String FILE_NAME = "C:\\Users\\ajity\\eclipse-workspace1\\E_Commerce_System\\src\\mainFile\\users.txt";

    public static User login(String username, String password) {
        try (Scanner sc = new Scanner(new File(FILE_NAME))) {
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(",");
                

                if (data[1].equals(username) && data[2].equals(password)) {
                	return new User(
                            Integer.parseInt(data[0]),
                            data[1],
                            data[2],
                            data[3]
                        );
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading user data");
        }
        return null;
    }
	
}

class UserFileHandler{
	private static final String FILE_NAME = "C:\\Users\\ajity\\eclipse-workspace1\\E_Commerce_System\\src\\mainFile\\users.txt";
	
	public static void saveUser(User user) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))){
			bw.write(user.getId() + "," +
                    user.getUsername() + "," +
                    user.getPassword() + "," +
                    user.getEmail());
           bw.newLine();
		}
		catch (IOException e) {
            System.out.println("Error saving user data");
        }
	}
}

class Product {
	int productId;
	String productName;
	double price;
	int stockQuantity;
	
	Product(int pId,String pName, double pPrice, int pQuantity){
		this.productId = pId;
		this.productName = pName;
		this.price = pPrice;
		this.stockQuantity = pQuantity;
	}
}

class Order {
	int pid;
	int uid;
	String userName;
	String productName;
	int quantity;
	double price;
	
	Order(int productId,int userId,String userName,String productName,int quantity,double price){
		this.pid = productId;
		this.uid = userId;
		this.userName = userName;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
	}
	
}

class Checkout{
	public static void generateInvoice(Order order) {
		double total = order.price * order.quantity;

        System.out.println("\n========== INVOICE ==========");
        System.out.println("Customer Name : " + order.userName);
        System.out.println("User ID       : " + order.uid);
        System.out.println("--------------------------------");
        System.out.println("Product       : " + order.productName);
        System.out.println("Price         : ₹" + order.price);
        System.out.println("Quantity      : " + order.quantity);
        System.out.println("--------------------------------");
        System.out.println("Total Amount  : ₹" + total);
        System.out.println("================================");
	}
}

class OrderFileHandler{
	private static final String FILE_NAME = "C:\\Users\\ajity\\eclipse-workspace1\\E_Commerce_System\\src\\mainFile\\orders.txt";
	
	public static void saveOrder(Order order) {
		try( BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true)) ){
			bw.write(
					order.uid+","+
					order.userName + "," +
	                order.pid + "," +
	                order.productName + "," +
	                order.quantity + "," +
	                (order.price * order.quantity)
					);
			bw.newLine();
		}
		catch(Exception e) {
			System.out.println("Error saving order");
		}
	}
}



class ProductService{
	public static void displayProducts() {
		final String FILE_NAME = "C:\\Users\\ajity\\eclipse-workspace1\\E_Commerce_System\\src\\mainFile\\products.txt";
		 System.out.println("\nID  Name           Price   Stock");
	        System.out.println("--------------------------------");

	        try (Scanner sc = new Scanner(new File(FILE_NAME))) {
	            while (sc.hasNextLine()) {
	                String[] p = sc.nextLine().split(",");
	                System.out.printf("%-3s %-14s %-7s %-5s\n",
	                        p[0], p[1], p[2], p[3]);
	            }
	        } catch (Exception e) {
	            System.out.println("Error reading products");
	        }
		
	}
	
	public static Product getProductById(int productId) {
		final String FILE_NAME = "C:\\Users\\ajity\\eclipse-workspace1\\E_Commerce_System\\src\\mainFile\\products.txt";
		try (Scanner sc = new Scanner(new File(FILE_NAME))){
			while(sc.hasNextLine()) {
				String[] p = sc.nextLine().split(",");
                if (Integer.parseInt(p[0]) == productId) {
                    return new Product(
                            Integer.parseInt(p[0]),
                            p[1],
                            Double.parseDouble(p[2]),
                            Integer.parseInt(p[3])
                    );
                }
			}
		}
		catch(Exception e) {
			System.out.println("Error occurred");
		}
		return null;
	}
	
	public static void updateProducts(int productId, int quantity) {

	    final String FILE_NAME = "C:\\Users\\ajity\\eclipse-workspace1\\E_Commerce_System\\src\\mainFile\\products.txt";
	    ArrayList<String> updatedLines = new ArrayList<>();

	    try (Scanner sc = new Scanner(new File(FILE_NAME))) {

	        while (sc.hasNextLine()) {
	            String line = sc.nextLine();
	            String[] p = line.split(",");

	            int id = Integer.parseInt(p[0].trim());
	            int stock = Integer.parseInt(p[3].trim());

	            if (id == productId) {
	                stock -= quantity;   // reduce stock
	                p[3] = String.valueOf(stock);
	            }

	            updatedLines.add(String.join(",", p));
	        }

	    } catch (Exception e) {
	        System.out.println("Error reading products file");
	        return;
	    }

	    // write updated data back to file
	    try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
	        for (String l : updatedLines) {
	            bw.write(l);
	            bw.newLine();
	        }
	    } catch (IOException e) {
	        System.out.println("Error writing products file");
	    }
	}

}


class Cart{
	ArrayList<CartItem> items = new ArrayList<>();
	
	public void addItem(CartItem item) {
		items.add(item);
		System.out.println("Item added to cart!");
	}
	
	public void viewCart() {
		if(items.isEmpty()) {
			System.out.println("Cart is Empty");
			return;
		}
		double total =0;
		
		for(CartItem item : items) {
			double cost = item.price * item.quantity;
			System.out.println(item.productName + " x" + item.quantity + " = ₹" + cost);
            total += cost;
		}
		System.out.println("--------------------------------");
        System.out.println("Total Amount: ₹" + total);
	}
	
	public void checkout(User user) {

	    if (items.isEmpty()) {
	        System.out.println("Cart is empty!");
	        return;
	    }

	    for (CartItem item : items) {

	        Order order = new Order(
	            item.productId,
	            user.userId,
	            user.userName,
	            item.productName,
	            item.quantity,
	            item.price
	        );

	        Checkout.generateInvoice(order);
	        OrderFileHandler.saveOrder(order);

	        ProductService.updateProducts(item.productId, item.quantity);
	    }

	    items.clear();
	    System.out.println("Checkout completed successfully!");
	}

}


class CartItem{
	int productId;
    String productName;
    double price;
    int quantity;

    CartItem(int id, String name, double price, int qty) {
        this.productId = id;
        this.productName = name;
        this.price = price;
        this.quantity = qty;
    }
}


class User{
	int userId;
	String userName;
	String password;
	String email;
	
	User(int id,String name,String password,String email){
		this.userId = id;
		this.userName = name;
		this.password = password;
		this.email = email;
	}

    public int getId() { return userId; }
    public String getUsername() { return userName; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
	
}
