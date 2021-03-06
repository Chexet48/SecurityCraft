package net.geforcemods.securitycraft.misc.conditions;

import com.google.gson.JsonObject;

import net.geforcemods.securitycraft.ConfigHandler.CommonConfig;
import net.geforcemods.securitycraft.SecurityCraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public final class ToggleKeycard3Condition implements ICondition
{
	public static final ToggleKeycard3Condition INSTANCE = new ToggleKeycard3Condition();
	private static final ResourceLocation NAME = new ResourceLocation(SecurityCraft.MODID, "toggle_keycard_3");

	private ToggleKeycard3Condition() {}

	@Override
	public ResourceLocation getID()
	{
		return NAME;
	}

	@Override
	public boolean test()
	{
		return CommonConfig.CONFIG.ableToCraftKeycard3.get();
	}

	@Override
	public String toString()
	{
		return "Config value: " + test();
	}

	public static class Serializer implements IConditionSerializer<ToggleKeycard3Condition>
	{
		public static final Serializer INSTANCE = new Serializer();

		@Override
		public void write(JsonObject json, ToggleKeycard3Condition value) {}

		@Override
		public ToggleKeycard3Condition read(JsonObject json)
		{
			return ToggleKeycard3Condition.INSTANCE;
		}

		@Override
		public ResourceLocation getID()
		{
			return ToggleKeycard3Condition.NAME;
		}
	}
}