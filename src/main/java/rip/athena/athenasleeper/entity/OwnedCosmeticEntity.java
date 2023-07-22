package rip.athena.athenasleeper.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "owned_cosmetics")
public class OwnedCosmeticEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "User_ID")
    private UserEntity userEntity;
    @ManyToOne
    @JoinColumn(name = "Owned_Cosmetic")
    private AvailableCosmeticEntity availableCosmeticEntity;
}
