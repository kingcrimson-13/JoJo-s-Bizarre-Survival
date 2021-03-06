package com.novarch.jojomod.entities.stands.goldExperienceRequiem;

import com.novarch.jojomod.capabilities.stand.Stand;
import com.novarch.jojomod.entities.stands.EntityStandBase;
import com.novarch.jojomod.entities.stands.EntityStandPunch;
import com.novarch.jojomod.init.EntityInit;
import com.novarch.jojomod.init.SoundInit;
import com.novarch.jojomod.util.Util;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("ConstantConditions")
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class EntityGoldExperienceRequiem extends EntityStandBase {
	private int oratick = 0;

	private int oratickr = 0;

	private StringTextComponent truthname = new StringTextComponent("You will never reach the truth.");

	private boolean truth = false;

	public EntityGoldExperienceRequiem(EntityType<? extends EntityStandBase> type, World world) {
		super(type, world);
		this.spawnSound = SoundInit.SPAWN_GER.get();
		this.standID = Util.StandID.GER;
	}

	public EntityGoldExperienceRequiem(World world) {
		super(EntityInit.GOLD_EXPERIENCE_REQUIEM.get(), world);
		this.spawnSound = SoundInit.SPAWN_GER.get();
		this.standID = Util.StandID.GER;
	}

	public void toggleFlight() {
		if (getMaster() != null)
			getMaster().setNoGravity(!getMaster().hasNoGravity());
	}

	public void toggleTruth() {
		truth = !truth;
	}

	@Override
	public void tick() {
		super.tick();

		if (getMaster() != null) {
			PlayerEntity player = getMaster();

			Stand.getLazyOptional(player).ifPresent(props -> {
				this.ability = props.getAbility();

				//Cooldown handler
				if (props.getTransformed() > 1) {
					props.subtractCooldown(1);
				}
				if (props.getCooldown() <= 0) {
					props.setTransformed(0);
					props.setCooldown(60);
				}

				player.getFoodStats().addStats(20, 20.0f);

				//Gold Experience Requiem's ability
				if (ability) {
					if (player.getLastAttackedEntity() != null) {
						if (truth)
							player.getLastAttackedEntity().attackEntityFrom(DamageSource.OUT_OF_WORLD, 3.0f);

						if (player.getLastAttackedEntity() instanceof PlayerEntity) {
							props.setDiavolo(player.getLastAttackedEntity().getDisplayName().toString());
						}
					}
					for (PlayerEntity playerEntity : this.world.getPlayers()) {
						if (playerEntity != this.getMaster()) {
							if (playerEntity.getLastAttackedEntity() == this.getMaster()) {
								props.setDiavolo(playerEntity.getDisplayName().toString());
							}
						}
					}

					if (props.getDiavolo() != null && !props.getDiavolo().equals("")) {
						for (PlayerEntity playerEntity : this.world.getPlayers()) {
							if (playerEntity != this.getMaster()) {
								if (playerEntity.getDisplayName().toString().equals(props.getDiavolo())) {
									if (playerEntity.isAlive()) {
										world.getServer().getWorld(dimension).getEntities()
												.filter(entity -> entity instanceof MobEntity)
												.forEach(entity -> ((MobEntity) entity).setAttackTarget(playerEntity));
										CreeperEntity truth = new CreeperEntity(EntityType.CREEPER, playerEntity.world);
										truth.setCustomName(truthname);
										truth.setPosition(playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ());
										truth.setAttackTarget(playerEntity);
										truth.setDropChance(EquipmentSlotType.MAINHAND, 0.0f);
										playerEntity.world.addEntity(truth);
										truth.setAttackTarget(playerEntity);
									} else {
										if (!world.isRemote) {
											world.getServer().getWorld(dimension).getEntities()
													.filter(entity -> entity instanceof CreeperEntity)
													.filter(entity -> entity.getCustomName().equals(truthname))
													.forEach(Entity::remove);
										}
									}
								}
							}
						}
					}
				}
			});

			followMaster();
			setRotationYawHead(player.rotationYaw);
			setRotation(player.rotationYaw, player.rotationPitch);

			if (!player.isAlive())
				remove();
			if (player.isSprinting()) {
				if (attackSwing(player))
					this.oratick++;
				if (this.oratick == 1) {
					if (!this.world.isRemote)
						this.world.playSound(null, new BlockPos(this.getPosX(), this.getPosY(), this.getPosZ()), SoundInit.MUDAGIORNO.get(), getSoundCategory(), 1.0F, 1.0F);

					if (!player.isCreative())
						player.getFoodStats().addStats(0, 0.0F);
					if (!this.world.isRemote)
						this.orarush = true;
				}
			} else if (attackSwing(getMaster())) {
				if (!this.world.isRemote) {
					this.oratick++;
					if (this.oratick == 1) {
						this.world.playSound(null, new BlockPos(this.getPosX(), this.getPosY(), this.getPosZ()), SoundInit.PUNCH_MISS.get(), getSoundCategory(), 1.0F, 0.8F / (this.rand.nextFloat() * 0.4F + 1.2F) + 0.5F);
						EntityStandPunch.GoldExperienceRequiem goldExperienceRequiem = new EntityStandPunch.GoldExperienceRequiem(this.world, this, player);
						goldExperienceRequiem.shoot(player, player.rotationPitch, player.rotationYaw, 2.0F, 0.2F);
						this.world.addEntity(goldExperienceRequiem);
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
						EntityStandPunch.GoldExperienceRequiem goldExperienceRequiem1 = new EntityStandPunch.GoldExperienceRequiem(this.world, this, player);
						goldExperienceRequiem1.setRandomPositions();
						goldExperienceRequiem1.shoot(player, player.rotationPitch, player.rotationYaw, 2.0F, 0.2F);
						this.world.addEntity(goldExperienceRequiem1);
						EntityStandPunch.GoldExperienceRequiem goldExperienceRequiem2 = new EntityStandPunch.GoldExperienceRequiem(this.world, this, player);
						goldExperienceRequiem2.setRandomPositions();
						goldExperienceRequiem2.shoot(player, player.rotationPitch, player.rotationYaw, 2.0F, 0.2F);
						this.world.addEntity(goldExperienceRequiem2);
					}
				if (this.oratickr >= 110) {
					this.orarush = false;
					this.oratickr = 0;
				}
			}
		}
	}
}
