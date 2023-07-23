package rip.athena.athenasleeper.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ActiveInfo implements Serializable {
    private UUID m_uuid;
    private Integer m_activeCosmetic;
    private String m_iconUrl;
}
