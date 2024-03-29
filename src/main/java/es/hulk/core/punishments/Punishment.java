package es.hulk.core.punishments;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@RequiredArgsConstructor
@Getter @Setter
public class Punishment {

    private String id;
    private UUID punishedUUID;
    private String punisher;
    private PunishmentType type;
    private String reason, madeOn, expiresOn, duration, punishedIP;
    private boolean ip, silent, active;

    public boolean isPermanent() {
        return this.duration.equalsIgnoreCase("Permanent") || this.expiresOn.equalsIgnoreCase("Never");
    }

}
