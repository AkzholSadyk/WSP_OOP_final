import java.io.Serializable;


abstract class User implements Serializable {
    private String login;
    private String password;
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract void openPanel();

    public String getRole() {
        return this.getClass().getSimpleName();
    }


}