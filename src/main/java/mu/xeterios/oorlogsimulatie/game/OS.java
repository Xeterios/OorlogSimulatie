package mu.xeterios.oorlogsimulatie.game;

import lombok.Getter;
import lombok.Setter;
import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.game.handlers.EventHandlers;
import mu.xeterios.oorlogsimulatie.game.handlers.PreGameEventHandler;
import mu.xeterios.oorlogsimulatie.game.handlers.ScoreboardHandler;
import mu.xeterios.oorlogsimulatie.game.timer.TimerHandler;
import mu.xeterios.oorlogsimulatie.game.timer.TimerType;
import mu.xeterios.oorlogsimulatie.game.handlers.ParticleHandler;
import mu.xeterios.oorlogsimulatie.map.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Objects;

public class OS {

    @Getter private ArrayList<Player> players;
    @Getter private ArrayList<Player> attackers;
    @Getter private ArrayList<Player> defenders;
    @Getter private ParticleHandler particleHandler;
    @Getter @Setter private boolean started;
    @Getter @Setter private boolean initialized;

    @Getter private Scoreboard scoreboard;
    @Getter @Setter private Scoreboard saveOldScoreboard;
    @Getter private Objective objective;

    private TimerHandler handler;
    @Getter private EventHandlers handlers;
    @Getter private PreGameEventHandler preGameEventHandlers;
    @Getter private ScoreboardHandler scoreboardHandler;

    @Getter private final ArrayList<ItemStack> kitItems;

    @Getter @Setter private Map map;

    public OS() {
        this.players = new ArrayList<>();
        this.attackers = new ArrayList<>();
        this.defenders = new ArrayList<>();
        this.kitItems = new ArrayList<>();
        this.handler = new TimerHandler(this);
        this.particleHandler = new ParticleHandler(this);
        this.handlers = new EventHandlers(this);
        this.preGameEventHandlers = new PreGameEventHandler(this);
        this.scoreboardHandler = new ScoreboardHandler(this);
        this.initialized = false;
        Setup();
        SetupKit();
    }

