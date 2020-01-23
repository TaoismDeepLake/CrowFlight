package com.deeplake.crowflight.util;

public class MathUtil {
    public static float Clamp(float val, float min, float max)
    {
        return Math.min(Math.max(val, min), max);
    }

    public static double Clamp(double val, double min, double max)
    {
        return Math.min(Math.max(val, min), max);
    }
}
