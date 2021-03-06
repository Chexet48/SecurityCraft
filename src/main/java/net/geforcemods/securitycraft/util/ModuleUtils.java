package net.geforcemods.securitycraft.util;

import java.util.ArrayList;
import java.util.List;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.api.CustomizableTileEntity;
import net.geforcemods.securitycraft.items.ModuleItem;
import net.geforcemods.securitycraft.misc.CustomModules;
import net.geforcemods.securitycraft.tileentity.InventoryScannerTileEntity;
import net.geforcemods.securitycraft.tileentity.KeycardReaderTileEntity;
import net.geforcemods.securitycraft.tileentity.KeypadTileEntity;
import net.geforcemods.securitycraft.tileentity.RetinalScannerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ModuleUtils{
	//North: Z-  South: Z+  East: X+  West: X-  Up: Y+  Down: Y-

	public static void checkForBlockAndInsertModule(World world, BlockPos pos, String dir, Block blockToCheckFor, int range, ItemStack module, boolean updateAdjacentBlocks){
		for(int i = 1; i <= range; i++)
			if(dir.equalsIgnoreCase("x+")){
				if(world.getBlockState(pos.east(i)).getBlock() == blockToCheckFor && !((CustomizableTileEntity) world.getTileEntity(pos.east(i))).hasModule(CustomModules.getModuleFromStack(module))){
					((CustomizableTileEntity) world.getTileEntity(pos.east(i))).insertModule(module);
					if(updateAdjacentBlocks)
						checkInAllDirsAndInsertModule(world, pos.east(i), blockToCheckFor, range, module, updateAdjacentBlocks);
				}
			}else if(dir.equalsIgnoreCase("x-")){
				if(world.getBlockState(pos.west(i)).getBlock() == blockToCheckFor && !((CustomizableTileEntity) world.getTileEntity(pos.west(i))).hasModule(CustomModules.getModuleFromStack(module))){
					((CustomizableTileEntity) world.getTileEntity(pos.west(i))).insertModule(module);
					if(updateAdjacentBlocks)
						checkInAllDirsAndInsertModule(world, pos.west(i), blockToCheckFor, range, module, updateAdjacentBlocks);
				}
			}else if(dir.equalsIgnoreCase("y+")){
				if(world.getBlockState(pos.up(i)).getBlock() == blockToCheckFor && !((CustomizableTileEntity) world.getTileEntity(pos.up(i))).hasModule(CustomModules.getModuleFromStack(module))){
					((CustomizableTileEntity) world.getTileEntity(pos.up(i))).insertModule(module);
					if(updateAdjacentBlocks)
						checkInAllDirsAndInsertModule(world, pos.up(i), blockToCheckFor, range, module, updateAdjacentBlocks);
				}
			}else if(dir.equalsIgnoreCase("y-")){
				if(world.getBlockState(pos.down(i)).getBlock() == blockToCheckFor && !((CustomizableTileEntity) world.getTileEntity(pos.down(i))).hasModule(CustomModules.getModuleFromStack(module))){
					((CustomizableTileEntity) world.getTileEntity(pos.down(i))).insertModule(module);
					if(updateAdjacentBlocks)
						checkInAllDirsAndInsertModule(world, pos.down(i), blockToCheckFor, range, module, updateAdjacentBlocks);
				}
			}else if(dir.equalsIgnoreCase("z+")){
				if(world.getBlockState(pos.south(i)).getBlock() == blockToCheckFor && !((CustomizableTileEntity) world.getTileEntity(pos.south(i))).hasModule(CustomModules.getModuleFromStack(module))){
					((CustomizableTileEntity) world.getTileEntity(pos.south(i))).insertModule(module);
					if(updateAdjacentBlocks)
						checkInAllDirsAndInsertModule(world, pos.south(i), blockToCheckFor, range, module, updateAdjacentBlocks);
				}
			}else if(dir.equalsIgnoreCase("z-"))
				if(world.getBlockState(pos.north(i)).getBlock() == blockToCheckFor && !((CustomizableTileEntity) world.getTileEntity(pos.north(i))).hasModule(CustomModules.getModuleFromStack(module))){
					((CustomizableTileEntity) world.getTileEntity(pos.north(i))).insertModule(module);
					if(updateAdjacentBlocks)
						checkInAllDirsAndInsertModule(world, pos.north(i), blockToCheckFor, range, module, updateAdjacentBlocks);
				}
	}

	public static void checkInAllDirsAndInsertModule(World world, BlockPos pos, Block blockToCheckFor, int range, ItemStack module, boolean updateAdjacentBlocks){
		checkForBlockAndInsertModule(world, pos, "x+", blockToCheckFor, range, module, updateAdjacentBlocks);
		checkForBlockAndInsertModule(world, pos, "x-", blockToCheckFor, range, module, updateAdjacentBlocks);
		checkForBlockAndInsertModule(world, pos, "y+", blockToCheckFor, range, module, updateAdjacentBlocks);
		checkForBlockAndInsertModule(world, pos, "y-", blockToCheckFor, range, module, updateAdjacentBlocks);
		checkForBlockAndInsertModule(world, pos, "z+", blockToCheckFor, range, module, updateAdjacentBlocks);
		checkForBlockAndInsertModule(world, pos, "z-", blockToCheckFor, range, module, updateAdjacentBlocks);
	}

	public static void checkForBlockAndRemoveModule(World world, BlockPos pos, String dir, Block blockToCheckFor, int range, CustomModules module, boolean updateAdjacentBlocks){
		for(int i = 1; i <= range; i++)
			if(dir.equalsIgnoreCase("x+")){
				if(world.getBlockState(pos.east(i)).getBlock() == blockToCheckFor && ((CustomizableTileEntity) world.getTileEntity(pos.east(i))).hasModule(module)){
					((CustomizableTileEntity) world.getTileEntity(pos.east(i))).removeModule(module);
					if(updateAdjacentBlocks)
						checkInAllDirsAndRemoveModule(world, pos.east(i), blockToCheckFor, range, module, updateAdjacentBlocks);
				}
			}else if(dir.equalsIgnoreCase("x-")){
				if(world.getBlockState(pos.west(i)).getBlock() == blockToCheckFor && ((CustomizableTileEntity) world.getTileEntity(pos.west(i))).hasModule(module)){
					((CustomizableTileEntity) world.getTileEntity(pos.west(i))).removeModule(module);
					if(updateAdjacentBlocks)
						checkInAllDirsAndRemoveModule(world, pos.west(i), blockToCheckFor, range, module, updateAdjacentBlocks);
				}
			}else if(dir.equalsIgnoreCase("y+")){
				if(world.getBlockState(pos.up(i)).getBlock() == blockToCheckFor && ((CustomizableTileEntity) world.getTileEntity(pos.up(i))).hasModule(module)){
					((CustomizableTileEntity) world.getTileEntity(pos.up(i))).removeModule(module);
					if(updateAdjacentBlocks)
						checkInAllDirsAndRemoveModule(world, pos.up(i), blockToCheckFor, range, module, updateAdjacentBlocks);
				}
			}else if(dir.equalsIgnoreCase("y-")){
				if(world.getBlockState(pos.down(i)).getBlock() == blockToCheckFor && ((CustomizableTileEntity) world.getTileEntity(pos.down(i))).hasModule(module)){
					((CustomizableTileEntity) world.getTileEntity(pos.down(i))).removeModule(module);
					if(updateAdjacentBlocks)
						checkInAllDirsAndRemoveModule(world, pos.down(i), blockToCheckFor, range, module, updateAdjacentBlocks);
				}
			}else if(dir.equalsIgnoreCase("z+")){
				if(world.getBlockState(pos.south(i)).getBlock() == blockToCheckFor && ((CustomizableTileEntity) world.getTileEntity(pos.south(i))).hasModule(module)){
					((CustomizableTileEntity) world.getTileEntity(pos.south(i))).removeModule(module);
					if(updateAdjacentBlocks)
						checkInAllDirsAndRemoveModule(world, pos.south(i), blockToCheckFor, range, module, updateAdjacentBlocks);
				}
			}else if(dir.equalsIgnoreCase("z-"))
				if(world.getBlockState(pos.north(i)).getBlock() == blockToCheckFor && ((CustomizableTileEntity) world.getTileEntity(pos.north(i))).hasModule(module)){
					((CustomizableTileEntity) world.getTileEntity(pos.north(i))).removeModule(module);
					if(updateAdjacentBlocks)
						checkInAllDirsAndRemoveModule(world, pos.north(i), blockToCheckFor, range, module, updateAdjacentBlocks);
				}
	}

	public static void checkInAllDirsAndRemoveModule(World world, BlockPos pos, Block blockToCheckFor, int range, CustomModules module, boolean updateAdjacentBlocks){
		checkForBlockAndRemoveModule(world, pos, "x+", blockToCheckFor, range, module, updateAdjacentBlocks);
		checkForBlockAndRemoveModule(world, pos, "x-", blockToCheckFor, range, module, updateAdjacentBlocks);
		checkForBlockAndRemoveModule(world, pos, "y+", blockToCheckFor, range, module, updateAdjacentBlocks);
		checkForBlockAndRemoveModule(world, pos, "y-", blockToCheckFor, range, module, updateAdjacentBlocks);
		checkForBlockAndRemoveModule(world, pos, "z+", blockToCheckFor, range, module, updateAdjacentBlocks);
		checkForBlockAndRemoveModule(world, pos, "z-", blockToCheckFor, range, module, updateAdjacentBlocks);
	}

	public static List<String> getPlayersFromModule(World world, BlockPos pos, CustomModules module) {
		CustomizableTileEntity te = (CustomizableTileEntity) world.getTileEntity(pos);

		if(te.hasModule(module))
			return getPlayersFromModule(te.getModule(module));
		else return new ArrayList<>();
	}

	public static List<String> getPlayersFromModule(ItemStack stack)
	{
		List<String> list = new ArrayList<>();

		if(stack.getItem() instanceof ModuleItem)
		{
			for(int i = 1; i <= 10; i++)
			{
				if(stack.getTag() != null && stack.getTag().getString("Player" + i) != null && !stack.getTag().getString("Player" + i).isEmpty())
					list.add(stack.getTag().getString("Player" + i).toLowerCase());
			}
		}

		return list;
	}

	public static boolean checkForModule(World world, BlockPos pos, PlayerEntity player, CustomModules module){
		TileEntity te = world.getTileEntity(pos);

		if(te == null || !(te instanceof CustomizableTileEntity))
			return false;

		if(te instanceof KeypadTileEntity){
			if(module == CustomModules.WHITELIST && ((CustomizableTileEntity) te).hasModule(CustomModules.WHITELIST) && ModuleUtils.getPlayersFromModule(world, pos, CustomModules.WHITELIST).contains(player.getName().getFormattedText().toLowerCase())){
				PlayerUtils.sendMessageToPlayer(player, ClientUtils.localize(SCContent.keypad.getTranslationKey()), ClientUtils.localize("messages.securitycraft:module.whitelisted"), TextFormatting.GREEN);
				return true;
			}

			if(module == CustomModules.BLACKLIST && ((CustomizableTileEntity) te).hasModule(CustomModules.BLACKLIST) && ModuleUtils.getPlayersFromModule(world, pos, CustomModules.BLACKLIST).contains(player.getName().getFormattedText().toLowerCase())){
				PlayerUtils.sendMessageToPlayer(player, ClientUtils.localize(SCContent.keypad.getTranslationKey()), ClientUtils.localize("messages.securitycraft:module.blacklisted"), TextFormatting.RED);
				return true;
			}
		}else if(te instanceof KeycardReaderTileEntity){
			if(module == CustomModules.WHITELIST && ((CustomizableTileEntity) te).hasModule(CustomModules.WHITELIST) && ModuleUtils.getPlayersFromModule(world, pos, CustomModules.WHITELIST).contains(player.getName().getFormattedText().toLowerCase())){
				PlayerUtils.sendMessageToPlayer(player, ClientUtils.localize(SCContent.keycardReader.getTranslationKey()), ClientUtils.localize("messages.securitycraft:module.whitelisted"), TextFormatting.GREEN);
				world.notifyNeighborsOfStateChange(pos, world.getBlockState(pos).getBlock());
				return true;
			}

			if(module == CustomModules.BLACKLIST && ((CustomizableTileEntity) te).hasModule(CustomModules.BLACKLIST) && ModuleUtils.getPlayersFromModule(world, pos, CustomModules.BLACKLIST).contains(player.getName().getFormattedText().toLowerCase())){
				PlayerUtils.sendMessageToPlayer(player, ClientUtils.localize(SCContent.keycardReader.getTranslationKey()), ClientUtils.localize("messages.securitycraft:module.blacklisted"), TextFormatting.RED);
				return true;
			}
		}else if(te instanceof RetinalScannerTileEntity){
			if(module == CustomModules.WHITELIST && ((CustomizableTileEntity) te).hasModule(CustomModules.WHITELIST) && ModuleUtils.getPlayersFromModule(world, pos, CustomModules.WHITELIST).contains(player.getName().getFormattedText().toLowerCase()))
				return true;
		}else if(te instanceof InventoryScannerTileEntity)
			if(module == CustomModules.WHITELIST && ((CustomizableTileEntity) te).hasModule(CustomModules.WHITELIST) && ModuleUtils.getPlayersFromModule(world, pos, CustomModules.WHITELIST).contains(player.getName().getFormattedText().toLowerCase()))
				return true;

		return false;
	}
}