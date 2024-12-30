public class Lesson {
    private int lessonID;
    private String title;
    private String content;
    private String schedule;
    private Course course;

    public Lesson(int lessonID, String title, String content, String schedule, Course course) {
        this.lessonID = lessonID;
        this.title = title;
        this.content = content;
        this.schedule = schedule;
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public int getLessonID() {
        return lessonID;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSchedule() {
        return schedule;
    }
}
