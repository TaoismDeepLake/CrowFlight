package com.deeplake.crowflight.potions;

import com.deeplake.crowflight.CrowFlight;
import com.deeplake.crowflight.init.ModPotions;
import com.deeplake.crowflight.util.IDLGeneral;
import com.deeplake.crowflight.util.MathUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nonnull;
import java.util.List;

public class PotionCrowFlight extends BasePotion {

    private final static float aoeDamage = 1f;
    private final static float range = 2f;
    private final static float KBPower = 1f;

    public PotionCrowFlight(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn, name, icon);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(@Nonnull EntityLivingBase living, int amplified) {
        EntityLivingBase entityIn = living;
        if (entityIn.motionY < -0.11f && !entityIn.onGround) {
            //PotionEffect curBuff
            //living.addPotionEffect(new PotionEffect(ModPotions.CROW_FLIGHT, ));

            entityIn.motionY = -0.1f;
            float maxSpeed = 1f;
            //CrowFlight.Log(String.format("--(%f, %f)", entityIn.motionX, entityIn.motionZ));
            entityIn.motionX = MathUtil.Clamp(entityIn.motionX * 1.1f, -maxSpeed, maxSpeed);
            entityIn.motionZ = MathUtil.Clamp(entityIn.motionZ * 1.1f, -maxSpeed, maxSpeed);

            double horiSpeedSqrt = entityIn.motionX * entityIn.motionX + entityIn.motionZ * entityIn.motionZ;
//            CrowFlight.Log(String.format("speed = (%f)", horiSpeedSqrt));
//            CrowFlight.Log(String.format("fallDistance = (%f)", entityIn.fallDistance));
            if (entityIn.fallDistance > 1f)
            {
                Vec3d mypos = entityIn.getPositionEyes(0f);
                List<EntityLivingBase> list = living.world.getEntitiesWithinAABB(EntityLivingBase.class, IDLGeneral.ServerAABB(mypos.addVector(-range, -range - entityIn.getEyeHeight(), -range), mypos.addVector(range, range, range)));
                for (EntityLivingBase creature : list) {
                    CrowFlight.Log(living.getDisplayName().toString());
                    if (creature != entityIn)
                    {
                        if (!living.world.isRemote) {
                            if (creature.attackEntityFrom(DamageSource.ANVIL, aoeDamage * entityIn.fallDistance))
                            {
                                creature.knockBack(entityIn, KBPower * entityIn.fallDistance, (entityIn.posX - creature.posX), (entityIn.posZ - creature.posZ));
                                entityIn.playSound(SoundEvents.BLOCK_PISTON_EXTEND, 1f, 1f);
                                CrowFlight.Log("Attack!");
                            }
                            else {
                                CrowFlight.Log("Attack FAIL");
                            }
                        }
                    }
                }
            }

        }
    }
}
