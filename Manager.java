import javax.swing.*;
import java.awt.*;


class Manager extends Employee {
    public Manager(String login, String password) {
        super(login, password);
    }

    @Override
    public void openPanel() {
        JFrame frame = new JFrame("Панель менеджера");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(2, 1, 10, 10));



        JButton updateUserButton = new JButton("Обновить пользователя");
        updateUserButton.addActionListener(e -> {

            JFrame userTypeFrame = new JFrame("Выберите тип пользователя");
            userTypeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            userTypeFrame.setSize(300, 150);
            userTypeFrame.setLayout(new GridLayout(2, 1, 10, 10));

            JButton studentButton = new JButton("Обновить студента");
            studentButton.addActionListener(ev -> {
                userTypeFrame.dispose();
                openUserUpdateWindow("Student");
            });

            JButton teacherButton = new JButton("Обновить учителя");
            teacherButton.addActionListener(ev -> {
                userTypeFrame.dispose();
                openUserUpdateWindow("Teacher");
            });

            userTypeFrame.add(studentButton);
            userTypeFrame.add(teacherButton);
            userTypeFrame.setVisible(true);
        });

        JButton createTechOrderButton = new JButton("Create Tech Order");
        createTechOrderButton.addActionListener(e -> openCreateTechOrderWindow());

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout(frame));


        frame.add(updateUserButton);
        frame.add(createTechOrderButton);
        frame.add(logoutButton);
        frame.setVisible(true);
        JPanel languagePanel = new JPanel();
        languagePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton kazButton = new JButton(new ImageIcon("kaz_icon1.png"));
        kazButton.setToolTipText("Kazakh");
        kazButton.addActionListener(e ->{
            updateUserButton.setText("Қолданушыны жаңарту");
            createTechOrderButton.setText("Техникалық тапсырыс жасау");
            logoutButton.setText("Шығу");
        });

        JButton engButton = new JButton(new ImageIcon("eng_icon1.png"));
        engButton.setToolTipText("English");
        engButton.addActionListener(e -> {
            updateUserButton.setText("Update the user");
            createTechOrderButton.setText("Create Tech Order");
            logoutButton.setText("Logout");

        });

        JButton rusButton = new JButton(new ImageIcon("rus_icon1.png"));
        rusButton.setToolTipText("Russian");
        rusButton.addActionListener(e -> {
            updateUserButton.setText("Обновить пользователя");
            createTechOrderButton.setText("Создать технический заказ");
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
    private void openAddDisciplinesWindow() {
        JFrame Oframe = new JFrame("Регистрация на дисциплины(эдвайзер)");
        Oframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Oframe.setSize(400, 300);
        Oframe.setLayout(new GridLayout(4, 2, 10, 10)); // Добавляем сеточный менеджер компоновки

        JLabel typeLabel = new JLabel("Тип пользователя:");
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Teacher", "Student"});

        JLabel schoolTypeLabel = new JLabel("Тип школы:");
        JComboBox<SchoolType> schoolTypeComboBox = new JComboBox<>(SchoolType.values());
        JLabel courseLabel = new JLabel("Курс:");
        JTextField courseField = new JTextField();


        Oframe.add(typeLabel);
        Oframe.add(typeComboBox);
        Oframe.add(schoolTypeLabel);
        Oframe.add(schoolTypeComboBox);
        Oframe.add(courseLabel);
        Oframe.add(courseField);

        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(e -> {
            String selectedType = (String) typeComboBox.getSelectedItem();
            String selectedSchoolType = schoolTypeComboBox.getSelectedItem().toString();
            String course = courseField.getText();
            JOptionPane.showMessageDialog(Oframe, "Данные сохранены:\nТип: " + selectedType +
                    "\nШкола: " + selectedSchoolType +
                    "\nКурс: " + course);
            Oframe.dispose();
        });

        Oframe.add(new JLabel());
        Oframe.add(saveButton);

        Oframe.setVisible(true);
    }
    private void openCreateTechOrderWindow() {
        JFrame Oframe = new JFrame("Create Tech Order");
        Oframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Oframe.setSize(400, 300);
        Oframe.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel issueLabel = new JLabel("Issue:");
        JTextField issueField = new JTextField();
        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField();

        JButton createButton = new JButton("Создать");
        createButton.addActionListener(e -> {
            String issue = issueField.getText();
            String description = descriptionField.getText();

            if (issue.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(Oframe, "Fields cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            createTechOrder(issue, description,getLogin());
            JOptionPane.showMessageDialog(Oframe, "Order created successfully!");
            Oframe.dispose();
        });
        Oframe.add(issueLabel);
        Oframe.add(issueField);
        Oframe.add(descriptionLabel);
        Oframe.add(descriptionField);
        Oframe.add(new JLabel());
        Oframe.add(createButton);

        Oframe.setVisible(true);
    }

    private void openUserUpdateWindow(String userType) {
        JFrame updateFrame = new JFrame("Update " + userType + " Info");
        updateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateFrame.setSize(400, 300);
        updateFrame.setLayout(new GridLayout(userType.equals("Student") ? 8 : 6, 2, 10, 10));

        JTextField loginField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField surnameField = new JTextField();
        JTextField idField = new JTextField();

        final JComboBox<SchoolType> schoolTypeComboBox = new JComboBox<>(SchoolType.values());
        final JTextField courseField = new JTextField();
        final JTextField subjectField = new JTextField();
        updateFrame.add(new JLabel("Login:"));
        updateFrame.add(loginField);
        updateFrame.add(new JLabel("Name:"));
        updateFrame.add(nameField);
        updateFrame.add(new JLabel("Surname:"));
        updateFrame.add(surnameField);
        if (userType.equals("Student")) {
            updateFrame.add(new JLabel("School Type:"));
            updateFrame.add(schoolTypeComboBox);
            updateFrame.add(new JLabel("Course:"));
            updateFrame.add(courseField);
        } else if (userType.equals("Teacher")) {
            updateFrame.add(new JLabel("Subject:"));
            updateFrame.add(subjectField);
        }
        updateFrame.add(new JLabel(userType + " ID:"));
        updateFrame.add(idField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                String login = loginField.getText();
                String name = nameField.getText();
                String surname = surnameField.getText();
                int id = Integer.parseInt(idField.getText());

                if (userType.equals("Student")) {
                    SchoolType schoolType = (SchoolType) schoolTypeComboBox.getSelectedItem();
                    int course = Integer.parseInt(courseField.getText());
                    StudentDataHandler.saveStudentInfo(login, name, surname, schoolType.toString(), course, id);

                    JOptionPane.showMessageDialog(updateFrame, "Student info updated successfully!");
                    System.out.printf("Updated Student: Login=%s, Name=%s, Surname=%s, SchoolType=%s, Course=%d, ID=%d%n",
                            login, name, surname, schoolType, course, id);
                } else if (userType.equals("Teacher")) {
                    String subject = subjectField.getText();
                    TeacherDataHandler.saveTeacherInfo(login, name, surname, subject, id);

                    JOptionPane.showMessageDialog(updateFrame, "Teacher info updated successfully!");
                    System.out.printf("Updated Teacher: Login=%s, Name=%s, Surname=%s, Subject=%s, ID=%d%n",
                            login, name, surname, subject, id);
                }

                updateFrame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(updateFrame, "ID and Course must be numeric!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateFrame.add(saveButton);
        updateFrame.setVisible(true);
    }



    protected void updateUser(String login, String name, String surname, String userType, String schoolType, int course) {
        System.out.println("User updated: ");
        System.out.println("Login: " + login);
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("Type: " + userType);
        System.out.println("School Type: " + schoolType);
        if (course > 0) {
            System.out.println("Course: " + course);
        }
    }

    protected void saveAccounts() {

        System.out.println("Accounts saved.");
    }
    @Override
    public void sendMessage() {

    }
}
