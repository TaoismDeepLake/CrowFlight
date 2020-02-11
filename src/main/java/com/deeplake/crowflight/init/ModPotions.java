package com.deeplake.crowflight.init;

import com.deeplake.crowflight.CrowFlight;
import com.deeplake.crowflight.potions.PotionCrowFlight;
import com.deeplake.crowflight.potions.PotionSimple;
import com.deeplake.crowflight.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

import static net.minecraft.util.DamageSource.FALL;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModPotions {
    public static Potion CROW_FLIGHT;
    public static PotionSimple TIGER_PUDDING;


    @Nullable
    private static Potion getRegisteredMobEffect(String id)
    {
        Potion potion = Potion.REGISTRY.getObject(new ResourceLocation(id));

        if (potion == null)
        {
            throw new IllegalStateException("Invalid MobEffect requested: " + id);
        }
        else
        {
            return potion;
        }
    }

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> evt)
    {
        CROW_FLIGHT = new PotionCrowFlight(false, 0x111111, "crow_flight", 0);
        TIGER_PUDDING = new PotionSimple(false, 0xccffcc,"tiger_pudding", 1);

        evt.getRegistry().register(CROW_FLIGHT);
        evt.getRegistry().register(TIGER_PUDDING);
    }

//    @SubscribeEvent
//    public static void onCreatureHurt(LivingHurtEvent evt) {
//        World world = evt.getEntity().getEntityWorld();
//        EntityLivingBase hurtOne = evt.getEntityLiving();
//
//        //Zen heart
//        Entity trueSource = evt.getSource().getTrueSource();
//        if (trueSource instanceof EntityLivingBase) {
//        }
//    }

//  * At this point armor, potion and absorption modifiers have already been applied to damage - this is FINAL value.<br>
//  * Also note that appropriate resources (like armor durability and absorption extra hearths) have already been consumed.<br>
    @SubscribeEvent
    public static void onCreatureDamaged(LivingDamageEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();
        //CrowFlight.Log(String.format("DMG:%s=%f",evt.getEntityLiving(), evt.getAmount()));
        if (evt.getSource() == FALL)
        {
            if (hurtOne.getActivePotionEffect(CROW_FLIGHT) != null){
                //CrowFlight.Log("Canceled fall damage");
                evt.setCanceled(true);
                return;
            }
        }
    }

    @SubscribeEvent
    public static void onCreatureKB(LivingKnockBackEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();

        if (hurtOne.getActivePotionEffect(CROW_FLIGHT) != null){
            //CrowFlight.LogWarning("Canceled a kb");
            evt.setCanceled(true);
            return;
        }

        Entity source = evt.getAttacker();
        if (source instanceof EntityLivingBase && ((EntityLivingBase)source).getActivePotionEffect(TIGER_PUDDING) != null)
        {
            evt.setCanceled(true);
        }

    }


}
