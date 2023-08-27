package com.iluwatar.activeobject.excersice;

import lombok.Data;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Data
public class Fight {

    private ExecutorService executor = Executors.newFixedThreadPool(5); // Adjust pool size as needed
    List<ActiveUnit> members;
    List<ActiveUnit> enemies;

    // private BlockingQueue<Runnable> actionQueue = new LinkedBlockingQueue<>(10);

    public Fight(List<ActiveUnit> members, List<ActiveUnit> enemies) {
        this.members = members;
        this.enemies = enemies;
        this.members.forEach(m -> {
            m.setEnemies(enemies);
        });

        // this.members.forEach(member -> {
        //     member.setFight(this);
        // });
        // this.enemies.forEach(member -> {
        //     member.setFight(this);
        // });
    }

    int winOrFail() {
        if (this.enemies.stream().allMatch(m -> m.getProperty().getHealth() <= 0)) {
            // win
            return 1;
        }
        if (this.members.stream().allMatch(e -> e.getProperty().getHealth() <= 0)) {
            // loss
            return 0;
        }
        return -1;
    }

    int maxRound = 15;


    public void start() {
        // round
        int round = 1;
        int winOrFail = -1;

        // sort by speed and position

        do {
            // round start
            System.out.printf("Round %s ----------- %n", round);
            for (ActiveUnit member : this.members) {
                winOrFail = winOrFail();
                if (winOrFail >= 0) {
                    if (winOrFail > 0) {
                        System.out.printf("Win at round %s %n", round);
                    } else {
                        System.out.printf("Loss at round %s %n", round);
                    }
                    break;
                }
                member.action();
                // actionQueue.offer(member::action);
            }
            // todo: share action queue between units, not one queue per unit
            // todo: notification for main
            round++;
        } while (round <= maxRound && winOrFail() < 0);

        if (round >= maxRound && winOrFail() == -1) {
            System.out.printf("Failed at round %s %n", round-1);
        }


        // todo: order action by speed
        // scheduler.enqueueAction(unit2, unit2::action);
        // scheduler.enqueueAction(unit1, unit1::action);
        // scheduler.enqueueAction(unit3, unit3::action);
        // scheduler.enqueueAction(unit4, unit4::action);
        // scheduler.enqueueAction(unit5, unit5::action);
        // scheduler.enqueueAction(unit5, unit5::action);
        // scheduler.enqueueAction(unit5, unit5::action);
        // scheduler.enqueueAction(unit5, unit5::action);
        // scheduler.enqueueAction(unit5, unit5::action);
        // scheduler.enqueueAction(unit5, unit5::action);
    }

}
