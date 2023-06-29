package rip.athena.AthenaSleeper.services;

import rip.athena.AthenaSleeper.model.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    List<User> getUsers();

    User addUser(User user);

    boolean removeUser(UUID uuid);

    User getUserById(UUID uuid);

    User updateUser(UUID uuid, User user);
}
