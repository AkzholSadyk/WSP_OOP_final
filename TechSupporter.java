import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TechSupporter extends Employee{

    public TechSupporter(String login,String password){
        super(login,password);

    }

    @Override
    public void sendMessage() {

    }

    @Override
    public void openPanel() {
        JFrame frame = new JFrame("Панель технического специалиста");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 4, 10, 10));

        JButton viewOrdersButton = new JButton("Все заказы");
        viewOrdersButton.addActionListener(e -> openViewOrdersWindow());

        JButton viewNewOrdersButton = new JButton("Все новые заказы");
        viewNewOrdersButton.addActionListener(e -> openViewNewOrdersWindow());

        JButton viewAcceptedOrdersButton = new JButton("Все принятый заказы");
        viewAcceptedOrdersButton.addActionListener(e -> openViewAcceptedOrdersWindow());

        JButton acceptOrderButton = new JButton("Операция с заказом");
        acceptOrderButton.addActionListener(e -> openOperationsOrderWindow());

        JButton viewCompletedOrdersButton = new JButton("Все завершенные заказы");
        viewCompletedOrdersButton.addActionListener(e -> openViewCompletedOrdersWindow());

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout(frame));

        frame.add(viewOrdersButton);
        frame.add(viewNewOrdersButton);
        frame.add(viewAcceptedOrdersButton);
        frame.add(acceptOrderButton);
        frame.add(viewCompletedOrdersButton);
        frame.add(logoutButton);

        JPanel languagePanel = new JPanel();
        languagePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton kazButton = new JButton(new ImageIcon("kaz_icon1.png"));
        kazButton.setToolTipText("Kazakh");
        kazButton.addActionListener(e ->{
            viewOrdersButton.setText("Барлық тапсырыстар");
            viewNewOrdersButton.setText("Барлық жаңа тапсырыстар");
            viewAcceptedOrdersButton.setText("Қабылданылған тапсырыстар");
            acceptOrderButton.setText("Тапсырысты қабылдау");
            viewCompletedOrdersButton.setText("Орындалған тапсырыстар");
            logoutButton.setText("Шығу");
        });

        JButton engButton = new JButton(new ImageIcon("eng_icon1.png"));
        engButton.setToolTipText("English");
        engButton.addActionListener(e -> {
            viewOrdersButton.setText("All orders");
            viewNewOrdersButton.setText("All new orders");
            viewAcceptedOrdersButton.setText("All orders accepted");
            acceptOrderButton.setText("Order operation");
            viewCompletedOrdersButton.setText("All completed orders");
            logoutButton.setText("Logout");

        });

        JButton rusButton = new JButton(new ImageIcon("rus_icon1.png"));
        rusButton.setToolTipText("Russian");
        rusButton.addActionListener(e -> {
            viewOrdersButton.setText("Все заказы");
            viewNewOrdersButton.setText("Все новые заказы");
            viewAcceptedOrdersButton.setText("Все принятый заказы");
            acceptOrderButton.setText("Операция с заказом");
            viewCompletedOrdersButton.setText("Все завершенные заказы");
            logoutButton.setText("Выйти");
        });

        languagePanel.add(kazButton);
        languagePanel.add(engButton);
        languagePanel.add(rusButton);

        frame.add(languagePanel, BorderLayout.WEST);
        frame.setVisible(true);

    }
    private void logout(JFrame frame) {
        JOptionPane.showMessageDialog(null, "You have been logged out.");
        frame.dispose();
        new MyFrame();
    }

    private void openViewOrdersWindow() {

        List<TechOrder> pendingOrders = getTechOrders();

        if (pendingOrders.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No technical orders", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder ordersInfo = new StringBuilder("Technical Orders:\n");

            for (TechOrder order : pendingOrders) {
                ordersInfo.append(order.toString()).append("\n");
            }

            JOptionPane.showMessageDialog(null, ordersInfo.toString(), "Technical Orders", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void openOperationsOrderWindow() {
        JFrame operationsFrame = new JFrame("Операции с заказами");
        operationsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        operationsFrame.setSize(400, 300);
        operationsFrame.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel ticketIdLabel = new JLabel("ticket ID:");
        JTextField ticketIdField = new JTextField();

        JLabel typeLabel = new JLabel("Operation Type:");
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Accept", "Complete"});

        JButton assignButton = new JButton("Go");
        assignButton.addActionListener(e -> {
            String ticketIdText = ticketIdField.getText();
            int ticketID = Integer.parseInt(ticketIdText);

            String operationType = (String) typeComboBox.getSelectedItem();

            if (ticketID==0) {
                JOptionPane.showMessageDialog(operationsFrame, "Fields cannot be eqauls to 0", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            TechOrder techOrder = findOrderByTicket(ticketID);
            if (techOrder != null) {
                doOperation(techOrder, operationType);

                JOptionPane.showMessageDialog(operationsFrame, "User type assigned successfully!");
                operationsFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(operationsFrame, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        operationsFrame.add(ticketIdLabel);
        operationsFrame.add(ticketIdField);
        operationsFrame.add(typeLabel);
        operationsFrame.add(typeComboBox);
        operationsFrame.add(new JLabel());
        operationsFrame.add(assignButton);

        operationsFrame.setVisible(true);

    }
    private TechOrder findOrderByTicket(int ticketId) {
        for (TechOrder order : getTechOrders()) {
            if (order.getTicketID() == ticketId) {
                return order;
            }
        }
        return null;
    }

    private void doOperation(TechOrder order, String typelabel){
        switch (typelabel) {
            case "Accept":
                order.acceptOrder();
                break;
            case "Complete":
                order.completeOrder();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid ticket ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void openViewNewOrdersWindow(){
        List<TechOrder> pendingOrders = getPendingOrders();

        if (pendingOrders.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No pending orders", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder ordersInfo = new StringBuilder("Pending Orders:\n");

            for (TechOrder order : pendingOrders) {
                ordersInfo.append(order.toString()).append("\n");
            }

            // Отображаем список заказов в диалоговом окне
            JOptionPane.showMessageDialog(null, ordersInfo.toString(), "Pending Orders", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private List<TechOrder> getPendingOrders() {
        List<TechOrder> pendingOrders = new ArrayList<>();

        for (TechOrder order : getTechOrders()) {
            if (!order.isAccepted() && !order.isCompleted()) {
                pendingOrders.add(order);
            }
        }
        return pendingOrders;
    }
    private void openViewAcceptedOrdersWindow(){
        List<TechOrder> pendingOrders = getAcceptingOrders();

        if (pendingOrders.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No accepted orders", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder ordersInfo = new StringBuilder("Accepted Orders:\n");

            for (TechOrder order : pendingOrders) {
                ordersInfo.append(order.toString()).append("\n");
            }

            // Отображаем список заказов в диалоговом окне
            JOptionPane.showMessageDialog(null, ordersInfo.toString(), "Accepted Orders", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private List<TechOrder> getAcceptingOrders() {
        List<TechOrder> pendingOrders = new ArrayList<>();

        for (TechOrder order : getTechOrders()) {
            if (order.isAccepted() && !order.isCompleted()) {
                pendingOrders.add(order);
            }
        }
        return pendingOrders;
    }
    private void openViewCompletedOrdersWindow(){
        List<TechOrder> pendingOrders = getCompletingOrders();

        if (pendingOrders.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No completed orders", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder ordersInfo = new StringBuilder("Completed Orders:\n");

            for (TechOrder order : pendingOrders) {
                ordersInfo.append(order.toString()).append("\n");
            }

            // Отображаем список заказов в диалоговом окне
            JOptionPane.showMessageDialog(null, ordersInfo.toString(), "Completed Orders", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private List<TechOrder> getCompletingOrders() {
        List<TechOrder> pendingOrders = new ArrayList<>();

        for (TechOrder order : getTechOrders()) {
            if (order.isAccepted() && order.isCompleted()) {
                pendingOrders.add(order);
            }
        }
        return pendingOrders;
    }
}
