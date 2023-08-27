package com.iluwatar.activeobject.excersice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UnitScheduler {
    // thread pool shared by multiple unit
    private ExecutorService executor = Executors.newFixedThreadPool(5); // Adjust pool size as needed

    public void submitAction(ActiveUnit activeUnit, Runnable action) {
        activeUnit.enqueue(action);
        // todo: why limit? what is submit means?
        executor.submit(activeUnit);
    }


    Map<String, ActiveUnit> units = new HashMap<>(5);

    public void register(ActiveUnit activeUnit) {
        if (!units.containsKey(activeUnit.name)) {
            units.put(activeUnit.name, activeUnit);
            executor.submit(activeUnit);
        }
    }

    public void enqueueAction(ActiveUnit activeUnit, Runnable action) {
        ActiveUnit unit = units.get(activeUnit.name);
        if (null == unit) return;
        unit.enqueue(action);
    }
}