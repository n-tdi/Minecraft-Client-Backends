package rip.athena.athenasleeper.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cosmetic_for_rank")
public class CosmeticForRankEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "Rank_ID")
    private RankEntity rankEntity;
    @ManyToOne
    @JoinColumn(name = "Cosmetic")
    private AvailableCosmeticEntity availableCosmeticEntity;
}
