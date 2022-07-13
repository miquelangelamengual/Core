package es.hulk.core.profile.social.objects;

import com.google.common.collect.Lists;
import es.hulk.core.profile.Profile;
import es.hulk.core.profile.social.Social;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SocialObject {

    private final List<SocialObject> socialNetworks = Lists.newArrayList();

    private final Profile profile;

    private Social twitter;
    private Social twitch;
    private Social instagram;
    private Social youtube;
    private Social discord;
    private Social telegram;

    public SocialObject(Profile profile) {
        this.profile = profile;

        this.socialNetworks.add(this);
    }

    public String toJSON() {
        JSONObject object = new JSONObject();

        object.put("uuid", this.profile.getUuid().toString());
        object.put("twitter", this.twitter.toJSON());
        object.put("twitch", this.twitch.toJSON());
        object.put("instagram", this.instagram.toJSON());
        object.put("youtube", this.youtube.toJSON());
        object.put("discord", this.discord.toJSON());
        object.put("telegram", this.telegram.toJSON());

        return object.toString();
    }

    public static SocialObject fromJSON(String json) {
        JSONObject object = new JSONObject(json);

        SocialObject socialObject = new SocialObject(Profile.getProfile(Bukkit.getPlayer(UUID.fromString(object.getString("uuid")))));
        socialObject.setTwitter(Social.fromJSON(object.getString("twitter")));
        socialObject.setTwitch(Social.fromJSON(object.getString("twitch")));
        socialObject.setInstagram(Social.fromJSON(object.getString("instagram")));
        socialObject.setYoutube(Social.fromJSON(object.getString("youtube")));
        socialObject.setDiscord(Social.fromJSON(object.getString("discord")));
        socialObject.setTelegram(Social.fromJSON(object.getString("telegram")));

        return socialObject;
    }
}
