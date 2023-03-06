package me.valdoveste.erase_uuid_on_death.commands;

import me.valdoveste.erase_uuid_on_death.Erase_UUID_On_Death;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListPlayersKeepLevelCommand implements CommandExecutor {

    Erase_UUID_On_Death plugin;
    public ListPlayersKeepLevelCommand(Erase_UUID_On_Death plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player playerSender = (Player) sender;
        if(args.length > 0){
            playerSender.sendMessage(ChatColor.RED + "Este comando não aceita argumentos/jogadores.");
            return false;
        }

        playerSender.sendMessage(ChatColor.YELLOW + "Os caféComLeite: " + ChatColor.AQUA + Erase_UUID_On_Death.playerKeepLevelOnDeath.toString());

        return true;
    }
}
