package DataBase.Models.User;

public interface UserDAO {
    UserRecord getById(Long id);
    void save(UserRecord user);
    void update(UserRecord user);
    void save(Long id);

}
