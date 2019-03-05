package net.geforcemods.securitycraft.blocks;

import net.geforcemods.securitycraft.ConfigHandler.ServerConfig;
import net.geforcemods.securitycraft.api.CustomizableSCTE;
import net.geforcemods.securitycraft.misc.EnumCustomModules;
import net.geforcemods.securitycraft.tileentity.TileEntityPortableRadar;
import net.geforcemods.securitycraft.util.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.state.IProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.BlockStateContainer;

public class BlockPortableRadar extends BlockContainer {

	public static final PropertyBool POWERED = PropertyBool.create("powered");

	public BlockPortableRadar(Material material) {
		super(Block.Properties.create(material).hardnessAndResistance(-1.0F, 6000000.0F));
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean isNormalCube(IBlockState state){
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state){
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader source, BlockPos pos)
	{
		//		return new AxisAlignedBB(0.3F, 0.0F, 0.3F, 0.7F, 0.45F, 0.7F);
		return VoxelShapes.fullCube();
	}

	public static void togglePowerOutput(World world, BlockPos pos, boolean par5) {
		if(par5 && !world.getBlockState(pos).get(POWERED)){
			BlockUtils.setBlockProperty(world, pos, POWERED, true, true);
			BlockUtils.updateAndNotify(world, pos, BlockUtils.getBlock(world, pos), 1, false);
		}else if(!par5 && world.getBlockState(pos).get(POWERED)){
			BlockUtils.setBlockProperty(world, pos, POWERED, false, true);
			BlockUtils.updateAndNotify(world, pos, BlockUtils.getBlock(world, pos), 1, false);
		}
	}

	@Override
	public boolean canProvidePower(IBlockState state)
	{
		return true;
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockReader blockAccess, BlockPos pos, EnumFacing side){
		if(blockState.get(POWERED) && ((CustomizableSCTE) blockAccess.getTileEntity(pos)).hasModule(EnumCustomModules.REDSTONE))
			return 15;
		else
			return 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return meta == 1 ? getDefaultState().withProperty(POWERED, true) : getDefaultState().withProperty(POWERED, false);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(POWERED).booleanValue() ? 1 : 0;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {POWERED});
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader world) {
		return new TileEntityPortableRadar().attacks(EntityPlayer.class, ServerConfig.CONFIG.portableRadarSearchRadius.get(), ServerConfig.CONFIG.portableRadarDelay.get()).nameable();
	}

}