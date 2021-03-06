package com.novarch.jojomod.events;

import com.novarch.jojomod.JojoBizarreSurvival;
import com.novarch.jojomod.capabilities.stand.Stand;
import com.novarch.jojomod.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JojoBizarreSurvival.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventAbilityGER
{
    @SubscribeEvent
    public static void cancelDamage(LivingHurtEvent event)
    {
        if(event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            Stand.getLazyOptional(player).ifPresent(props -> {
                if (props.getStandID() == Util.StandID.GER) {
                event.setCanceled(true);
                if (event.getSource().getTrueSource() instanceof PlayerEntity) {
                    props.setDiavolo(event.getSource().getTrueSource().getDisplayName().toString());
                }
                else if (event.getSource() == DamageSource.OUT_OF_WORLD && player.dimension != DimensionType.THE_END) {
                    player.setPositionAndUpdate(player.getPosX(), Util.getHighestBlock(player.world, player.getPosition()) + 1, player.getPosZ());
                } else if (event.getSource() == DamageSource.OUT_OF_WORLD && player.dimension == DimensionType.THE_END) {
                    if (Util.getNearestBlockEnd(player.world, player.getPosition()) != null)
                        player.setPositionAndUpdate(Util.getNearestBlockEnd(player.world, player.getPosition()).getX(), Util.getNearestBlockEnd(player.world, player.getPosition()).getY() + 1, Util.getNearestBlockEnd(player.world, player.getPosition()).getZ());
                }
            }
        });
        }
    }

    @SubscribeEvent
    public static void stopExplosion(ExplosionEvent.Start event)
    {
        PlayerEntity player = JojoBizarreSurvival.PROXY.getPlayer();
        if(player != null)
            Stand.getLazyOptional(player).ifPresent(props -> {
                if (props.getStandID() == Util.StandID.GER)
                    if (event.getExplosion().getPosition().distanceTo(player.getPositionVec()) < 30.0f)
                        event.setCanceled(true);
            });
    }

    @SubscribeEvent
    public static void stopKnockback(LivingKnockBackEvent event)
    {
        if(event.getEntity() instanceof PlayerEntity)
            Stand.getLazyOptional((PlayerEntity) event.getEntity()).ifPresent(props -> {
                if(props.getStandID() == Util.StandID.GER) {
                    event.setCanceled(true);
                }
            });
    }

    @SubscribeEvent
    public static void noClip(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof PlayerEntity)
            Stand.getLazyOptional((PlayerEntity) event.getEntity()).ifPresent(props -> {
                PlayerEntity player = (PlayerEntity) event.getEntity();
                if (props.getNoClip()) {
                    player.noClip = true;
                    if(props.getStandID()== Util.StandID.GER) {
                        player.setVelocity(player.getMotion().getX(), 0, player.getMotion().getZ());
                        if (Minecraft.getInstance().gameSettings.keyBindJump.isKeyDown())
                            player.setPosition(player.getPosX(), player.getPosY() + 1, player.getPosZ());
                        else if (Minecraft.getInstance().gameSettings.keyBindSneak.isKeyDown())
                            player.setPosition(player.getPosX(), player.getPosY() - 1, player.getPosZ());
                    }
                }
            });
    }
}
