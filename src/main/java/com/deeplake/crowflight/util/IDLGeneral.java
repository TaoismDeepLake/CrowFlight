package com.deeplake.crowflight.util;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class IDLGeneral {
    public final static int TICK_PER_SECOND = 20;

    //server side dont have this constructor.
    public static AxisAlignedBB ServerAABB(Vec3d from, Vec3d to)
    {
        return new AxisAlignedBB(from.x, from.y, from.z, to.x, to.y, to.z);
    }


}
