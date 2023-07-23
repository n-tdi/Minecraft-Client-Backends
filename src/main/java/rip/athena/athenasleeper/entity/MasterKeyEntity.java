package rip.athena.athenasleeper.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "master_key")
public class MasterKeyEntity implements Serializable {
    @Id
    @Column(name = "Key")
    private String key;
    @Basic(optional = false)
    @Column(name = "Creation_Date", insertable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    private Date creationDate;
}
