package net.geforcemods.securitycraft.blocks.reinforced;

import java.util.List;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.SecurityCraft;
import net.geforcemods.securitycraft.blocks.OwnableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.storage.loot.LootContext.Builder;

public class BaseReinforcedBlock extends OwnableBlock implements IReinforcedBlock
{
	private final Block vanillaBlock;

	public BaseReinforcedBlock(Material mat, Block vB, String registryPath)
	{
		this(mat, vB, registryPath, 0);
	}

	public BaseReinforcedBlock(Material mat, Block vB, String registryPath, int lightValue)
	{
		this(SoundType.STONE, mat, vB, registryPath, lightValue);
	}

	public BaseReinforcedBlock(SoundType soundType, Material mat, Block vB, String registryPath)
	{
		this(soundType, mat, vB, registryPath, 0);
	}

	public BaseReinforcedBlock(SoundType soundType, Material mat, Block vB, String registryPath, int lightValue)
	{
		super(soundType, Block.Properties.create(mat).hardnessAndResistance(-1.0F, 6000000.0F).lightValue(lightValue));

		vanillaBlock = vB;
		setRegistryName(new ResourceLocation(SecurityCraft.MODID, registryPath));
	}

	@Override
	public Block getVanillaBlock()
	{
		return vanillaBlock;
	}

	@Override
	public BlockState getConvertedState(BlockState vanillaState)
	{
		return getDefaultState();
	}

	@Override
	public boolean isBeaconBase(BlockState state, IWorldReader world, BlockPos pos, BlockPos beacon)
	{
		return this == SCContent.reinforcedIronBlock || this == SCContent.reinforcedGoldBlock || this == SCContent.reinforcedDiamondBlock || this == SCContent.reinforcedEmeraldBlock;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder)
	{
		return NonNullList.from(ItemStack.EMPTY, new ItemStack(this));
	}
}
