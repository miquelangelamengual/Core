package es.hulk.core.punishments;

import es.hulk.core.utils.chat.ChatUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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

    public void punish() {
        Player player = Bukkit.getPlayer(this.punishedUUID);
        if(player == null) return;

        switch(this.type) {
            case KICK:
                player.kickPlayer(ChatUtil.translate(this.madeOn + "\n\n&fReason&7: &b" + this.reason));
                break;
            case WARN:
                player.sendMessage(ChatUtil.translate(this.reason + "."));
                break;
            case BAN:
                player.kickPlayer(ChatUtil.translate(this.madeOn + "\n\n&fReason&7: &b" + this.reason + "\n&fExpires In&7: &b" + this.expiresOn));
                break;
        }
    }

}
