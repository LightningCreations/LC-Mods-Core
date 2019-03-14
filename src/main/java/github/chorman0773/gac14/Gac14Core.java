package github.chorman0773.gac14;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

import javax.annotation.Nonnull;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("gac14-core")
public class Gac14Core
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public Gac14Core() {
    	assert instance==null:"Initialization of Gac14Core shall occur exactly once";
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        instance = this;
        
    }
    private MinecraftServer server;
    
    @Nonnull
    public MinecraftServer getServer() {
    	assert server!=null:"Server has not been initialized. Cannot be called until after onServerStarting has completed";
    	return server;
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        server = event.getServer();
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        public static void createRegistries(RegistryEvent.NewRegistry registry) {
        	new RegistryBuilder<Gac14Module<?>>().setType((Class<Gac14Module<?>>)(Class<?>)Gac14Module.class).allowModification().create();
        }
        public static void registerModule(RegistryEvent.Register<Gac14Module<?>> modules) {
        	modules.getRegistry().register(new CoreModule());
        }
    }
    
    private static Gac14Core instance;

    @Nonnull
	public static Gac14Core getInstance() {
		assert instance!=null:"Mod Construction Has not occured yet, Construction Must Complete";
		return instance;
	}
}
