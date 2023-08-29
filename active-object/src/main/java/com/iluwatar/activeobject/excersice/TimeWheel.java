package com.iluwatar.activeobject.excersice;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TimeWheel {

    int totalRound;
    List<Runner> runners;

    Runner z = new Runner("Z", 100, Position.Z);

    boolean end = false;

    Runner current = null;

    public TimeWheel(List<Runner> runners, int totalRound) {
        if (null == runners || runners.size() == 0) {
            throw new RuntimeException("Please add at least one runner");
        }
        this.totalRound = totalRound;
        this.runners = runners;
        this.runners.add(z);
    }

    public int currentRound() {
        return z.round;
    }

    public Runner currentRunner() {
        return current;
    }

    public void setEnd() {
        end = true;
    }
    public boolean end() {
        return z.round > totalRound;
    }

    public Runner next() {
        if (null != current) {
            // todo: if current is freeze, keep current round? or just next round
            this.current.roundNext();
        }
        if (end()) {
            return null; // END
        }
        Runner theOne = this.runners.stream().min((me, others) -> {
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

        this.current = theOne;

        // theOne.roundNext();

        if (z.round > totalRound) {
            setEnd();
        }
        return theOne;
    }

    @Getter
    @Setter
    @ToString
    public static class Runner {
        // todo: generic
        public Runner(String name, float speed, Position position) {
            this.name = name;
            this.speed = speed;
            this.position = position;
            this.round = 1;
        }
        String name;
        float speed;
        Position position;
        int round;

        public void roundNext() {
            this.round++;
        }
    }

    /**
     * length per round
     */
    static int length = 1000;

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
        Runner a = new Runner("A", 120, Position.ONE);
        Runner b = new Runner("B", 150, Position.TWO);
        Runner c = new Runner("C", 200, Position.THREE);
        Runner d = new Runner("D", 150, Position.FOUR);
        Runner e = new Runner("E", 100, Position.FIVE);
        List<Runner> runners = new ArrayList<>();
        runners.add(a);
        runners.add(b);
        runners.add(c);
        runners.add(d);
        runners.add(e);
        TimeWheel timeWheel = new TimeWheel(runners, 10);
        while(!timeWheel.end()) {
            Runner theOne = timeWheel.next();
            if (theOne == null) {
                System.out.println("END-----------");
                break;
            }
            System.out.printf("(%s): %s", timeWheel.currentRound(), theOne);
            System.out.printf("\t%s %n", length*theOne.round/theOne.speed);
        }
    }

    public static void main0(String[] args) {
        Runner a = new Runner("A", 120, Position.ONE);
        Runner b = new Runner("B", 150, Position.TWO);
        Runner c = new Runner("C", 200, Position.THREE);
        Runner d = new Runner("D", 150, Position.FOUR);
        Runner e = new Runner("E", 100, Position.FIVE);

        Runner z = new Runner("Z", 100, Position.Z);

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
