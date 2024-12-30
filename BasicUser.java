import javax.swing.*;

public class BasicUser extends User{
    public BasicUser(String login, String password) {
        super(login, password);
    }

    @Override
    public void openPanel() {
        JFrame frame = new JFrame("Basic User Panel");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 200);

        frame.setVisible(true);
    }
}