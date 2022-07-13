package es.hulk.core.rank.commands;

import com.google.common.collect.Lists;
import es.hulk.core.Core;
import es.hulk.core.rank.commands.impl.*;

import java.util.Arrays;
import java.util.List;

public class RankCommandManager {

    public RankCommandManager() {
        List<Object> rankCommands = Arrays.asList(
                new RankCommand(),
                new RankDeleteCommand(),
                new RankCreateCommand(),
                new RankListCommand(),
                new RankSetCommand(),
                new RankSetColorCommand(),
                new RankSetPrefixCommand(),
                new RankSetSuffixCommand(),
                new RankSetPriorityCommand(),
                new RankRenameCommand(),
                new RankAddPermCommand(),
                new RankSetDefaultCommand(),
                new RankInfoCommand(),
                new RankDeletePermissionCommand()
        );

        for (Object rankCommand : rankCommands) {
            Core.getInstance().getCommandManager().registerCommands(rankCommand, Lists.newArrayList());
        }
    }
}
