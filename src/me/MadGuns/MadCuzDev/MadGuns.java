package me.MadGuns.MadCuzDev;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class MadGuns extends JavaPlugin implements Listener {
	
	HashMap<String, Integer> rpgDelay = new HashMap<String, Integer>();
	HashMap<String, Integer> rifleDelay = new HashMap<String, Integer>();
	HashMap<String, Integer> sniperDelay = new HashMap<String, Integer>();
	
	ArrayList<UUID> rpgHit = new ArrayList<UUID>();
	ArrayList<UUID> rifleHit = new ArrayList<UUID>();
	ArrayList<UUID> sniperHit = new ArrayList<UUID>();
	ArrayList<UUID> doomHit = new ArrayList<UUID>();
	ArrayList<UUID> serverBreakerHit = new ArrayList<UUID>();
	
	public void onEnable() {
		getLogger().info("MadGuns Enabled");
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void snowBallExploRifle(ProjectileHitEvent event) {
		if (rifleHit.contains(event.getEntity().getUniqueId())) {
			Location loc = event.getEntity().getLocation();
			World w = event.getEntity().getLocation().getWorld();
			w.playEffect(loc, Effect.SMOKE, 0);
		}
	}
	
	@EventHandler
	public void ifRifleHit(EntityDamageByEntityEvent event) {
			Entity damaged = event.getEntity();
			if (damaged instanceof Player) {
				((Player) damaged).damage(2.0);
		}
	}
	
	@EventHandler
	public void rifleShoot(PlayerInteractEvent event) {
		Player p = (Player) event.getPlayer();
		if (!p.getItemInHand().getType().equals(Material.AIR)) {
			if (p.getItemInHand().getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS) && p.getItemInHand().getType().equals(Material.IRON_AXE) && event.getAction().equals(Action.RIGHT_CLICK_AIR) || p.getItemInHand().getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS) && p.getItemInHand().getType().equals(Material.IRON_AXE) && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (rifleDelay.get(p.getName() + "delay") == null || rifleDelay.get(p.getName() + "delay") == 0) {
				Location loc = p.getLocation();
			loc.setY(p.getLocation().getY() + 1.5);
			Snowball snow = p.getWorld().spawn(loc, Snowball.class);
            snow.setShooter(p);
            snow.setVelocity(p.getEyeLocation().getDirection().multiply(2));
            rifleHit.add(snow.getUniqueId());
            rifleDelay.put(p.getName() + "delay", 5000);
            World w = event.getPlayer().getWorld();
            w.playSound(loc, Sound.CLICK, 30F, 1000000F);
            
            new BukkitRunnable() {
                @Override
                public void run() {
                	rifleDelay.put(p.getName() + "delay", 0);
                }
            }.runTaskLater(this, 4L);
		}
      }
		}
	}
	
	@EventHandler
	public void rpgShoot(PlayerInteractEvent event) {
		Player p = (Player) event.getPlayer();
		if (!p.getItemInHand().getType().equals(Material.AIR)) {
			if (p.getItemInHand().getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS) && p.getItemInHand().getType().equals(Material.IRON_SPADE) && event.getAction().equals(Action.RIGHT_CLICK_AIR) || p.getItemInHand().getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS) && p.getItemInHand().getType().equals(Material.IRON_SPADE) && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (rpgDelay.get(p.getName() + "delay") == null || rpgDelay.get(p.getName() + "delay") == 0) {
				Location loc = p.getLocation();
			loc.setY(p.getLocation().getY() + 1.5);
			Snowball snow = p.getWorld().spawn(loc, Snowball.class);
            snow.setShooter(p);
            snow.setVelocity(p.getEyeLocation().getDirection().multiply(3));
            rpgHit.add(snow.getUniqueId());
            rpgDelay.put(p.getName() + "delay", 5000);
            World w = event.getPlayer().getWorld();
            w.playSound(loc, Sound.FIZZ, 100F, 1000F);
            
            new BukkitRunnable() {
                @Override
                public void run() {
                	rpgDelay.put(p.getName() + "delay", 0);
                }
            }.runTaskLater(this, 100L);
			}
        }
		}
	}
	
	@EventHandler
	public void snowBallExploRPG(ProjectileHitEvent event) {
		if (rpgHit.contains(event.getEntity().getUniqueId())) {
			Location loc = event.getEntity().getLocation();
			World w = event.getEntity().getLocation().getWorld();
			w.createExplosion(loc, 3);
			
			List<Entity> near = w.getEntities();
			for(Entity e : near) {
				Entity damaged = e;
			    if (e.getLocation().distance(event.getEntity().getLocation()) <= 4D && e instanceof LivingEntity) {
			    	((Player) damaged).damage(10.0);
			    }
			}
		}
	}
	
	@EventHandler
	public void snowBallExploSniper(ProjectileHitEvent event) {
		if (sniperHit.contains(event.getEntity().getUniqueId())) {
			Location loc = event.getEntity().getLocation();
			World w = event.getEntity().getLocation().getWorld();
			w.playEffect(loc, Effect.SMOKE, 0);
		}
	}
	
	@EventHandler
	public void ifsniperSniper(EntityDamageByEntityEvent event) {
			Entity damaged = event.getEntity();
			if (damaged instanceof Player) {
				((Player) damaged).damage(20.0);
		}
	}
	
	@EventHandler
	public void sniperZoom(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (player.getItemInHand().getType().equals(Material.DIAMOND_AXE)) {
        if(!player.isSneaking() && player.getInventory().getItemInHand().getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999999, 254), true);
        } else {
        	player.removePotionEffect(PotionEffectType.SLOW);
        	}
        }
    }
	
	@EventHandler
	public void sniperShoot(PlayerInteractEvent event) {
		Player p = (Player) event.getPlayer();
		if (!p.getItemInHand().getType().equals(Material.AIR)) {
			if (p.getItemInHand().getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS) && p.getItemInHand().getType().equals(Material.DIAMOND_AXE) && event.getAction().equals(Action.RIGHT_CLICK_AIR) || p.getItemInHand().getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS) && p.getItemInHand().getType().equals(Material.DIAMOND_AXE) && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (sniperDelay.get(p.getName() + "delay") == null || sniperDelay.get(p.getName() + "delay") == 0) {
				Location loc = p.getLocation();
			loc.setY(p.getLocation().getY() + 1.5);
			Snowball snow = p.getWorld().spawn(loc, Snowball.class);
            snow.setShooter(p);
            snow.setVelocity(p.getEyeLocation().getDirection().multiply(4));
            sniperHit.add(snow.getUniqueId());
            sniperDelay.put(p.getName() + "delay", 5000);
            World w = event.getPlayer().getWorld();
            w.playSound(loc, Sound.ITEM_BREAK, 50F, 0F);
            
            new BukkitRunnable() {
                @Override
                public void run() {
                	sniperDelay.put(p.getName() + "delay", 0);
                }
            }.runTaskLater(this, 60L);
		}
			}
      }
	}
	
	@EventHandler
	public void doomShoot(PlayerInteractEvent event) {
		Player p = (Player) event.getPlayer();
		if (!p.getItemInHand().getType().equals(Material.AIR)) {
			if (p.getItemInHand().getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS) && p.getItemInHand().getType().equals(Material.DIAMOND_SPADE) && event.getAction().equals(Action.RIGHT_CLICK_AIR) || p.getItemInHand().getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS) && p.getItemInHand().getType().equals(Material.DIAMOND_SPADE) && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				Location loc = p.getLocation();
			loc.setY(p.getLocation().getY() + 1.5);
			Snowball snow = p.getWorld().spawn(loc, Snowball.class);
            snow.setShooter(p);
            snow.setVelocity(p.getEyeLocation().getDirection().multiply(4));
            doomHit.add(snow.getUniqueId());
            World w = event.getPlayer().getWorld();
            w.playSound(loc, Sound.AMBIENCE_THUNDER, 1000F, 0F);
			}
		}
	}
	
	@EventHandler
	public void snowBallExplodoom(ProjectileHitEvent event) {
		if (doomHit.contains(event.getEntity().getUniqueId())) {
			Location loc = event.getEntity().getLocation();
			World w = event.getEntity().getLocation().getWorld();
			w.createExplosion(loc, 30);
			
			List<Entity> near = w.getEntities();
			for(Entity e : near) {
				Entity damaged = e;
			    if (e.getLocation().distance(event.getEntity().getLocation()) <= 20D && e instanceof LivingEntity) {
			    	((Player) damaged).damage(100.0);
			    }
			}
		}
	}
	
	@EventHandler
	public void serverBreakerShoot(PlayerInteractEvent event) {
		Player p = (Player) event.getPlayer();
		if (!p.getItemInHand().getType().equals(Material.AIR)) {
			if (p.getItemInHand().getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS) && p.getItemInHand().getType().equals(Material.NETHER_STAR) && event.getAction().equals(Action.RIGHT_CLICK_AIR) || p.getItemInHand().getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS) && p.getItemInHand().getType().equals(Material.NETHER_STAR) && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				Location loc = p.getLocation();
			loc.setY(p.getLocation().getY() + 1.5);
			for (int i = 0; i < 100; i++) {
			Snowball snow = p.getWorld().spawn(loc, Snowball.class);
            snow.setShooter(p);
            snow.setVelocity(p.getEyeLocation().getDirection().multiply(4));
            serverBreakerHit.add(snow.getUniqueId());
			}
            World w = event.getPlayer().getWorld();
            w.playSound(loc, Sound.AMBIENCE_THUNDER, 1000F, 0F);
            w.playSound(loc, Sound.AMBIENCE_THUNDER, 1000F, 0F);
            w.playSound(loc, Sound.AMBIENCE_THUNDER, 1000F, 0F);
            w.playSound(loc, Sound.AMBIENCE_THUNDER, 1000F, 0F);
            w.playSound(loc, Sound.AMBIENCE_THUNDER, 1000F, 0F);
			}
		}
	}
	
	@EventHandler
	public void serverBreakerExplo(ProjectileHitEvent event) {
		if (serverBreakerHit.contains(event.getEntity().getUniqueId())) {
			Location loc = event.getEntity().getLocation();
			World w = event.getEntity().getLocation().getWorld();
			w.createExplosion(loc, 80);
			
			List<Entity> near = w.getEntities();
			for(Entity e : near) {
				Entity damaged = e;
			    if (e.getLocation().distance(event.getEntity().getLocation()) <= 80D && e instanceof LivingEntity) {
			    	((Player) damaged).damage(100.0);
			    }
			}
		}
	}
	
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	
    	Player player = (Player) sender;
    	
    	if (cmd.getName().equalsIgnoreCase("gun") && sender instanceof Player) {
    		if (player.hasPermission("madguns.admin")) {
    		if (args.length == 1) {
    			if (args[0].equalsIgnoreCase("rifle")) {
    				ItemStack rifle = new ItemStack(Material.IRON_AXE);
    				ItemMeta im = rifle.getItemMeta();
    				rifle.getItemMeta().spigot().setUnbreakable(true);
    				im.setDisplayName("§cRifle");
    				im.addEnchant(Enchantment.DURABILITY, 1, true);
    				im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    				im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    				rifle.setItemMeta(im);
    				player.getInventory().addItem(rifle);
    			} else if (args[0].equalsIgnoreCase("rpg")) {
    				ItemStack rpg = new ItemStack(Material.IRON_SPADE);
    				ItemMeta im = rpg.getItemMeta();
    				rpg.getItemMeta().spigot().setUnbreakable(true);
    				im.setDisplayName("§cRPG");
    				im.addEnchant(Enchantment.DURABILITY, 1, true);
    				im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    				im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    				rpg.setItemMeta(im);
    				player.getInventory().addItem(rpg);
    			} else if (args[0].equalsIgnoreCase("sniper")) {
    				ItemStack sniper = new ItemStack(Material.DIAMOND_AXE);
    				ItemMeta im = sniper.getItemMeta();
    				sniper.getItemMeta().spigot().setUnbreakable(true);
    				im.setDisplayName("§cSniper");
    				im.addEnchant(Enchantment.DURABILITY, 1, true);
    				im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    				im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    				sniper.setItemMeta(im);
    				player.getInventory().addItem(sniper);
    			} else if (args[0].equalsIgnoreCase("doom")) {
    				ItemStack doom = new ItemStack(Material.DIAMOND_SPADE);
    				ItemMeta im = doom.getItemMeta();
    				doom.getItemMeta().spigot().setUnbreakable(true);
    				im.setDisplayName("§c§l§oDoom");
    				im.addEnchant(Enchantment.DURABILITY, 1, true);
    				im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    				im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    				doom.setItemMeta(im);
    				player.getInventory().addItem(doom);
    			} else if (args[0].equalsIgnoreCase("serverbreaker")) {
    				ItemStack serverbreaker = new ItemStack(Material.NETHER_STAR);
    				ItemMeta im = serverbreaker.getItemMeta();
    				serverbreaker.getItemMeta().spigot().setUnbreakable(true);
    				im.setDisplayName("§c§l§oServer Breaker");
    				im.addEnchant(Enchantment.DURABILITY, 1, true);
    				im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    				im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    				serverbreaker.setItemMeta(im);
    				player.getInventory().addItem(serverbreaker);
    			} else { player.sendMessage(ChatColor.RED + "Valid guns: rifle, rpg, sniper, doom, serverbreaker"); } } else {
    			player.sendMessage(ChatColor.RED + "Usage: /gun (gun)");
    		} } else {
    			player.sendMessage("Sorry dude, you're not oped.");
    		}
    	} return true;
}
}
