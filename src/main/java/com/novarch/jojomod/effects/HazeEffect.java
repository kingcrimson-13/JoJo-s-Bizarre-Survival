package com.novarch.jojomod.effects;

import com.novarch.jojomod.entities.stands.EntityStandBase;
import com.novarch.jojomod.init.EffectInit;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class HazeEffect extends Effect {
    public HazeEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % 5 == 0;
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        entityLivingBaseIn.attackEntityFrom(DamageSource.WITHER, (entityLivingBaseIn.getMaxHealth() / entityLivingBaseIn.getHealth()) * 1.25f);
        if (!entityLivingBaseIn.world.isRemote)
            entityLivingBaseIn.world.getServer().getWorld(entityLivingBaseIn.dimension).getEntities()
                    .filter(entity -> entity != entityLivingBaseIn)
                    .filter(entity -> entity instanceof LivingEntity)
                    .filter(entity -> !(entity instanceof EntityStandBase))
                    .filter(entity -> entity.getDistance(entityLivingBaseIn) < 7)
                    .forEach(entity -> ((LivingEntity) entity).addPotionEffect(new EffectInstance(EffectInit.HAZE.get(), 200, 2)));
    }
}
