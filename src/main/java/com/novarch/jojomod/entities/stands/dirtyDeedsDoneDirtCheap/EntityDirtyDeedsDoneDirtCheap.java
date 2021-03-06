package com.novarch.jojomod.entities.stands.dirtyDeedsDoneDirtCheap;

import com.novarch.jojomod.capabilities.stand.Stand;
import com.novarch.jojomod.entities.stands.EntityStandBase;
import com.novarch.jojomod.entities.stands.EntityStandPunch;
import com.novarch.jojomod.events.EventD4CTeleportProcessor;
import com.novarch.jojomod.init.DimensionInit;
import com.novarch.jojomod.init.EntityInit;
import com.novarch.jojomod.init.SoundInit;
import com.novarch.jojomod.util.DimensionHopTeleporter;
import com.novarch.jojomod.util.Util;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("ConstantConditions")
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class EntityDirtyDeedsDoneDirtCheap extends EntityStandBase {
	private int oratick = 0;

	private int oratickr = 0;

	public EntityDirtyDeedsDoneDirtCheap(EntityType<? extends EntityStandBase> type, World world) {
		super(type, world);
		this.spawnSound = SoundInit.SPAWN_D4C.get();
		this.standID = Util.StandID.dirtyDeedsDoneDirtCheap;
	}

	public EntityDirtyDeedsDoneDirtCheap(World world) {
		super(EntityInit.D4C.get(), world);
		this.spawnSound = SoundInit.SPAWN_D4C.get();
		this.standID = Util.StandID.dirtyDeedsDoneDirtCheap;
	}

	/**
	 * Used to shorten the code of the method below, removing the need to make a <code>new</code> {@link DimensionHopTeleporter} every time {@link Entity}# changeDimension is called.
	 *
	 * @param entity	The {@link Entity} that will be transported.
	 * @param type		The {@link DimensionType} the entity will be transported to.
	 */
	private void changeDimension(DimensionType type, Entity entity) {
		entity.changeDimension(type, new DimensionHopTeleporter((ServerWorld) entity.getEntityWorld(), entity.getPosX(), entity.getPosY(), entity.getPosZ()));
	}

	private void transportEntity(Entity entity) {
		if (entity.world.getDimension().getType() == DimensionType.OVERWORLD)
			changeDimension(DimensionType.byName(DimensionInit.D4C_DIMENSION_TYPE), entity);
		else if (entity.world.getDimension().getType() == DimensionType.byName(DimensionInit.D4C_DIMENSION_TYPE))
			changeDimension(DimensionType.OVERWORLD, entity);
		else if (entity.world.getDimension().getType() == DimensionType.THE_NETHER)
			changeDimension(DimensionType.byName(DimensionInit.D4C_DIMENSION_TYPE_NETHER), entity);
		else if (entity.world.getDimension().getType() == DimensionType.byName(DimensionInit.D4C_DIMENSION_TYPE_NETHER))
			changeDimension(DimensionType.THE_NETHER, entity);
		else if (entity.world.getDimension().getType() == DimensionType.THE_END)
			changeDimension(DimensionType.byName(DimensionInit.D4C_DIMENSION_TYPE_END), entity);
		else if (entity.world.getDimension().getType() == DimensionType.byName(DimensionInit.D4C_DIMENSION_TYPE_END))
			changeDimension(DimensionType.THE_END, entity);
	}

	private void changePlayerDimension(PlayerEntity player) {
		if (player.world.getDimension().getType() == DimensionType.OVERWORLD)
			EventD4CTeleportProcessor.d4cPassengers.put(player, DimensionType.byName(DimensionInit.D4C_DIMENSION_TYPE));
		else if (player.world.getDimension().getType() == DimensionType.byName(DimensionInit.D4C_DIMENSION_TYPE))
			EventD4CTeleportProcessor.d4cPassengers.put(player, DimensionType.OVERWORLD);
		else if (player.world.getDimension().getType() == DimensionType.THE_NETHER)
			EventD4CTeleportProcessor.d4cPassengers.put(player, DimensionType.byName(DimensionInit.D4C_DIMENSION_TYPE_NETHER));
		else if (player.world.getDimension().getType() == DimensionType.byName(DimensionInit.D4C_DIMENSION_TYPE_NETHER))
			EventD4CTeleportProcessor.d4cPassengers.put(player, DimensionType.THE_NETHER);
		else if (player.world.getDimension().getType() == DimensionType.THE_END)
			EventD4CTeleportProcessor.d4cPassengers.put(player, DimensionType.byName(DimensionInit.D4C_DIMENSION_TYPE_END));
		else if (player.world.getDimension().getType() == DimensionType.byName(DimensionInit.D4C_DIMENSION_TYPE_END))
			EventD4CTeleportProcessor.d4cPassengers.put(player, DimensionType.THE_END);
	}

	@Override
	public void tick() {
		super.tick();
		this.fallDistance = 0.0F;
		if (getMaster() != null) {
			PlayerEntity player = getMaster();
			Stand.getLazyOptional(player).ifPresent(props -> {
				if (props.getCooldown() > 0)
					props.subtractCooldown(1);

				player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 40, 2));

				if ((player.world.getBlockState(new BlockPos(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ() - 1)).getMaterial() != Material.AIR && player.world.getBlockState(new BlockPos(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ() + 1)).getMaterial() != Material.AIR) || (player.world.getBlockState(new BlockPos(player.getPosition().getX() - 1, player.getPosition().getY(), player.getPosition().getZ())).getMaterial() != Material.AIR && player.world.getBlockState(new BlockPos(player.getPosition().getX() + 1, player.getPosition().getY(), player.getPosition().getZ())).getMaterial() != Material.AIR)) {
					if (player.isCrouching() || player.isAirBorne) {
						if (props.getAbility() && props.getCooldown() <= 0) {
							changePlayerDimension(player);

							world.getServer().getWorld(this.dimension).getEntities()
									.filter(entity -> entity instanceof LivingEntity)
									.filter(entity -> !(entity instanceof PlayerEntity))
									.filter(entity -> !(entity instanceof EntityStandBase))
									.filter(entity -> entity.getDistance(player) < 3.0f || entity.getDistance(this) < 3.0f)
									.forEach(this::transportEntity);

							world.getPlayers()
									.stream()
									.filter(playerEntity -> Stand.getCapabilityFromPlayer(playerEntity).getStandID() != Util.StandID.GER)
									.filter(playerEntity -> player.getDistance(player) < 3.0f || playerEntity.getDistance(this) < 3.0f)
									.forEach(this::changePlayerDimension);

							player.getFoodStats().addStats(-3, 0.0f);
							props.setStandOn(false);
							props.setCooldown(200);
							this.remove();
						}
					}
				}
			});
			if (standOn) {
				followMaster();
				setRotationYawHead(player.rotationYaw);
				setRotation(player.rotationYaw, player.rotationPitch);

				if (!player.isAlive())
					remove();
				if (player.isSprinting()) {
					if (attackSwing(player))
						this.oratick++;
					if (this.oratick == 1) {
						if (!player.isCreative())
							player.getFoodStats().addStats(-1, 0.0F);
						if (!this.world.isRemote)
							this.orarush = true;
					}
				} else if (attackSwing(getMaster())) {
					if (!this.world.isRemote) {
						this.oratick++;
						if (this.oratick == 1) {
							this.world.playSound(null, new BlockPos(this.getPosX(), this.getPosY(), this.getPosZ()), SoundInit.PUNCH_MISS.get(), getSoundCategory(), 1.0F, 0.8F / (this.rand.nextFloat() * 0.4F + 1.2F) + 0.5F);
							EntityStandPunch.DirtyDeedsDoneDirtCheap dirtyDeedsDoneDirtCheap = new EntityStandPunch.DirtyDeedsDoneDirtCheap(this.world, this, player);
							dirtyDeedsDoneDirtCheap.shoot(player, player.rotationPitch, player.rotationYaw, 2.0F, 0.2F);
							this.world.addEntity(dirtyDeedsDoneDirtCheap);
						}
					}
				}
				if (player.swingProgressInt == 0)
					this.oratick = 0;
				if (this.orarush) {
					player.setSprinting(false);
					this.oratickr++;
					if (this.oratickr >= 10)
						if (!this.world.isRemote) {
							player.setSprinting(false);
							EntityStandPunch.DirtyDeedsDoneDirtCheap dirtyDeedsDoneDirtCheap1 = new EntityStandPunch.DirtyDeedsDoneDirtCheap(this.world, this, player);
							dirtyDeedsDoneDirtCheap1.setRandomPositions();
							dirtyDeedsDoneDirtCheap1.shoot(player, player.rotationPitch, player.rotationYaw, 2.0F, 0.2F);
							this.world.addEntity(dirtyDeedsDoneDirtCheap1);
							EntityStandPunch.DirtyDeedsDoneDirtCheap dirtyDeedsDoneDirtCheap2 = new EntityStandPunch.DirtyDeedsDoneDirtCheap(this.world, this, player);
							dirtyDeedsDoneDirtCheap2.setRandomPositions();
							dirtyDeedsDoneDirtCheap2.shoot(player, player.rotationPitch, player.rotationYaw, 2.0F, 0.2F);
							this.world.addEntity(dirtyDeedsDoneDirtCheap2);
						}
					if (this.oratickr >= 80) {
						this.orarush = false;
						this.oratickr = 0;
					}
				}
			}
		}
	}
}
