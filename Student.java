import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class Student extends User {
    private final String name;
    private final String surname;
    private final SchoolType school;
    private final int course;
    private final int studentID;
    private List<Course> courses;
    private List<Mark> marks = new ArrayList<>();
    private List<StudentAttendance> attendances;
    private List<Discipline> acceptedDisciplines;

    public Student(String login, String password, String name, String surname, SchoolType school, int course, int studentID) {
        super(login, password);
        this.name = name;
        this.surname = surname;
        this.school = school;
        this.course = course;
        this.studentID = studentID;
        this.courses = new ArrayList<>();
        this.acceptedDisciplines = new ArrayList<>();
    }





    public List<Mark> getMarksForDiscipline(Discipline discipline) {
        List<Mark> marksForDiscipline = new ArrayList<>();
        for (Mark mark : getMarks()) {
            if (mark.getDiscipline().equals(discipline)) {
                marksForDiscipline.add(mark);
            }
        }
        return marksForDiscipline;
    }

    public List<StudentAttendance> getAttendanceForDiscipline(Discipline discipline) {
        List<StudentAttendance> attendanceForDiscipline = new ArrayList<>();
        for (StudentAttendance attendance : getAttendances()) {
            if (attendance.getDiscipline().equals(discipline)) {
                attendanceForDiscipline.add(attendance);
            }
        }
        return attendanceForDiscipline;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public List<StudentAttendance> getAttendances() {
        return attendances;
    }

    public void addMark(Mark mark) {
        marks.add(mark);
    }

    public void addAttendance(StudentAttendance attendance) {
        this.attendances.add(attendance);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void enrollInCourse(Course course) {
        this.courses.add(course);
    }

    public int getID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void enrollInDiscipline(Discipline discipline) {
        this.acceptedDisciplines.add(discipline);
        discipline.addStudent(this);
    }

    public void dropDiscipline(Discipline discipline) {
        this.acceptedDisciplines.remove(discipline);
        discipline.removeStudent(this);
    }

    public List<Discipline> getAcceptedDisciplines() {
        return acceptedDisciplines;
    }

    @Override
    public void openPanel() {
        JFrame frame = new JFrame("Student");
        ImageIcon icon = new ImageIcon("WSP_logo.png");
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 1, 10, 10));

        JButton infobutton = new JButton("Information about Student");
        infobutton.addActionListener(e -> openStudentInfoWindow());


        JButton viewMarksAndAttendanceButton = new JButton("Journal");
        viewMarksAndAttendanceButton.addActionListener(e -> openViewMarksAndAttendanceWindow());

        JButton newsButton = new JButton("News");
        newsButton.addActionListener(e -> openNewsWindow());

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout(frame));

        JButton viewDisciplines = new JButton("View Disciplines");
        viewDisciplines.addActionListener(e -> openViewDisciplinesWindow());

        frame.add(infobutton);

        frame.add(viewMarksAndAttendanceButton);
        frame.add(newsButton);
        JButton addTranscript = new JButton("Transcript");
        frame.add(addTranscript);
        frame.add(viewDisciplines);
        frame.add(logoutButton);

        frame.setVisible(true);
        JPanel languagePanel = new JPanel();
        languagePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton kazButton = new JButton(new ImageIcon("kaz_icon1.png"));
        kazButton.setToolTipText("Kazakh");
        kazButton.addActionListener(e ->{
            infobutton.setText("Студент туралы ақпарат");
            viewMarksAndAttendanceButton.setText("Журнал");
            newsButton.setText("Жаңалықтар");
            viewDisciplines.setText("Дисциплинаға тіркелу");
            addTranscript.setText("Транскрипт");
            logoutButton.setText("Шығу");
        });

        JButton engButton = new JButton(new ImageIcon("eng_icon1.png"));
        engButton.setToolTipText("English");
        engButton.addActionListener(e -> {
            infobutton.setText("Information about Student");
            viewMarksAndAttendanceButton.setText("Journal");
            newsButton.setText("News");
            viewDisciplines.setText("View Disciplines");
            addTranscript.setText("Transcript");
            logoutButton.setText("Logout");

        });

        JButton rusButton = new JButton(new ImageIcon("rus_icon1.png"));
        rusButton.setToolTipText("Russian");
        rusButton.addActionListener(e -> {
            infobutton.setText("Информация о студенте");
            viewMarksAndAttendanceButton.setText("Журнал");
            newsButton.setText("Новости");
            viewDisciplines.setText("Регистрация на дисциплин");
            addTranscript.setText("Transcript");
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

    public void openViewDisciplinesWindow() {
        JFrame frame = new JFrame("View and Manage Disciplines");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        DefaultListModel<String> disciplinesModel = new DefaultListModel<>();
        for (Discipline discipline : Teacher.getDisciplines()) {
            disciplinesModel.addElement(discipline.toString());
        }

        JList<String> disciplinesList = new JList<>(disciplinesModel);
        disciplinesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        frame.add(new JScrollPane(disciplinesList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton acceptButton = new JButton("Accept Discipline");
        JButton dropButton = new JButton("Drop Discipline");
        JButton closeButton = new JButton("Close");

        acceptButton.addActionListener(e -> {
            int selectedIndex = disciplinesList.getSelectedIndex();
            if (selectedIndex >= 0) {
                Discipline selectedDiscipline = Teacher.getDisciplines().get(selectedIndex);
                selectedDiscipline.addStudent(this);
                JOptionPane.showMessageDialog(frame, "You have accepted the discipline: " + selectedDiscipline.getName());
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a discipline to accept.");
            }
        });

        dropButton.addActionListener(e -> {
            int selectedIndex = disciplinesList.getSelectedIndex();
            if (selectedIndex >= 0) {
                Discipline selectedDiscipline = Teacher.getDisciplines().get(selectedIndex);
                selectedDiscipline.removeStudent(this);
                JOptionPane.showMessageDialog(frame, "You have dropped the discipline: " + selectedDiscipline.getName());
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a discipline to drop.");
            }
        });

        closeButton.addActionListener(e -> frame.dispose());

        buttonPanel.add(acceptButton);
        buttonPanel.add(dropButton);
        buttonPanel.add(closeButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }


    private void openStudentInfoWindow() {
        JFrame infoFrame = new JFrame("Student Info");
        infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        infoFrame.setSize(400, 300);
        infoFrame.setLayout(new GridLayout(7, 2, 10, 10));

        Map<String, String> studentInfo = StudentDataHandler.loadStudentInfo();

        infoFrame.add(new JLabel("Login:"));
        infoFrame.add(new JLabel(studentInfo.getOrDefault("Login", "N/A")));
        infoFrame.add(new JLabel("Name:"));
        infoFrame.add(new JLabel(studentInfo.getOrDefault("Name", "N/A")));
        infoFrame.add(new JLabel("Surname:"));
        infoFrame.add(new JLabel(studentInfo.getOrDefault("Surname", "N/A")));
        infoFrame.add(new JLabel("School:"));
        infoFrame.add(new JLabel(studentInfo.getOrDefault("School", "N/A")));
        infoFrame.add(new JLabel("Course:"));
        infoFrame.add(new JLabel(studentInfo.getOrDefault("Course", "N/A")));
        infoFrame.add(new JLabel("Student ID:"));
        infoFrame.add(new JLabel(studentInfo.getOrDefault("StudentID", "N/A")));

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> infoFrame.dispose());
        infoFrame.add(closeButton);

        infoFrame.setVisible(true);
    }

    private void openViewMarksAndAttendanceWindow() {
        JFrame frame = new JFrame("View Marks and Attendance");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        DefaultListModel<String> disciplinesModel = new DefaultListModel<>();
        for (Discipline discipline : Teacher.getDisciplines()) {
            disciplinesModel.addElement(discipline.toString());
        }
        JList<String> disciplinesList = new JList<>(disciplinesModel);
        disciplinesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        frame.add(new JScrollPane(disciplinesList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton viewMarksButton = new JButton("View Marks");
        JButton viewAttendanceButton = new JButton("View Attendance");
        JButton closeButton = new JButton("Close");

        viewMarksButton.addActionListener(e -> {
            int selectedIndex = disciplinesList.getSelectedIndex();
            if (selectedIndex >= 0) {
                Discipline selectedDiscipline = Teacher.getDisciplines().get(selectedIndex);
                List<Mark> marks = getMarksForDiscipline(selectedDiscipline);
                StringBuilder marksInfo = new StringBuilder("Marks for " + selectedDiscipline.getName() + ":\n");
                for (Mark mark : marks) {
                    marksInfo.append("Student ID: ").append(mark.getStudentID())
                            .append(" Mark: ").append(mark.getMark()).append("\n");
                }
                JOptionPane.showMessageDialog(frame, marksInfo.toString());
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a discipline.");
            }
        });
        viewAttendanceButton.addActionListener(e -> {
            int selectedIndex = disciplinesList.getSelectedIndex();
            if (selectedIndex >= 0) {
                Discipline selectedDiscipline = Teacher.getDisciplines().get(selectedIndex);
                List<StudentAttendance> attendance = getAttendanceForDiscipline(selectedDiscipline);
                StringBuilder attendanceInfo = new StringBuilder("Attendance for " + selectedDiscipline.getName() + ":\n");
                for (StudentAttendance record : attendance) {
                    attendanceInfo.append("Student ID: ").append(record.getStudentID())
                            .append(" Date: ").append(record.getDate())
                            .append(" Present: ").append(record.getAttendance().name())
                            .append("\n");
                }
                JOptionPane.showMessageDialog(frame, attendanceInfo.toString());
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a discipline.");
            }
        });

        // Close button
        closeButton.addActionListener(e -> frame.dispose());

        // Add buttons to panel
        buttonPanel.add(viewMarksButton);
        buttonPanel.add(viewAttendanceButton);
        buttonPanel.add(closeButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Make the frame visible
        frame.setVisible(true);
    }



    private void openNewsWindow() {
        News.loadNewsCatalog();
        Collections.sort(News.getNewsCatalog());

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
}
