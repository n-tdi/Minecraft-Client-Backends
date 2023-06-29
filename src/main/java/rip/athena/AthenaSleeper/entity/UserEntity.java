package rip.athena.AthenaSleeper.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
public class UserEntity {
    @Id
    private UUID uuid;
    private String username;
    private boolean online;
    private String rank;
    private List<Integer> cosmetics;
    private UUID passphrase;
}
