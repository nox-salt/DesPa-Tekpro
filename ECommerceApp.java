/*---------------------------------------
|NAMA   : RADHITYA MAULANA ARRAFI       |
|NIM    : 241524025                     |
|KELAS  : 1A-D4 Teknik Informatika      |
|----------------------------------------
*/

/*---------------------------------------*/
/*  PROGRAM E-Commerce / Online Shopping */
/*---------------------------------------*/

// ========================================
// ||        CREATIONAL PATTERNS         ||
// ========================================

// ========================================
// 1. ABSTRACT FACTORY PATTERN           ||
// ========================================
// Untuk membuat berbagai jenis produk (Electronics, Fashion, Books)

interface Product {
    String getName();
    double getPrice();
    String getCategory();
}

interface ProductFactory {
    Product createProduct(String type);
    String getFactoryType();
}

// Electronics Products
class Smartphone implements Product {
    private String name;
    private double price;

    public Smartphone(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return "Electronics - Smartphone"; }
}

class Laptop implements Product {
    private String name;
    private double price;

    public Laptop(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return "Electronics - Laptop"; }
}

// Fashion Products
class Shirt implements Product {
    private String name;
    private double price;

    public Shirt(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return "Fashion - Shirt"; }
}

class Shoes implements Product {
    private String name;
    private double price;

    public Shoes(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return "Fashion - Shoes"; }
}

// Concrete Factories
class ElectronicsFactory implements ProductFactory {
    public Product createProduct(String type) {
        switch(type.toLowerCase()) {
            case "smartphone":
                return new Smartphone("iPhone 15", 15000000);
            case "laptop":
                return new Laptop("MacBook Pro", 25000000);
            default:
                throw new IllegalArgumentException("Unknown electronics type: " + type);
        }
    }

    public String getFactoryType() { return "Electronics Factory"; }
}

class FashionFactory implements ProductFactory {
    public Product createProduct(String type) {
        switch(type.toLowerCase()) {
            case "shirt":
                return new Shirt("Cotton Shirt", 250000);
            case "shoes":
                return new Shoes("Running Shoes", 800000);
            default:
                throw new IllegalArgumentException("Unknown fashion type: " + type);
        }
    }

    public String getFactoryType() { return "Fashion Factory"; }
}

// Factory Provider
class FactoryProvider {
    public static ProductFactory getFactory(String category) {
        switch(category.toLowerCase()) {
            case "electronics":
                return new ElectronicsFactory();
            case "fashion":
                return new FashionFactory();
            default:
                throw new IllegalArgumentException("Unknown category: " + category);
        }
    }
}

// ========================================
// 2. SINGLETON PATTERN                  ||
// ========================================
// Untuk Database Connection dan Shopping Cart

class DatabaseConnection {
    private static DatabaseConnection instance;
    private String connectionString;

    private DatabaseConnection() {
        this.connectionString = "jdbc:mysql://localhost:3306/ecommerce";
        System.out.println("Database connection established: " + connectionString);
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void executeQuery(String query) {
        System.out.println("Executing query: " + query);
    }

    public String getConnectionString() {
        return connectionString;
    }
}

class ShoppingCart {
    private static ShoppingCart instance;
    private java.util.List<Product> items;
    private double totalAmount;

    private ShoppingCart() {
        this.items = new java.util.ArrayList<>();
        this.totalAmount = 0.0;
    }

    public static synchronized ShoppingCart getInstance() {
        if (instance == null) {
            instance = new ShoppingCart();
        }
        return instance;
    }

    public void addItem(Product product) {
        items.add(product);
        totalAmount += product.getPrice();
        System.out.println("Added to cart: " + product.getName());
    }

