package com.iluwatar.activeobject.excersice;

public enum Position {

    ONE(1, 1),
    TWO(2, 1),
    THREE(3, 2),
    FOUR(4, 2),
    FIVE(5, 2),
    Z(99, 99);

    final int num;
    final int type; // front or back

    Position(int num, int type) {
        this.num = num;
        this.type = type;
    }
}
