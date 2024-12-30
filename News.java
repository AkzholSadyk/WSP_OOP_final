import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class News implements Comparable<News>, Serializable {
    private String title;
    private String content;
    private boolean isPinned;
    private ArrayList<String> comments;
    private List<Observer> observers;

    private static List<News> newsCatalog = new ArrayList<>();
    private static final String FILE_NAME = "news_catalog.ser";

    public News(String title, String content, boolean isPinned) {
        this.title = title;
        this.content = content;
        this.isPinned = isPinned;
        this.comments = new ArrayList<>();
        addNewsToCatalog(this);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<String> getComments() {
        return comments;
    }
    private static void addNewsToCatalog(News news) {
        newsCatalog.add(news);
        saveNewsCatalog();
    }

    public static void removeNewsFromCatalog(News news) {
        newsCatalog.remove(news);
        saveNewsCatalog();
    }

    public static List<News> getNewsCatalog() {
        return Collections.unmodifiableList(newsCatalog);
    }

    public void addComment(String comment) {
        comments.add(comment);
        saveNewsCatalog();
    }

    public void addObserver(Observer observer) {}

    public void removeObserver(Observer observer) {}

    public void notifyObservers() {}

    public boolean isPinned() {
        return isPinned;
    }

    public void pinNews() {
        this.isPinned = true;
        saveNewsCatalog();
    }

    @Override
    public int compareTo(News other) {
        return Boolean.compare(other.isPinned, this.isPinned);
    }

    @Override
    public String toString() {
        return String.format("Title: %s, Pinned: %b, Content: %s, Comments: %s",
                title, isPinned, content, comments);
    }

    public static void saveNewsCatalog() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(newsCatalog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadNewsCatalog() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            newsCatalog = (List<News>) ois.readObject();
        } catch (FileNotFoundException e) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