    public void removeItem(Product product) {
        if (items.remove(product)) {
            totalAmount -= product.getPrice();
            System.out.println("Removed from cart: " + product.getName());
        }
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public java.util.List<Product> getItems() {
        return new java.util.ArrayList<>(items);
    }

    public void clearCart() {
        items.clear();
        totalAmount = 0.0;
        System.out.println("Cart cleared");
    }
}

// ========================================
// ||        STRUCTURAL PATTERNS         ||
// ========================================

// ========================================
// 3. DECORATOR PATTERN                  ||
// ========================================
// Untuk menambahkan fitur tambahan pada produk (Gift Wrap, Express Shipping, etc.)

interface ProductService {
    String getDescription();
    double getCost();
}

class BasicProduct implements ProductService {
    private Product product;

    public BasicProduct(Product product) {
        this.product = product;
    }

    public String getDescription() {
        return product.getName();
    }

    public double getCost() {
        return product.getPrice();
    }
}

abstract class ProductDecorator implements ProductService {
    protected ProductService productService;

    public ProductDecorator(ProductService productService) {
        this.productService = productService;
    }

    public String getDescription() {
        return productService.getDescription();
    }

    public double getCost() {
        return productService.getCost();
    }
}

class GiftWrapDecorator extends ProductDecorator {
    public GiftWrapDecorator(ProductService productService) {
        super(productService);
    }

    public String getDescription() {
        return productService.getDescription() + " + Gift Wrap";
    }

    public double getCost() {
        return productService.getCost() + 50000; // Gift wrap cost
    }
}

class ExpressShippingDecorator extends ProductDecorator {
    public ExpressShippingDecorator(ProductService productService) {
        super(productService);
    }

    public String getDescription() {
        return productService.getDescription() + " + Express Shipping";
    }

    public double getCost() {
        return productService.getCost() + 75000; // Express shipping cost
    }
}

class InsuranceDecorator extends ProductDecorator {
    public InsuranceDecorator(ProductService productService) {
        super(productService);
    }

    public String getDescription() {
        return productService.getDescription() + " + Insurance";
    }

    public double getCost() {
        return productService.getCost() + (productService.getCost() * 0.05); // 5% insurance
    }
}

// ========================================
// 4. PROXY PATTERN                      ||
// ========================================
// Untuk kontrol akses ke sistem pembayaran dan cache produk

interface PaymentService {
    boolean processPayment(double amount, String cardNumber);
}

class RealPaymentService implements PaymentService {
    public boolean processPayment(double amount, String cardNumber) {
        System.out.println("Processing payment of Rp " + amount + " with card: " + cardNumber);
        // Simulasi proses pembayaran
        try {
            Thread.sleep(2000); // Simulasi delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Payment processed successfully!");
        return true;
    }
}

class PaymentProxy implements PaymentService {
    private RealPaymentService realPaymentService;
    private String userRole;

    public PaymentProxy(String userRole) {
        this.userRole = userRole;
    }

    public boolean processPayment(double amount, String cardNumber) {
        if (!hasPaymentAccess()) {
            System.out.println("Access denied: Insufficient permissions for payment");
            return false;
        }

        if (!isValidCardNumber(cardNumber)) {
            System.out.println("Invalid card number format");
            return false;
        }

        if (amount <= 0) {
            System.out.println("Invalid payment amount");
            return false;
        }

        // Lazy initialization
        if (realPaymentService == null) {
            realPaymentService = new RealPaymentService();
        }

        return realPaymentService.processPayment(amount, cardNumber);
    }

    private boolean hasPaymentAccess() {
        return "CUSTOMER".equals(userRole) || "ADMIN".equals(userRole);
    }

    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber != null && cardNumber.length() >= 13 && cardNumber.length() <= 19;
    }
}

// ========================================
// ||        BEHAVIORAL PATTERNS         ||
// ========================================

// ========================================
// 5. OBSERVER PATTERN                   ||
// ========================================
// Untuk notifikasi kepada customer tentang status order, stock, dll.

interface Observer {
    void update(String message);
}

interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String message);
}

class Customer implements Observer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public void update(String message) {
        System.out.println("Notification to " + name + ": " + message);
    }

    public String getName() {
        return name;
    }
}

class Order implements Subject {
    private java.util.List<Observer> observers;
    private String orderId;
    private String status;

