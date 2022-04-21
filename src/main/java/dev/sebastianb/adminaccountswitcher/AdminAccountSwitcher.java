package dev.sebastianb.adminaccountswitcher;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.sebastianb.adminaccountswitcher.listener.GameProfileRequest;
import org.slf4j.Logger;

@Plugin(
        id = "adminaccountswitcher",
        name = "AdminAccountSwitcher",
        version = BuildConstants.VERSION,
        description = "Login to any account as a server admin from the subdomain",
        authors = {"SebaSphere"}
)
public class AdminAccountSwitcher {

    @Inject
    public static Logger logger;

    private final ProxyServer proxy;

    @Inject
    public AdminAccountSwitcher(ProxyServer proxy) {
        this.proxy = proxy;
    }


    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        // Init listener
        proxy.getEventManager().register(this, new GameProfileRequest(this));
    }

}
