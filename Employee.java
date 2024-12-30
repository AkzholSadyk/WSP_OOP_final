import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public abstract class Employee extends User implements Emp {
    private String position;
    private String department;
    private static List<TechOrder> techOrders = new ArrayList<>();
    private static final String TECH_ORDERS_FILE = "tech_orders.dat";
    public Employee(String login,String password) {
        super(login,password);

    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getDetail() {
        return String.format("Login: %s\nPosition: %s\nDepartment: %s",
                getLogin(), getPosition(), getDepartment());
    }
    public void createTechOrder(String issue, String description, String login) {
        TechOrder techOrder = new TechOrder(issue,description,login);
        techOrders.add(techOrder);

    }

    public List<TechOrder> getTechOrders() {
        return techOrders;
    }

    public static void saveTechOrders() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TECH_ORDERS_FILE))) {
            oos.writeObject(techOrders);
            System.out.println("Technical orders saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving tech orders!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void loadTechOrders() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TECH_ORDERS_FILE))) {
            List<TechOrder> loadedOrders = (List<TechOrder>) ois.readObject();
            techOrders.clear(); // Очищаем текущий список перед загрузкой
            techOrders.addAll(loadedOrders);
            System.out.println("Technical orders loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("No previous tech orders found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error loading tech orders!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}
