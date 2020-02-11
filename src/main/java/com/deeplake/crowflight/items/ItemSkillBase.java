package com.deeplake.crowflight.items;

import com.deeplake.crowflight.util.CommonFunctions;
import com.deeplake.crowflight.util.NBTStrDef.IDLNBTDef;
import com.deeplake.crowflight.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.deeplake.crowflight.util.CommonFunctions.SendMsgToPlayer;
import static com.deeplake.crowflight.util.IDLGeneral.TICK_PER_SECOND;

enum SKILL_MSG_TYPE
{
    SUCCESS,
    CD,
}

public class ItemSkillBase extends ItemBase {
    public float cool_down = 0.2f;
    public float cool_down_reduce_per_lv = 0.2f;

    public float base_range = 5f;
    public float range_per_level = 0f;

    public float basic_val = 0f;
    public float val_per_level = 0f;

    public float level_modifier = 0f;

    public int maxLevel = 5;

    public static final String SUCCESS_DESC_KEY = ".on_sucess";
    public static final String IN_CD_DESC_KEY = ".on_cooldown";


    private int GetMaxTick(ItemStack stack) {
        return (int) (getCoolDown(stack) * TICK_PER_SECOND);
    }

    public ItemSkillBase(String name) {
        super(name);
        setMaxStackSize(1);
        setNoRepair();
    }

    public ItemSkillBase setVal(float val, float val_per_level)
    {
        basic_val = val;
        this.val_per_level = val_per_level;
        return this;
    }

    public ItemSkillBase setCD(float val, float val_per_level)
    {
        cool_down = val;
        this.cool_down_reduce_per_lv = val_per_level;
        return this;
    }

    public float getRange(ItemStack stack)
    {
        return (GetLevel(stack) - 1) * range_per_level + base_range;
    }

    public float getVal(ItemStack stack)
    {
        return  (GetLevel(stack) - 1) * val_per_level + basic_val;
    }
    public float getCoolDown(ItemStack stack) {return -(GetLevel(stack) - 1) * cool_down_reduce_per_lv + cool_down; }

    //leveling-------------------------------------

    public int GetLevelMax(ItemStack stack)
    {
        if (!(stack.getItem() instanceof ItemSkillBase)) {
            return 0;
        }
        return maxLevel;
    }

    public int GetLevel(ItemStack stack)
    {
        int level = IDLNBTUtil.GetInt(stack, IDLNBTDef.LEVEL_TAG);
        return level == 0 ? 1 : level;
    }

    public void SetLevel(ItemStack stack, int count)
    {
        if (!(stack.getItem() instanceof ItemSkillBase)) {
            return;
        }

        if (count <= GetLevelMax(stack)) {
            IDLNBTUtil.SetInt(stack, IDLNBTDef.LEVEL_TAG, count);
        }
    }

    public void AddLevelByCount(ItemStack stack, int count)
    {
        if (!(stack.getItem() instanceof ItemSkillBase)) {
            return;
        }

        int anticipatedCount = count + GetLevel(stack);
        if (anticipatedCount <= GetLevelMax(stack)) {
            IDLNBTUtil.SetInt(stack, IDLNBTDef.LEVEL_TAG, anticipatedCount);
        }
        else {
            IDLNBTUtil.SetInt(stack, IDLNBTDef.LEVEL_TAG, GetLevelMax(stack));
        }
    }
    
    public void SendDefaultMsg(EntityPlayer player, ItemStack stack, SKILL_MSG_TYPE msg_type)
    {
        switch (msg_type)
        {
            case CD:
                SendMsgToPlayer((EntityPlayerMP)player, stack.getUnlocalizedName()+ IN_CD_DESC_KEY);
            case SUCCESS:
                SendMsgToPlayer((EntityPlayerMP)player, stack.getUnlocalizedName()+ SUCCESS_DESC_KEY);
                default:
                    break;
        }
    }

    public static void activateCoolDown(EntityPlayer player, ItemStack stack)
    {
        Item item = stack.getItem();
        if (item instanceof ItemSkillBase)
        {
            player.getCooldownTracker().setCooldown(stack.getItem(), ((ItemSkillBase) item).GetMaxTick(stack));
        }
    }

    public static void notifyCoolingDown(EntityPlayerMP player)
    {
        SendMsgToPlayer(player, "idealland.skill.msg.cool_down");
    }

    public static boolean isStackReady(EntityPlayer player, ItemStack stack)
    {
        return !player.getCooldownTracker().hasCooldown(stack.getItem());
        //return stack.getItemDamage() == 0;
    }

    //--------------------------

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        stack.setItemDamage(stack.getItemDamage() - 1);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        return false;
    }

    //Desc
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        String mainDesc = String.format(I18n.format(stack.getUnlocalizedName() + ".desc"));
        tooltip.add(mainDesc);
    }
}
