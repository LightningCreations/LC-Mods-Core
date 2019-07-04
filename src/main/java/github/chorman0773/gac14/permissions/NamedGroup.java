package github.chorman0773.gac14.permissions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import github.chorman0773.gac14.Gac14Core;
import github.chorman0773.gac14.server.DataEvent;
import github.chorman0773.gac14.util.Comparators;
import github.chorman0773.gac14.util.Helpers;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants.NBT;

public class NamedGroup implements IGroup<ResourceLocation, PermissionManager, NamedGroup> {
	
	private Set<IGroup<ResourceLocation, PermissionManager,?>> impliedGroups;
	private Set<IPermission<PermissionManager,String,?>> impliedNodes;
	private ResourceLocation name;
	
	public NamedGroup(ResourceLocation loc) {
		this.name = loc;
		MinecraftForge.EVENT_BUS.addListener(this::load);
		MinecraftForge.EVENT_BUS.addListener(this::save);
		impliedGroups = new TreeSet<>(Comparator.comparing(IGroup::getName, Comparators.stringOrder));
		impliedNodes = new TreeSet<>(Comparator.comparing(IPermission::getName));
	}
	
	public void addImpliedGroup(IGroup<ResourceLocation,PermissionManager,?> group) {
		impliedGroups.add(group);
	}
	
	public void removeImpliedGroup(IGroup<ResourceLocation,PermissionManager,?> group) {
		impliedGroups.remove(group);
	}
	
	public void addImpliedPermission(IPermission<PermissionManager,String,?> node) {
		impliedNodes.add(node);
	}
	
	public void removeImpliedPermission(IPermission<PermissionManager,String,?> node) {
		impliedNodes.remove(node);
	}
	
	private static final Gac14Core core = Gac14Core.getInstance();
	private static final PermissionManager manager = core.getPermissionManager();
	
	private void load(DataEvent.Load load) {
		try {
			String subpath = "groups/"+name.getNamespace()+"/"+name.getPath();
			Path p = core.getStoragePath(new ResourceLocation("gac14",subpath));
			if(!Files.exists(p))
				return;
			InputStream strm = Files.newInputStream(p);
			CompoundNBT comp = CompressedStreamTools.readCompressed(strm);
			ListNBT nodes = comp.getList("Nodes", NBT.TAG_STRING);
			nodes
				.stream()
				.map(Helpers.castTo(StringNBT.class))
				.map(StringNBT::getString)
				.map(manager::getPermission)
				.forEachOrdered(impliedNodes::add);
			ListNBT groups = comp.getList("Groups", NBT.TAG_STRING);
			groups
				.stream()
				.map(Helpers.castTo(StringNBT.class))
				.map(StringNBT::getString)
				.map(ResourceLocation::new)
				.map(manager::getGroupByName)
				.forEachOrdered(impliedGroups::add);
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void save(DataEvent.Save save) {
		try {
			String subpath = "groups/"+name.getNamespace()+"/"+name.getPath();
			Path p = core.getStoragePath(new ResourceLocation("gac14",subpath));
			OutputStream strm = Files.newOutputStream(p, StandardOpenOption.CREATE);
			CompoundNBT comp = new CompoundNBT();
			ListNBT nodes = new ListNBT();
			impliedNodes.stream()
				.map(IPermission::getName)
				.map(StringNBT::new)
				.forEachOrdered(nodes::add);
			comp.put("Nodes", nodes);
			ListNBT groups = new ListNBT();
			impliedGroups.stream()
				.map(IGroup::getName)
				.map(ResourceLocation::toString)
				.map(StringNBT::new)
				.forEachOrdered(groups::add);
			comp.put("Groups", groups);
			CompressedStreamTools.writeCompressed(comp, strm);
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Set<? extends IGroup<ResourceLocation, PermissionManager, ?>> impliedGroups(PermissionManager manager) {
		// TODO Auto-generated method stub
		return impliedGroups;
	}

	@Override
	public Set<? extends IPermission<PermissionManager, ?, ?>> implied(PermissionManager manager) {
		// TODO Auto-generated method stub
		return impliedNodes;
	}

	@Override
	public ResourceLocation getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
