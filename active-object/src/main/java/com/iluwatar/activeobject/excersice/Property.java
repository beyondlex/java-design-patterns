package com.iluwatar.activeobject.excersice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Property {
    float atk = 0;
    float def = 0;
    float health = 0;
    float power = 0;

    float speed = 100;

    Position position;
    /*
    speed
    position
     */

}
