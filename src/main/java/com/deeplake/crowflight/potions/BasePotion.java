package com.deeplake.crowflight.potions;

import com.deeplake.crowflight.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.deeplake.crowflight.util.Reference.MOD_ID;

public class BasePotion extends Potion {
    private static final ResourceLocation resource = new ResourceLocation(MOD_ID,"textures/misc/potions.png");
    private final int iconIndex;
    protected float damageReductionRatioBase = 0.0f;
    protected float damageReductionRatioPerLevel = 0.0f;

    protected float attackIncreaseRatioBase = 0.0f;
    protected float attackIncreaseRatioPerLevel = 0.0f;


    public BasePotion(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn);
        setRegistryName(new ResourceLocation(MOD_ID, name));
        setPotionName(MOD_ID + ".potion." + name);
        iconIndex = icon;
    }

    //see InventoryEffectRenderer.drawActivePotionEffects, buff modifier > 3  won't display level.
//    public String getName()
//    {
//        return this.getName() + "." + this.getmo;
//    }

    public void playOnHitEffect(EntityLivingBase entityLivingBase, float damage)
    {

    }

    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    public float getDamageReductionMultiplier(int level)
    {
        //cant deal negative damage
        return Math.max(0f, damageReductionRatioBase + level * damageReductionRatioPerLevel);
    }

    public float getAttackMultiplier(int level)
    {
        //cant deal negative damage
        return Math.max(0f, attackIncreaseRatioBase + level * attackIncreaseRatioPerLevel);
    }


    @SideOnly(Side.CLIENT)
    private void render(int x, int y, float alpha) {
        Minecraft.getMinecraft().renderEngine.bindTexture(resource);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buf = tessellator.getBuffer();
        buf.begin(7, DefaultVertexFormats.POSITION_TEX);
        GlStateManager.color(1, 1, 1, alpha);

        int textureX = iconIndex % 8 * 18;
        int textureY = 198 + iconIndex / 8 * 18;

        buf.pos(x, y + 18, 0).tex(textureX * 0.00390625, (textureY + 18) * 0.00390625).endVertex();
        buf.pos(x + 18, y + 18, 0).tex((textureX + 18) * 0.00390625, (textureY + 18) * 0.00390625).endVertex();
        buf.pos(x + 18, y, 0).tex((textureX + 18) * 0.00390625, textureY * 0.00390625).endVertex();
        buf.pos(x, y, 0).tex(textureX * 0.00390625, textureY * 0.00390625).endVertex();

        tessellator.draw();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        render(x + 6, y + 7, 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        render(x + 3, y + 3, alpha);
    }
}
