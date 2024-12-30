import java.io.Serializable;
import java.time.LocalDate;

public class StudentAttendance implements Serializable {
    private static final long serialVersionUID = 1L;

    private int studentID;
    private Discipline discipline;
    private Attendance attendance;
    private LocalDate date;

    public StudentAttendance(int studentID, Discipline discipline, Attendance attendance, LocalDate date) {
        this.studentID = studentID;
        this.discipline = discipline;
        this.attendance = attendance;
        this.date = date;
    }

    public int getStudentID() {
        return studentID;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentID + " Date: " + date + " Attendance: " + attendance.name();
    }
}

