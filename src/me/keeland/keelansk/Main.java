package me.keeland.keelansk;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.palmergames.bukkit.towny.event.DeleteNationEvent;
import com.palmergames.bukkit.towny.event.DeleteTownEvent;
import com.palmergames.bukkit.towny.event.MobRemovalEvent;
import com.palmergames.bukkit.towny.event.NationAddTownEvent;
import com.palmergames.bukkit.towny.event.NationRemoveTownEvent;
import com.palmergames.bukkit.towny.event.NewNationEvent;
import com.palmergames.bukkit.towny.event.NewTownEvent;
import com.palmergames.bukkit.towny.event.TownAddResidentEvent;
import com.palmergames.bukkit.towny.event.TownClaimEvent;
import com.palmergames.bukkit.towny.event.TownRemoveResidentEvent;
import com.palmergames.bukkit.towny.event.TownUnclaimEvent;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.events.ChallengeCompleteEvent;
import com.wasteofplastic.askyblock.events.ChallengeLevelCompleteEvent;
import com.wasteofplastic.askyblock.events.IslandEnterEvent;
import com.wasteofplastic.askyblock.events.IslandExitEvent;
import com.wasteofplastic.askyblock.events.IslandJoinEvent;
import com.wasteofplastic.askyblock.events.IslandLeaveEvent;
import com.wasteofplastic.askyblock.events.IslandLevelEvent;
import com.wasteofplastic.askyblock.events.IslandNewEvent;
import com.wasteofplastic.askyblock.events.IslandResetEvent;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.keeland.keelansk.askyblock.EffSetIslandBiome;
import me.keeland.keelansk.askyblock.ExprGetSpawnLocation;
import me.keeland.keelansk.askyblock.ExprGetSpawnRange;
import me.keeland.keelansk.askyblock.ExprGetTopTen;
import me.keeland.keelansk.askyblock.ExprHomeLocationFromPlayer;
import me.keeland.keelansk.askyblock.ExprIslandLevel;
import me.keeland.keelansk.askyblock.ExprIslandLocationFromPlayer;
import me.keeland.keelansk.askyblock.ExprIslandWorld;
import me.keeland.keelansk.askyblock.ExprNetherWorld;
import me.keeland.keelansk.askyblock.ExprOwnerOfIsland;
import me.keeland.keelansk.askyblock.ExprTeamLeaderFromPlayer;
import me.keeland.keelansk.askyblock.ExprTeamMembersFromPlayer;
import me.keeland.keelansk.griefprevention.ExprClaimAtLocation;
import me.keeland.keelansk.griefprevention.ExprClaimAtPlayer;
import me.keeland.keelansk.griefprevention.ExprOwnerOfClaim;
import me.keeland.keelansk.griefprevention.ExprRemainingClaimBlocks;
import me.keeland.keelansk.misc.EffFakeMaxPlayers;
import me.keeland.keelansk.misc.EffOpenBrewingStand;
import me.keeland.keelansk.misc.EffReloadServer;
import me.keeland.keelansk.misc.EffRunGarbageCollector;
import me.keeland.keelansk.misc.EffSendResourcePack;
import me.keeland.keelansk.misc.EffStopServer;
import me.keeland.keelansk.misc.ExprAllowEnd;
import me.keeland.keelansk.misc.ExprAllowFlight;
import me.keeland.keelansk.misc.ExprAllowNether;
import me.keeland.keelansk.misc.ExprAmbientSpawnLimit;
import me.keeland.keelansk.misc.ExprAnimalSpawnLimit;
import me.keeland.keelansk.misc.ExprBukkitVersion;
import me.keeland.keelansk.misc.ExprCPUByte;
import me.keeland.keelansk.misc.ExprCPUCores;
import me.keeland.keelansk.misc.ExprCPUUsage;
import me.keeland.keelansk.misc.ExprDefaultGamemode;
import me.keeland.keelansk.misc.ExprFixedTPS;
import me.keeland.keelansk.misc.ExprFreeMemory;
import me.keeland.keelansk.misc.ExprIdleTimeout;
import me.keeland.keelansk.misc.ExprKeelanSKVersion;
import me.keeland.keelansk.misc.ExprMaxMemory;
import me.keeland.keelansk.misc.ExprMonsterSpawnLimit;
import me.keeland.keelansk.misc.ExprNumberOfAllLoadedChunks;
import me.keeland.keelansk.misc.ExprNumberOfAllLoadedEntities;
import me.keeland.keelansk.misc.ExprOSArchitecture;
import me.keeland.keelansk.misc.ExprOSUsername;
import me.keeland.keelansk.misc.ExprOnlineMode;
import me.keeland.keelansk.misc.ExprOperatingSystem;
import me.keeland.keelansk.misc.ExprPing;
import me.keeland.keelansk.misc.ExprServerIP;
import me.keeland.keelansk.misc.ExprServerPort;
import me.keeland.keelansk.misc.ExprSpawnRadius;
import me.keeland.keelansk.misc.ExprThreads;
import me.keeland.keelansk.misc.ExprTicksPerAnimalSpawns;
import me.keeland.keelansk.misc.ExprTicksPerMonsterSpawns;
import me.keeland.keelansk.misc.ExprTotalMemory;
import me.keeland.keelansk.misc.ExprUptime;
import me.keeland.keelansk.misc.ExprViewDistance;
import me.keeland.keelansk.misc.ExprWaterAnimalSpawnLimit;
import me.keeland.keelansk.towny.EffAddBonusBlocksToTown;
import me.keeland.keelansk.towny.EffAddResidentToTown;
import me.keeland.keelansk.towny.EffBackupTownyData;
import me.keeland.keelansk.towny.EffDeleteNation;
import me.keeland.keelansk.towny.EffDeleteResident;
import me.keeland.keelansk.towny.EffDeleteTown;
import me.keeland.keelansk.towny.EffEndWarEvent;
import me.keeland.keelansk.towny.EffFireStateOfTown;
import me.keeland.keelansk.towny.EffNewNation;
import me.keeland.keelansk.towny.EffNewTown;
import me.keeland.keelansk.towny.EffPublicStateOfTown;
import me.keeland.keelansk.towny.EffPvPStateOfTown;
import me.keeland.keelansk.towny.EffRemoveNation;
import me.keeland.keelansk.towny.EffRemoveResidentFromTown;
import me.keeland.keelansk.towny.EffRemoveTown;
import me.keeland.keelansk.towny.EffRemoveTownFromNation;
import me.keeland.keelansk.towny.EffRenameNation;
import me.keeland.keelansk.towny.EffRenameTown;
import me.keeland.keelansk.towny.EffSaveTownyData;
import me.keeland.keelansk.towny.EffSetBonusBlocksOfTown;
import me.keeland.keelansk.towny.EffSetSetupDelayWar;
import me.keeland.keelansk.towny.EffStartWarEvent;
import me.keeland.keelansk.towny.ExprFireStateOfTown;
import me.keeland.keelansk.towny.ExprMayorOfTown;
import me.keeland.keelansk.towny.ExprNationAtLocation;
import me.keeland.keelansk.towny.ExprNationBank;
import me.keeland.keelansk.towny.ExprNationOfPlayer;
import me.keeland.keelansk.towny.ExprNationOfTown;
import me.keeland.keelansk.towny.ExprNationPlayerCount;
import me.keeland.keelansk.towny.ExprNationPlayers;
import me.keeland.keelansk.towny.ExprNationTaxes;
import me.keeland.keelansk.towny.ExprNationTownCount;
import me.keeland.keelansk.towny.ExprPublicStateOfTown;
import me.keeland.keelansk.towny.ExprPvPStateOfTown;
import me.keeland.keelansk.towny.ExprResidentsWithoutTown;
import me.keeland.keelansk.towny.ExprTownAtLocation;
import me.keeland.keelansk.towny.ExprTownsInNation;
import me.keeland.keelansk.towny.ExprTownsInNationCount;
import me.keeland.keelansk.towny.ExprWarTime;
import me.keeland.keelansk.utils.Timer;
import me.keeland.keelansk.utils.User;

