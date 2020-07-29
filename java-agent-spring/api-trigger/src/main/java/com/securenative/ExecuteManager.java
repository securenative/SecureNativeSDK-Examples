package com.securenative;

import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecuteManager {
    private final ScheduledExecutorService execute;
    private final long delay;
    private final long period;
    private final Runnable task;
    private final String name;
    private final Logger logger;

    public ExecuteManager(long delay, long period, String name, Runnable task) {
        this.execute = Executors.newSingleThreadScheduledExecutor();
        this.delay = delay;
        this.period = period;
        this.task = task;
        this.name = name;
        this.logger = java.util.logging.Logger.getLogger("main");
        this.logger.setLevel(Level.ALL);
    }

    public void execute() {
        try {
            this.logger.log(Level.INFO, String.format("Executing task %s", this.name));
            this.execute.scheduleAtFixedRate(this.task, this.delay, this.period, TimeUnit.MILLISECONDS);
        } catch (IllegalArgumentException | NullPointerException | RejectedExecutionException e) {
            this.logger.log(Level.INFO, String.format("Could not start executing task %s; %s", this.task.toString(), e));
        }
    }
}
