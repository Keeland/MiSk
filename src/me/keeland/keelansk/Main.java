package me.keeland.keelansk;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.mrlolethan.nexgenkoths.events.PlayerCaptureKothEvent;
import com.mrlolethan.nexgenkoths.events.PlayerEnterKothEvent;
import com.mrlolethan.nexgenkoths.events.PlayerExitKothEvent;
import com.mrlolethan.nexgenkoths.events.PlayerStartCaptureKothEvent;
import com.mrlolethan.nexgenkoths.events.PlayerStopCaptureKothEvent;
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
import com.wasteofplastic.askyblock.events.ChallengeCompleteEvent;
import com.wasteofplastic.askyblock.events.ChallengeLevelCompleteEvent;
import com.wasteofplastic.askyblock.events.IslandEnterEvent;
import com.wasteofplastic.askyblock.events.IslandExitEvent;
import com.wasteofplastic.askyblock.events.IslandJoinEvent;
import com.wasteofplastic.askyblock.events.IslandLeaveEvent;
import com.wasteofplastic.askyblock.events.IslandLevelEvent;
import com.wasteofplastic.askyblock.events.IslandNewEvent;
import com.wasteofplastic.askyblock.events.IslandResetEvent;
import com.wimbli.WorldBorder.Events.WorldBorderFillFinishedEvent;
import com.wimbli.WorldBorder.Events.WorldBorderTrimFinishedEvent;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import ch.njol.skript.util.Timespan;
import me.keeland.keelansk.askyblock.EffCalculateIslandLevelOfPlayer;
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
import me.keeland.keelansk.griefprevention.ExprAccruedClaimBlocks;
import me.keeland.keelansk.griefprevention.ExprBonusClaimBlocks;
import me.keeland.keelansk.griefprevention.ExprClaimAtLocation;
import me.keeland.keelansk.griefprevention.ExprClaimAtPlayer;
import me.keeland.keelansk.griefprevention.ExprOwnerOfClaim;
import me.keeland.keelansk.griefprevention.ExprRemainingClaimBlocks;
import me.keeland.keelansk.koth.ExprCappingPlayerOfKoth;
import me.keeland.keelansk.koth.ExprRemainingTime;
import me.keeland.keelansk.misc.effects.EffFakeMaxPlayers;
import me.keeland.keelansk.misc.effects.EffMakePlayerSpectateEntity;
import me.keeland.keelansk.misc.effects.EffOpenBrewingStand;
import me.keeland.keelansk.misc.effects.EffReloadServer;
import me.keeland.keelansk.misc.effects.EffRunGarbageCollector;
import me.keeland.keelansk.misc.effects.EffSendResourcePack;
import me.keeland.keelansk.misc.effects.EffStopServer;
import me.keeland.keelansk.misc.events.EvtPlayerLeave;
import me.keeland.keelansk.misc.expressions.ExprAllowEnd;
import me.keeland.keelansk.misc.expressions.ExprAllowFlight;
import me.keeland.keelansk.misc.expressions.ExprAllowNether;
import me.keeland.keelansk.misc.expressions.ExprAmbientSpawnLimit;
import me.keeland.keelansk.misc.expressions.ExprAnimalSpawnLimit;
import me.keeland.keelansk.misc.expressions.ExprBukkitVersion;
import me.keeland.keelansk.misc.expressions.ExprCPUByte;
import me.keeland.keelansk.misc.expressions.ExprCPUCores;
import me.keeland.keelansk.misc.expressions.ExprCPUUsage;
import me.keeland.keelansk.misc.expressions.ExprDefaultGamemode;
import me.keeland.keelansk.misc.expressions.ExprDurationOfPlayersPotionType;
import me.keeland.keelansk.misc.expressions.ExprFreeMemory;
import me.keeland.keelansk.misc.expressions.ExprIdleTimeout;
import me.keeland.keelansk.misc.expressions.ExprKeelanSKVersion;
import me.keeland.keelansk.misc.expressions.ExprKickReason;
import me.keeland.keelansk.misc.expressions.ExprMaxMemory;
import me.keeland.keelansk.misc.expressions.ExprMonsterSpawnLimit;
import me.keeland.keelansk.misc.expressions.ExprNumberOfAllLoadedChunks;
import me.keeland.keelansk.misc.expressions.ExprNumberOfAllLoadedEntities;
import me.keeland.keelansk.misc.expressions.ExprOSArchitecture;
import me.keeland.keelansk.misc.expressions.ExprOSUsername;
import me.keeland.keelansk.misc.expressions.ExprOnlineMode;
import me.keeland.keelansk.misc.expressions.ExprOperatingSystem;
import me.keeland.keelansk.misc.expressions.ExprPing;
import me.keeland.keelansk.misc.expressions.ExprPlayersSpectateTarget;
import me.keeland.keelansk.misc.expressions.ExprServerIP;
import me.keeland.keelansk.misc.expressions.ExprServerPort;
import me.keeland.keelansk.misc.expressions.ExprSpawnRadius;
import me.keeland.keelansk.misc.expressions.ExprTPS;
import me.keeland.keelansk.misc.expressions.ExprTeamsPrefix;
import me.keeland.keelansk.misc.expressions.ExprTeamsPrefixFromPlayer;
import me.keeland.keelansk.misc.expressions.ExprThreads;
import me.keeland.keelansk.misc.expressions.ExprTicksPerAnimalSpawns;
import me.keeland.keelansk.misc.expressions.ExprTicksPerMonsterSpawns;
import me.keeland.keelansk.misc.expressions.ExprTierOfPlayersPotionType;
import me.keeland.keelansk.misc.expressions.ExprTotalMemory;
import me.keeland.keelansk.misc.expressions.ExprUptime;
import me.keeland.keelansk.misc.expressions.ExprViewDistance;
import me.keeland.keelansk.misc.expressions.ExprWaterAnimalSpawnLimit;
import me.keeland.keelansk.misc.expressions.TravelAgent.ExprPortalTravelCanCreate;
import me.keeland.keelansk.misc.expressions.TravelAgent.ExprPortalTravelCreate;
import me.keeland.keelansk.misc.expressions.TravelAgent.ExprPortalTravelCreationRadius;
import me.keeland.keelansk.misc.expressions.TravelAgent.ExprPortalTravelFind;
import me.keeland.keelansk.misc.expressions.TravelAgent.ExprPortalTravelFindOrCreate;
import me.keeland.keelansk.misc.expressions.TravelAgent.ExprPortalTravelSearchRadius;
import me.keeland.keelansk.protocollib.ExprEnchPreviewAbilityOfPlayer;
import me.keeland.keelansk.towny.EffAddResidentToTown;
import me.keeland.keelansk.towny.EffBackupTownyData;
import me.keeland.keelansk.towny.EffDeleteNation;
import me.keeland.keelansk.towny.EffDeleteResident;
import me.keeland.keelansk.towny.EffDeleteTown;
import me.keeland.keelansk.towny.EffEliminateNation;
import me.keeland.keelansk.towny.EffEliminateTown;
import me.keeland.keelansk.towny.EffEndWarEvent;
import me.keeland.keelansk.towny.EffNewNation;
import me.keeland.keelansk.towny.EffNewTown;
import me.keeland.keelansk.towny.EffRemoveNation;
import me.keeland.keelansk.towny.EffRemoveResidentFromTown;
import me.keeland.keelansk.towny.EffRemoveTown;
import me.keeland.keelansk.towny.EffRemoveTownFromNation;
import me.keeland.keelansk.towny.EffRenameNation;
import me.keeland.keelansk.towny.EffRenameTown;
import me.keeland.keelansk.towny.EffSaveTownyData;
import me.keeland.keelansk.towny.EffSetSetupDelayWar;
import me.keeland.keelansk.towny.EffStartWarEvent;
import me.keeland.keelansk.towny.ExprAlliesOfNation;
import me.keeland.keelansk.towny.ExprAssistantsOfNation;
import me.keeland.keelansk.towny.ExprEnemiesOfNation;
import me.keeland.keelansk.towny.ExprFireStateOfTown;
import me.keeland.keelansk.towny.ExprFriendsOfPlayer;
import me.keeland.keelansk.towny.ExprKingOfNation;
import me.keeland.keelansk.towny.ExprMayorOfTown;
import me.keeland.keelansk.towny.ExprNationAtLocation;
import me.keeland.keelansk.towny.ExprNationBank;
import me.keeland.keelansk.towny.ExprNationCapital;
import me.keeland.keelansk.towny.ExprNationOfPlayer;
import me.keeland.keelansk.towny.ExprNationOfTown;
import me.keeland.keelansk.towny.ExprNationPlayerCount;
import me.keeland.keelansk.towny.ExprNationPlayers;
import me.keeland.keelansk.towny.ExprNationTaxes;
import me.keeland.keelansk.towny.ExprNationTownCount;
import me.keeland.keelansk.towny.ExprPlayerIsNeutral;
import me.keeland.keelansk.towny.ExprPlayersInNation;
import me.keeland.keelansk.towny.ExprPublicStateOfTown;
import me.keeland.keelansk.towny.ExprPvPStateOfTown;
import me.keeland.keelansk.towny.ExprResidentsWithoutTown;
import me.keeland.keelansk.towny.ExprTownAtLocation;
import me.keeland.keelansk.towny.ExprTownBoard;
import me.keeland.keelansk.towny.ExprTownBonusBlocks;
import me.keeland.keelansk.towny.ExprTownsInNation;
import me.keeland.keelansk.towny.ExprTownsInNationCount;
import me.keeland.keelansk.towny.ExprTownsWithoutNation;
import me.keeland.keelansk.towny.ExprWarTime;
import me.keeland.keelansk.towny.inumbaska.ExprRegisteredDateOfPlayer;
import me.keeland.keelansk.towny.inumbaska.ExprTownBank;
import me.keeland.keelansk.towny.inumbaska.ExprTownOfPlayer;
import me.keeland.keelansk.uskyblock.ExprIslandLevelOfPlayer;
import me.keeland.keelansk.uskyblock.ExprIslandMembersAtLocationOfIsland;
import me.keeland.keelansk.uskyblock.ExprIslandMembersOfPlayersIsland;
import me.keeland.keelansk.uskyblock.ExprIslandRankAtLocation;
import me.keeland.keelansk.uskyblock.ExprIslandRankOfPlayer;
import me.keeland.keelansk.utils.EnchPacListener;
import me.keeland.keelansk.utils.ReflectionUtils;
import me.keeland.keelansk.utils.Timer;
import me.keeland.keelansk.worldborderpl.ExprXCenterOfrBorder;
import me.keeland.keelansk.worldborderpl.ExprXRadiusOfrBorder;
import me.keeland.keelansk.worldborderpl.ExprZCenterOfrBorder;
import me.keeland.keelansk.worldborderpl.ExprZRadiusOfrBorder;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.events.ClaimDeletedEvent;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonEndEvent;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonStartEvent;

