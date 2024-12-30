import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ResearcherTeacher extends Teacher implements Researcher {
    private String name;
    private String surname;
    private String subject;
    private int teacherID;
    private static Map<Discipline, List<Student>> enrolledStudentsMap = new HashMap<>();
    protected final List<News> newsFeed = new ArrayList<>();
    private static List<Discipline> disciplines = new ArrayList<>();
    private List<Course> courses;
    private static final String MARKS_FILE = "marks.dat";
    private final List<Student> students;
    private final List<Lesson> lessons = new ArrayList<>();
    private final List<StudentAttendance> studentAttendances = new ArrayList<>();
    private static final String ATTENDANCE_FILE = "attendance.dat";
    public static List<Discipline> getDisciplines() {
        return disciplines;
    }
    private List<TechOrder> techOrders = new ArrayList<>();

    public void createTechOrder(String issue, String description, String teacherLogin) {
        TechOrder newOrder = new TechOrder(issue, description, teacherLogin);
        techOrders.add(newOrder);

        saveTechOrderToFile(newOrder);

        System.out.println("Tech order created successfully: " + newOrder);
    }

    private void saveTechOrderToFile(TechOrder techOrder) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("techOrders.dat", true))) {
            oos.writeObject(techOrder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enrollStudentInDiscipline(Student student, Discipline discipline) {
        enrolledStudentsMap.computeIfAbsent(discipline, k -> new ArrayList<>()).add(student);
        JOptionPane.showMessageDialog(null, "Student " + student.getLogin() + " has been enrolled in " + discipline.getName());
    }

    public List<Student> getEnrolledStudentsInDiscipline(Discipline discipline) {
        return enrolledStudentsMap.getOrDefault(discipline, new ArrayList<>());
    }

    private static int lastMarkID = 0;
    private final ArrayList<ResearchPaper> papers = new ArrayList<>();

    public ResearcherTeacher(String login, String password) {
        super(login, password);
        loadTeacherInfo();
        this.students = new ArrayList<>();
        this.courses = new ArrayList<>();
    }

    public ArrayList<ResearchPaper> getPapers() {
        return papers;
    }

    public void publishResearchPaper(String title, String authors, String journal, int pages, String doi, int citations) {
        ResearchPaper paper = new ResearchPaper(title, authors, journal, pages, new Date(), doi, citations);
        papers.add(paper);

        News researchNews = new News(title, "Research Paper Published", true);
        newsFeed.add(researchNews);
    }

    @Override
    public void openPanel() {
        JFrame frame = new JFrame("Researcher Teacher Panel");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(6, 1, 10, 10));

        JButton infoButton = new JButton("Information about Teacher");
        infoButton.addActionListener(e -> openTeacherInfoWindow());

        JButton assignMarksAndAttendanceButton = new JButton("Attestation and Attendance");
        assignMarksAndAttendanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openMarkAndAttendanceWindow();
            }
        });

        JButton createTechOrderButton = new JButton("Create Tech Order");
        createTechOrderButton.addActionListener(e -> openCreateTechOrderWindow());

        JButton enrollStudentButton = new JButton("Enroll Students in Discipline");
        enrollStudentButton.addActionListener(e -> openEnrollStudentsWindow());

        JButton viewEnrolledStudentsButton = new JButton("View Enrolled Students");
        viewEnrolledStudentsButton.addActionListener(e -> openViewEnrolledStudentsWindow());

        JButton addDiscipline = new JButton("Регистрация на дисциплины(эдвайзер)");
        addDiscipline.addActionListener(e -> openAddDisciplinesWindow());

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout(frame));

        JButton newsButton = new JButton("News");
        newsButton.addActionListener(e -> openNewsWindow());

        JButton publishResearchButton = new JButton("Publish Research");
        publishResearchButton.addActionListener(e -> publishResearchPaperUI());

        frame.add(infoButton);
        frame.add(publishResearchButton);
        frame.add(assignMarksAndAttendanceButton);
        JButton addJournal = new JButton("Journal (Edviser)");
        JButton addTranscript = new JButton("Transcript (Edviser)");
        frame.add(addJournal);
        frame.add(addTranscript);
        frame.add(createTechOrderButton);
        frame.add(enrollStudentButton);
        frame.add(viewEnrolledStudentsButton);
        frame.add(addDiscipline);
        frame.add(newsButton);
        frame.add(logoutButton);
        frame.setVisible(true);

        JPanel languagePanel = new JPanel();
        languagePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton kazButton = new JButton(new ImageIcon("kaz_icon1.png"));
        kazButton.setToolTipText("Kazakh");
        kazButton.addActionListener(e ->{
            infoButton.setText("Мұғалім туралы ақпараттар");
            assignMarksAndAttendanceButton.setText("Аттенденс және Аттецтация (эдвайзер)");
            createTechOrderButton.setText("Техникалық тапсырыс жасау");
            enrollStudentButton.setText("Студенттерді дисциплинаға қосу");
            viewEnrolledStudentsButton.setText("Дисциплинадағы студенттерді көру");
            addDiscipline.setText("Дисциплина қосу (эдвайзер)");
            addJournal.setText("Журнал (эдвайзер)");
            addTranscript.setText("Транскрипт (эдвайзер)");
            publishResearchButton.setText("Зеріттеуді жариялау");
            logoutButton.setText("Шығу");
        });

        JButton engButton = new JButton(new ImageIcon("eng_icon1.png"));
        engButton.setToolTipText("English");
        engButton.addActionListener(e -> {
            infoButton.setText("Information about Teacher");
            assignMarksAndAttendanceButton.setText("Attestation and Attendance (advisor)");
            createTechOrderButton.setText("Create Tech Order");
            enrollStudentButton.setText("Enroll Students in Discipline");
            viewEnrolledStudentsButton.setText("View Enrolled Students");
            addDiscipline.setText("Registration for disciplines (advisor)");
            addJournal.setText("Journal (advisor)");
            addTranscript.setText("Transcript (advisor)");
            publishResearchButton.setText("Publish Research");
            logoutButton.setText("Logout");

        });

        JButton rusButton = new JButton(new ImageIcon("rus_icon1.png"));
        rusButton.setToolTipText("Russian");
        rusButton.addActionListener(e -> {
            infoButton.setText("Информация об учителе");
            assignMarksAndAttendanceButton.setText("Аттестация и посещение занятий (эдвайзер)");
            createTechOrderButton.setText("Создать технический заказ");
            enrollStudentButton.setText("Зачислять студентов по дисциплине");
            viewEnrolledStudentsButton.setText("Просмотр зачисленных студентов");
            addDiscipline.setText("Регистрация на занятия по дисциплинам (эдвайзер)");
            addJournal.setText("Журнал (эдвайзер)");
            addTranscript.setText("Транскрипт (эдвайзер)");
            publishResearchButton.setText("Публиковать исследования");
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

    private void openTeacherInfoWindow() {
        JFrame infoFrame = new JFrame("Teacher Info");
        infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        infoFrame.setSize(400, 300);
        infoFrame.setLayout(new GridLayout(6, 2, 10, 10));

        Map<String, String> teacherInfo = TeacherDataHandler.loadTeacherInfo(getLogin());

        infoFrame.add(new JLabel("Login:"));
        infoFrame.add(new JLabel(teacherInfo.getOrDefault("Login", "N/A")));
        infoFrame.add(new JLabel("Name:"));
        infoFrame.add(new JLabel(teacherInfo.getOrDefault("Name", "N/A")));
        infoFrame.add(new JLabel("Surname:"));
        infoFrame.add(new JLabel(teacherInfo.getOrDefault("Surname", "N/A")));
        infoFrame.add(new JLabel("Subject:"));
        infoFrame.add(new JLabel(teacherInfo.getOrDefault("Subject", "N/A")));
        infoFrame.add(new JLabel("Teacher ID:"));
        infoFrame.add(new JLabel(teacherInfo.getOrDefault("TeacherID", "N/A")));

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> infoFrame.dispose());
        infoFrame.add(closeButton);

        infoFrame.setVisible(true);
    }

    public void openEnrollStudentsWindow() {
        JFrame frame = new JFrame("Enroll Students in Discipline");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLayout(new GridLayout(3, 2, 10, 10));

        JComboBox<Discipline> disciplineDropdown = new JComboBox<>(getDisciplines().toArray(new Discipline[0]));
        JComboBox<String> studentDropdown = new JComboBox<>();
        for (Student student : students) {
            studentDropdown.addItem(student.getLogin());
        }

        JButton enrollButton = new JButton("Enroll Student");
        enrollButton.addActionListener(e -> {
            Discipline selectedDiscipline = (Discipline) disciplineDropdown.getSelectedItem();
            String selectedStudentLogin = (String) studentDropdown.getSelectedItem();
            Student selectedStudent = findStudentByLogin(selectedStudentLogin);

            if (selectedStudent == null) {
                JOptionPane.showMessageDialog(frame, "Student not found!");
                return;
            }

            enrollStudentInDiscipline(selectedStudent, selectedDiscipline);
            JOptionPane.showMessageDialog(frame, "Student " + selectedStudent.getLogin() + " has been enrolled in " + selectedDiscipline.getName());
        });

        frame.add(new JLabel("Select Discipline:"));
        frame.add(disciplineDropdown);
        frame.add(new JLabel("Select Student:"));
        frame.add(studentDropdown);
        frame.add(enrollButton);

        frame.setVisible(true);
    }

    public void openViewEnrolledStudentsWindow() {
        JFrame frame = new JFrame("View Enrolled Students");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLayout(new BorderLayout());

        JComboBox<Discipline> disciplineDropdown = new JComboBox<>(getDisciplines().toArray(new Discipline[0]));
        JList<String> studentList = new JList<>();
        DefaultListModel<String> studentListModel = new DefaultListModel<>();
        studentList.setModel(studentListModel);

        disciplineDropdown.addActionListener(e -> {
            Discipline selectedDiscipline = (Discipline) disciplineDropdown.getSelectedItem();
            List<Student> enrolledStudents = getEnrolledStudentsInDiscipline(selectedDiscipline);

            studentListModel.clear();
            for (Student student : enrolledStudents) {
                studentListModel.addElement(student.getLogin());
            }
        });

        frame.add(new JLabel("Select Discipline:"), BorderLayout.NORTH);
        frame.add(disciplineDropdown, BorderLayout.CENTER);
        frame.add(new JScrollPane(studentList), BorderLayout.SOUTH);

        frame.setVisible(true);
    }


    private void openAddDisciplinesWindow() {
        JFrame frame = new JFrame("Add Discipline (Advisor)");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLayout(new GridLayout(3, 2, 10, 10));


        JLabel subjectLabel = new JLabel("Discipline Name:");
        JTextField subjectField = new JTextField();
        JLabel subLabel = new JLabel("Discipline Code:");
        JTextField subField = new JTextField();
        JLabel dayLabel = new JLabel("Day:");
        JTextField dayField = new JTextField();
        JLabel timeLabel = new JLabel("Time:");
        JTextField timeField = new JTextField();


        JButton createButton = new JButton("Create");
        createButton.addActionListener(e -> {
            String disciplineName = subjectField.getText();
            String disciplineCode = subField.getText();
            String day = dayField.getText();
            String time = timeField.getText();


            if (disciplineName.isEmpty() || time.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                createDiscipline(disciplineName, disciplineCode, day, time);
                JOptionPane.showMessageDialog(frame, "Discipline created: \n" +
                        "Code: " + disciplineCode + "\n" +
                        "Name: " + disciplineName + "\nDay:" + day + "\nTime: " + time);
                frame.dispose();
            }
        });
        frame.add(subLabel);
        frame.add(subField);
        frame.add(subjectLabel);
        frame.add(subjectField);
        frame.add(dayLabel);
        frame.add(dayField);
        frame.add(timeLabel);
        frame.add(timeField);
        frame.add(new JLabel());
        frame.add(createButton);

        frame.setVisible(true);
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

        JButton createButton = new JButton("Create");
        createButton.addActionListener(e -> {
            String issue = issueField.getText();
            String description = descriptionField.getText();

            if (issue.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(Oframe, "Fields cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            createTechOrder(issue, description, getLogin());
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

    @SuppressWarnings("unchecked")
    protected List<StudentAttendance> loadAttendance() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ATTENDANCE_FILE))) {
            return (List<StudentAttendance>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public List<Lesson> getAllLessons_1() {
        return new ArrayList<>();
    }

    // private Student findStudentByLogin_1(String login) {
    //     for (Student student : students) {
    //         if (student.getLogin().equals(login)) {
    //             return student;
    //         }
    //     }
    //     return null;
    // }


    public void openMarkAndAttendanceWindow() {
        JFrame frame = new JFrame("Assign Marks and Attendance");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        JComboBox<Discipline> disciplineDropdown = new JComboBox<>(getDisciplines().toArray(new Discipline[0]));
        JComboBox<String> studentDropdown = new JComboBox<>();
        for (Student student : students) {
            studentDropdown.addItem(student.getLogin());
        }

        JTextField studentLoginField = new JTextField("Enter Student Login");

        JTextField firstAttField = new JTextField("Enter First Attendance Mark");
        JTextField secondAttField = new JTextField("Enter Second Attendance Mark");
        JTextField finalExamField = new JTextField("Enter Final Exam Mark");

        JComboBox<Attendance> attendanceDropdown = new JComboBox<>(Attendance.values());

        JButton assignButton = new JButton("Assign Mark & Attendance");

        DefaultListModel<String> assignmentModel = new DefaultListModel<>();
        JList<String> assignmentList = new JList<>(assignmentModel);

        assignButton.addActionListener(e -> {
            try {
                Discipline selectedDiscipline = (Discipline) disciplineDropdown.getSelectedItem();
                String selectedStudentLogin = studentLoginField.getText().trim();
                double firstAtt = Double.parseDouble(firstAttField.getText());
                double secondAtt = Double.parseDouble(secondAttField.getText());
                double finalExam = Double.parseDouble(finalExamField.getText());
                Attendance selectedAttendance = (Attendance) attendanceDropdown.getSelectedItem();

                Student selectedStudent = findStudentByLogin(selectedStudentLogin);
                if (selectedStudent == null) {
                    JOptionPane.showMessageDialog(frame, "Student not found!");
                    return;
                }

                setMarkForDiscipline(selectedStudent, selectedDiscipline, firstAtt, secondAtt, finalExam);

                LocalDate currentDate = LocalDate.now();
                StudentAttendance attendance = new StudentAttendance(selectedStudent.getID(), selectedDiscipline, selectedAttendance, currentDate);
                selectedStudent.addAttendance(attendance);
                saveAttendance();

                String markDetails = "Student: " + selectedStudent.getLogin() +
                        ", Discipline: " + selectedDiscipline.getName() +
                        ", Marks: " + firstAtt + ", " + secondAtt + ", " + finalExam;
                String attendanceDetails = "Student: " + selectedStudent.getLogin() +
                        ", Discipline: " + selectedDiscipline.getName() +
                        ", Attendance: " + selectedAttendance;

                assignmentModel.addElement(markDetails);
                assignmentModel.addElement(attendanceDetails);

                JOptionPane.showMessageDialog(frame, "Mark and Attendance assigned successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid mark entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error assigning mark and attendance: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(8, 1, 10, 10));
        inputPanel.add(new JLabel("Select Discipline:"));
        inputPanel.add(disciplineDropdown);
        inputPanel.add(new JLabel("Select Student:"));
        inputPanel.add(studentLoginField);
        inputPanel.add(new JLabel("First Attendance Mark:"));
        inputPanel.add(firstAttField);
        inputPanel.add(new JLabel("Second Attendance Mark:"));
        inputPanel.add(secondAttField);
        inputPanel.add(new JLabel("Final Exam Mark:"));
        inputPanel.add(finalExamField);
        inputPanel.add(new JLabel("Attendance:"));
        inputPanel.add(attendanceDropdown);
        inputPanel.add(assignButton);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(assignmentList), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private Student findStudentByLogin(String login) {
        for (Student student : students) {
            if (student.getLogin().equals(login)) {
                return student;
            }
        }
        return null;
    }

    private void publishResearchPaperUI() {
        JFrame publishFrame = new JFrame("Publish Research Paper");
        publishFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        publishFrame.setSize(400, 300);
        publishFrame.setLayout(new GridLayout(4, 1, 10, 10));

        JTextField titleField = new JTextField("Title");
        JTextField authorsField = new JTextField("Authors");
        JTextField journalField = new JTextField("Journal");
        JTextField pagesField = new JTextField("Pages");
        JTextField doiField = new JTextField("DOI");
        JTextField citationsField = new JTextField("Citations");

        JButton publishButton = new JButton("Publish");
        publishButton.addActionListener(e -> {
            String title = titleField.getText();
            String authors = authorsField.getText();
            String journal = journalField.getText();
            int pages = Integer.parseInt(pagesField.getText());
            String doi = doiField.getText();
            int citations = Integer.parseInt(citationsField.getText());

            publishResearchPaper(title, authors, journal, pages, doi, citations);
            JOptionPane.showMessageDialog(publishFrame, "Research paper published as pinned news.");
            publishFrame.dispose();
        });

        publishFrame.add(titleField);
        publishFrame.add(authorsField);
        publishFrame.add(journalField);
        publishFrame.add(pagesField);
        publishFrame.add(doiField);
        publishFrame.add(citationsField);
        publishFrame.add(publishButton);

        publishFrame.setVisible(true);
    }

    public void openNewsWindow() {
        News.loadNewsCatalog();
        // NewsCatalog.sort(News.getNewsCatalog());


        JFrame newsFrame = new JFrame("News");
        newsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newsFrame.setSize(500, 400);
        newsFrame.setLayout(new BorderLayout());

        DefaultListModel<String> newsModel = new DefaultListModel<>();
        for (News news : News.getNewsCatalog()) {
            newsModel.addElement(news.toString());
        }

        JList<String> newsJList = new JList<>(newsModel);
        newsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        newsFrame.add(new JScrollPane(newsJList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addCommentButton = new JButton("Add Comment");
        JButton closeButton = new JButton("Close");

        addCommentButton.addActionListener(e -> {
            int selectedIndex = newsJList.getSelectedIndex();
            if (selectedIndex >= 0) {
                News selectedNews = News.getNewsCatalog().get(selectedIndex);
                String comment = JOptionPane.showInputDialog(newsFrame, "Enter your comment:");
                if (comment != null && !comment.isEmpty()) {
                    selectedNews.addComment(comment);
                    News.saveNewsCatalog();
                    newsModel.set(selectedIndex, selectedNews.toString());
                }
            } else {
                JOptionPane.showMessageDialog(newsFrame, "Please select a news item to comment on.");
            }
        });

        closeButton.addActionListener(e -> newsFrame.dispose());

        buttonPanel.add(addCommentButton);
        buttonPanel.add(closeButton);
        newsFrame.add(buttonPanel, BorderLayout.SOUTH);

        newsFrame.setVisible(true);
    }
    protected List<Mark> loadMarks() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MARKS_FILE))) {
            List<Mark> marks = (List<Mark>) ois.readObject();
            lastMarkID = (int) ois.readObject();
            return marks;
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public List<Lesson> getAllLessons() {
        return lessons;
    }
}
