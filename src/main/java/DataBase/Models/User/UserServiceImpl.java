package DataBase.Models.User;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public UserRecord getUserOrCreate(Long id) {
        UserRecord user = userDAO.getById(id);

        if (user == null) {
            userDAO.save(id);
            user = userDAO.getById(id);
        }

        return user;
    }


}
