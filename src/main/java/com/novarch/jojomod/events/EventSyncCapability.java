package com.novarch.jojomod.events;

import com.novarch.jojomod.JojoBizarreSurvival;
import com.novarch.jojomod.capabilities.stand.Stand;
import com.novarch.jojomod.capabilities.timestop.Timestop;
import com.novarch.jojomod.config.JojoBizarreSurvivalConfig;
import com.novarch.jojomod.entities.fakePlayer.FakePlayerEntity;
import com.novarch.jojomod.entities.stands.killerQueen.sheerHeartAttack.EntitySheerHeartAttack;
import com.novarch.jojomod.entities.stands.theWorld.EntityTheWorld;
import com.novarch.jojomod.network.message.server.SSyncStandCapabilityPacket;
import com.novarch.jojomod.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.novarch.jojomod.util.Util.StandID.theWorld;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = JojoBizarreSurvival.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventSyncCapability
{
    @SubscribeEvent
    public static void saveStand(PlayerEvent.Clone event)
    {
        if(!event.isWasDeath() || JojoBizarreSurvivalConfig.COMMON.saveStandOnDeath.get())
        {
            Stand.getLazyOptional(event.getOriginal()).ifPresent(originalProps -> {
                ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
                Stand.getLazyOptional(player).ifPresent(newProps -> newProps.clone(originalProps));
            });
        }
    }

    @SubscribeEvent
    public static void playerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        Stand.getLazyOptional(player).ifPresent(props -> JojoBizarreSurvival.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SSyncStandCapabilityPacket(props)));
    }

    @SubscribeEvent
    public static void playerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        Stand.getLazyOptional(player).ifPresent(props -> JojoBizarreSurvival.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SSyncStandCapabilityPacket(props)));
    }

    @SubscribeEvent
    public static void playerJoinWorld(PlayerEvent.PlayerLoggedInEvent event)
    {
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        Stand.getLazyOptional(player).ifPresent(props -> JojoBizarreSurvival.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SSyncStandCapabilityPacket(props)));
    }

    @SubscribeEvent
    public static void playerLogOut(PlayerEvent.PlayerLoggedOutEvent event)
    {
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        player.setInvulnerable(false);
        Stand.getLazyOptional(player).ifPresent(props -> {
            props.putStandOn(false);
            if (!player.world.isRemote) {
                player.getServerWorld().getEntities()
                        .filter(entity -> entity instanceof FakePlayerEntity)
                        .filter(entity -> ((FakePlayerEntity) entity).getParent() == player)
                        .forEach(Entity::remove);
                player.getServerWorld().getEntities()
                        .filter(entity -> entity instanceof EntitySheerHeartAttack)
                        .filter(entity -> ((EntitySheerHeartAttack) entity).getMaster() == player)
                        .forEach(Entity::remove);
                if (props.getStandID() == theWorld) {
                    player.getServerWorld().getEntities()
                            .forEach(entity -> {
                                Timestop.getLazyOptional(entity).ifPresent(props2 -> {
                                    if ((entity instanceof IProjectile || entity instanceof ItemEntity || entity instanceof DamagingProjectileEntity) && (props2.getMotionX() != 0 && props2.getMotionY() != 0 && props2.getMotionZ() != 0)) {
                                        entity.setMotion(props2.getMotionX(), props2.getMotionY(), props2.getMotionZ());
                                        entity.setNoGravity(false);
                                    } else {
                                        if (props2.getMotionX() != 0 && props2.getMotionY() != 0 && props2.getMotionZ() != 0)
                                            entity.setMotion(props2.getMotionX(), props2.getMotionY(), props2.getMotionZ());
                                    }
                                    if (entity instanceof MobEntity)
                                        ((MobEntity) entity).setNoAI(false);
                                    entity.velocityChanged = true;
                                    entity.setMotion(props2.getMotionX(), props2.getMotionY(), props2.getMotionZ());
                                    entity.fallDistance = props2.getFallDistance();
                                    entity.setInvulnerable(false);
                                    props2.clear();

                                });
                                if (entity instanceof EntityTheWorld)
                                    if (entity == EntityTheWorld.theWorld)
                                        EntityTheWorld.theWorld = null;
                            });
                } else if(props.getStandID() == Util.StandID.starPlatinum) {
                    player.getServerWorld().getEntities()
                            .forEach(entity -> {
                                Timestop.getLazyOptional(entity).ifPresent(props2 -> {
                                    if ((entity instanceof IProjectile || entity instanceof ItemEntity || entity instanceof DamagingProjectileEntity) && (props2.getMotionX() != 0 && props2.getMotionY() != 0 && props2.getMotionZ() != 0)) {
                                        entity.setMotion(props2.getMotionX(), props2.getMotionY(), props2.getMotionZ());
                                        entity.setNoGravity(false);
                                    } else {
                                        if (props2.getMotionX() != 0 && props2.getMotionY() != 0 && props2.getMotionZ() != 0)
                                            entity.setMotion(props2.getMotionX(), props2.getMotionY(), props2.getMotionZ());
                                    }
                                    if (entity instanceof MobEntity)
                                        ((MobEntity) entity).setNoAI(false);
                                    entity.velocityChanged = true;
                                    entity.setMotion(props2.getMotionX(), props2.getMotionY(), props2.getMotionZ());
                                    entity.fallDistance = props2.getFallDistance();
                                    entity.setInvulnerable(false);
                                    props2.clear();

                                });
                                if (entity instanceof EntityTheWorld)
                                    if (entity == EntityTheWorld.theWorld)
                                        EntityTheWorld.theWorld = null;
                            });
                }
                JojoBizarreSurvival.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SSyncStandCapabilityPacket(props));
            }
        });
    }
}
