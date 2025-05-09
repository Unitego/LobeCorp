package net.unitego.lobecorp.common.util;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BooleanSupplier;

public class DelayedTaskUtils {
    private static final List<DelayedTask> TASKS = new LinkedList<>();
    private static boolean initialized = false;

    private static void init() {
        if (!initialized) {
            NeoForge.EVENT_BUS.register(DelayedTaskUtils.class);
            initialized = true;
        }
    }

    public static void runLater(int delayTicks, Runnable runnable) {
        runLater(delayTicks, runnable, null);
    }

    public static void runLater(int delayTicks, Runnable runnable, BooleanSupplier condition) {
        init();
        TASKS.add(new DelayedTask(delayTicks, runnable, condition));
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        Iterator<DelayedTask> iterator = TASKS.iterator();
        while (iterator.hasNext()) {
            DelayedTask task = iterator.next();
            task.delay--;

            if (task.delay <= 0) {
                if (task.condition == null || task.condition.getAsBoolean()) {
                    task.runnable.run();
                }
                iterator.remove();
            }
        }
    }

    private static class DelayedTask {
        int delay;
        final Runnable runnable;
        final BooleanSupplier condition;

        DelayedTask(int delay, Runnable runnable, BooleanSupplier condition) {
            this.delay = delay;
            this.runnable = runnable;
            this.condition = condition;
        }
    }
}
