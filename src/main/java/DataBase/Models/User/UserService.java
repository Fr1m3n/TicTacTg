package DataBase.Models.User;

public interface UserService {
    UserRecord getUserOrCreate(Long id);
}
