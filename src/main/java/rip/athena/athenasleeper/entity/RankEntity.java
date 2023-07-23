package rip.athena.athenasleeper.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ranks")
public class RankEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Rank_ID")
    private int rankId;
    @Column(name = "Rank_Name")
    private String rankName;
    @Column(name = "Asset_Location")
    private String assetLocation;
}
