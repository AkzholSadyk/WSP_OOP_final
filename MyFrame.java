import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class  MyFrame extends JFrame {
    private final Admin admin;

    private Language selectedLanguage = Language.ENG; // Default language

    MyFrame() {
        admin = new Admin("admin", "admin123");
        // admin.addUser(new Manager("manager", "manager123"));
        // admin.addUser(new Teacher("teacher", "teacher123"));
        // admin.addUser(new ResearcherTeacher("researcher_teacher", "researcher123"));
        // admin.addUser(new Student("ak_sadyk", "Akjol006", "Akzhol", "Sadyk", SchoolType.SITE, 2));
        // admin.addUser(new Student("e_kenzhegaly", "Akjol006", "Yerassyl", "Kenzhegaly", SchoolType.SEPI, 2));
        // admin.addUser(new Student("a_zhurmag", "Akjol006", "Aliyar", "Jurmaganbetov", SchoolType.BS, 2));

        this.setTitle("WSP");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);

        ImageIcon image = new ImageIcon("WSP_logo.png");
        this.setIconImage(image.getImage());

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JLabel loginLabel = new JLabel("Логин:");
        JTextField loginField = new JTextField(15);

        JLabel passwordLabel = new JLabel("Пароль:");
        JPasswordField passwordField = new JPasswordField(15);

        JButton loginButton = new JButton("Войти");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginField.getText();
                String password = new String(passwordField.getPassword());

                User authenticatedUser = authenticateUser(login, password);
                if (authenticatedUser != null) {
                    dispose();
                    authenticatedUser.openPanel();
                } else if (admin.getLogin().equals(login) && admin.getPassword().equals(password)) {
                    dispose();
                    admin.openPanel();
                } else {
                    JOptionPane.showMessageDialog(null, "Неверный логин или пароль!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(loginLabel, gbc);

        gbc.gridx = 1;
        panel.add(loginField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        this.add(panel);


        JPanel languagePanel = new JPanel();
        languagePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton kazButton = new JButton(new ImageIcon("kaz_icon1.png"));
        kazButton.setToolTipText("Kazakh");
        kazButton.addActionListener(e ->{
            loginLabel.setText("Логин");
            passwordLabel.setText("Құпия сөз");
            loginButton.setText("Кіру");
        });

        JButton engButton = new JButton(new ImageIcon("eng_icon1.png"));
        engButton.setToolTipText("English");
        engButton.addActionListener(e -> {
            loginLabel.setText("Login");
            passwordLabel.setText("Password");
            loginButton.setText("Enter");
        });

        JButton rusButton = new JButton(new ImageIcon("rus_icon1.png"));
        rusButton.setToolTipText("Russian");
        rusButton.addActionListener(e -> {
            loginLabel.setText("Логин");
            passwordLabel.setText("Пароль");
            loginButton.setText("Войти");
        });

        languagePanel.add(kazButton);
        languagePanel.add(engButton);
        languagePanel.add(rusButton);

        this.add(languagePanel, BorderLayout.NORTH);

        this.setVisible(true);
    }

    private void setLanguage(Language language) {
        this.selectedLanguage = language;
        JOptionPane.showMessageDialog(this, "Language changed to: " + language);
        // You can add more functionality here to update the UI based on the selected language.
    }

    private User authenticateUser(String login, String password) {
        for (User user : admin.getAccounts()) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                if (user instanceof ResearcherTeacher) {
                    System.out.println("Authenticated as a ResearcherTeacher.");
                } else if (user instanceof Teacher) {
                    System.out.println("Authenticated as a Teacher.");
                }
                return user;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        new MyFrame();
    }
}