import me.ryanhamshire.GriefPrevention.Claim;

public class Main extends JavaPlugin implements Listener{
	
	private final Logger logger = Bukkit.getServer().getLogger();
	
	public static Main plugin;
	public static String version;
	public static Main instance;

    private static Timer timer;
	
	private int exprAmount = 0;
	private int effAmount = 0;
	private int evtAmount = 0;
	
	public void onEnable(){
		if (Bukkit.getPluginManager().getPlugin("Skript") != null) {
			Skript.registerAddon(this);  
			Bukkit.getPluginManager().registerEvents(this, this);
			/**
			 * Register independent classes
			 */
			Bukkit.getLogger().info("sKeeland > Enabled, registering independent expressions...");
			effAmount += 0;
			Skript.registerEffect(EffStopServer.class, new String[] { "stop server" });
			Skript.registerEffect(EffRunGarbageCollector.class, new String[] { "run garbage collector" });
			Skript.registerEffect(EffOpenBrewingStand.class, new String[] { "open brewing inventory (for|to) %player%" } );
			Skript.registerEffect(EffReloadServer.class, new String[] { "reload [server]" });
			Skript.registerEffect(EffFakeMaxPlayers.class, new String[] { "set fake max players to %number%" });
			Skript.registerEffect(EffSendResourcePack.class, new String[] { "send (resource|texture)[ ]pack to %player% from [url] %string%" });
			effAmount += 6;
			Skript.registerExpression(ExprCPUCores.class, Integer.class, ExpressionType.SIMPLE, "[available[ ]]processors");
			Skript.registerExpression(ExprCPUByte.class, String.class, ExpressionType.SIMPLE, "cpu[[ ]byte]");
			Skript.registerExpression(ExprOperatingSystem.class, String.class, ExpressionType.SIMPLE, "(os|operating system)");
			Skript.registerExpression(ExprOSArchitecture.class, String.class, ExpressionType.SIMPLE, "os arch[itecture]");
			Skript.registerExpression(ExprOSUsername.class, String.class, ExpressionType.SIMPLE, "[os[ ]]username");
			Skript.registerExpression(ExprThreads.class, Integer.class, ExpressionType.SIMPLE, "[running[ ]]threads");
			Skript.registerExpression(ExprAllowEnd.class, Boolean.class, ExpressionType.SIMPLE, "allow end");
			Skript.registerExpression(ExprAllowFlight.class, Boolean.class, ExpressionType.SIMPLE, "allow flight");
			Skript.registerExpression(ExprAllowNether.class, Boolean.class, ExpressionType.SIMPLE, "allow nether");
			Skript.registerExpression(ExprAmbientSpawnLimit.class, Integer.class, ExpressionType.SIMPLE, "ambient spawn[s] limit");
			Skript.registerExpression(ExprAnimalSpawnLimit.class, Integer.class, ExpressionType.SIMPLE, "animal spawn[s] limit");
			Skript.registerExpression(ExprBukkitVersion.class, String.class, ExpressionType.SIMPLE, "bukkit version");
			Skript.registerExpression(ExprCPUUsage.class, Double.class, ExpressionType.SIMPLE, "cpu usage");
			Skript.registerExpression(ExprDefaultGamemode.class, GameMode.class, ExpressionType.SIMPLE, "default gamemode"); //set-table
			Skript.registerExpression(ExprFreeMemory.class, Integer.class, ExpressionType.SIMPLE, "free memory [in] mb");
			Skript.registerExpression(ExprFixedTPS.class, Double.class, ExpressionType.SIMPLE, "[(skeeland[[']s]|fixed)] tps");
			Skript.registerExpression(ExprIdleTimeout.class, Integer.class, ExpressionType.SIMPLE, "idle timeout"); //set-table
			Skript.registerExpression(ExprKeelanSKVersion.class, String.class, ExpressionType.SIMPLE, "skeeland version");
			Skript.registerExpression(ExprMaxMemory.class, Integer.class, ExpressionType.SIMPLE, "max memory [in] mb");
			Skript.registerExpression(ExprMonsterSpawnLimit.class, Integer.class, ExpressionType.SIMPLE, "monster spawn[s] limit");
			Skript.registerExpression(ExprNumberOfAllLoadedChunks.class, Integer.class, ExpressionType.SIMPLE, "number of [all] load[ed] chunks");
			Skript.registerExpression(ExprNumberOfAllLoadedEntities.class, Integer.class, ExpressionType.SIMPLE, "number of [all] load[ed] entities");
			Skript.registerExpression(ExprOnlineMode.class, Boolean.class, ExpressionType.SIMPLE, "online mode");
			Skript.registerExpression(ExprPing.class, Integer.class, ExpressionType.PROPERTY, "kping of %player%");
			Skript.registerExpression(ExprServerIP.class, String.class, ExpressionType.SIMPLE, "[server[ ]]ip");
			Skript.registerExpression(ExprServerPort.class, Integer.class, ExpressionType.SIMPLE, "[server[ ]]port");
			Skript.registerExpression(ExprSpawnRadius.class, Integer.class, ExpressionType.SIMPLE, "spawn radi[us]"); //set-table
			Skript.registerExpression(ExprTicksPerAnimalSpawns.class, Integer.class, ExpressionType.SIMPLE, "tick[s] per animal spawn[s]");
			Skript.registerExpression(ExprTicksPerMonsterSpawns.class, Integer.class, ExpressionType.SIMPLE, "tick[s] per monster spawn[s]");
			Skript.registerExpression(ExprTotalMemory.class, Integer.class, ExpressionType.SIMPLE, "total memory [in] mb");
			Skript.registerExpression(ExprUptime.class, String.class, ExpressionType.SIMPLE, "[server] up[(-| )]time");
			Skript.registerExpression(ExprViewDistance.class, Integer.class, ExpressionType.SIMPLE, "view distance");
			Skript.registerExpression(ExprWaterAnimalSpawnLimit.class, Integer.class, ExpressionType.SIMPLE, "water animal spawn[s] limit");
			exprAmount += 32;
			
			if (Bukkit.getServer().getPluginManager().getPlugin("GriefPrevention") != null) {
				Bukkit.getLogger().info("sKeeland > GriefPrevention found, registering related expressions...");
				/**
				 * GriefPrevention Expressions
				 */
				Skript.registerExpression(ExprClaimAtPlayer.class, Claim.class, ExpressionType.PROPERTY, "claim at player %player%");
				Skript.registerExpression(ExprClaimAtLocation.class, Claim.class, ExpressionType.PROPERTY, "claim at %location%");
				Skript.registerExpression(ExprOwnerOfClaim.class, String.class, ExpressionType.PROPERTY, "owner of [claim] %claim%");
				Skript.registerExpression(ExprRemainingClaimBlocks.class, Integer.class, ExpressionType.PROPERTY, "remaining [claim] block[[']s] of [player] %player%");
				exprAmount += 4;
				
			} else {
				getLogger().info("sKeeland > Unable to find GriefPrevention!");
			}
			
		    if (Bukkit.getServer().getPluginManager().getPlugin("Towny") != null) {
		    	Bukkit.getLogger().info("sKeeland > Towny found, registering related expressions...");
		    	/**
				 * TOWNY Events, Expressions & Effects
				 */
		    	Skript.registerEvent("Delete Nation Event", SimpleEvent.class, DeleteNationEvent.class, "nation del[ete[d]]"); //event-string
		    	Skript.registerEvent("Delete Town Event", SimpleEvent.class, DeleteTownEvent.class, "town del[ete[d]]");
		    	Skript.registerEvent("Towny Mob Removal Event", SimpleEvent.class, MobRemovalEvent.class, "[towny] mob rem[oval] [event]");
		    	Skript.registerEvent("Nation Add Town Event", SimpleEvent.class, NationAddTownEvent.class, "[towny] nation add town [event]");
		    	Skript.registerEvent("Nation Remove Town Event", SimpleEvent.class, NationRemoveTownEvent.class, "[towny] nation remove town [event]");
		    	Skript.registerEvent("New Nation Event", SimpleEvent.class, NewNationEvent.class, "new nation [event]","nation new [event]","nation create","create nation [event]");
		    	Skript.registerEvent("New Town Event", SimpleEvent.class, NewTownEvent.class, "new town [event]","town new [event]","town create","create town [event]");
		    	Skript.registerEvent("Town Add Resident Event", SimpleEvent.class, TownAddResidentEvent.class, "town add (resident|player) [event]");
		    	Skript.registerEvent("Town Remove Resident Event", SimpleEvent.class, TownRemoveResidentEvent.class, "town remove (resident|player) [event]");
		    	Skript.registerEvent("Town Claim Event", SimpleEvent.class, TownClaimEvent.class, "town claim [event]");
		    	Skript.registerEvent("Town UnClaim Event", SimpleEvent.class, TownUnclaimEvent.class, "town unclaim [event]");
		    	// event-string return deleted nation name
		    	EventValues.registerEventValue(DeleteNationEvent.class, String.class, new Getter<String, DeleteNationEvent>() {
					
					public String get(DeleteNationEvent e) {
						
						return e.getNationName();
					}
				}, 0);
		    	// event-string return deleted town name
		    	EventValues.registerEventValue(DeleteTownEvent.class, String.class, new Getter<String, DeleteTownEvent>() {
					
					public String get(DeleteTownEvent e) {
						
						return e.getTownName();
					}
				}, 0);
		    	// nation add town event. event-nation?
		    	EventValues.registerEventValue(NationAddTownEvent.class, Nation.class, new Getter<Nation, NationAddTownEvent>() {
					
					public Nation get(NationAddTownEvent e) {
						
						return e.getNation();
					}
				}, 0);
		    	// nation add town event. event-town?
		    	EventValues.registerEventValue(NationAddTownEvent.class, Town.class, new Getter<Town, NationAddTownEvent>() {
					
					public Town get(NationAddTownEvent e) {
						
						return e.getTown();
					}
				}, 0);
		    	// nation remove town event. event-nation?
		    	EventValues.registerEventValue(NationRemoveTownEvent.class, Nation.class, new Getter<Nation, NationRemoveTownEvent>() {
					
					public Nation get(NationRemoveTownEvent e) {
						
						return e.getNation();
					}
				}, 0);
		    	// nation remove town event. event-town?
		    	EventValues.registerEventValue(NationRemoveTownEvent.class, Town.class, new Getter<Town, NationRemoveTownEvent>() {
					
					public Town get(NationRemoveTownEvent e) {
						
						return e.getTown();
					}
				}, 0);
		    	// new nation event event-string?
		    	EventValues.registerEventValue(NewNationEvent.class, Nation.class, new Getter<Nation, NewNationEvent>() {
					
					public Nation get(NewNationEvent e) {
						
						return e.getNation();
					}
				}, 0);
		    	// new town event event-string?
		    	EventValues.registerEventValue(NewTownEvent.class, Town.class, new Getter<Town, NewTownEvent>() {
					
					public Town get(NewTownEvent e) {
						
						return e.getTown();
					}
				}, 0);
		    	// town add resident event event-resident?
		    	EventValues.registerEventValue(TownAddResidentEvent.class, Resident.class, new Getter<Resident, TownAddResidentEvent>() {
					
					public Resident get(TownAddResidentEvent e) {
						
						return e.getResident();
					}
				}, 0);
		    	// town add resident event event-town?
		    	EventValues.registerEventValue(TownAddResidentEvent.class, Town.class, new Getter<Town, TownAddResidentEvent>() {
					
					public Town get(TownAddResidentEvent e) {
						
						return e.getTown();
					}
				}, 0);
		    	// town remove resident event event-resident?
		    	EventValues.registerEventValue(TownRemoveResidentEvent.class, Resident.class, new Getter<Resident, TownRemoveResidentEvent>() {
					
					public Resident get(TownRemoveResidentEvent e) {
						
						return e.getResident();
					}
				}, 0);
		    	// town remove resident event event-town?
		    	EventValues.registerEventValue(TownRemoveResidentEvent.class, Town.class, new Getter<Town, TownRemoveResidentEvent>() {
					
					public Town get(TownRemoveResidentEvent e) {
						
						return e.getTown();
					}
				}, 0);
		    	/***
		    	 * Claim Event Values
		    	 */
		    	//event-town? on claim
		    	EventValues.registerEventValue(TownClaimEvent.class, Town.class, new Getter<Town, TownClaimEvent>() {
					
					public Town get(TownClaimEvent e) {
						
						try {
							return e.getTownBlock().getTown();
						} catch (NotRegisteredException e1) {
							e1.printStackTrace();
							return null;
						}
					}
				}, 0);
		    	//event-town? on unclaim
		    	EventValues.registerEventValue(TownUnclaimEvent.class, Town.class, new Getter<Town, TownUnclaimEvent>() {
					
					public Town get(TownUnclaimEvent e) {
						
						return e.getTown();
						
					}
				}, 0);
		    	Skript.registerEffect(EffBackupTownyData.class, new String[] { "backup towny [data]" });
		    	Skript.registerEffect(EffEndWarEvent.class, new String[] { "end war[[ ]event]" });
		    	Skript.registerEffect(EffStartWarEvent.class, new String[] { "start war[[ ]event]" });
		    	Skript.registerEffect(EffAddResidentToTown.class, new String[] { "add %string% to town %town%" });
		    	Skript.registerEffect(EffDeleteNation.class, new String[] { "(delete|del) nation %nation%" });
		    	Skript.registerEffect(EffDeleteTown.class, new String[] { "(delete|del) town %town%" });
		    	Skript.registerEffect(EffDeleteResident.class, new String[] { "(delete|del) (resident|player) %string%" });
		    	Skript.registerEffect(EffNewNation.class, new String[] { "new nation [named] %string%" });
		    	Skript.registerEffect(EffNewTown.class, new String[] { "new town [named] %string%" });
		    	Skript.registerEffect(EffRenameTown.class, new String[] { "[re]name town %town% to %string%" });
		    	Skript.registerEffect(EffRenameNation.class, new String[] { "[re]name nation %nation% to %string%" });
		    	Skript.registerEffect(EffRemoveNation.class, new String[] { "(remove|rem) nation %nation%" });
		    	Skript.registerEffect(EffRemoveTown.class, new String[] { "(remove|rem) town %town%" });
		    	Skript.registerEffect(EffRemoveResidentFromTown.class, new String[] { "remove %string% from town %town%" });
		    	Skript.registerEffect(EffRemoveTownFromNation.class, new String[] { "remove %string% from nation %nation%" });
		    	Skript.registerEffect(EffPublicStateOfTown.class, new String[] { "set public state of %town% to %boolean%" });
		    	Skript.registerEffect(EffPvPStateOfTown.class, new String[] { "set pvp state of %town% to %boolean%" });
		    	Skript.registerEffect(EffFireStateOfTown.class, new String[] { "set fire state of %town% to %boolean%" });
		    	Skript.registerEffect(EffSetSetupDelayWar.class, new String[] { "set war [set[up]] delay to %integer%" });
		    	Skript.registerEffect(EffSaveTownyData.class, new String[] { "save towny [data]" });
		    	Skript.registerEffect(EffAddBonusBlocksToTown.class, new String[] { "add %integer% bonus blocks to [town] %string%" });
		    	Skript.registerEffect(EffSetBonusBlocksOfTown.class, new String[] { "set bonus blocks of %string% to %integer%" });
		    	effAmount += 22;
		    	Skript.registerExpression(ExprFireStateOfTown.class, Boolean.class, ExpressionType.PROPERTY, "fire state of [town] %town%");
		    	Skript.registerExpression(ExprMayorOfTown.class, Resident.class, ExpressionType.PROPERTY, "mayor of [town] %town%"); //set-table
		    	Skript.registerExpression(ExprNationAtLocation.class, Nation.class, ExpressionType.PROPERTY, "nation at %location%");
		    	Skript.registerExpression(ExprNationOfPlayer.class, Nation.class, ExpressionType.PROPERTY, "nation of %player%");
		    	Skript.registerExpression(ExprNationOfTown.class, Nation.class, ExpressionType.PROPERTY, "nation of %string%");
		    	Skript.registerExpression(ExprNationBank.class, Double.class, ExpressionType.PROPERTY, "nation balance of %string%");
		    	Skript.registerExpression(ExprNationPlayerCount.class, Integer.class, ExpressionType.PROPERTY, "nation player[ ]count of %string%");
		    	Skript.registerExpression(ExprNationTownCount.class, Integer.class, ExpressionType.PROPERTY, "nation town[ ]count of %string%");
		    	Skript.registerExpression(ExprNationPlayers.class, String.class, ExpressionType.PROPERTY, "player[[']s] of nation %string%, player[[']s] in nation %string%");
		    	Skript.registerExpression(ExprNationTaxes.class, Double.class, ExpressionType.PROPERTY, "nation taxes of %string%");
		    	Skript.registerExpression(ExprPublicStateOfTown.class, Boolean.class, ExpressionType.PROPERTY, "public state of [town] %town%");
		    	Skript.registerExpression(ExprPvPStateOfTown.class, Boolean.class, ExpressionType.PROPERTY, "pvp state of [town] %town%");
		    	Skript.registerExpression(ExprTownsInNation.class, String.class, ExpressionType.PROPERTY, "town[[']s] (in|of) [nation] %string%");
		    	Skript.registerExpression(ExprTownAtLocation.class, String.class, ExpressionType.PROPERTY, "town at location %location%");
		    	Skript.registerExpression(ExprTownsInNationCount.class, Integer.class, ExpressionType.PROPERTY, "[nation[ ]]town[ ]count of %string%");
		    	Skript.registerExpression(ExprResidentsWithoutTown.class, String.class, ExpressionType.SIMPLE, "(resident[[']s]|player[[']s]) without [a] town");
		    	Skript.registerExpression(ExprWarTime.class, Boolean.class, ExpressionType.SIMPLE, "war[ ]time");
		    	exprAmount += 17;
		    	
			} else {
				getLogger().info("sKeeland > Unable to find Towny!");
			}
		    if (Bukkit.getServer().getPluginManager().getPlugin("ASkyBlock") != null) {
		    	Bukkit.getLogger().info("sKeeland > ASkyBlock found, registering related expressions...");
		    	/**
		    	 * ASkyBlock Expressions
		    	 */
		    	Skript.registerEvent("ASkyBlock Challenge Complete", SimpleEvent.class, ChallengeCompleteEvent.class, "[askyblock] challenge complete"); //event-player + getRewardText + getExpReward
		    	Skript.registerEvent("ASkyBlock Challenge Level Complete", SimpleEvent.class, ChallengeLevelCompleteEvent.class, "[askyblock] challenge level complete"); //event-player + getOldLevel + getNewLevel
		    	Skript.registerEvent("ASkyBlock Island Enter Event", SimpleEvent.class, IslandEnterEvent.class, "[askyblock] island enter[(ed|ing)] [event]"); //event-player + getIslandOwner + getIslandLocation + getLocation
		    	Skript.registerEvent("ASkyBlock Island Exit Event", SimpleEvent.class, IslandExitEvent.class, "[askyblock] island exit[(ed|ing)] [event]"); //event-player + getIslandOwner + getIslandLocation + getLocation
		    	Skript.registerEvent("ASkyBlock Island Join Event", SimpleEvent.class, IslandJoinEvent.class, "[askyblock] island join [event]"); //event-player + getIslandLocation
		    	Skript.registerEvent("ASkyBlock Island Leave Event", SimpleEvent.class, IslandLeaveEvent.class, "[askyblock] island leave [event]"); //event-player + getIslandLocation
		    	Skript.registerEvent("ASkyBlock Island Level Event", SimpleEvent.class, IslandLevelEvent.class, "[askyblock] island level [event]"); //event-player + getIslandLocation
		    	Skript.registerEvent("ASkyBlock Island New Event", SimpleEvent.class, IslandNewEvent.class, "[askyblock] new island [event]","[askyblock] island create [event]");
		    	Skript.registerEvent("ASkyBlock Island Reset Event", SimpleEvent.class, IslandResetEvent.class, "[askyblock] reset island [event]","[askyblock] island reset [event]");
		    	evtAmount += 9;
		    	EventValues.registerEventValue(ChallengeCompleteEvent.class, Player.class, new Getter<Player, ChallengeCompleteEvent>() {
					
					public Player get(ChallengeCompleteEvent e) {
						
						return e.getPlayer();
					}
				}, 0);
		    	EventValues.registerEventValue(ChallengeCompleteEvent.class, String.class, new Getter<String, ChallengeCompleteEvent>() {
					
					public String get(ChallengeCompleteEvent e) {
						
						return e.getRewardText();
					}
				}, 0);
		    	EventValues.registerEventValue(ChallengeCompleteEvent.class, Integer.class, new Getter<Integer, ChallengeCompleteEvent>() {
					
					public Integer get(ChallengeCompleteEvent e) {
						
						return e.getExpReward();
					}
				}, 0);
		    	EventValues.registerEventValue(ChallengeLevelCompleteEvent.class, Player.class, new Getter<Player, ChallengeLevelCompleteEvent>() {
					
					public Player get(ChallengeLevelCompleteEvent e) {
						
						return e.getPlayer();
					}
				}, 0);
		    	EventValues.registerEventValue(ChallengeLevelCompleteEvent.class, Integer.class, new Getter<Integer, ChallengeLevelCompleteEvent>() {
					
					public Integer get(ChallengeLevelCompleteEvent e) {
						
						return e.getNewLevel();
					}
				}, 0);
		    	EventValues.registerEventValue(ChallengeLevelCompleteEvent.class, Integer.class, new Getter<Integer, ChallengeLevelCompleteEvent>() {
					
					public Integer get(ChallengeLevelCompleteEvent e) {
						
						return e.getOldLevel();
					}
				}, 0);
		    	EventValues.registerEventValue(IslandEnterEvent.class, Player.class, new Getter<Player, IslandEnterEvent>() {
					
					public Player get(IslandEnterEvent e) {
						
						Player entereventplayer = Bukkit.getPlayer(e.getPlayer());
						return entereventplayer;
					}
				}, 0);
		    	EventValues.registerEventValue(IslandEnterEvent.class, Location.class, new Getter <Location, IslandEnterEvent>() {
		    		
		    		public Location get(IslandEnterEvent e) {
		    			return e.getIslandLocation();
		    		}
		    	}, 0);
		    	EventValues.registerEventValue(IslandEnterEvent.class, Location.class, new Getter <Location, IslandEnterEvent>() {
		    		
		    		public Location get(IslandEnterEvent e) {
		    			return e.getLocation();
		    		}
		    	}, 0);
		    	EventValues.registerEventValue(IslandEnterEvent.class, Player.class, new Getter<Player, IslandEnterEvent>() {
					
					public Player get(IslandEnterEvent e) {
						
						Player enterownerplayer = Bukkit.getPlayer(e.getIslandOwner());
						return enterownerplayer;
					}
				}, 0);
		    	EventValues.registerEventValue(IslandExitEvent.class, Player.class, new Getter<Player, IslandExitEvent>() {
					
					public Player get(IslandExitEvent e) {
						
						Player exiteventplayer = Bukkit.getPlayer(e.getPlayer());
						return exiteventplayer;
					}
				}, 0);
		    	EventValues.registerEventValue(IslandExitEvent.class, Location.class, new Getter <Location, IslandExitEvent>() {
		    		
		    		public Location get(IslandExitEvent e) {
		    			return e.getIslandLocation();
		    		}
		    	}, 0);
		    	EventValues.registerEventValue(IslandExitEvent.class, Location.class, new Getter <Location, IslandExitEvent>() {
		    		
		    		public Location get(IslandExitEvent e) {
		    			return e.getLocation();
		    		}
		    	}, 0);
		    	EventValues.registerEventValue(IslandExitEvent.class, Player.class, new Getter<Player, IslandExitEvent>() {
					
					public Player get(IslandExitEvent e) {
						
						Player exitownerplayer = Bukkit.getPlayer(e.getIslandOwner());
						return exitownerplayer;
					}
				}, 0);
		    	EventValues.registerEventValue(IslandJoinEvent.class, Player.class, new Getter<Player, IslandJoinEvent>() {
					
					public Player get(IslandJoinEvent e) {
		
						return Bukkit.getPlayer(e.getPlayer());
					}
				}, 0);
		    	EventValues.registerEventValue(IslandJoinEvent.class, Location.class, new Getter <Location, IslandJoinEvent>() {
		    		
		    		public Location get(IslandJoinEvent e) {
		    			return e.getIslandLocation();
		    		}
		    	}, 0);
		    	EventValues.registerEventValue(IslandLeaveEvent.class, Player.class, new Getter<Player, IslandLeaveEvent>() {
					
					public Player get(IslandLeaveEvent e) {
		
						return Bukkit.getPlayer(e.getPlayer());
					}
				}, 0);
		    	EventValues.registerEventValue(IslandLeaveEvent.class, Location.class, new Getter <Location, IslandLeaveEvent>() {
		    		
		    		public Location get(IslandLeaveEvent e) {
		    			return e.getIslandLocation();
		    		}
		    	}, 0);
		    	EventValues.registerEventValue(IslandLevelEvent.class, Location.class, new Getter <Location, IslandLevelEvent>() {
		    		
		    		public Location get(IslandLevelEvent e) {
		    			return e.getIslandLocation();
		    		}
		    	}, 0);
		    	EventValues.registerEventValue(IslandLevelEvent.class, Player.class, new Getter<Player, IslandLevelEvent>() {
					
					public Player get(IslandLevelEvent e) {
		
						return Bukkit.getPlayer(e.getPlayer());
					}
				}, 0);
		    	EventValues.registerEventValue(IslandNewEvent.class, Player.class, new Getter<Player, IslandNewEvent>() {
					
					public Player get(IslandNewEvent e) {
		
						return e.getPlayer();
					}
				}, 0);
		    	EventValues.registerEventValue(IslandNewEvent.class, Location.class, new Getter <Location, IslandNewEvent>() {
		    		
		    		public Location get(IslandNewEvent e) {
		    			return e.getIslandLocation();
		    		}
		    	}, 0);
		    	EventValues.registerEventValue(IslandResetEvent.class, Player.class, new Getter<Player, IslandResetEvent>() {
					
					public Player get(IslandResetEvent e) {
		
						return e.getPlayer();
					}
				}, 0);
		    	EventValues.registerEventValue(IslandResetEvent.class, Location.class, new Getter <Location, IslandResetEvent>() {
		    		
		    		public Location get(IslandResetEvent e) {
		    			return e.getLocation();
		    		}
		    	}, 0);
		    	/**
		    	 * ASkyblock Effect + Rest of le Expressions
		    	 */
		    	Skript.registerEffect(EffSetIslandBiome.class, new String[]{ "set island[[']s] biome at %location% to %biome%" });
		    	effAmount += 1;
		    	Skript.registerExpression(ExprGetSpawnLocation.class, Location.class, ExpressionType.SIMPLE, "[(get|askyblock)] spawn location");
		    	Skript.registerExpression(ExprGetSpawnRange.class, Integer.class, ExpressionType.SIMPLE, "[(get|askyblock)] spawn range");
		    	Skript.registerExpression(ExprIslandLevel.class, Integer.class, ExpressionType.PROPERTY, "[askyblock] island level of %player%");
		    	Skript.registerExpression(ExprGetTopTen.class, String.class, ExpressionType.SIMPLE, "[(get|askyblock)] top ten");
		    	Skript.registerExpression(ExprIslandWorld.class, World.class, ExpressionType.SIMPLE, "[askyblock] island world");
		    	Skript.registerExpression(ExprNetherWorld.class, World.class, ExpressionType.SIMPLE, "[askyblock] nether world");
		    	Skript.registerExpression(ExprHomeLocationFromPlayer.class, Location.class, ExpressionType.PROPERTY, "[askyblock] %player%[[']s] home location");
		    	Skript.registerExpression(ExprIslandLocationFromPlayer.class, Location.class, ExpressionType.PROPERTY, "[askyblock] %player%[[']s] island location");
		    	Skript.registerExpression(ExprOwnerOfIsland.class, Player.class, ExpressionType.PROPERTY, "[askyblock] owner of island at %location%");
		    	Skript.registerExpression(ExprTeamLeaderFromPlayer.class, Player.class, ExpressionType.PROPERTY, "[askyblock] team leader[[']s] from [player] %player%");
		    	Skript.registerExpression(ExprTeamMembersFromPlayer.class, String.class, ExpressionType.PROPERTY, "[askyblock] team member[[']s] from [player] %player%");
		    	exprAmount += 11;
		    	
		    } else {
		    	getLogger().info("sKeeland > Unable to find ASkyBlock!");
		    }
				
		    getLogger().info("Loaded a total of " + evtAmount + (evtAmount == 1 ? " event, " : " events, ")  + effAmount + (effAmount == 1 ? " effect and " : " effects and ") + exprAmount + (exprAmount == 1 ? " expression" : " expressions!"));
		    
		} else {
			getLogger().info("Unable to find Skript, disabling sKeeland...");
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}
	
	public void onDisable() {
		
	    PluginDescriptionFile pdfFile = getDescription();
	    this.logger.info(pdfFile.getName() + " is now disabled.");
	    plugin = null;
	    
	}
	
    @SuppressWarnings("unused") // need to fix xd or acc use
	private void utils() {
        timer = new Timer();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, timer, 1000L, 50L);
        for (Player p : Bukkit.getOnlinePlayers()) User.get(p);
    }
	
    public static Main getInstance() {
        return plugin;
    }
    
    public static ASkyBlockAPI hookASB() {
    	Plugin plugin = Bukkit.getPluginManager().getPlugin("ASkyBlock");
        if (plugin instanceof ASkyBlockAPI && plugin.isEnabled()) { // I should probably stop doing this.
        	ASkyBlockAPI asb = (ASkyBlockAPI) plugin;
        	return asb;
        } else {
        	return null;
        }
    }
    
    public static Timer getTimer() {
        return timer;
    }
    
    public static String getVersion() {
        return version;
    }
    
}
