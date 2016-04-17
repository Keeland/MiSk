package me.keeland.keelansk.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;

import java.util.HashMap;

public class EnchPacListener extends PacketAdapter implements Listener {
	
	private final ProtocolManager manager;
    private Plugin plug;
    private HashMap<Player, Boolean> EnchTTPPlayers = new HashMap<>();

    public EnchPacListener(Plugin p){
    	super(p, ListenerPriority.NORMAL, PacketType.Play.Server.CRAFT_PROGRESS_BAR);
		
		manager = ProtocolLibrary.getProtocolManager();
        this.plug = p;
        Bukkit.getPluginManager().registerEvents(this, this.plug);
    }

    public void setEnchPreviewAbility(Player p, Boolean isAblePreviewEnch){
    	EnchTTPPlayers.put(p, isAblePreviewEnch);
    }

    public Boolean isAblePreviewEnch(Player p){
        if (EnchTTPPlayers.containsKey(p)){
            return EnchTTPPlayers.get(p);
        }
        return false;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    @Override
    public void onPacketSending(PacketEvent e){
        if (!e.getPacketType().equals(Play.Server.CRAFT_PROGRESS_BAR)) {
        	return;
        }
        if (!isAblePreviewEnch(e.getPlayer())){
        	StructureModifier<Integer> integers = e.getPacket().getIntegers();
            int property = integers.read(1);
            
            if (property >= 4) {
            	integers.write(2, -1);
            }
        }
    }
    
    public void enable() {
    	manager.addPacketListener(this);
    }    
    
    public void disable() {
    	manager.removePacketListener(this);
    }
}