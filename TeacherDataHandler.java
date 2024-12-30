import java.io.*;
import java.util.*;

public class TeacherDataHandler {
    private static final String FILE_PATH = "teacher_info.txt";

    public static void saveTeacherInfo(String login, String name, String surname, String subject, int teacherID) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, false))) {
            writer.println("Login: " + login);
            writer.println("Name: " + name);
            writer.println("Surname: " + surname);
            writer.println("Subject: " + subject);
            writer.println("TeacherID: " + teacherID);
        } catch (IOException e) {
            System.err.println("Error saving teacher info: " + e.getMessage());
        }
    }

    public static Map<String, String> loadTeacherInfo(String login) {
        Map<String, String> teacherInfo = new HashMap<>();
        File file = new File(FILE_PATH);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    teacherInfo.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading teacher info: " + e.getMessage());
        }

        return teacherInfo;
    }
}
