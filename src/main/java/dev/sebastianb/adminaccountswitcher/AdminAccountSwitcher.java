package dev.sebastianb.adminaccountswitcher;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.sebastianb.adminaccountswitcher.command.AdminPlayerLoginManager;
import dev.sebastianb.adminaccountswitcher.listener.GameProfileRequest;

import java.util.logging.Logger;

@Plugin(
        id = "adminaccountswitcher",
        name = "AdminAccountSwitcher",
        version = BuildConstants.VERSION,
        description = "Login to any account as a server admin from the subdomain",
        authors = {"SebaSphere"}
)
public class AdminAccountSwitcher {

    public static Logger logger = Logger.getLogger("AdminAccountSwitcher");

    private final ProxyServer proxy;

    @Inject
    public AdminAccountSwitcher(ProxyServer proxy) {
        this.proxy = proxy;
    }


    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        // Init listener
        proxy.getEventManager().register(this, new GameProfileRequest(this));
        proxy.getCommandManager().register("adminlogin", new AdminPlayerLoginManager(), "al");

    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {

    }

}