    private void Setup(){
        try {
            this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            this.saveOldScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
            if (scoreboard.getTeam("Attackers") != null){
                Objects.requireNonNull(scoreboard.getTeam("Attackers")).unregister();
            }
            Team att = scoreboard.registerNewTeam("Attackers");
            att.setPrefix(ChatColor.RED + "");
            att.setColor(ChatColor.RED);

            if (scoreboard.getTeam("Defenders") != null){
                Objects.requireNonNull(scoreboard.getTeam("Defenders")).unregister();
            }
            Team def = scoreboard.registerNewTeam("Defenders");
            def.setPrefix(ChatColor.BLUE + "");
            def.setColor(ChatColor.BLUE);

            if (saveOldScoreboard.getTeam("Attackers") != null){
                Objects.requireNonNull(scoreboard.getTeam("Attackers")).unregister();
            }
            Team att2 = saveOldScoreboard.registerNewTeam("Attackers");
            att2.setPrefix(ChatColor.RED + "");
            att2.setColor(ChatColor.RED);

            if (saveOldScoreboard.getTeam("Defenders") != null){
                Objects.requireNonNull(saveOldScoreboard.getTeam("Defenders")).unregister();
            }
            Team def2 = saveOldScoreboard.registerNewTeam("Defenders");
            def2.setPrefix(ChatColor.BLUE + "");
            def2.setColor(ChatColor.BLUE);

            this.objective = scoreboard.registerNewObjective("Oorlog", "dummy");
            this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            this.objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getPlugin(Main.class).GetConfig().getPluginColor()) + "" + ChatColor.BOLD + "Oorlog");
        } catch (Exception e){
            this.objective = null;
            this.scoreboard = null;
        }
    }

    public boolean Start(){
        if (!started){
            if (defenders.size() == 0 || attackers.size() == 0){
                return false;
            }
            this.started = true;
            return handler.RunTimer(TimerType.STARTUP);
        } else {
            return false;
        }
    }

    public void Stop(){
        if (started || initialized){
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            scoreboardHandler.ResetScoreboard();
            for(int i = 0; i < getAttackers().size(); i++){
                RemoveAttacker(getAttackers().get(i));
            }
            for(int i = 0; i < getDefenders().size(); i++){
                RemoveDefender(getDefenders().get(i));
            }
            for (Player p : players){
                p.sendTitle(ChatColor.YELLOW + "" +  ChatColor.BOLD + "Klaar!", ChatColor.WHITE + "Het spel is afgelopen!", 10, 40, 10);
                p.setScoreboard(saveOldScoreboard);
                p.setBedSpawnLocation(map.getSpawnAttackers().getWorld().getSpawnLocation());
                p.getInventory().clear();
                p.getActivePotionEffects().clear();
                String command = "lp user " + p.getName() + " permission set os.queued false";
                Bukkit.dispatchCommand(console, command);
                String command2 = "spawn " + p.getName();
                Bukkit.dispatchCommand(console, command2);
            }
            this.handler.StopTimer();
            this.players = new ArrayList<>();
            this.attackers = new ArrayList<>();
            this.defenders = new ArrayList<>();
            this.initialized = false;
            this.started = false;
            this.particleHandler.disableParticles();
            HandlerList.unregisterAll(Main.getPlugin(Main.class));
            this.handler = new TimerHandler(this);
            this.particleHandler = new ParticleHandler(this);
            this.handlers = new EventHandlers(this);
            this.preGameEventHandlers = new PreGameEventHandler(this);
            this.scoreboardHandler = new ScoreboardHandler(this);
        }
    }

    public void AddAttacker(Player p){
        scoreboard.getTeam("Attackers").addEntry(p.getName());
        saveOldScoreboard.getTeam("Attackers").addEntry(p.getName());
        p.setScoreboard(scoreboard);
        this.attackers.add(p);
    }

    public void AddDefender(Player p){
        scoreboard.getTeam("Defenders").addEntry(p.getName());
        saveOldScoreboard.getTeam("Defenders").addEntry(p.getName());
        p.setScoreboard(scoreboard);
        this.defenders.add(p);
    }

    public void RemoveAttacker(Player p){
        scoreboard.getTeam("Attackers").removeEntry(p.getName());
        saveOldScoreboard.getTeam("Attackers").removeEntry(p.getName());
        p.setScoreboard(saveOldScoreboard);
        this.attackers.remove(p);
    }

    public void RemoveDefender(Player p){
        scoreboard.getTeam("Defenders").removeEntry(p.getName());
        saveOldScoreboard.getTeam("Defenders").removeEntry(p.getName());
        p.setScoreboard(saveOldScoreboard);
        this.defenders.remove(p);
    }

    public boolean isAttacker(Player target){
        for(Player p : attackers){
            if (p.getUniqueId() == target.getUniqueId()){
                return true;
            }
        }
        return false;
    }

    public boolean isDefender(Player target){
        for(Player p : defenders){
            if (p.getUniqueId() == target.getUniqueId()){
                return true;
            }
        }
        return false;
    }

    public void AddPlayer(Player p){
        this.players.add(p);
    }

    public void ActivateListener(){
        Bukkit.getPluginManager().registerEvents(handlers, Main.getPlugin(Main.class));
    }

    public void ActivatePreGameListener(){
        Bukkit.getPluginManager().registerEvents(preGameEventHandlers, Main.getPlugin(Main.class));
    }

    public void StopPreGameListener(){
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), () -> HandlerList.unregisterAll(preGameEventHandlers), 20L);
    }

    public void StopListener(){
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), () -> HandlerList.unregisterAll(handlers), 20L);
    }

    private void SetupKit(){
        kitItems.add(new ItemStack(Material.DIAMOND_HELMET, 1));
        kitItems.add(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
        kitItems.add(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
        kitItems.add(new ItemStack(Material.DIAMOND_BOOTS, 1));
        kitItems.add(new ItemStack(Material.NETHERITE_SWORD, 1));
        kitItems.add(new ItemStack(Material.NETHERITE_SWORD, 1));
        kitItems.add(new ItemStack(Material.NETHERITE_AXE, 1));
        kitItems.add(new ItemStack(Material.SHIELD, 1));
        kitItems.add(new ItemStack(Material.BOW, 1));
        kitItems.add(new ItemStack(Material.BOW, 1));
        kitItems.add(new ItemStack(Material.COOKED_BEEF, 64));
        kitItems.add(new ItemStack(Material.COOKED_BEEF, 64));
        kitItems.add(new ItemStack(Material.GOLDEN_APPLE, 64));
        kitItems.add(new ItemStack(Material.WATER_BUCKET, 1));
        kitItems.add(new ItemStack(Material.WATER_BUCKET, 1));
        kitItems.add(new ItemStack(Material.ARROW, 1));
        kitItems.add(new ItemStack(Material.DIAMOND_HELMET, 1));
        kitItems.add(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
        kitItems.add(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
        kitItems.add(new ItemStack(Material.DIAMOND_BOOTS, 1));

//        ItemStack firePot = new ItemStack(Material.POTION, 1);
//        PotionEffect fireRes = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 9600, 0);
//        PotionMeta fireResMeta = (PotionMeta) firePot.getItemMeta();
//        fireResMeta.addCustomEffect(fireRes, false);
//        fireResMeta.setColor(Color.fromRGB(228, 154, 58));
//        fireResMeta.setDisplayName(ChatColor.WHITE + "Potion of Fire Resistance");
//        firePot.setItemMeta(fireResMeta);

        ItemStack speedPot = new ItemStack(Material.POTION, 1);
        PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 4800, 0);
        PotionMeta speedMeta = (PotionMeta) speedPot.getItemMeta();
        speedMeta.addCustomEffect(speed, false);
        speedMeta.setColor(Color.fromRGB(124, 175, 198));
        speedMeta.setDisplayName(ChatColor.WHITE + "Potion of Swiftness");
        speedPot.setItemMeta(speedMeta);

        for (int i = 0; i <= 8; i++){
            if (i < 4){
//                kitItems.add(new ItemStack(firePot));
            } else {
                kitItems.add(new ItemStack(speedPot));
            }
        }

        int count = 0;
        for(ItemStack item : kitItems){
            applyMaxEnchants(item, count == 5 || count == 9);
            count++;
        }
    }

    public void giveKit(Player p){
        p.getInventory().clear();
        for(ItemStack item : kitItems){
            p.getInventory().addItem(item);
        }
    }

    private void applyMaxEnchants(ItemStack item, boolean knockerBacker){
        switch (item.getType()){
            case DIAMOND_HELMET:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_LEGGINGS:
            case DIAMOND_BOOTS:
            case NETHERITE_SWORD:
            case NETHERITE_AXE:
            case BOW:
                item.addEnchantment(Enchantment.DURABILITY, 3);
                break;
        }
        switch (item.getType()){
            case DIAMOND_HELMET:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_LEGGINGS:
            case DIAMOND_BOOTS:
                switch (item.getType()){
                    case DIAMOND_HELMET:
                        item.addEnchantment(Enchantment.WATER_WORKER, 1);
                        item.addEnchantment(Enchantment.OXYGEN, 3);
                        break;
                    case DIAMOND_BOOTS:
                        item.addEnchantment(Enchantment.PROTECTION_FALL, 4);
                        item.addEnchantment(Enchantment.DEPTH_STRIDER, 3);
                        break;
                }
                item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                break;
            case NETHERITE_SWORD:
            case NETHERITE_AXE:
                item.addEnchantment(Enchantment.DAMAGE_ALL, 5);
                if (item.getType().equals(Material.NETHERITE_SWORD)) {
                    item.addEnchantment(Enchantment.FIRE_ASPECT, 2);
                }
                if (knockerBacker){
                    item.addEnchantment(Enchantment.KNOCKBACK, 2);
                }
                break;
            case BOW:
                item.addEnchantment(Enchantment.ARROW_DAMAGE, 5);
                item.addEnchantment(Enchantment.ARROW_INFINITE, 1);
                item.addEnchantment(Enchantment.ARROW_FIRE, 1);
                if (knockerBacker){
                    item.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
                }
                break;
        }
    }

    public ChatColor getTeamColor(Player target){
        for(Player p : defenders){
            if (target.getUniqueId() == p.getUniqueId()){
                return ChatColor.BLUE;
            }
        }
        for(Player p : attackers){
            if (target.getUniqueId() == p.getUniqueId()){
                return ChatColor.RED;
            }
        }
        return ChatColor.GRAY;
    }
}
