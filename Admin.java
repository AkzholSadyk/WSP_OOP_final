import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Admin extends Employee {
    private final List<User> accounts;
    private static final String FILE_NAME = "user_data.ser";
    private String role;
    private static Admin instance; //singletone

    public Admin(String login, String password) {
        super(login, password);
        this.accounts = new ArrayList<>();
        loadAccounts();
    }

    public void openPanel() {
        JFrame frame = new JFrame("Admin Panel");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new GridLayout(5, 1, 10, 10));

        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(e -> openAddUserWindow());

        JButton updateUserButton = new JButton("Update User");
        updateUserButton.addActionListener(e -> openUpdateUserWindow());

        JButton removeUserButton = new JButton("Remove User");
        removeUserButton.addActionListener(e -> openRemoveUserWindow());

        JButton assignUserTypeButton = new JButton("Assign User Type");
        assignUserTypeButton.addActionListener(e -> openAssignUserTypeWindow());

        JButton viewUsersButton = new JButton("View Users");
        viewUsersButton.addActionListener(e -> openViewUsersWindow());

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout(frame));

        frame.add(addUserButton);
        frame.add(updateUserButton);
        frame.add(removeUserButton);
        frame.add(assignUserTypeButton);
        frame.add(viewUsersButton);
        frame.add(logoutButton);

        frame.setVisible(true);

        JPanel languagePanel = new JPanel();
        languagePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton kazButton = new JButton(new ImageIcon("kaz_icon1.png"));
        kazButton.setToolTipText("Kazakh");
        kazButton.addActionListener(e ->{
            addUserButton.setText("Қолданушын қосу");
            updateUserButton.setText("Қолданушыны өзгерту");
            removeUserButton.setText("Қолданушыны кертіру");
            assignUserTypeButton.setText("Қолданушының типін анықтау");
            viewUsersButton.setText("Қолданушыларды көру");
            logoutButton.setText("Шығу");
        });

        JButton engButton = new JButton(new ImageIcon("eng_icon1.png"));
        engButton.setToolTipText("English");
        engButton.addActionListener(e -> {
            addUserButton.setText("Add User");
            updateUserButton.setText("Update User");
            removeUserButton.setText("Remove User");
            assignUserTypeButton.setText("Assign User Type");
            viewUsersButton.setText("View Users");
            logoutButton.setText("Logout");

        });

        JButton rusButton = new JButton(new ImageIcon("rus_icon1.png"));
        rusButton.setToolTipText("Russian");
        rusButton.addActionListener(e -> {
            addUserButton.setText("Добавить пользователя");
            updateUserButton.setText("Обновить пользователя");
            removeUserButton.setText("Удалить пользрователя");
            assignUserTypeButton.setText("Определить тип пользователя");
            viewUsersButton.setText("Показать пользователей");
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

    private void openAddUserWindow() {
        JFrame addUserFrame = new JFrame("Add User");
        addUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addUserFrame.setSize(300, 200);
        addUserFrame.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel loginLabel = new JLabel("Login:");
        JTextField loginField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JTextField passwordField = new JTextField();

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            String login = loginField.getText();
            String password = passwordField.getText();

            if (login.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(addUserFrame, "Fields cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            addUser(new BasicUser(login, password));
            saveAccounts();
            JOptionPane.showMessageDialog(addUserFrame, "User added successfully!");
            addUserFrame.dispose();
        });

        addUserFrame.add(loginLabel);
        addUserFrame.add(loginField);
        addUserFrame.add(passwordLabel);
        addUserFrame.add(passwordField);
        addUserFrame.add(new JLabel());
        addUserFrame.add(addButton);

        addUserFrame.setVisible(true);
    }


    private void openUpdateUserWindow() {
        JFrame updateUserFrame = new JFrame("Update User");
        updateUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateUserFrame.setSize(300, 200);
        updateUserFrame.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel loginLabel = new JLabel("Login:");
        JTextField loginField = new JTextField();
        JLabel newPasswordLabel = new JLabel("New Password:");
        JTextField newPasswordField = new JTextField();

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            String login = loginField.getText();
            String newPassword = newPasswordField.getText();

            if (login.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(updateUserFrame, "Fields cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            updateUser(login, newPassword);
            saveAccounts();
            JOptionPane.showMessageDialog(updateUserFrame, "User password updated successfully!");
            updateUserFrame.dispose();
        });

        updateUserFrame.add(loginLabel);
        updateUserFrame.add(loginField);
        updateUserFrame.add(newPasswordLabel);
        updateUserFrame.add(newPasswordField);
        updateUserFrame.add(new JLabel());
        updateUserFrame.add(updateButton);

        updateUserFrame.setVisible(true);
    }

    private void openRemoveUserWindow() {
        JFrame removeUserFrame = new JFrame("Remove User");
        removeUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        removeUserFrame.setSize(300, 150);
        removeUserFrame.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel loginLabel = new JLabel("Login:");
        JTextField loginField = new JTextField();

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
            String login = loginField.getText();

            if (login.isEmpty()) {
                JOptionPane.showMessageDialog(removeUserFrame, "Login cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            removeUser(login);
            saveAccounts();
            JOptionPane.showMessageDialog(removeUserFrame, "User removed successfully!");
            removeUserFrame.dispose();
        });

        removeUserFrame.add(loginLabel);
        removeUserFrame.add(loginField);
        removeUserFrame.add(new JLabel());
        removeUserFrame.add(removeButton);

        removeUserFrame.setVisible(true);
    }

    private void openAssignUserTypeWindow() {
        JFrame assignUserFrame = new JFrame("Assign User Type");
        assignUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        assignUserFrame.setSize(400, 300);
        assignUserFrame.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel loginLabel = new JLabel("Login:");
        JTextField loginField = new JTextField();

        JLabel typeLabel = new JLabel("User Type:");
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Manager", "Teacher", "ResearcherTeacher", "Student", "TechSupporter"});

        JButton assignButton = new JButton("Assign");
        assignButton.addActionListener(e -> {
            String login = loginField.getText();
            String userType = (String) typeComboBox.getSelectedItem();

            if (login.isEmpty() || userType == null) {
                JOptionPane.showMessageDialog(assignUserFrame, "Fields cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = findUserByLogin(login);
            if (user != null) {
                assignUserType(user, userType);
                saveAccounts();
                JOptionPane.showMessageDialog(assignUserFrame, "User type assigned successfully!");
                assignUserFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(assignUserFrame, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        assignUserFrame.add(loginLabel);
        assignUserFrame.add(loginField);
        assignUserFrame.add(typeLabel);
        assignUserFrame.add(typeComboBox);
        assignUserFrame.add(new JLabel());
        assignUserFrame.add(assignButton);

        assignUserFrame.setVisible(true);
    }

    private void openViewUsersWindow() {
        JFrame viewUsersFrame = new JFrame("View Users");
        viewUsersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewUsersFrame.setSize(400, 300);

        DefaultListModel<String> userModel = new DefaultListModel<>();
        for (User user : accounts) {
            userModel.addElement(user.getLogin() + " - " + user.getClass().getSimpleName());
        }

        JList<String> userList = new JList<>(userModel);
        viewUsersFrame.add(new JScrollPane(userList));

        viewUsersFrame.setVisible(true);
    }

    private User findUserByLogin(String login) {
        for (User user : accounts) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    private void assignUserType(User user, String userType) {
        switch (userType) {
            case "Manager":
                accounts.remove(user);
                accounts.add(new Manager(user.getLogin(), user.getPassword()));
                break;
            case "Teacher":
                accounts.remove(user);
                accounts.add(new Teacher(user.getLogin(), user.getPassword()));
                break;
            case "ResearcherTeacher":
                accounts.remove(user);
                accounts.add(new ResearcherTeacher(user.getLogin(), user.getPassword()));
                break;
            case "Student":
                accounts.remove(user);
                accounts.add(new Student(user.getLogin(), user.getPassword(), "Default", "Default", SchoolType.SITE, 1, generateStudentID()));
                break;
            case "TechSupporter":
                accounts.remove(user);
                accounts.add(new TechSupporter(user.getLogin(), user.getPassword()));
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid user type!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addUser(User user) {
        for (User account : accounts) {
            if (account.getLogin().equals(user.getLogin())) {
                JOptionPane.showMessageDialog(null, "User already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        accounts.add(user);
    }

    private int generateStudentID() {
        return new Random().nextInt(900000) + 100000;
    }

    public void updateAccount(User updatedUser) {
        for (int i = 0; i < accounts.size(); i++) {
            User account = accounts.get(i);
            if (account.getLogin().equals(updatedUser.getLogin())) {
                accounts.set(i, updatedUser);
                saveAccounts();
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void updateUser(String login, String newPassword) {
        for (User account : accounts) {
            if (account.getLogin().equals(login)) {
                account.setPassword(newPassword);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void removeUser(String login) {
        accounts.removeIf(account -> account.getLogin().equals(login));
    }

    public List<User> getAccounts() {
        return accounts;
    }

    private void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving user data!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadAccounts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            List<User> loadedAccounts = (List<User>) ois.readObject();
            accounts.addAll(loadedAccounts);
        } catch (FileNotFoundException e) {
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error loading user data!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage() {
    }

    private SortStrategy strategy;

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void sortUsers(List<User> users) {
        if (strategy != null) {
            strategy.sort(users);
        }
    }

    public String getRole() {
        return this.getClass().getSimpleName();
    }


    public void sortAccounts() {
        if (strategy != null) {
            strategy.sort(accounts);
        } else {
            JOptionPane.showMessageDialog(null, "Sort strategy not set!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static Admin getInstance(String login, String password) {
        if (instance == null) {
            instance = new Admin(login, password);
        }
        return instance;
    }

    public void performAdminTask() {
        System.out.println("Admin task performed.");
    }
}


