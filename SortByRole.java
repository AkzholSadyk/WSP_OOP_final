import java.util.Comparator;
import java.util.List;

public class SortByRole implements SortStrategy {
    @Override
    public void sort(List<User> users) {
        users.sort(Comparator.comparing(User::getRole));
    }
}