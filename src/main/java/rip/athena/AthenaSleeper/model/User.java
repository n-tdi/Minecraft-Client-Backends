package rip.athena.AthenaSleeper.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID uuid;
    private String username;
    private boolean online;
    private String rank;
    private List<Integer> cosmetics;
    private UUID passphrase;
}
