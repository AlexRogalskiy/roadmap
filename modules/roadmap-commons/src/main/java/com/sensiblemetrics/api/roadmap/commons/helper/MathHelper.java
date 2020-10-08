package com.sensiblemetrics.api.roadmap.commons.helper;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MathHelper {

    public static double getLengthByCoords(final double x1,
                                           final double y1,
                                           final double x2,
                                           final double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