    public Order(String orderId) {
        this.orderId = orderId;
        this.observers = new java.util.ArrayList<>();
        this.status = "PENDING";
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
        notifyObservers("Order " + orderId + " status updated to: " + newStatus);
    }

    public String getStatus() {
        return status;
    }

    public String getOrderId() {
        return orderId;
    }
}

// ========================================
// 6. STRATEGY PATTERN                   ||
// ========================================
// Untuk berbagai strategi pembayaran dan pengiriman

interface PaymentStrategy {
    boolean pay(double amount);
    String getPaymentType();
}

class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String holderName;

    public CreditCardPayment(String cardNumber, String holderName) {
        this.cardNumber = cardNumber;
        this.holderName = holderName;
    }

    public boolean pay(double amount) {
        System.out.println("Paying Rp " + amount + " using Credit Card");
        System.out.println("Card: " + cardNumber + ", Holder: " + holderName);
        return true;
    }

    public String getPaymentType() {
        return "Credit Card";
    }
}

class EWalletPayment implements PaymentStrategy {
    private String walletId;

    public EWalletPayment(String walletId) {
        this.walletId = walletId;
    }

    public boolean pay(double amount) {
        System.out.println("Paying Rp " + amount + " using E-Wallet");
        System.out.println("Wallet ID: " + walletId);
        return true;
    }

    public String getPaymentType() {
        return "E-Wallet";
    }
}

class BankTransferPayment implements PaymentStrategy {
    private String accountNumber;
    private String bankName;

    public BankTransferPayment(String accountNumber, String bankName) {
        this.accountNumber = accountNumber;
        this.bankName = bankName;
    }

    public boolean pay(double amount) {
        System.out.println("Paying Rp " + amount + " using Bank Transfer");
        System.out.println("Bank: " + bankName + ", Account: " + accountNumber);
        return true;
    }

    public String getPaymentType() {
        return "Bank Transfer";
    }
}

interface ShippingStrategy {
    double calculateCost(double weight, String destination);
    String getShippingType();
}

class StandardShipping implements ShippingStrategy {
    public double calculateCost(double weight, String destination) {
        return weight * 5000; // Rp 5,000 per kg
    }

    public String getShippingType() {
        return "Standard Shipping (3-5 days)";
    }
}

class ExpressShipping implements ShippingStrategy {
    public double calculateCost(double weight, String destination) {
        return weight * 12000; // Rp 12,000 per kg
    }

    public String getShippingType() {
        return "Express Shipping (1-2 days)";
    }
}

class SameDayShipping implements ShippingStrategy {
    public double calculateCost(double weight, String destination) {
        return weight * 25000; // Rp 25,000 per kg
    }

    public String getShippingType() {
        return "Same Day Shipping";
    }
}

// Context classes for Strategy Pattern
class PaymentProcessor {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean processPayment(double amount) {
        if (paymentStrategy == null) {
            System.out.println("No payment method selected");
            return false;
        }

        System.out.println("Using payment method: " + paymentStrategy.getPaymentType());
        return paymentStrategy.pay(amount);
    }
}

class ShippingCalculator {
    private ShippingStrategy shippingStrategy;

    public void setShippingStrategy(ShippingStrategy shippingStrategy) {
        this.shippingStrategy = shippingStrategy;
    }

    public double calculateShippingCost(double weight, String destination) {
        if (shippingStrategy == null) {
            System.out.println("No shipping method selected");
            return 0;
        }

        double cost = shippingStrategy.calculateCost(weight, destination);
        System.out.println("Shipping method: " + shippingStrategy.getShippingType());
        System.out.println("Shipping cost: Rp " + cost);
        return cost;
    }
}

// ========================================
// ||       MAIN APPLICATION CLASS       ||
// ========================================

