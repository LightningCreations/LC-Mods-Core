package github.chorman0773.gac14.permissions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
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

public class NamedGroup implements IGroup<ResourceLocation,String, PermissionManager, NamedGroup> {
	
	private Set<IGroup<ResourceLocation,String, PermissionManager,?>> impliedGroups;
	private Set<IPermission<PermissionManager,String,?>> impliedNodes;
	private ResourceLocation name;
	
	private Set<IGroup<ResourceLocation,String,PermissionManager,?>> cachedGroups;
	private Set<IPermission<PermissionManager,String,?>> cachedPermissions;
	
	private boolean groupsDirty = true;
	private boolean permissionsDirty = true;
	private boolean dirty = false;
	
	public NamedGroup(ResourceLocation loc) {
		this.name = loc;
		MinecraftForge.EVENT_BUS.addListener(this::load);
		MinecraftForge.EVENT_BUS.addListener(this::save);
		impliedGroups = new TreeSet<>(Comparator.comparing(IGroup::getName, Comparators.stringOrder));
		impliedNodes = new TreeSet<>(Comparator.comparing(IPermission::getName));
	}
	
	public void joinGroup(IGroup<ResourceLocation,String,PermissionManager,?> group) {
		impliedGroups.add(group);
		groupsDirty = true;
		dirty = true;
	}
	
	public void leaveGroup(IGroup<ResourceLocation,String,PermissionManager,?> group) {
		impliedGroups.remove(group);
		groupsDirty = true;
		dirty = true;
	}
	
	public void addPermission(IPermission<PermissionManager,String,?> node) {
		impliedNodes.add(node);
		dirty = true;
	}
	
	public void removePermission(IPermission<PermissionManager,String,?> node) {
		impliedNodes.remove(node);
		dirty = true;
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
			groupsDirty = true;
			
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void save(DataEvent.Save save) {
		if(dirty)
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
	public Set<? extends IGroup<ResourceLocation,String, PermissionManager, ?>> getGroups(PermissionManager manager) {
		if(groupsDirty) {
			Set<IGroup<ResourceLocation,String,PermissionManager,?>> groups = new TreeSet<>(PermissionManager.groupsByName);
			for(IGroup<ResourceLocation,String,PermissionManager,?> g:this.impliedGroups)
				groups.addAll(g.getGroups(manager));
			cachedGroups = groups;
		}
		return Collections.unmodifiableSet(cachedGroups);
	}

	/**
	 * Gets the set of all permissions.
	 * The result of this method is invalidated on any call to addPermission, removePermission, and revokePermission.
	 * <br/>
	 * Complexity Guarantee:<br/>
	 * O(n^2)
	 */
	@Override
	public Set<? extends IPermission<PermissionManager, String, ?>> getPermissions(PermissionManager manager) {
		if(permissionsDirty) {
			Set<IPermission<PermissionManager,String,?>> permissions = new TreeSet<>();
			for(IPermission<PermissionManager,String,?> p:this.impliedNodes)
				permissions.addAll(p.implies(manager));
			for(IGroup<ResourceLocation,String,PermissionManager,?> g:this.impliedGroups)
				permissions.addAll((Set<? extends IPermission<PermissionManager,String,?>>)g.getPermissions(manager));
			cachedPermissions = permissions;
			permissionsDirty = false;
		}
		return Collections.unmodifiableSet(this.cachedPermissions);
	}

	@Override
	public ResourceLocation getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
