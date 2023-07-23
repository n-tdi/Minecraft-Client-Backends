package rip.athena.athenasleeper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ActiveInfo implements Serializable {
    private String type;
    private String uuid;
    private AvailableCosmeticEntity activeCosmetic;
    private String iconUrl;
}
