package github.chorman0773.gac14;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryInternal;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryManager;
import net.minecraftforge.server.permission.PermissionAPI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import github.chorman0773.gac14.cmd.SystemCommand;
import github.chorman0773.gac14.cmd.execute.ExecuteSubcommandInjector;
import github.chorman0773.gac14.permissions.PermissionHandlerAdapter;
import github.chorman0773.gac14.permissions.PermissionManager;
import github.chorman0773.gac14.server.DataEvent;
import github.chorman0773.gac14.server.PeriodicEvent;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import javax.annotation.Nonnull;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("gac14-core")
public class Gac14Core
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    
    private static final JsonParser parser = new JsonParser();

    public Gac14Core() {
    	assert instance==null:"Initialization of Gac14Core shall occur exactly once";
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::setup);
        instance = this;
        
    }
    
    public void setup(FMLCommonSetupEvent event) {
    	manager = new PermissionManager();
    	PermissionAPI.setPermissionHandler(new PermissionHandlerAdapter());
    }
    
    private MinecraftServer server;
    private PermissionManager manager;
    private Path root;
    private Path config;
    private Path data;
    private Path players;
    
    /**
     * Gets the active MinecraftServer.
     * If this method is called before the completion of onServerStarting or after the completetion of onServerStopping, the behavior is undefined.
     */
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
        
        config = root.resolve("config");
        data = root.resolve("data");
        MinecraftForge.EVENT_BUS.post(new DataEvent.Load());
        serverRunning = true;
        periodicEventThread = new Thread(this::firePeriodicEvent);
        periodicEventThread.setDaemon(true);
        periodicEventThread.start();
        
        SystemCommand.register(server.getCommandManager().getDispatcher());
        ExecuteSubcommandInjector.register(server.getCommandManager().getDispatcher());
    }
    
    private boolean serverRunning = false;
    private Thread periodicEventThread;
    
    private void firePeriodicEvent() {
    	try {
	    	while(serverRunning) {
	    		Thread.sleep(600000);
	    		MinecraftForge.EVENT_BUS.post(new PeriodicEvent());
	    	}
    	}catch(InterruptedException e) {}
    }
    
    
    
    @SubscribeEvent
    public void onServerStopping(FMLServerStoppingEvent event) throws InterruptedException {
    	serverRunning = false;
    	periodicEventThread.interrupt();
    	periodicEventThread.join();
    	MinecraftForge.EVENT_BUS.post(new DataEvent.Save());
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SuppressWarnings("unchecked")
		public static void createRegistries(RegistryEvent.NewRegistry registry) {
        	new RegistryBuilder<Gac14Module<?>>()
        	.setName(new ResourceLocation("gac14:modules"))
        	.setType((Class<Gac14Module<?>>)(Class<?>)Gac14Module.class)
        	.disableSync()
        	.disableOverrides()
        	.disableSaving()
        	.create();
        }
    }

	private void checkModuleRegistry(IForgeRegistryInternal<Gac14Module<?>> registry,RegistryManager owner) {
		
	}
    
    private final CoreModule module = new CoreModule();
    
    public CoreModule getModule() {
    	return module;
    }
    
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
    public static class RegisterStuff{
    	 public static void registerModules(RegistryEvent.Register<Gac14Module<?>> mod) {
    		 mod.getRegistry().register(Gac14Core.instance.module);
    	 }
    }
    
    private static Gac14Core instance;

    /**
     * Obtains the instance of the Gac14Core.
     */
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
    
    public JsonObject getConfig(ResourceLocation loc) throws IOException {
    	return getConfig(loc.toString());
    }
    
    public JsonObject getConfig(String loc) throws IOException {
    	String name = loc.replace(':', '/')+".json";
    	Path p = config.resolve(loc);
    	try(InputStream strm = Files.newInputStream(p)){
    		JsonReader reader = new JsonReader(new InputStreamReader(strm,Charsets.UTF_8));
    		return parser.parse(reader).getAsJsonObject();
    	}
    }
    
    public Path getStoragePath(ResourceLocation loc) {
    	String name = loc.getNamespace()+"/"+loc.getPath();
    	return this.data.resolve(name);
    }
    
    
    public Path getStoragePath(String loc) {
    	String name = loc.replace(':', '/');
    	return this.data.resolve(name);
    }
    
    public Path getPlayerProfileFile(UUID id) {
    	return getStoragePath("gac14:core/players").resolve(id+".dat");
    }
}
