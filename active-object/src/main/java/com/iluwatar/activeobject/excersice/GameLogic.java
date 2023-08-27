package com.iluwatar.activeobject.excersice;

import java.util.Arrays;

public class GameLogic {
    public static void main(String[] args) {
        // UnitScheduler scheduler = new UnitScheduler();

        ActiveUnit unit1 = new ActiveUnit("Dog001", Position.ONE);
        ActiveUnit unit2 = new ActiveUnit("Dog002", Position.TWO);
        ActiveUnit unit3 = new ActiveUnit("Dog003", Position.THREE);
        ActiveUnit unit4 = new ActiveUnit("Dog004", Position.FOUR);
        ActiveUnit unit5 = new ActiveUnit("Dog005", Position.FIVE);

        Fight fight = new Fight(
                Arrays.asList(unit1, unit2, unit3, unit4, unit5),
                Arrays.asList(
                        new ActiveUnit("Boss1", Position.ONE),
                        new ActiveUnit("Boss2", Position.TWO)
                ));
        fight.start();
        // scheduler.submitAction(unit1, () -> unit1.moveTo(100, 200));
        // scheduler.submitAction(unit2, () -> unit2.attack(enemyUnit));
        // scheduler.submitAction(unit1, () -> unit1.attack(enemyUnit));
        // scheduler.submitAction(unit3, () -> unit3.attack(enemyUnit));
        // scheduler.submitAction(unit4, () -> unit4.attack(enemyUnit));
        // scheduler.submitAction(unit5, () -> unit5.attack(enemyUnit));

        // scheduler.register(unit1);
        // scheduler.register(unit2);
        // scheduler.register(unit3);
        // scheduler.register(unit4);
        // scheduler.register(unit5);

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