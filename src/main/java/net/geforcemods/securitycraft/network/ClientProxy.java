package net.geforcemods.securitycraft.network;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.SecurityCraft;
import net.geforcemods.securitycraft.blocks.KeypadBlock;
import net.geforcemods.securitycraft.entity.BouncingBettyEntity;
import net.geforcemods.securitycraft.entity.BulletEntity;
import net.geforcemods.securitycraft.entity.IMSBombEntity;
import net.geforcemods.securitycraft.entity.SentryEntity;
import net.geforcemods.securitycraft.items.CameraMonitorItem;
import net.geforcemods.securitycraft.misc.KeyBindings;
import net.geforcemods.securitycraft.renderers.ItemKeypadChestRenderer;
import net.geforcemods.securitycraft.renderers.BouncingBettyRenderer;
import net.geforcemods.securitycraft.renderers.BulletRenderer;
import net.geforcemods.securitycraft.renderers.IMSBombRenderer;
import net.geforcemods.securitycraft.renderers.SentryRenderer;
import net.geforcemods.securitycraft.renderers.KeypadChestTileEntityRenderer;
import net.geforcemods.securitycraft.renderers.SecretSignTileEntityRenderer;
import net.geforcemods.securitycraft.renderers.SecurityCameraTileEntityRenderer;
import net.geforcemods.securitycraft.renderers.TrophySystemTileEntityRenderer;
import net.geforcemods.securitycraft.screen.BlockPocketManagerScreen;
import net.geforcemods.securitycraft.screen.BlockReinforcerScreen;
import net.geforcemods.securitycraft.screen.BriefcasePasswordScreen;
import net.geforcemods.securitycraft.screen.BriefcaseInventoryScreen;
import net.geforcemods.securitycraft.screen.BriefcaseSetupScreen;
import net.geforcemods.securitycraft.screen.CameraMonitorScreen;
import net.geforcemods.securitycraft.screen.CheckPasswordScreen;
import net.geforcemods.securitycraft.screen.CustomizeBlockScreen;
import net.geforcemods.securitycraft.screen.DisguiseModuleScreen;
import net.geforcemods.securitycraft.screen.EditModuleScreen;
import net.geforcemods.securitycraft.screen.EditSecretSignScreen;
import net.geforcemods.securitycraft.screen.IMSScreen;
import net.geforcemods.securitycraft.screen.InventoryScannerScreen;
import net.geforcemods.securitycraft.screen.KeyChangerScreen;
import net.geforcemods.securitycraft.screen.KeycardReaderSetupScreen;
import net.geforcemods.securitycraft.screen.KeypadFurnaceScreen;
import net.geforcemods.securitycraft.screen.UsernameLoggerScreen;
import net.geforcemods.securitycraft.screen.MineRemoteAccessToolScreen;
import net.geforcemods.securitycraft.screen.SCManualScreen;
import net.geforcemods.securitycraft.screen.SetPasswordScreen;
import net.geforcemods.securitycraft.tileentity.KeypadChestTileEntity;
import net.geforcemods.securitycraft.tileentity.SecretSignTileEntity;
import net.geforcemods.securitycraft.tileentity.SecurityCameraTileEntity;
import net.geforcemods.securitycraft.tileentity.TileEntityTrophySystem;
import net.geforcemods.securitycraft.util.Reinforced;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid=SecurityCraft.MODID, value=Dist.CLIENT)
public class ClientProxy implements IProxy {
	@Override
	public void clientSetup()
	{
		RenderingRegistry.registerEntityRenderingHandler(BouncingBettyEntity.class, manager -> new BouncingBettyRenderer(manager));
		RenderingRegistry.registerEntityRenderingHandler(IMSBombEntity.class, manager -> new IMSBombRenderer(manager));
		RenderingRegistry.registerEntityRenderingHandler(SentryEntity.class, manager -> new SentryRenderer(manager));
		RenderingRegistry.registerEntityRenderingHandler(BulletEntity.class, manager -> new BulletRenderer(manager));
		ClientRegistry.bindTileEntitySpecialRenderer(KeypadChestTileEntity.class, new KeypadChestTileEntityRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(SecurityCameraTileEntity.class, new SecurityCameraTileEntityRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(SecretSignTileEntity.class, new SecretSignTileEntityRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrophySystem.class, new TrophySystemTileEntityRenderer());
		ScreenManager.registerFactory(SCContent.cTypeBlockReinforcer, BlockReinforcerScreen::new);
		ScreenManager.registerFactory(SCContent.cTypeBriefcase, BriefcasePasswordScreen::new);
		ScreenManager.registerFactory(SCContent.cTypeBriefcaseInventory, BriefcaseInventoryScreen::new);
		ScreenManager.registerFactory(SCContent.cTypeBriefcaseSetup, BriefcaseSetupScreen::new);
		ScreenManager.registerFactory(SCContent.cTypeCustomizeBlock, CustomizeBlockScreen::new);
		ScreenManager.registerFactory(SCContent.cTypeDisguiseModule, DisguiseModuleScreen::new);
		ScreenManager.registerFactory(SCContent.cTypeInventoryScanner, InventoryScannerScreen::new);
		ScreenManager.registerFactory(SCContent.cTypeKeypadFurnace, KeypadFurnaceScreen::new);
		ScreenManager.registerFactory(SCContent.cTypeCheckPassword, CheckPasswordScreen::new);
		ScreenManager.registerFactory(SCContent.cTypeSetPassword, SetPasswordScreen::new);
		ScreenManager.registerFactory(SCContent.cTypeUsernameLogger, UsernameLoggerScreen::new);
		ScreenManager.registerFactory(SCContent.cTypeIMS, IMSScreen::new);
		ScreenManager.registerFactory(SCContent.cTypeKeycardSetup, KeycardReaderSetupScreen::new);
		ScreenManager.registerFactory(SCContent.cTypeKeyChanger, KeyChangerScreen::new);
		ScreenManager.registerFactory(SCContent.cTypeBlockPocketManager, BlockPocketManagerScreen::new);
		KeyBindings.init();
	}

	@Override
	public void registerKeypadChestItem(Register<Item> event)
	{
		event.getRegistry().register(new BlockItem(SCContent.keypadChest, new Item.Properties().group(SecurityCraft.groupSCTechnical).setTEISR(() -> () -> new ItemKeypadChestRenderer())).setRegistryName(SCContent.keypadChest.getRegistryName()));
	}

	@Override
	public void tint()
	{
		Map<Block,Integer> toTint = new HashMap<>();

		for(Field field : SCContent.class.getFields())
		{
			if(field.isAnnotationPresent(Reinforced.class) && field.getAnnotation(Reinforced.class).hasTint())
			{
				try
				{
					toTint.put((Block)field.get(null), field.getAnnotation(Reinforced.class).tint());
				}
				catch(IllegalArgumentException | IllegalAccessException e)
				{
					e.printStackTrace();
				}
			}
		}

		toTint.put(SCContent.blockPocketManager, 0x0E7063);
		toTint.put(SCContent.blockPocketWall, 0x0E7063);
		toTint.put(SCContent.chiseledCrystalQuartz, 0x15b3a2);
		toTint.put(SCContent.crystalQuartz, 0x15b3a2);
		toTint.put(SCContent.crystalQuartzPillar, 0x15b3a2);
		toTint.put(SCContent.crystalQuartzSlab, 0x15b3a2);
		toTint.put(SCContent.stairsCrystalQuartz, 0x15b3a2);
		toTint.forEach((block, tint) -> Minecraft.getInstance().getBlockColors().register((state, world, pos, tintIndex) -> tint, block));
		toTint.forEach((item, tint) -> Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> tint, item));
		Minecraft.getInstance().getBlockColors().register((state, world, pos, tintIndex) -> {
			Block block = Block.getBlockFromItem(KeypadBlock.getDisguisedStack(world, pos).getItem());

			if(block != Blocks.AIR && !(block instanceof KeypadBlock))
				return Minecraft.getInstance().getBlockColors().getColor(block.getDefaultState(), world, pos, tintIndex);
			else return 0xFFFFFF;
		}, SCContent.keypad);
	}

	@Override
	public World getClientWorld()
	{
		return Minecraft.getInstance().world;
	}

	@Override
	public PlayerEntity getClientPlayer()
	{
		return Minecraft.getInstance().player;
	}

	@Override
	public void displayMRATGui(ItemStack stack)
	{
		Minecraft.getInstance().displayGuiScreen(new MineRemoteAccessToolScreen(stack));
	}

	@Override
	public void displayEditModuleGui(ItemStack stack)
	{
		Minecraft.getInstance().displayGuiScreen(new EditModuleScreen(stack));
	}

	@Override
	public void displayCameraMonitorGui(PlayerInventory inv, CameraMonitorItem item, CompoundNBT stackTag)
	{
		Minecraft.getInstance().displayGuiScreen(new CameraMonitorScreen(inv, item, stackTag));
	}

	@Override
	public void displaySCManualGui()
	{
		Minecraft.getInstance().displayGuiScreen(new SCManualScreen());
	}

	@Override
	public void displayEditSecretSignGui(SecretSignTileEntity te)
	{
		Minecraft.getInstance().displayGuiScreen(new EditSecretSignScreen(te));
	}
}
