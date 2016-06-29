package me.keeland.keelansk.protocollib;

import com.comphenix.protocol.PacketType.Play;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.keeland.keelansk.Main;

public class HardcoreHeartsListener extends PacketAdapter {
	private final ProtocolManager manager;

    public HardcoreHeartsListener(Main plugin) {
        super(plugin, ListenerPriority.NORMAL, Play.Server.LOGIN);

        manager = ProtocolLibrary.getProtocolManager();
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        if (!event.getPacketType().equals(Play.Server.LOGIN)) {
            return;
        }
        
        event.getPacket().getBooleans().write(0, true);
    }
    
    public void enable() {
        manager.addPacketListener(this);
    }    
    
    public void disable() {
        manager.removePacketListener(this);
    }
}