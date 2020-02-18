package net.geforcemods.securitycraft.items;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.api.CustomizableTileEntity;
import net.geforcemods.securitycraft.api.IOwnable;
import net.geforcemods.securitycraft.api.LinkedAction;
import net.geforcemods.securitycraft.api.OwnableTileEntity;
import net.geforcemods.securitycraft.blocks.CageTrapBlock;
import net.geforcemods.securitycraft.blocks.InventoryScannerBlock;
import net.geforcemods.securitycraft.blocks.LaserBlock;
import net.geforcemods.securitycraft.blocks.OwnableBlock;
import net.geforcemods.securitycraft.blocks.ScannerDoorBlock;
import net.geforcemods.securitycraft.blocks.reinforced.ReinforcedDoorBlock;
import net.geforcemods.securitycraft.tileentity.InventoryScannerTileEntity;
import net.geforcemods.securitycraft.util.ClientUtils;
import net.geforcemods.securitycraft.util.PlayerUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class UniversalBlockRemoverItem extends Item
{
	public UniversalBlockRemoverItem(Properties properties)
	{
		super(properties);
	}

	@Override
	public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext ctx)
	{
		World world = ctx.getWorld();
		BlockPos pos = ctx.getPos();
		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		TileEntity tileEntity = world.getTileEntity(pos);
		PlayerEntity player = ctx.getPlayer();

		if(tileEntity != null && isOwnableBlock(block, tileEntity))
		{
			if(!((IOwnable) tileEntity).getOwner().isOwner(player))
			{
				PlayerUtils.sendMessageToPlayer(player, ClientUtils.localize(SCContent.universalBlockRemover.getTranslationKey()), ClientUtils.localize("messages.securitycraft:notOwned").replace("#", ((IOwnable) tileEntity).getOwner().getName()), TextFormatting.RED);
				return ActionResultType.FAIL;
			}

			if(block == SCContent.laserBlock)
			{
				CustomizableTileEntity te = (CustomizableTileEntity)world.getTileEntity(pos);

				for(ItemStack module : te.modules)
				{
					if(!module.isEmpty())
						te.createLinkedBlockAction(LinkedAction.MODULE_REMOVED, new Object[] {module, ((ModuleItem)module.getItem()).getModule()}, te);
				}

				world.destroyBlock(pos, true);
				LaserBlock.destroyAdjacentLasers(world, pos);
				player.inventory.getCurrentItem().damageItem(1, player, p -> p.sendBreakAnimation(ctx.getHand()));
			}
			else if(block == SCContent.cageTrap && world.getBlockState(pos).get(CageTrapBlock.DEACTIVATED))
			{
				BlockPos originalPos = pos;
				BlockPos middlePos = originalPos.up(4);

				new CageTrapBlock.BlockModifier(world, new BlockPos.Mutable(originalPos), ((IOwnable)tileEntity).getOwner()).loop((w, p, o) -> {
					TileEntity te = w.getTileEntity(p);

					if(te instanceof IOwnable && ((IOwnable)te).getOwner().equals(o))
					{
						Block b = w.getBlockState(p).getBlock();

						if(b == SCContent.reinforcedIronBars || (p.equals(middlePos) && b == SCContent.horizontalReinforcedIronBars))
							w.destroyBlock(p, false);
					}
				});

				world.destroyBlock(originalPos, false);
			}
			else
			{
				if((block instanceof ReinforcedDoorBlock || block instanceof ScannerDoorBlock) && state.get(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER)
					pos = pos.down();

				if(block == SCContent.inventoryScanner)
				{
					InventoryScannerTileEntity te = InventoryScannerBlock.getConnectedInventoryScanner(world, pos);

					if(te != null)
						te.modules.clear();
				}

				world.destroyBlock(pos, true);
				world.removeTileEntity(pos);
				player.inventory.getCurrentItem().damageItem(1, player, p -> p.sendBreakAnimation(ctx.getHand()));
			}

			return ActionResultType.SUCCESS;
		}

		return ActionResultType.PASS;
	}

	private static boolean isOwnableBlock(Block block, TileEntity te)
	{
		return (te instanceof OwnableTileEntity || te instanceof IOwnable || block instanceof OwnableBlock);
	}
}