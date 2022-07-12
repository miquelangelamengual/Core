package es.hulk.core.utils.task;

/*
 * Copyright (c) 2020, Jordi Xavier. All rights reserved.
 *
 * Do not redistribute without permission from the author.
 */

import es.hulk.core.Core;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskUtil {

    private static final Core core = Core.getInstance();

    public static void runTaskAsync(Runnable runnable) {
        core.getServer().getScheduler().runTaskAsynchronously(core, runnable);
    }

    public static void runTaskLater(Runnable runnable, long delay) {
        core.getServer().getScheduler().runTaskLater(core, runnable, delay);
    }

    public static void runTaskLaterAsync(Runnable runnable, long delay) {
        core.getServer().getScheduler().runTaskLaterAsynchronously(core, runnable, delay);
    }

    public static void runTaskTimer(BukkitRunnable runnable, long delay, long timer) {
        runnable.runTaskTimer(core, delay, timer);
    }

    public static void runTaskTimer(Runnable runnable, long delay, long timer) {
        core.getServer().getScheduler().runTaskTimer(core, runnable, delay, timer);
    }

    public static void runTaskTimerAsync(Runnable runnable, long delay, long timer) {
        core.getServer().getScheduler().runTaskTimerAsynchronously(core, runnable, delay, timer);
    }

    public static void run(Runnable runnable) {
        core.getServer().getScheduler().runTask(core, runnable);
    }
}
