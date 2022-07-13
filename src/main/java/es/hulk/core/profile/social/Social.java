package es.hulk.core.profile.social;

import com.google.common.collect.Lists;
import es.hulk.core.profile.Profile;
import es.hulk.core.profile.social.objects.SocialObject;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.util.List;

@Getter
public class Social {

    private final List<Social> socials = Lists.newArrayList();

    private final SocialNetwork socialNetwork;
    private final String socialLink;

    public Social(SocialNetwork socialNetwork, String socialLink) {
        this.socialNetwork = socialNetwork;
        this.socialLink = socialLink;
    }

    public enum SocialNetwork {
        TWITTER, TWITCH, YOUTUBE, INSTAGRAM, DISCORD, TELEGRAM
    }

    public String toJSON() {
        JSONObject object = new JSONObject();

        object.put("network", this.socialNetwork.name());
        object.put("link", this.socialLink);

        return object.toString();
    }

    public static Social fromJSON(String json) {
        JSONObject object = new JSONObject(json);

        return new Social(
                SocialNetwork.valueOf(object.getString("network")),
                object.getString("link")
        );
    }

}
