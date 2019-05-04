package github.chorman0773.gac14;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.registries.RegistryBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import github.chorman0773.gac14.permissions.PermissionManager;

import java.nio.file.Path;
import java.util.UUID;

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
    private PermissionManager manager;
    private Path root;
    private Path config;
    private Path data;
    private Path players;
    
    @Nonnull
    public MinecraftServer getServer() {
    	assert server!=null:"Server has not been initialized. Cannot be called until after onServerStarting has completed";
    	return server;
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        server = event.getServer();
        root = server.getDataDirectory().toPath();
        manager = new PermissionManager();
        config = root.resolve("config");
        data = root.resolve("data");
        
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
    
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
    public static class RegisterStuff{
    	 
    }
    
    private static Gac14Core instance;

    @Nonnull
	public static Gac14Core getInstance() {
		assert instance!=null:"Mod Construction Has not occured yet, Construction Must Complete";
		return instance;
	}
    
    @Nonnull
    public PermissionManager getPermissionManager() {
    	assert manager!=null;
    	return manager;
    }
    
    
    public Path getConfigPath(ResourceLocation loc) {
    	String name = loc.getNamespace()+"/"+loc.getPath();
    	return config.resolve(name);
    }
    
    public Path getStoragePath(ResourceLocation loc) {
    	String name = loc.getNamespace()+"/"+loc.getPath();
    	return config.resolve(name);
    }
    
    public Path getConfigPath(String loc) {
    	String name = loc.replace(':', '/');
    	return config.resolve(name);
    }
    
    public Path getStoragePath(String loc) {
    	String name = loc.replace(':', '/');
    	return config.resolve(name);
    }
    
    public Path getPlayerProfileFile(UUID id) {
    	return getStoragePath("gac14:core/players").resolve(id+".dat");
    }
}
