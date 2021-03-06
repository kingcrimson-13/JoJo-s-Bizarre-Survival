package com.novarch.jojomod.init;

import com.novarch.jojomod.JojoBizarreSurvival;
import com.novarch.jojomod.JojoBizarreSurvival.JojoItemGroup;
import com.novarch.jojomod.objects.items.ItemRemoveStand;
import com.novarch.jojomod.objects.items.ItemStandArrow;
import com.novarch.jojomod.objects.items.ItemStandDisc;
import com.novarch.jojomod.objects.items.stands.ItemEmperor;
import com.novarch.jojomod.util.Util;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
public class ItemInit
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, JojoBizarreSurvival.MOD_ID);

	public static final RegistryObject<Item> stand_arrow = ITEMS.register("stand_arrow", () -> new ItemStandArrow(new Item.Properties().maxStackSize(1).rarity(Rarity.create("arrow", TextFormatting.YELLOW)).group(JojoItemGroup.instance), 0, "On use, grants the user the power of a STAND."));
	public static final RegistryObject<Item> summon_king_crimson = ITEMS.register("summon_king_crimson", () -> new ItemStandArrow(new Item.Properties().maxStackSize(1).rarity(Rarity.create("king_crimson", TextFormatting.DARK_RED)).group(JojoItemGroup.instance), Util.StandID.kingCrimson, "Skips 10 seconds of time, allowing the user to see others movements."));
	public static final RegistryObject<Item> summon_d4c = ITEMS.register("summon_d4c", () -> new ItemStandArrow(new Item.Properties().maxStackSize(1).rarity(Rarity.create("d4c", TextFormatting.AQUA)).group(JojoItemGroup.instance), Util.StandID.dirtyDeedsDoneDirtCheap, "Allows the user to hop to parallel worlds when between two objects."));
	public static final RegistryObject<Item> summon_gold_experience = ITEMS.register("summon_gold_experience", () -> new ItemStandArrow(new Item.Properties().maxStackSize(1).rarity(Rarity.create("gold_experience", TextFormatting.GOLD)).group(JojoItemGroup.instance), Util.StandID.goldExperience, "Has the ability to turn objects into living creatures."));
	public static final RegistryObject<Item> summon_made_in_heaven = ITEMS.register("summon_made_in_heaven", () -> new ItemStandArrow(new Item.Properties().maxStackSize(1).rarity(Rarity.create("made_in_heaven", TextFormatting.GREEN)).group(JojoItemGroup.instance), Util.StandID.madeInHeaven, "Possess the ability to accelerate the passage of time."));
	public static final RegistryObject<Item> summon_aerosmith = ITEMS.register("summon_aerosmith", () -> new ItemStandArrow(new Item.Properties().maxStackSize(1).rarity(Rarity.create("aerosmith", TextFormatting.LIGHT_PURPLE)).group(JojoItemGroup.instance), Util.StandID.aerosmith, "Remote controlled Stand, allows the user to detect entities by their breathing."));
	public static final RegistryObject<Item> summon_weather_report = ITEMS.register("summon_weather_report", () -> new ItemStandArrow(new Item.Properties().maxStackSize(1).rarity(Rarity.create("weather_report", TextFormatting.WHITE)).group(JojoItemGroup.instance), Util.StandID.weatherReport, "Possess the ability to control the weather, is there something more to this ability?"));
	public static final RegistryObject<Item> summon_killer_queen = ITEMS.register("summon_killer_queen", () -> new ItemStandArrow(new Item.Properties().maxStackSize(1).rarity(Rarity.create("killer_queen", TextFormatting.GRAY)).group(JojoItemGroup.instance), Util.StandID.killerQueen, "Can turn anything it touches into a bomb.\n\nControls: \n"+ Util.KeyCodes.ability1+": Activate 1st bomb.\n"+ Util.KeyCodes.ability2+": Summon Sheer Heart Attack."));
	public static final RegistryObject<Item> summon_crazy_diamond = ITEMS.register("summon_crazy_diamond", () -> new ItemStandArrow(new Item.Properties().maxStackSize(1).rarity(Rarity.create("crazy_diamond", TextFormatting.BLUE)).group(JojoItemGroup.instance), Util.StandID.crazyDiamond, "Has the ability to revert objects to a previous state."));
	public static final RegistryObject<Item> summon_purple_haze = ITEMS.register("summon_purple_haze", () -> new ItemStandArrow(new Item.Properties().maxStackSize(1).rarity(Rarity.create("purple_haze", TextFormatting.DARK_PURPLE)).group(JojoItemGroup.instance), Util.StandID.purpleHaze, "Releases a deadly virus into the atmosphere."));
	public static final RegistryObject<Item> summon_the_emperor = ITEMS.register("summon_the_emperor", () -> new ItemStandArrow(new Item.Properties().maxStackSize(1).rarity(Rarity.create("emperor", TextFormatting.DARK_GRAY)).group(JojoItemGroup.instance), Util.StandID.theEmperor, ""));
	public static final RegistryObject<Item> summon_whitesnake = ITEMS.register("summon_whitesnake", () -> new ItemStandArrow(new Item.Properties().maxStackSize(1).rarity(Rarity.create("whitesnake", TextFormatting.WHITE)).group(JojoItemGroup.instance), Util.StandID.whitesnake, ""));
	public static final RegistryObject<Item> summon_cmoon = ITEMS.register("summon_cmoon", () -> new ItemStandArrow(new Item.Properties().maxStackSize(1).rarity(Rarity.create("cmoon", TextFormatting.DARK_GREEN)).group(JojoItemGroup.instance), Util.StandID.cMoon, ""));
	public static final RegistryObject<Item> summon_the_world = ITEMS.register("summon_the_world", () -> new ItemStandArrow(new Item.Properties().maxStackSize(1).rarity(Rarity.create("the_world", TextFormatting.YELLOW)).group(JojoItemGroup.instance), Util.StandID.theWorld, ""));
	public static final RegistryObject<Item> summon_star_platinum = ITEMS.register("summon_star_platinum", () -> new ItemStandArrow(new Item.Properties().maxStackSize(1).rarity(Rarity.create("star_platinum", TextFormatting.DARK_PURPLE)).group(JojoItemGroup.instance), Util.StandID.starPlatinum, ""));
	public static final RegistryObject<Item> summon_silver_chariot = ITEMS.register("summon_silver_chariot", () -> new ItemStandArrow(new Item.Properties().maxStackSize(1).rarity(Rarity.create("silver_chariot", TextFormatting.GRAY)).group(JojoItemGroup.instance), Util.StandID.silverChariot, ""));
	public static final RegistryObject<Item> summon_magicians_red = ITEMS.register("summon_magicians_red", () -> new ItemStandArrow(new Item.Properties().maxStackSize(1).rarity(Rarity.create("magicians_red", TextFormatting.RED)).group(JojoItemGroup.instance), Util.StandID.magiciansRed, ""));
	public static final RegistryObject<Item> summon_the_hand = ITEMS.register("summon_the_hand", () -> new ItemStandArrow(new Item.Properties().maxStackSize(1).rarity(Rarity.create("the_hand", TextFormatting.DARK_BLUE)).group(JojoItemGroup.instance), Util.StandID.theHand, ""));

	public static final RegistryObject<Item> stand_disc = ITEMS.register("stand_disc", () -> new ItemStandDisc(new Item.Properties().maxStackSize(1).group(JojoItemGroup.instance)));
	public static final RegistryObject<Item> the_emperor = ITEMS.register("the_emperor", () -> new ItemEmperor(new Item.Properties().maxStackSize(1)));
	public static final RegistryObject<Item> remove_stand = ITEMS.register("remove_stand", () -> new ItemRemoveStand(new Item.Properties().maxStackSize(1).rarity(Rarity.create("stand_disc", TextFormatting.GRAY)).group(JojoItemGroup.instance)));
	public static final RegistryObject<Item> canzoni_preferite = ITEMS.register("canzoni_preferite", () -> new MusicDiscItem(1, SoundInit.CANZONI_PREFERITE, new Item.Properties().maxStackSize(1).rarity(Rarity.RARE).group(JojoItemGroup.instance)));
}