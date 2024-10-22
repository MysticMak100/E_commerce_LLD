package Common_systems.e_commerce;

import java.util.*;

public class shopping {
    public static void main(String[] args) {
        inventory i = new inventory();

        product p1 = new product(1, 2000, "Addidas shoes", productType.shoes);
        product p2 = new product(2, 3000, "Nike Shoes", productType.shoes);
        product p3 = new product(3, 1000, "Jeans", productType.bottomwear);
        product p4 = new product(4, 1200, "Shirt", productType.bottomwear);
        product p5 = new product(5, 1500, "Tshirt", productType.upperwear);

        i.addtoinventory(p1);
        i.addtoinventory(p2);
        i.addtoinventory(p3);
        i.addtoinventory(p4);
        i.addtoinventory(p5);
        i.addtoinventory(p5);

        user u1 = new user(1, "mysticmak", i);

        user u2 = new user(2, "mota", i);

        i.getinstockproduct();

        u1.addtocart(p1);
        u2.addtocart(p5);
        u2.addtocart(p5);
        u2.addtocart(p3);
        u1.addtocart(p2);
        u2.placeorder();
        i.addtoinventory(p5);
        u1.placeorder();

        i.getinstockproduct();

    }
}

class user {
    private int id;
    private String username;
    private inventory i;
    private HashMap<product, Integer> wishlist = new HashMap<>();
    private cart cart = new cart(0);

    user(int id, String username, inventory i) {
        this.id = id;
        this.username = username;
        this.i = i;
    }

    public void addtowishlist(product p) {
        wishlist.put(p, p.price);
    }

    public void addtocart(product p) {
        if (i.productsinventory.get(p) == 0) {
            System.out.println("Sorry,The product " + p + " is out of stock");
        }
        if (!cart.getProducts().contains(p)) {
            cart.addproduct(p);
            System.out.println("Product " + p + " has been added to the cart of user:" + this.username);
            cart.modifyamount(p.price);
        } else {
            System.out.println("Product " + p + " is already in the cart for user " + this.username);
        }
    }

    public void placeorder() {
        order o = new order(this);
        System.out.println("Dear " + this.username + ",order has been placed, your order id is: " + o.getId());
        System.out.println("Total Amount:" + this.cart.getAmount());
        for (product p : cart.getProducts()) {
            int x = i.productsinventory.get(p);
            i.productsinventory.put(p, x - 1);
        }

        cart.getProducts().clear();
        cart.setAmount(0);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public inventory getI() {
        return this.i;
    }

    public void setI(inventory i) {
        this.i = i;
    }

    public HashMap<product, Integer> getWishlist() {
        return this.wishlist;
    }

    public void setWishlist(HashMap<product, Integer> wishlist) {
        this.wishlist = wishlist;
    }

    public cart getCart() {
        return this.cart;
    }

    public void setCart(cart cart) {
        this.cart = cart;
    }

}

class cart {
    private int amount;
    private ArrayList<product> products = new ArrayList<>();

    cart(int amount) {
        this.amount = amount;
    }

    public void addproduct(product p) {
        products.add(p);
    }

    public void modifyamount(int pr) {
        amount = amount + pr;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ArrayList<product> getProducts() {
        return this.products;
    }

    public void setProducts(ArrayList<product> products) {
        this.products = products;
    }

}

class order {
    private String id;
    private long time;
    private user u;

    order(user u) {
        this.u = u;
        this.time = System.currentTimeMillis();
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public user getU() {
        return this.u;
    }

    public void setU(user u) {
        this.u = u;
    }

}

class inventory {
    HashMap<product, Integer> productsinventory = new HashMap<>();

    void addtoinventory(product p) {
        if (productsinventory.containsKey(p)) {
            int x = productsinventory.get(p);
            productsinventory.put(p, x + 1);
        } else {
            productsinventory.put(p, 1);
        }
    }

    void getinstockproduct() {
        for (product p : productsinventory.keySet()) {
            if (productsinventory.get(p) > 0) {
                System.out.println(p + ":" + productsinventory.get(p));
            }
        }
    }
}

enum productType {
    shoes,
    upperwear,
    bottomwear
}

class product {
    int id;
    String name;
    int price;
    productType type;

    product(int id, int price, String name, productType type) {
        this.id = id;
        this.price = price;
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " (Price: " + price + ", Type: " + type + ")";
    }

}