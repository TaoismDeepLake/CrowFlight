package com.deeplake.crowflight.potions;

import com.deeplake.crowflight.CrowFlight;
import com.deeplake.crowflight.util.IDLGeneral;
import com.deeplake.crowflight.util.MathUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nonnull;
import java.util.List;

public class PotionSimple extends BasePotion {
    public PotionSimple(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn, name, icon);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(@Nonnull EntityLivingBase living, int amplified) {
    }
}
