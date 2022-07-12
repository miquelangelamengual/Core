package es.hulk.core.rank;

import es.hulk.core.Core;

import java.util.Map;

public class RankManager {

    public Rank getDefaultRank() {
        Rank defaultRank = null;
        if (this.getRank("user") == null) {
            defaultRank = new Rank("user");
            defaultRank.setDefaultRank(true);
        } else {
            defaultRank = this.getRank("user");
        }
        return defaultRank;
    }

    public Rank getRank(String name) {
        for (Map.Entry<Rank, String> entry : Rank.getRanks().entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
