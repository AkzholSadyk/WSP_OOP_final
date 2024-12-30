import java.io.*;
import java.util.*;

public class StudentDataHandler {
    private static final String FILE_PATH = "student_info.txt";

    public static void saveStudentInfo(String login, String name, String surname, String school, int course, int studentID) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, false))) {
            writer.println("Login: " + login);
            writer.println("Name: " + name);
            writer.println("Surname: " + surname);
            writer.println("School: " + school);
            writer.println("Course: " + course);
            writer.println("StudentID: " + studentID);
        } catch (IOException e) {
            System.err.println("Error saving student info: " + e.getMessage());
        }
    }

    public static Map<String, String> loadStudentInfo() {
        Map<String, String> studentInfo = new HashMap<>();
        File file = new File(FILE_PATH);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    studentInfo.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading student info: " + e.getMessage());
        }

        return studentInfo;
    }
}
