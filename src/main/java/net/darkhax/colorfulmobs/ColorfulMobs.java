package net.darkhax.colorfulmobs;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.darkhax.colorfulmobs.common.CommonProxy;
import net.darkhax.colorfulmobs.common.PacketColorSync;
import net.darkhax.colorfulmobs.common.handler.EntityHandler;
import net.darkhax.colorfulmobs.common.handler.GuiHandler;
import net.darkhax.colorfulmobs.common.items.ItemColorWand;
import net.darkhax.colorfulmobs.common.items.ItemColoredPowder;
import net.darkhax.colorfulmobs.common.items.ItemGhostDust;
import net.darkhax.colorfulmobs.lib.Constants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

import java.util.Arrays;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER)
public class ColorfulMobs {

    public static SimpleNetworkWrapper network;

    @SidedProxy(clientSide = Constants.CLIENT_PROXY_CLASS, serverSide = Constants.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.Instance(Constants.MOD_ID)
    public static ColorfulMobs instance;

    public static CreativeTabs tabColor = new CreativeTabColor();
    public static Item itemColorWand = new ItemColorWand();
    public static Item itemGhostDust = new ItemGhostDust();
    public static Item itemPowder = new ItemColoredPowder();

    @EventHandler
    public void preInit(FMLPreInitializationEvent pre) {

        network = NetworkRegistry.INSTANCE.newSimpleChannel("ColorfulMobs");
        network.registerMessage(PacketColorSync.PacketColorSyncHandler.class, PacketColorSync.class, 0, Side.CLIENT);

        setModInfo(pre.getModMetadata());
        proxy.registerSidedEvents();
        GameRegistry.registerItem(itemColorWand, "colorWand", Constants.MOD_ID);
        GameRegistry.registerItem(itemGhostDust, "ghostDust", Constants.MOD_ID);
        GameRegistry.registerItem(itemPowder, "colorPowder", Constants.MOD_ID);
        MinecraftForge.EVENT_BUS.register(new EntityHandler());

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }

    void setModInfo(ModMetadata meta) {

        meta.authorList = Arrays.asList("Darkhax", "lclc98");
        meta.logoFile = "";
        meta.credits = "Maintained by Darkhax";
        meta.description = "More Swords provides a magnificent chain of lustrous new swords that expound upon the foundation of Minecraft weaponry. Razor sharp swords of magic as well as stronger, more practical blaes!";
        meta.url = "http://darkhax.net";
        meta.autogenerated = false;
    }
}