public class Main extends JavaPlugin implements Listener{
	
	private final Logger logger = Bukkit.getServer().getLogger();
	
	public static Main plugin;
	public static String version = "0.5.10";
	public static Main instance;
	public static EnchPacListener encpaclist;

	private static Timer timer;
	private int exprAmount = 0;
	private int effAmount = 0;
	private int evtAmount = 0;
	
	// Sort registry into separate files later m8
	public void onEnable(){
		
		if (Bukkit.getPluginManager().getPlugin("Skript") != null) {
			Skript.registerAddon(this);  
			Bukkit.getPluginManager().registerEvents(this, this);
			String v = ReflectionUtils.getVersion();
			utils();
		//	saveDefaultConfig();
			/**
			 * Register independent classes
			 */
			Bukkit.getLogger().info("sKeeland > Enabled, registering independent expressions...");
			Skript.registerEvent("player leave", SimpleEvent.class, EvtPlayerLeave.class, "player leave [server]");
			EventValues.registerEventValue(EvtPlayerLeave.class, Player.class, new Getter<Player, EvtPlayerLeave>() {
				
				public Player get(EvtPlayerLeave e) {
					
					return e.getPlayer();
				}
			}, 0);
			EventValues.registerEventValue(EvtPlayerLeave.class, String.class, new Getter<String, EvtPlayerLeave>() {
				
				public String get(EvtPlayerLeave e) {
					
					return e.getQuitMessage();
				}
			}, 0);
			Skript.registerEvent("prepare craft", SimpleEvent.class, PrepareItemCraftEvent.class, "prepare [item] craft[ing]");
			EventValues.registerEventValue(PrepareItemCraftEvent.class, Inventory.class, new Getter<Inventory, PrepareItemCraftEvent>() {
				
				public Inventory get(PrepareItemCraftEvent e) {
					
					return e.getInventory();
				}
			}, 0);
//			EventValues.registerEventValue(PrepareItemCraftEvent.class, Player.class, new Getter<List<Player>, PrepareItemCraftEvent>() {
//				
//				public List<Player> get(PrepareItemCraftEvent e) {
//					List<Player> players = new ArrayList<Player>();
//					for (HumanEntity h : e.getViewers()) {
//						players.add(Bukkit.getPlayer(h.getName().toString()));
//					}
//					return players;
//				}
//			}, 0);
			Skript.registerEvent("entity portal", SimpleEvent.class, EntityPortalEvent.class, "entity portal");
			EventValues.registerEventValue(EntityPortalEvent.class, Entity.class, new Getter<Entity, EntityPortalEvent>() {
				
				public Entity get(EntityPortalEvent e) {
					
					return e.getEntity();
				}
			}, 0);
			EventValues.registerEventValue(EntityPortalEvent.class, Location.class, new Getter<Location, EntityPortalEvent>() {
				
				public Location get(EntityPortalEvent e) {
					
					return e.getFrom();
				}
			}, 0);
			EventValues.registerEventValue(EntityPortalEvent.class, Location.class, new Getter<Location, EntityPortalEvent>() {
				
				public Location get(EntityPortalEvent e) {
					
					return e.getTo();
				}
			}, 0);
			evtAmount += 2;
			effAmount += 0;
			Skript.registerEffect(EffStopServer.class, new String[] { "stop server" });
			Skript.registerEffect(EffRunGarbageCollector.class, new String[] { "run garbage collector" });
			Skript.registerEffect(EffMakePlayerSpectateEntity.class, new String[] { "make %player% spectate %entity%" });
			Skript.registerEffect(EffOpenBrewingStand.class, new String[] { "open brewing inventory (for|to) %player%" } );
			Skript.registerEffect(EffReloadServer.class, new String[] { "reload [server]" });
			Skript.registerEffect(EffFakeMaxPlayers.class, new String[] { "set fake max players to %number%" });
			Skript.registerEffect(EffSendResourcePack.class, new String[] { "send (resource|texture)[ ]pack to %player% from [url] %string%" });
			effAmount += 7;
			Skript.registerExpression(ExprCPUCores.class, Integer.class, ExpressionType.SIMPLE, "[available[ ]]processors");
			Skript.registerExpression(ExprCPUByte.class, String.class, ExpressionType.SIMPLE, "cpu[[ ]byte]");
			Skript.registerExpression(ExprOperatingSystem.class, String.class, ExpressionType.SIMPLE, "(os|operating system)");
			Skript.registerExpression(ExprOSArchitecture.class, String.class, ExpressionType.SIMPLE, "os arch[itecture]");
			Skript.registerExpression(ExprOSUsername.class, String.class, ExpressionType.SIMPLE, "[os[ ]]username");
			Skript.registerExpression(ExprThreads.class, Integer.class, ExpressionType.SIMPLE, "[running[ ]]threads");
			Skript.registerExpression(ExprAllowEnd.class, Boolean.class, ExpressionType.SIMPLE, "allow end");
			Skript.registerExpression(ExprAllowFlight.class, Boolean.class, ExpressionType.SIMPLE, "allow flight"); //settable
			Skript.registerExpression(ExprAllowNether.class, Boolean.class, ExpressionType.SIMPLE, "allow nether");
			Skript.registerExpression(ExprAmbientSpawnLimit.class, Integer.class, ExpressionType.SIMPLE, "ambient spawn[s] limit");
			Skript.registerExpression(ExprAnimalSpawnLimit.class, Integer.class, ExpressionType.SIMPLE, "animal spawn[s] limit");
			Skript.registerExpression(ExprBukkitVersion.class, String.class, ExpressionType.SIMPLE, "bukkit version");
			Skript.registerExpression(ExprCPUUsage.class, Double.class, ExpressionType.SIMPLE, "cpu usage");
			Skript.registerExpression(ExprDurationOfPlayersPotionType.class, Timespan.class, ExpressionType.PROPERTY, "duration of %player%[[']s] %potioneffecttype%");
			Skript.registerExpression(ExprTierOfPlayersPotionType.class, Integer.class, ExpressionType.PROPERTY, "tier of %player%[[']s] %potioneffecttype%");
			Skript.registerExpression(ExprDefaultGamemode.class, GameMode.class, ExpressionType.SIMPLE, "default gamemode"); //settable
			Skript.registerExpression(ExprFreeMemory.class, String.class, ExpressionType.SIMPLE, "free memory [in] mb");
			Skript.registerExpression(ExprIdleTimeout.class, Integer.class, ExpressionType.SIMPLE, "idle timeout"); //settable
			Skript.registerExpression(ExprKeelanSKVersion.class, String.class, ExpressionType.SIMPLE, "skeeland version");
			Skript.registerExpression(ExprKickReason.class, String.class, ExpressionType.SIMPLE, "(kick|quit|leave|left) reason");
			Skript.registerExpression(ExprMaxMemory.class, String.class, ExpressionType.SIMPLE, "max memory [in] mb");
			Skript.registerExpression(ExprMonsterSpawnLimit.class, Integer.class, ExpressionType.SIMPLE, "monster spawn[s] limit");
			Skript.registerExpression(ExprNumberOfAllLoadedChunks.class, Integer.class, ExpressionType.SIMPLE, "number of [all] load[ed] chunks");
			Skript.registerExpression(ExprNumberOfAllLoadedEntities.class, Integer.class, ExpressionType.SIMPLE, "number of [all] load[ed] entities");
			Skript.registerExpression(ExprOnlineMode.class, Boolean.class, ExpressionType.SIMPLE, "online mode"); //settable
			Skript.registerExpression(ExprTeamsPrefixFromPlayer.class, String.class, ExpressionType.PROPERTY, "team prefix (of|from) %player%"); //settable
			Skript.registerExpression(ExprTeamsPrefix.class, String.class, ExpressionType.PROPERTY, "team %string%[[']s] prefix","prefix of team %string%"); //settable
			Skript.registerExpression(ExprPlayersSpectateTarget.class, Entity.class, ExpressionType.PROPERTY, "spectate target of %player%","%player%[[']s] spectat(e|or|ing) target");
			Skript.registerExpression(ExprPing.class, Integer.class, ExpressionType.PROPERTY, "cb ping of %player%");
			Skript.registerExpression(ExprServerIP.class, String.class, ExpressionType.SIMPLE, "[server[ ]]ip");
			Skript.registerExpression(ExprServerPort.class, Integer.class, ExpressionType.SIMPLE, "[server[ ]]port");
			Skript.registerExpression(ExprSpawnRadius.class, Integer.class, ExpressionType.SIMPLE, "spawn radi[us]"); //set-table
			Skript.registerExpression(ExprTicksPerAnimalSpawns.class, Integer.class, ExpressionType.SIMPLE, "tick[s] per animal spawn[s]");
			Skript.registerExpression(ExprTicksPerMonsterSpawns.class, Integer.class, ExpressionType.SIMPLE, "tick[s] per monster spawn[s]");
			Skript.registerExpression(ExprTotalMemory.class, String.class, ExpressionType.SIMPLE, "total memory [in] mb");
			Skript.registerExpression(ExprTPS.class, Double.class, ExpressionType.SIMPLE, "[skeeland] tps");
			Skript.registerExpression(ExprUptime.class, Timespan.class, ExpressionType.SIMPLE, "[server] up[(-| )]time");
			Skript.registerExpression(ExprViewDistance.class, Integer.class, ExpressionType.SIMPLE, "view distance");
			Skript.registerExpression(ExprWaterAnimalSpawnLimit.class, Integer.class, ExpressionType.SIMPLE, "water animal spawn[s] limit");
			exprAmount += 37;
			/**
			 * TravelAgent Nether Portal things
			 */
			Skript.registerExpression(ExprPortalTravelCanCreate.class, Boolean.class, ExpressionType.SIMPLE, "portal travel[agent] can create [(status|ability)]"); //settable
			Skript.registerExpression(ExprPortalTravelCreationRadius.class, Integer.class, ExpressionType.SIMPLE, "portal travel[agent] creat(ion|e|ed|ing) radi[us]"); //setable
			Skript.registerExpression(ExprPortalTravelSearchRadius.class, Integer.class, ExpressionType.SIMPLE, "portal travel[agent] search[(ed|ing)] radi[us]");//setable
			Skript.registerExpression(ExprPortalTravelFind.class, Location.class, ExpressionType.PROPERTY, "portal travel[agent] find [at] %location%");
			Skript.registerExpression(ExprPortalTravelCreate.class, Boolean.class, ExpressionType.PROPERTY, "portal travel[agent] create [at] %location%");
			Skript.registerExpression(ExprPortalTravelFindOrCreate.class, Location.class, ExpressionType.PROPERTY, "portal travel[agent] find or create [at] %location%");
			exprAmount += 6;
			if (v.contains("v1_8")) {
				
				if (Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
					Bukkit.getLogger().info("sKeeland > ProtocolLib found, registering related expressions...");
					/**
					 * Protocollib required expressions/effects
					 */
					Skript.registerExpression(ExprEnchPreviewAbilityOfPlayer.class, Boolean.class, ExpressionType.PROPERTY, "ench[ant[ing]] preview (ability|state) of %player%");
					exprAmount += 1;
					
				} else {
					getLogger().info("sKeeland > Unable to find Protocollib!");
				}
				
			} else {
				
				getLogger().info("sKeeland > Skipping 1.8 dependent stuff");
				
			}
			if (Bukkit.getServer().getPluginManager().getPlugin("BloodMoon") != null) {
				Bukkit.getLogger().info("sKeeland > Bloodmoon found, registering related expressions...");
				/**
				 * BloodMoon Events & Expressions; I'll do this l8r
				 */
				Skript.registerEvent("Bloodmoon Start Event", SimpleEvent.class, BloodMoonStartEvent.class, "bloodmoon (start|commence)");
				Skript.registerEvent("Bloodmoon End Event", SimpleEvent.class, BloodMoonEndEvent.class, "bloodmoon (end|finish)");
				EventValues.registerEventValue(BloodMoonStartEvent.class, World.class, new Getter<World, BloodMoonStartEvent>() {
					
					public World get(BloodMoonStartEvent e) {
						
						return e.getWorld();
					}
				}, 0);
				EventValues.registerEventValue(BloodMoonEndEvent.class, World.class, new Getter<World, BloodMoonEndEvent>() {
					
					public World get(BloodMoonEndEvent e) {
						
						return e.getWorld();
					}
				}, 0);
				evtAmount += 2;
			} else {
				getLogger().info("sKeeland > Unable to find BloodMoon!");
			}
			if (Bukkit.getServer().getPluginManager().getPlugin("GriefPrevention") != null) {
				Bukkit.getLogger().info("sKeeland > GriefPrevention found, registering related expressions...");
				/**
				 * GriefPrevention Events & Expressions
				 */
				Skript.registerEvent("Claim Delete Event", SimpleEvent.class, ClaimDeletedEvent.class, "claim delet(e|ing)[d]");
				EventValues.registerEventValue(ClaimDeletedEvent.class, String.class, new Getter<String, ClaimDeletedEvent>() {
					
					public String get(ClaimDeletedEvent e) {
						
						return e.getClaim().toString();
					}
				}, 0);
				EventValues.registerEventValue(ClaimDeletedEvent.class, Player.class, new Getter<Player, ClaimDeletedEvent>() {
					
					public Player get(ClaimDeletedEvent e) {
						
						return Bukkit.getPlayer(e.getClaim().getOwnerName());
					}
				}, 0);
				evtAmount += 1;
				Skript.registerExpression(ExprAccruedClaimBlocks.class, Integer.class, ExpressionType.PROPERTY, "[remaining] accrued [claim] block[[']s] of %player%");
				Skript.registerExpression(ExprClaimAtPlayer.class, Claim.class, ExpressionType.PROPERTY, "claim at player %player%");
				Skript.registerExpression(ExprClaimAtLocation.class, Claim.class, ExpressionType.PROPERTY, "claim at %location%");
				Skript.registerExpression(ExprOwnerOfClaim.class, String.class, ExpressionType.PROPERTY, "owner of [claim] %string%");
				Skript.registerExpression(ExprRemainingClaimBlocks.class, Integer.class, ExpressionType.PROPERTY, "remaining [claim] block[[']s] of [player] %player%");
				Skript.registerExpression(ExprBonusClaimBlocks.class, Integer.class, ExpressionType.PROPERTY, "bonus [claim] block[[']s] of [player] %player%");
				exprAmount += 6;
				
			} else {
				getLogger().info("sKeeland > Unable to find GriefPrevention!");
			}
			
			if (Bukkit.getServer().getPluginManager().getPlugin("NexGenKoTHs") != null) {
		    	Bukkit.getLogger().info("sKeeland > NexGenKoTHs found, registering related expressions...");
		    	/**
		    	 * NexGenKoTHs Expressions
		    	 */
		    	Skript.registerEvent("Koth Player Start Capture", SimpleEvent.class, PlayerStartCaptureKothEvent.class, "player start capture koth [event]","start koth capture by player [event]");
		    	Skript.registerEvent("Koth Player Stop Capture", SimpleEvent.class, PlayerStopCaptureKothEvent.class, "player stop[ped] capture koth [event]","stop[ped] koth capture by player [event]");
		    	Skript.registerEvent("Player Capture Koth", SimpleEvent.class, PlayerCaptureKothEvent.class, "player capture koth");
		    	Skript.registerEvent("Player Enter Koth Event", SimpleEvent.class, PlayerEnterKothEvent.class, "[player] enter koth", "koth enter");
		    	Skript.registerEvent("Player Exit Koth Event", SimpleEvent.class, PlayerExitKothEvent.class, "[player] exit koth", "koth exit", "koth leav(e|ing)", "leav(e|ing) koth");
		    	EventValues.registerEventValue(PlayerStartCaptureKothEvent.class, String.class, new Getter<String, PlayerStartCaptureKothEvent>() {
		    		
		    		public String get(PlayerStartCaptureKothEvent e) {
		    			
		    			return e.getKoth().toString();
		    		}
		    	}, 0);
		    	EventValues.registerEventValue(PlayerStartCaptureKothEvent.class, Player.class, new Getter<Player, PlayerStartCaptureKothEvent>() {
		    		
		    		public Player get(PlayerStartCaptureKothEvent e) {
		    			
		    			return e.getPlayer();
		    		}
		    	}, 0);
		    	EventValues.registerEventValue(PlayerStopCaptureKothEvent.class, String.class, new Getter<String, PlayerStopCaptureKothEvent>() {
		    		
		    		public String get(PlayerStopCaptureKothEvent e) {
		    			
		    			return e.getKoth().toString();
		    		}
		    	}, 0);
		    	EventValues.registerEventValue(PlayerStopCaptureKothEvent.class, Player.class, new Getter<Player, PlayerStopCaptureKothEvent>() {
		    		
		    		public Player get(PlayerStopCaptureKothEvent e) {
		    			
		    			return e.getPlayer();
		    		}
		    	}, 0);
		    	EventValues.registerEventValue(PlayerCaptureKothEvent.class, String.class, new Getter<String, PlayerCaptureKothEvent>() {
		    		
		    		public String get(PlayerCaptureKothEvent e) {
		    			
		    			return e.getKoth().toString();
		    		}
		    	}, 0);
		    	EventValues.registerEventValue(PlayerCaptureKothEvent.class, Player.class, new Getter<Player, PlayerCaptureKothEvent>() {
		    		
		    		public Player get(PlayerCaptureKothEvent e) {
		    			
		    			return e.getPlayer();
		    		}
		    	}, 0);
		    	EventValues.registerEventValue(PlayerEnterKothEvent.class, String.class, new Getter<String, PlayerEnterKothEvent>() {
		    		
		    		public String get(PlayerEnterKothEvent e) {
		    			
		    			return e.getKoth().toString();
		    		}
		    	}, 0);
		    	EventValues.registerEventValue(PlayerEnterKothEvent.class, Player.class, new Getter<Player, PlayerEnterKothEvent>() {
		    		
		    		public Player get(PlayerEnterKothEvent e) {
		    			
		    			return e.getPlayer();
		    		}
		    	}, 0);
		    	EventValues.registerEventValue(PlayerExitKothEvent.class, String.class, new Getter<String, PlayerExitKothEvent>() {
		    		
		    		public String get(PlayerExitKothEvent e) {
		    			
		    			return e.getKoth().toString();
		    		}
		    	}, 0);
		    	EventValues.registerEventValue(PlayerExitKothEvent.class, Player.class, new Getter<Player, PlayerExitKothEvent>() {
		    		
		    		public Player get(PlayerExitKothEvent e) {
		    			
		    			return e.getPlayer();
		    		}
		    	}, 0);
		    	evtAmount += 5;
		    	Skript.registerExpression(ExprRemainingTime.class, String.class, ExpressionType.PROPERTY, "[koth] [get] remaining [capture] time of [koth] %string%");
		    	Skript.registerExpression(ExprCappingPlayerOfKoth.class, Player.class, ExpressionType.PROPERTY, "[koth] [get] (capping|capturing) player of [koth] %string%");
		    	exprAmount += 2;
		    	
			} else {
				getLogger().info("sKeeland > Unable to find NexGenKoTHs!");
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
		    	EventValues.registerEventValue(NewTownEvent.class, String.class, new Getter<String, NewTownEvent>() {
					
					public String get(NewTownEvent e) {
						
						return e.getTown().toString();
					}
				}, 0);
		    	// town add resident event event-player
		    	EventValues.registerEventValue(TownAddResidentEvent.class, Player.class, new Getter<Player, TownAddResidentEvent>() {
					
					public Player get(TownAddResidentEvent e) {
						
						return Bukkit.getPlayer(e.getResident().toString());
					}
				}, 0);
		    	// town add resident event event-town?
		    	EventValues.registerEventValue(TownAddResidentEvent.class, Town.class, new Getter<Town, TownAddResidentEvent>() {
					
					public Town get(TownAddResidentEvent e) {
						
						return e.getTown();
					}
				}, 0);
		    	// town remove resident event event-player
		    	EventValues.registerEventValue(TownRemoveResidentEvent.class, Player.class, new Getter<Player, TownRemoveResidentEvent>() {
					
					public Player get(TownRemoveResidentEvent e) {
						
						return Bukkit.getPlayer(e.getResident().toString());
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
		    	evtAmount += 10;
		    	Skript.registerEffect(EffBackupTownyData.class, new String[] { "backup towny [data]" });
		    	Skript.registerEffect(EffEndWarEvent.class, new String[] { "end war[[ ]event]" });
		    	Skript.registerEffect(EffStartWarEvent.class, new String[] { "start war[[ ]event]" });
		    	Skript.registerEffect(EffAddResidentToTown.class, new String[] { "add %string% to town %town%" });
		    	Skript.registerEffect(EffDeleteNation.class, new String[] { "(delete|del) nation %nation%" });
		    	Skript.registerEffect(EffDeleteTown.class, new String[] { "(delete|del) town %town%" });
		    	Skript.registerEffect(EffDeleteResident.class, new String[] { "(delete|del) (resident|player) %string%" });
		    	Skript.registerEffect(EffEliminateTown.class, new String[] { "eliminate town %string% [from war]" });
		    	Skript.registerEffect(EffEliminateNation.class, new String[] { "eliminate nation %string% [from war]" });
		    	Skript.registerEffect(EffNewNation.class, new String[] { "new nation [named] %string%" });
		    	Skript.registerEffect(EffNewTown.class, new String[] { "new town [named] %string%" });
		    	Skript.registerEffect(EffRenameTown.class, new String[] { "[re]name town %town% to %string%" });
		    	Skript.registerEffect(EffRenameNation.class, new String[] { "[re]name nation %nation% to %string%" });
		    	Skript.registerEffect(EffRemoveNation.class, new String[] { "(remove|rem) nation %nation%" });
		    	Skript.registerEffect(EffRemoveTown.class, new String[] { "(remove|rem) town %town%" });
		    	Skript.registerEffect(EffRemoveResidentFromTown.class, new String[] { "remove %string% from town %town%" });
		    	Skript.registerEffect(EffRemoveTownFromNation.class, new String[] { "remove %string% from nation %nation%" });
		    	Skript.registerEffect(EffSetSetupDelayWar.class, new String[] { "set war [set[up]] delay to %integer%" });
		    	Skript.registerEffect(EffSaveTownyData.class, new String[] { "save towny [data]" });
		    	effAmount += 19;
		    	
		    	if (Bukkit.getPluginManager().getPlugin("Umbaska") == null) {
		    		/**
		    		 * Umbaska not installed, add certain towny expressions
		    		 */ 
		    		Skript.registerExpression(ExprRegisteredDateOfPlayer.class, String.class, ExpressionType.PROPERTY, "registered date of %player%");
		    		Skript.registerExpression(ExprTownBank.class, Double.class, ExpressionType.PROPERTY, "balance of town %string%");
		    		Skript.registerExpression(ExprTownOfPlayer.class, Town.class, ExpressionType.PROPERTY, "town of %player%");
		    		exprAmount += 3;
		    	}
		    	
		    	Skript.registerExpression(ExprAlliesOfNation.class, Nation.class, ExpressionType.PROPERTY, "all(y|ies) of [nation] %string%");
		    	Skript.registerExpression(ExprAssistantsOfNation.class, Player.class, ExpressionType.PROPERTY, "assistant[[']s] (of|in) [nation] %string%");
		    	Skript.registerExpression(ExprEnemiesOfNation.class, Nation.class, ExpressionType.PROPERTY, "enem(y|ies) of [nation] %string%");
		    	Skript.registerExpression(ExprFireStateOfTown.class, Boolean.class, ExpressionType.PROPERTY, "fire state of [town] %string%");
		    	Skript.registerExpression(ExprFriendsOfPlayer.class, Player.class, ExpressionType.PROPERTY, "[towny] friend[[']s] of %player%");
		    	Skript.registerExpression(ExprNationCapital.class, String.class, ExpressionType.PROPERTY, "capital of [nation] %string%");
		    	Skript.registerExpression(ExprKingOfNation.class, Player.class, ExpressionType.PROPERTY, "king of [nation] %string%");
		    	Skript.registerExpression(ExprMayorOfTown.class, Resident.class, ExpressionType.PROPERTY, "mayor of [town] %string%"); //settable
		    	Skript.registerExpression(ExprNationAtLocation.class, Nation.class, ExpressionType.PROPERTY, "nation at %location%");
		    	Skript.registerExpression(ExprNationOfPlayer.class, Nation.class, ExpressionType.PROPERTY, "nation of %player%");
		    	Skript.registerExpression(ExprNationOfTown.class, Nation.class, ExpressionType.PROPERTY, "nation of town %string%", "%string%[']s nation");
		    	Skript.registerExpression(ExprNationBank.class, Double.class, ExpressionType.PROPERTY, "nation balance of %string%");
		    	Skript.registerExpression(ExprNationPlayerCount.class, Integer.class, ExpressionType.PROPERTY, "nation player[ ]count of %string%");
		    	Skript.registerExpression(ExprNationTownCount.class, Integer.class, ExpressionType.PROPERTY, "nation town[ ]count of %string%");
		    	Skript.registerExpression(ExprNationPlayers.class, String.class, ExpressionType.PROPERTY, "player[[']s] of nation %string%, player[[']s] in nation %string%");
		    	Skript.registerExpression(ExprNationTaxes.class, Double.class, ExpressionType.PROPERTY, "nation taxes of %string%");
		    	Skript.registerExpression(ExprPlayerIsNeutral.class, Boolean.class, ExpressionType.PROPERTY, "%player%[[']s] neutrality [state], %player% is neutral");
		    	Skript.registerExpression(ExprPlayersInNation.class, String.class, ExpressionType.PROPERTY, "players of nation %string%");
		    	Skript.registerExpression(ExprPublicStateOfTown.class, Boolean.class, ExpressionType.PROPERTY, "public state of [town] %string%");
		    	Skript.registerExpression(ExprPvPStateOfTown.class, Boolean.class, ExpressionType.PROPERTY, "pvp state of [town] %string%");
		    	Skript.registerExpression(ExprTownsInNation.class, String.class, ExpressionType.PROPERTY, "town[[']s] (in|of) [nation] %string%");
		    	Skript.registerExpression(ExprTownAtLocation.class, String.class, ExpressionType.PROPERTY, "town at location %location%");
		    	Skript.registerExpression(ExprTownBoard.class, String.class, ExpressionType.PROPERTY, "town board of %string%");
		    	Skript.registerExpression(ExprTownBonusBlocks.class, Integer.class, ExpressionType.PROPERTY, "bonus block[s] of [town] %string%");
		    	Skript.registerExpression(ExprTownsInNationCount.class, Integer.class, ExpressionType.PROPERTY, "[nation[ ]]town[ ]count of %string%");
		    	Skript.registerExpression(ExprResidentsWithoutTown.class, String.class, ExpressionType.SIMPLE, "(resident[[']s]|player[[']s]) without [a] town");
		    	Skript.registerExpression(ExprTownsWithoutNation.class, String.class, ExpressionType.SIMPLE, "town[[']s] without [a] nation");
		    	Skript.registerExpression(ExprWarTime.class, Boolean.class, ExpressionType.SIMPLE, "war[ ]time");
		    	exprAmount += 26;
		    	
			} else {
				getLogger().info("sKeeland > Unable to find Towny!");
			}
		    
		    if (Bukkit.getServer().getPluginManager().getPlugin("WorldBorder") != null) {
		    	Bukkit.getLogger().info("sKeeland > WorldBorder found, registering related expressions...");
		    	/**
		    	 * Worldborder Expressions
		    	 */
		    	//Skript.registerEvent("WorldBorder Fill Start", SimpleEvent.class, WorldBorderFillStartEvent.class, "worldborder (fill|pregen) start [event]");
		    	Skript.registerEvent("WorldBorder Fill Finish Event", SimpleEvent.class, WorldBorderFillFinishedEvent.class, "worldborder (fill|pregen) finish [event]");
		    	Skript.registerEvent("WorldBorder Trim Finish Event", SimpleEvent.class, WorldBorderTrimFinishedEvent.class, "worldborder trim finish [event]");
		    	evtAmount += 2;
		    	Skript.registerExpression(ExprXCenterOfrBorder.class, Double.class, ExpressionType.PROPERTY, "[[r]border] x center location of [world] %string%");
		    	Skript.registerExpression(ExprZCenterOfrBorder.class, Double.class, ExpressionType.PROPERTY, "[[r]border] z center location of [world] %string%");
		    	Skript.registerExpression(ExprXRadiusOfrBorder.class, Integer.class, ExpressionType.PROPERTY, "[[r]border] x radius of [world] %string%");
		    	Skript.registerExpression(ExprZRadiusOfrBorder.class, Integer.class, ExpressionType.PROPERTY, "[[r]border] z radius of [world] %string%");
		    	exprAmount += 4;
		    	
		    } else {
		    	
		    	getLogger().info("sKeeland > Unable to find WorldBorder!");
		    }
		    if (Bukkit.getServer().getPluginManager().getPlugin("uSkyBlock") != null) {
		    	Bukkit.getLogger().info("sKeeland > uSkyBlock found, registering related expressions...");
		    	/**
		    	 * uSkyBlock Expressions
		    	 */
		    	Skript.registerExpression(ExprIslandRankOfPlayer.class, Integer.class, ExpressionType.PROPERTY, "[u[skyblock]] island rank of %player%");
		    	Skript.registerExpression(ExprIslandLevelOfPlayer.class, Double.class, ExpressionType.PROPERTY, "[u[skyblock]] island level of %player%");
		    	Skript.registerExpression(ExprIslandRankAtLocation.class, Integer.class, ExpressionType.PROPERTY, "[u[skyblock]] island rank at %location%");
		    	Skript.registerExpression(ExprIslandMembersOfPlayersIsland.class, Player.class, ExpressionType.PROPERTY, "[u[skyblock]] member[[']s] of %player%[[']s] island");
		    	Skript.registerExpression(ExprIslandMembersAtLocationOfIsland.class, Player.class, ExpressionType.PROPERTY, "[u[skyblock]] member[[']s] of island at %location%");
		    	exprAmount += 5;
		    	
		    } else {
		    	
		    	getLogger().info("sKeeland > Unable to find uSkyBlock!");
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
//		    	EventValues.registerEventValue(IslandEnterEvent.class, Location.class, new Getter <Location, IslandEnterEvent>() {
//		    		
//		    		public Location get(IslandEnterEvent e) {
//		    			return e.getIslandLocation();
//		    		}
//		    	}, 0);
		    	EventValues.registerEventValue(IslandEnterEvent.class, Location.class, new Getter <Location, IslandEnterEvent>() {
		    		
		    		public Location get(IslandEnterEvent e) {
		    			return e.getLocation();
		    		}
		    	}, 0);
//		    	EventValues.registerEventValue(IslandEnterEvent.class, Player.class, new Getter<Player, IslandEnterEvent>() {
//					
//					public Player get(IslandEnterEvent e) {
//						
//						Player enterownerplayer = Bukkit.getPlayer(e.getIslandOwner());
//						return enterownerplayer;
//					}
//				}, 0);
		    	EventValues.registerEventValue(IslandExitEvent.class, Player.class, new Getter<Player, IslandExitEvent>() {
					
					public Player get(IslandExitEvent e) {
						
						Player exiteventplayer = Bukkit.getPlayer(e.getPlayer());
						return exiteventplayer;
					}
				}, 0);
//		    	EventValues.registerEventValue(IslandExitEvent.class, Location.class, new Getter <Location, IslandExitEvent>() {
//		    		
//		    		public Location get(IslandExitEvent e) {
//		    			return e.getIslandLocation();
//		    		}
//		    	}, 0);
		    	EventValues.registerEventValue(IslandExitEvent.class, Location.class, new Getter <Location, IslandExitEvent>() {
		    		
		    		public Location get(IslandExitEvent e) {
		    			return e.getLocation();
		    		}
		    	}, 0);
//		    	EventValues.registerEventValue(IslandExitEvent.class, Player.class, new Getter<Player, IslandExitEvent>() {
//					
//					public Player get(IslandExitEvent e) {
//						
//						Player exitownerplayer = Bukkit.getPlayer(e.getIslandOwner());
//						return exitownerplayer;
//					}
//				}, 0);
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
		    	EventValues.registerEventValue(IslandLevelEvent.class, Integer.class, new Getter <Integer, IslandLevelEvent>() {
		    		
		    		public Integer get(IslandLevelEvent e) {
		    			return e.getLevel();
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
		    	Skript.registerEffect(EffCalculateIslandLevelOfPlayer.class, new String[] { "calculate island level of %player%" });
		    	effAmount += 2;
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
		    	Skript.registerExpression(ExprTeamMembersFromPlayer.class, Player.class, ExpressionType.PROPERTY, "[askyblock] team member[[']s] from [player] %player%");
		    	exprAmount += 11;
		    	
		    } else {
		    	getLogger().info("sKeeland > Unable to find ASkyBlock!");
		    }
		    
//		    if (DataUtils.usecilentsideworldborder) {
//		    	Bukkit.getLogger().info("sKeeland > registering cilentside worldborder effects...");
//		    	/**
//		    	 * Cilent Worldborder Effects
//		    	 */
//		    	
//		    	Skript.registerEffect(EffSetWarningBlocksOfPlayer.class, new String[] { "set [[world]border] warning blocks (of|for|to) %player% to %integer%" });
//		    	Skript.registerEffect
//		    	effAmount += 1;
//		    	
//		    } else {
//		    	Bukkit.getLogger().info("sKeeland > Skipping cilent worldborder effects...");
//		    }
//		    
		    getLogger().info("Loaded " + evtAmount + (evtAmount == 1 ? " event, " : " events, ")  + effAmount + (effAmount == 1 ? " effect and " : " effects and ") + exprAmount + (exprAmount == 1 ? " expression" : " expressions!"));
		    
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

    public static Main getInstance() {
        return plugin;
    }
    
    public static Timer getTimer() {
        return timer;
    }
    
    private void utils() {
        timer = new Timer();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, timer, 1000L, 50L);
    }
    
    public static String getVersion() {
        return version;
    }
}
