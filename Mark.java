import java.util.ArrayList;
import java.util.List;

public class Mark {
    private int markID;
    private static List<Mark> mark = new ArrayList<>();
    private int studentID;
    private Discipline discipline;
    private double firstAtt;
    private double secondAtt;
    private double finalExam;

    public Mark(int markID, int studentID, Discipline discipline, double firstAtt, double secondAtt, double finalExam) {
        this.markID = markID;
        this.studentID = studentID;
        this.discipline = discipline;
        this.firstAtt = firstAtt;
        this.secondAtt = secondAtt;
        this.finalExam = finalExam;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public int getMarkID() {
        return markID;
    }

    public int getStudentID() {
        return studentID;
    }

    public static List<Mark> getMark()
    {
        return mark;
    }
    public double getFirstAtt() {
        return firstAtt;
    }

    public double getSecondAtt() {
        return secondAtt;
    }

    public double getFinalExam() {
        return finalExam;
    }

    public double getTotal() {
        return firstAtt + secondAtt + finalExam;
    }
}

