package me.valdoveste.erase_uuid_on_death;

import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.skills.Skill;
import com.archyx.aureliumskills.skills.Skills;
import me.valdoveste.erase_uuid_on_death.commands.AddPlayerKeepLevelCommand;
import me.valdoveste.erase_uuid_on_death.commands.ListPlayersKeepLevelCommand;
import me.valdoveste.erase_uuid_on_death.commands.RemovePlayerKeepLevelCommand;
import me.valdoveste.erase_uuid_on_death.files.CustomConfig;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Erase_UUID_On_Death extends JavaPlugin implements Listener {
    FileConfiguration config = getConfig();
    public static List<String> playerKeepLevelOnDeath = new ArrayList<>();

    private List<Skills> skillsList = new ArrayList<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("eadd")).setExecutor(new AddPlayerKeepLevelCommand(this));
        Objects.requireNonNull(getCommand("eremove")).setExecutor(new RemovePlayerKeepLevelCommand(this));
        Objects.requireNonNull(getCommand("elist")).setExecutor(new ListPlayersKeepLevelCommand(this));

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        CustomConfig.setupFile();
        CustomConfig.getFile().addDefault("PlayersKeepLevel", "");
        CustomConfig.getFile().options().copyDefaults(true);
        CustomConfig.saveFile();

        if (CustomConfig.getFile().get("PlayersKeepLevel") != "")
            playerKeepLevelOnDeath = (List<String>) CustomConfig.getFile().getList("PlayersKeepLevel");
    }

    @Override
    public void onDisable() {
        CustomConfig.saveFile();
        saveDefaultConfig();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        skillsList = Skills.getOrderedValues();

        assert player != null;
        if (!playerKeepLevelOnDeath.contains(player.getName())) {
            getServer().dispatchCommand(getServer().getConsoleSender(), "sk skill reset " + player.getName());
            for (Skill skill : skillsList)
                AureliumAPI.addXpRaw(player, skill, (AureliumAPI.getXp(player, skill) * config.getDouble("level_reset.difficult.I") / -100));
        }else{
            for (Skill skill : skillsList) {
                if(AureliumAPI.getSkillLevel(player, skill) > 1)
                    getServer().dispatchCommand(getServer().getConsoleSender(), "sk skill setlevel " + Objects.requireNonNull(player.getPlayer()).getName() + " " + skill + " " + (AureliumAPI.getSkillLevel(player, skill) * config.getDouble("level_reset.difficult.II") / 100));

                AureliumAPI.addXpRaw(player, skill, (AureliumAPI.getXp(player, skill) * config.getDouble("level_reset.difficult.II") / -100));
            }
        }
        player.sendTitle(ChatColor.GREEN + "Skill Issue", ChatColor.YELLOW + "Perdeu os _ -lêVéis- _ na parrada " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "g" + ChatColor.AQUA + ChatColor.BOLD + "a" + ChatColor.GOLD + ChatColor.BOLD + "y", 20, 100, 20);
    }
}
