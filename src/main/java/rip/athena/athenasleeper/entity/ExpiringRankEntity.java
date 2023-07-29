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
    @Column(name = "User_ID")
    private String userEntityUuid;
    @ManyToOne
    @JoinColumn(name = "Rank_After_Expiration")
    private RankEntity rankEntity;
    @Column(name = "Timestamp_At_Expiration")
    private long timestampAtExpiration;
}
