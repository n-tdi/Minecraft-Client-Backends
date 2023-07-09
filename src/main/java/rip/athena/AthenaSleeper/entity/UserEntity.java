package rip.athena.AthenaSleeper.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class UserEntity {
    @Id
    private UUID uuid;
    private String username;
    private boolean online;
    private String rank;
    @ElementCollection(targetClass = Integer.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "cosmetics", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "cosmetic", nullable = false)
    private List<Integer> cosmetics;
    @ElementCollection(targetClass = UUID.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "friends", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "friends", nullable = false)
    private List<UUID> friends;
    private UUID passphrase;
}
