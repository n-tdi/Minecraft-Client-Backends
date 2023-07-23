package rip.athena.athenasleeper.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ActiveInfo implements Serializable {
    private String type;
    private String uuid;
    private Integer activeCosmetic;
    private String iconUrl;
}
