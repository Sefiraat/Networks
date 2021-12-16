package io.github.sefiraat.networks.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class NetworksMain implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player player) {
            if (player.isOp() && args.length >= 1) {
                if (args[0].equalsIgnoreCase("tp")) {

                }
            }
        }
        return true;
    }

}
