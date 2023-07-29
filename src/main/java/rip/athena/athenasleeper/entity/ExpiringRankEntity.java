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
@Table(name = "expiring_rank")
public class ExpiringRankEntity {
    @Id
    @OneToOne
    @JoinColumn(name = "User_ID")
    private UserEntity userEntity;
    @ManyToOne
    @JoinColumn(name = "Rank_After_Expiration")
    private RankEntity rankEntity;
    @Column(name = "Timestamp_At_Expiration")
    private long timestampAtExpiration;
}
