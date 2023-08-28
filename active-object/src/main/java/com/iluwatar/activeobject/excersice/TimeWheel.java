package com.iluwatar.activeobject.excersice;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Stream;

public class TimeWheel {

    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    public static class Runner {
        String name;
        float speed;
        int round;

        Position position;

        public void roundNext() {
            this.round++;
        }
    }
    static int length = 1000; // todo: per round?

    /*
    i.speed = 100

    a.speed = 120
    b.speed = 150
    c.speed = 200

    round 1
    first: c at 5
    second: b at 1000/150 = 6.66
    third: a at 2000/120 = 8.3
    fourth: c at 2000/200 = 10
    fifth: i at 1000/100 = 10 ROUND 1 END

    round 2
    b at 2000/150 = 13.3
    c at 3000/200 = 15
    a at 2000/120 = 16.6
    b at 3000/150 = 20
    c at 4000/200 = 20
    i at 2000/100 = 20 ROUND 2 END

     */
    public static void main(String[] args) {
        Runner a = new Runner("A", 120, 1, Position.ONE);
        Runner b = new Runner("B", 150, 1, Position.TWO);
        Runner c = new Runner("C", 200, 1, Position.THREE);
        Runner d = new Runner("D", 150, 1, Position.FOUR);
        Runner e = new Runner("E", 100, 1, Position.FIVE);

        Runner z = new Runner("Z", 100, 1, Position.Z);

        int totalRound = 4;
        System.out.printf("Round %s begin: %n", z.getRound());
        while (z.round <= totalRound) {
            Runner theOne = Stream.of(a, b, c, d, e, z).min((me, others) -> {
                float myLen = length * me.round;
                float othersLen = length * others.round;
                if (myLen / me.speed > othersLen / others.speed) {
                    return 1;
                } else if (myLen / me.speed < othersLen / others.speed) {
                    return -1;
                }
                // if speed is the same, compare with position
                if (me.position.num < others.position.num) {
                    return -1;
                } else if (me.position.num > others.position.num) {
                    return 1;
                }
                return 0;
            }).get();

            System.out.print(theOne);
            System.out.printf("\t%s %n", length*theOne.round/theOne.speed);
            theOne.roundNext();

            if (theOne.getName().equals("Z")) {
                System.out.printf("Round %s end. %n", theOne.getRound()-1);
                if (z.round <= totalRound) {
                    System.out.printf("Round %s begin: %n", theOne.getRound());
                }
            }
        }


        // // who is next
        // // take first
        // Runner first = Stream.of(a, b, c, i).min((me, others) -> {
        //     float myLen = length * me.round;
        //     float othersLen = length * others.round;
        //     if (myLen / me.speed > othersLen / others.speed) {
        //         return 1;
        //     } else if (myLen / me.speed < othersLen / others.speed) {
        //         return -1;
        //     }
        //     return 0;
        // }).get();
        //
        // System.out.println(first);
        // first.roundNext();
        //
        // // take second
        // Runner second = Stream.of(a, b, c, i).min((me, others) -> {
        //     float myLen = length * me.round;
        //     float othersLen = length * others.round;
        //     if (myLen / me.speed > othersLen / others.speed) {
        //         return 1;
        //     } else if (myLen / me.speed < othersLen / others.speed) {
        //         return -1;
        //     }
        //     return 0;
        // }).get();
        // System.out.println(second);
        // second.roundNext();
        //
        // // take third
        // Runner third = Stream.of(a, b, c, i).min((me, others) -> {
        //     float myLen = length * me.round;
        //     float othersLen = length * others.round;
        //     if (myLen / me.speed > othersLen / others.speed) {
        //         return 1;
        //     } else if (myLen / me.speed < othersLen / others.speed) {
        //         return -1;
        //     }
        //     return 0;
        // }).get();
        // System.out.println(third);
        // third.roundNext();
        //
        // // take fourth
        // Runner fourth = Stream.of(a, b, c, i).min((me, others) -> {
        //     float myLen = length * me.round;
        //     float othersLen = length * others.round;
        //     if (myLen / me.speed > othersLen / others.speed) {
        //         return 1;
        //     } else if (myLen / me.speed < othersLen / others.speed) {
        //         return -1;
        //     }
        //     return 0;
        // }).get();
        // System.out.println(fourth);
        // fourth.roundNext();
        //
        // // take fifth
        // Runner fifth = Stream.of(a, b, c, i).min((me, others) -> {
        //     float myLen = length * me.round;
        //     float othersLen = length * others.round;
        //     if (myLen / me.speed > othersLen / others.speed) {
        //         return 1;
        //     } else if (myLen / me.speed < othersLen / others.speed) {
        //         return -1;
        //     }
        //     return 0;
        // }).get();
        // System.out.println(fifth);
        // fifth.roundNext();
        //
        // // take sixth
        // Runner sixth = Stream.of(a, b, c, i).min((me, others) -> {
        //     float myLen = length * me.round;
        //     float othersLen = length * others.round;
        //     if (myLen / me.speed > othersLen / others.speed) {
        //         return 1;
        //     } else if (myLen / me.speed < othersLen / others.speed) {
        //         return -1;
        //     }
        //     return 0;
        // }).get();
        // System.out.println(sixth);
        // sixth.roundNext();
    }


    /*
    try:
    a = 1.2i
    b = 1.5i
    c = 2.0i

    todo: who is next?
     */
}
