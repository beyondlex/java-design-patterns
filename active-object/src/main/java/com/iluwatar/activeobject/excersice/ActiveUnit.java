package com.iluwatar.activeobject.excersice;

import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Data
public class ActiveUnit implements UnitActions, Runnable, Comparable<ActiveUnit> {
    private BlockingQueue<Runnable> actionQueue = new LinkedBlockingQueue<>(10);

    Property property;

    Position position;

    float powerPlus = 25;

    String name;

    List<ActiveUnit> enemies;


    // Fight fight;

    ActiveUnit(String name, Position position) {
        this.name = name;
        this.property = new Property(100, 20, 1000, 0, 100, position);
    }


    @Override
    public void action() {
        // ActiveUnit target = enemies.get(0); // todo: random or ...
        Optional<ActiveUnit> target = enemies.stream().filter(e -> e.getProperty().getHealth() > 0).findAny();
        if (target.isPresent()) {
            // todo: attack multiple target
            if (this.property.getPower() == 100) {
                bigAttack(target.get());
            } else {
                attack(target.get());
            }
        }
    }

    @Override
    public void attack(ActiveUnit target) {
        System.out.printf("%s attacking %s. (%s) %n", name, target.name, Thread.currentThread().getName());
        this.property.setPower(Math.min(this.getProperty().power + this.powerPlus, 100));
        System.out.printf("%s power= %s. (%s) %n", name, this.getProperty().power, Thread.currentThread().getName());

        // calculate health
        target.getProperty().setHealth(calculateHealthAfterAttack(this.getProperty(), target.getProperty(), false));
        System.out.printf("%s health=%s %n", target.name, target.getProperty().health);
        // actionQueue.offer(() -> {
        //     // Implementation of attacking logic here
        // });
    }

    float calculateHealthAfterAttack(Property hitter, Property target, boolean bigAttack) {
        // calculate health

        float atk = bigAttack ? hitter.getAtk() * 2 : hitter.getAtk();
        float health = target.getHealth();
        return Math.max(health - (atk - target.getDef()), 0);
    }

    @Override
    public void bigAttack(ActiveUnit target) {
        System.out.printf("%s BIG ATTACKING %s. (%s) %n", name, target.name, Thread.currentThread().getName());
        this.getProperty().setPower(0);
        System.out.printf("%s power= %s. (%s) %n", name, this.getProperty().getPower(), Thread.currentThread().getName());
        // calculate health
        target.getProperty().setHealth(calculateHealthAfterAttack(this.getProperty(), target.getProperty(), true));
        System.out.printf("%s health=%s %n", target.name, target.getProperty().health);
        // actionQueue.offer(() -> {
        //     // Implementation of attacking logic here
        // });
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Runnable action = actionQueue.take();
                action.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void enqueue(Runnable action) {
        actionQueue.add(action);
    }

    @Override
    public int compareTo(ActiveUnit o) {
        // speed and priority
        float totalLength = 15*1000;

        return 0;
    }
}