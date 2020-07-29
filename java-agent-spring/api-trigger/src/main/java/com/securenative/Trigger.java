package com.securenative;

public class Trigger {
    public static void main(String[] args) {
        long delay = 0;
        if (System.getenv("EXECUTE_DELAY") != null) {
            delay = Long.parseLong(System.getenv("EXECUTE_DELAY"));
        }

        long period = 1000 * 5 * 60;
        if (System.getenv("EXECUTE_PERIOD") != null) {
            period = Long.parseLong(System.getenv("EXECUTE_PERIOD"));
        }

        String name = "Periodic api calls";
        Runnable task = ApiClient.trigger();

        ExecuteManager manager = new ExecuteManager(delay, period, name, task);
        manager.execute();
    }
}
