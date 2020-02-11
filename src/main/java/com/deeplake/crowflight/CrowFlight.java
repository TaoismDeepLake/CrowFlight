package com.deeplake.crowflight;

import com.deeplake.crowflight.proxy.ProxyBase;
import com.deeplake.crowflight.util.Reference;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = CrowFlight.MODID, name = CrowFlight.NAME, version = CrowFlight.VERSION)
public class CrowFlight
{
    public static final String MODID = "crowflight";
    public static final String NAME = "Crow Flight";
    public static final String VERSION = "0.1.2";

    private static Logger logger;

    public static final boolean SHOW_WARN = true;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static ProxyBase proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        //GameRegistry.registerWorldGenerator(new ModWorldGen(), 100);
    }

    @EventHandler
    public static void Init(FMLInitializationEvent event)
    {
        //ModRecipes.Init();
    }

    public static void LogWarning(String str)
    {
        if (SHOW_WARN)
        {
            logger.warn(str);
        }
    }

    public static void Log(String str)
    {
        if (SHOW_WARN)
        {
            logger.info(str);
        }
    }
}
