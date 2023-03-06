package me.valdoveste.erase_uuid_on_death.commands;

import me.valdoveste.erase_uuid_on_death.Erase_UUID_On_Death;
import me.valdoveste.erase_uuid_on_death.files.CustomConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemovePlayerKeepLevelCommand implements CommandExecutor {
    Erase_UUID_On_Death plugin;

    public RemovePlayerKeepLevelCommand(Erase_UUID_On_Death plugin) { this.plugin = plugin; }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("eremove")) {
            if(sender instanceof Player && sender.isOp()){
                Player playerSender = (Player) sender;
                if(args.length == 0){
                    playerSender.sendMessage(ChatColor.RED + "Você precisa informar o nome de um jogador.");
                    return false;
                }

                if(args.length == 1){
                    Erase_UUID_On_Death.playerKeepLevelOnDeath.remove(args[0]);
                    CustomConfig.addPlayer(Erase_UUID_On_Death.playerKeepLevelOnDeath);
                    playerSender.sendMessage(ChatColor.AQUA + args[0] + ChatColor.YELLOW + " Foi removito dos caféComLeite.");
                }

                if(args.length >= 2) {
                    playerSender.sendMessage(ChatColor.RED + "Este comando não aceita mais de um argumento/jogador.");
                    return false;
                }
            }
        }
        return true;
    }
}
