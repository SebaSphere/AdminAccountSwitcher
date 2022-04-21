package dev.sebastianb.adminaccountswitcher.command;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import dev.sebastianb.adminaccountswitcher.Utils;
import dev.sebastianb.adminaccountswitcher.database.AdminPlayerLoginList;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;

public class AdminPlayerLoginManager implements SimpleCommand {


    @Override
    public void execute(Invocation invocation) {
        CommandSource commandSource = invocation.source();
        String[] strings = invocation.arguments();
        if (strings.length == 0) {
            commandSource.sendMessage(Component.text("Please read the help page for more information on usage"));
        }
        if (strings.length >= 1) {
            switch (strings[0]) {
                case "add":
                    if (strings.length == 2) {
                        AdminPlayerLoginList.addPlayer(strings[1]);
                        commandSource.sendMessage(Component.text("Added player " + strings[1] + " to the list of players that can login as anyone"));
                    } else {
                        commandSource.sendMessage(Component.text("Please read the help page for more information on usage"));
                    }
                    break;
                case "remove":
                    if (strings.length == 2) {
                        AdminPlayerLoginList.removePlayer(strings[1]);
                        commandSource.sendMessage(Component.text("Removed player " + strings[1] + " from the list of players that can login as anyone"));
                    } else {
                        commandSource.sendMessage(Component.text("Please read the help page for more information on usage"));
                    }
                    break;
                case "list":
                    commandSource.sendMessage(Component.text("Players that can login as an any account: " + AdminPlayerLoginList.getPlayerNames()));
            }
        }

    }
}
