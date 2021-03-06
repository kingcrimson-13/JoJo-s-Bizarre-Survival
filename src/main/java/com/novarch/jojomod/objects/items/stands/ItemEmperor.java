package com.novarch.jojomod.objects.items.stands;

import com.novarch.jojomod.capabilities.stand.IStand;
import com.novarch.jojomod.capabilities.stand.Stand;
import com.novarch.jojomod.entities.stands.theEmperor.EntityEmperorBullet;
import com.novarch.jojomod.util.Util;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemEmperor extends Item {

    public ItemEmperor(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        if (!playerIn.world.isRemote) {
            IStand props = Stand.getCapabilityFromPlayer(playerIn);
            if (props.getStandID() != Util.StandID.theEmperor)
                itemStack.shrink(1);
            if (props.getCooldown() <= 0) {
                EntityEmperorBullet bullet = new EntityEmperorBullet(playerIn, worldIn);
                bullet.setSilent(true);
                bullet.setPositionAndRotation(playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), playerIn.rotationYaw, playerIn.rotationPitch);
                bullet.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 20.0f, Float.MIN_VALUE);
                playerIn.world.addEntity(bullet);
                if(!playerIn.isCreative() && !playerIn.isSpectator())
                    playerIn.getFoodStats().addStats(2, 0);
                props.setCooldown(100);
                return new ActionResult<>(ActionResultType.SUCCESS, itemStack);
            } else
                return new ActionResult<>(ActionResultType.FAIL, itemStack);
        }
        return new ActionResult<>(ActionResultType.PASS, itemStack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(entityIn instanceof PlayerEntity)
            Stand.getLazyOptional((PlayerEntity) entityIn).ifPresent(props -> {
                props.setAbility(false);
                if(!isSelected) {
                    props.setStandOn(false);
                    stack.shrink(1);
                }
                if(!props.getStandOn() || props.getStandID() != Util.StandID.theEmperor)
                    stack.shrink(1);
            });
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 100;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.CROSSBOW;
    }
}
