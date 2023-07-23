package rip.athena.athenasleeper.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/*
  Notes for Ziue: This is how we create a java representation of a sql row.
  The row must match these constraints (aka be in the exact table)
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class UserEntity implements Serializable {
    @Id
    @Column(name = "User_ID")
    private String uuid;
    @Column(name = "Username")
    private String username;
    @Column(name = "Online_Status")
    private Boolean online;
    @ManyToOne
    @JoinColumn(name = "Rank_Entity")
    private RankEntity rankEntity;
    @ManyToOne
    @JoinColumn(name = "Equipped_Cosmetic")
    private AvailableCosmeticEntity availableCosmeticEntity;
}
