package rip.athena.AthenaSleeper.services;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import rip.athena.AthenaSleeper.entity.UserEntity;
import rip.athena.AthenaSleeper.model.User;
import rip.athena.AthenaSleeper.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<User> users = userEntities.stream().map(
                user -> new User(
                        user.getUuid(), user.getUsername(), user.isOnline(), user.getRank(), user.getCosmetics(), user.getFriends()))
                .toList();

        return users;
    }

    @Override
    public User addUser(User user) {
        UserEntity userEntity = new UserEntity();

        BeanUtils.copyProperties(user, userEntity);
        userRepository.save(userEntity);
        return user;
    }

    @Override
    public boolean removeUser(UUID uuid) {
        UserEntity userEntity = userRepository.findById(uuid).get();
        userRepository.delete(userEntity);

        return true;
    }

    @Override
    public User getUserById(UUID uuid) {
        UserEntity userEntity = userRepository.findById(uuid).get();
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        return user;
    }

    @Override
    public User updateUser(UUID uuid, User user) {
        UserEntity userEntity = userRepository.findById(uuid).get();
        userEntity.setUsername(user.getUsername());
        userEntity.setOnline(user.isOnline());
        userEntity.setUsername(user.getUsername());
        userEntity.setCosmetics(user.getCosmetics());
        userRepository.save(userEntity);
        return user;
    }
}
