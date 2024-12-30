import java.util.ArrayList;
import java.util.List;

public class Course {
    private int courseID;
    private String title;
    private String description;
    private List<Lesson> lessons;
    private List<Mark> marks;

    public Course(int courseID, String title, String description) {
        this.courseID = courseID;
        this.title = title;
        this.description = description;
        this.lessons = new ArrayList<>();
        this.marks = new ArrayList<>();
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }

    public void addMark(Mark mark) {
        marks.add(mark);
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

