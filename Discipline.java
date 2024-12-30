import java.util.List;
import java.util.ArrayList;

public class Discipline {
    private String code;
    private String name;
    private String day;
    private String time;
    private List<Student> acceptedStudents;

    public Discipline(String code, String name, String day, String time) {
        this.code = code;
        this.name = name;
        this.day = day;
        this.time = time;
        this.acceptedStudents = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public List<Student> getAcceptedStudents() {
        return acceptedStudents;
    }

    public void addStudent(Student student) {
        if (!acceptedStudents.contains(student)) {
            acceptedStudents.add(student);
        }
    }

    public void removeStudent(Student student) {
        acceptedStudents.remove(student);
    }

    @Override
    public String toString() {
        return name + " (" + code + ") - " + day + " at " + time;
    }
}
