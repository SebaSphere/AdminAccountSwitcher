package dev.sebastianb.adminaccountswitcher.listener;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.GameProfileRequestEvent;
import com.velocitypowered.api.util.GameProfile;
import dev.sebastianb.adminaccountswitcher.AdminAccountSwitcher;
import dev.sebastianb.adminaccountswitcher.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameProfileRequest {

    AdminAccountSwitcher plugin;

    public GameProfileRequest(AdminAccountSwitcher adminAccountSwitcher) {
        this.plugin = adminAccountSwitcher;
    }

    @Subscribe
    public void onGameProfileRequest(GameProfileRequestEvent event) {

        String virtualHostStr = event.getConnection().getVirtualHost().map(InetSocketAddress::getHostString)
                .orElse("")
                .toLowerCase(Locale.ROOT);
        String extractedUsername = Utils.getPlayerNameFromSubdomain(virtualHostStr);
        // TODO: replace with a command based admin system for who can use this
        if (extractedUsername != null) {
            if (event.isOnlineMode()) {
                // TODO: Clean this up by separating the logic into a class
                String playerOnlineUniqueID = getOnlineUuid(extractedUsername.toLowerCase())
                        .flatMap(jsonObject -> parseJson(jsonObject.get("id"), String.class)).orElse(null);
                String playerOnlinePlayerName = getOnlineUuid(extractedUsername.toLowerCase())
                        .flatMap(jsonObject -> parseJson(jsonObject.get("name"), String.class)).orElse(null);

                GameProfile profile = new GameProfile(playerOnlineUniqueID, playerOnlinePlayerName, ImmutableList.of());
                event.setGameProfile(profile);
                AdminAccountSwitcher.logger.info(event.getUsername() + " is logging in as the user " + playerOnlinePlayerName);
            } else {
                event.setGameProfile(GameProfile.forOfflinePlayer(extractedUsername));
                AdminAccountSwitcher.logger.info(event.getUsername() + " is logging in as the user " + extractedUsername);
            }
        }

    }

    private Optional<JsonObject> getOnlineUuid(String username) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
            URLConnection urlConnection = url.openConnection();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                return parseJson(bufferedReader.lines().collect(Collectors.joining()), JsonObject.class);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    private <T> Optional<T> parseJson(String json, Class<T> type) {
        try {
            return parseJson(JsonParser.parseString(json), type);
        } catch (RuntimeException ex) {
            return Optional.empty();
        }
    }

    private <T> Optional<T> parseJson(JsonElement jsonElement, Class<T> type) {
        try {
            return Optional.of(new Gson().fromJson(jsonElement, type));
        } catch (RuntimeException ex) {
            return Optional.empty();
        }
    }

}