public class ECommerceApp {
    public static void main(String[] args) {
        System.out.println("=== E-COMMERCE PLATFORM DEMO ===\n");

        // 1. ABSTRACT FACTORY PATTERN Demo
        System.out.println("1. ABSTRACT FACTORY PATTERN - Creating Products");
        ProductFactory electronicsFactory = FactoryProvider.getFactory("electronics");
        ProductFactory fashionFactory = FactoryProvider.getFactory("fashion");

        Product smartphone = electronicsFactory.createProduct("smartphone");
        Product laptop = electronicsFactory.createProduct("laptop");
        Product shirt = fashionFactory.createProduct("shirt");
        Product shoes = fashionFactory.createProduct("shoes");

        System.out.println("Created: " + smartphone.getName() + " - " + smartphone.getCategory());
        System.out.println("Created: " + laptop.getName() + " - " + laptop.getCategory());
        System.out.println("Created: " + shirt.getName() + " - " + shirt.getCategory());
        System.out.println("Created: " + shoes.getName() + " - " + shoes.getCategory());

        // 2. SINGLETON PATTERN Demo
        System.out.println("\n2. SINGLETON PATTERN - Database & Shopping Cart");
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        System.out.println("Same database instance? " + (db1 == db2));

        ShoppingCart cart = ShoppingCart.getInstance();
        cart.addItem(smartphone);
        cart.addItem(shirt);
        System.out.println("Cart total: Rp " + cart.getTotalAmount());

        // 3. DECORATOR PATTERN Demo
        System.out.println("\n3. DECORATOR PATTERN - Adding Services");
        ProductService basicProduct = new BasicProduct(smartphone);
        ProductService giftWrapped = new GiftWrapDecorator(basicProduct);
        ProductService expressShipped = new ExpressShippingDecorator(giftWrapped);
        ProductService fullyDecorated = new InsuranceDecorator(expressShipped);

        System.out.println("Basic: " + basicProduct.getDescription() + " - Rp " + basicProduct.getCost());
        System.out.println("Decorated: " + fullyDecorated.getDescription() + " - Rp " + fullyDecorated.getCost());

        // 4. PROXY PATTERN Demo
        System.out.println("\n4. PROXY PATTERN - Payment Security");
        PaymentService customerPayment = new PaymentProxy("CUSTOMER");
        PaymentService guestPayment = new PaymentProxy("GUEST");

        customerPayment.processPayment(1000000, "1234567890123456");
        guestPayment.processPayment(1000000, "1234567890123456");

        // 5. OBSERVER PATTERN Demo
        System.out.println("\n5. OBSERVER PATTERN - Order Notifications");
        Order order = new Order("ORD-001");
        Customer customer1 = new Customer("John Doe");
        Customer customer2 = new Customer("Jane Smith");

        order.addObserver(customer1);
        order.addObserver(customer2);

        order.updateStatus("CONFIRMED");
        order.updateStatus("SHIPPED");
        order.updateStatus("DELIVERED");

        // 6. STRATEGY PATTERN Demo
        System.out.println("\n6. STRATEGY PATTERN - Payment & Shipping");

        // Payment strategies
        PaymentProcessor paymentProcessor = new PaymentProcessor();

        paymentProcessor.setPaymentStrategy(new CreditCardPayment("1234-5678-9012-3456", "John Doe"));
        paymentProcessor.processPayment(500000);

        paymentProcessor.setPaymentStrategy(new EWalletPayment("john.doe@email.com"));
        paymentProcessor.processPayment(300000);

        paymentProcessor.setPaymentStrategy(new BankTransferPayment("1234567890", "BCA"));
        paymentProcessor.processPayment(750000);

        // Shipping strategies
        ShippingCalculator shippingCalculator = new ShippingCalculator();

        shippingCalculator.setShippingStrategy(new StandardShipping());
        shippingCalculator.calculateShippingCost(2.5, "Jakarta");

        shippingCalculator.setShippingStrategy(new ExpressShipping());
        shippingCalculator.calculateShippingCost(2.5, "Jakarta");

        shippingCalculator.setShippingStrategy(new SameDayShipping());
        shippingCalculator.calculateShippingCost(2.5, "Jakarta");

        System.out.println("\n=== DEMO COMPLETED ===");
    }
}