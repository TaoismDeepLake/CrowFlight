package com.deeplake.crowflight.util;

import com.deeplake.crowflight.util.NBTStrDef.IDLNBTDef;
import net.minecraft.nbt.NBTTagCompound;

public class IDLSkillNBT {
    public int level;
    public int range_boost = 0;
    public int duration_boost = 0;
    public int[] gua_boost = new int[8];

    private final NBTTagCompound basic;

    public IDLSkillNBT()
    {
        level = 1;
        range_boost = 0;
        duration_boost = 0;

        basic = new NBTTagCompound();
    }

    public IDLSkillNBT(NBTTagCompound srcNBT)
    {
        readFromBasic(srcNBT);
        basic = srcNBT;
    }

    public void readFromBasic(NBTTagCompound tag)
    {
        if (tag != null)
        {
            level = tag.getInteger(IDLNBTDef.LEVEL_TAG);
            range_boost = tag.getInteger(IDLNBTDef.RANGE_BOOST);
            duration_boost = tag.getInteger(IDLNBTDef.DURA_BOOST);
        }
    }

    public void writeToBasic(NBTTagCompound tag)
    {
        if (tag == null)
        {
            tag = new NBTTagCompound();
        }

        tag.setInteger(IDLNBTDef.LEVEL_TAG, level);
        tag.setInteger(IDLNBTDef.RANGE_BOOST, range_boost);
        tag.setInteger(IDLNBTDef.DURA_BOOST, duration_boost);
    }

    public NBTTagCompound getBasic()
    {
        NBTTagCompound tag = basic.copy();
        writeToBasic(tag);

        return tag;
    }

    public void save()
    {
        writeToBasic(basic);
    }
}
