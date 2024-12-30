public class ManagerFactory extends UserFactory {
    @Override
    public User createUser(String login, String password) {
        return new Manager(login, password);
    }
}
