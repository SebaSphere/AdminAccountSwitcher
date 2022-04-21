package dev.sebastianb.adminaccountswitcher.database;

import com.velocitypowered.api.util.UuidUtils;
import dev.sebastianb.adminaccountswitcher.Utils;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

public class AdminPlayerLoginList {

    private static HashSet<String> playerUUIDs = new HashSet<>();

    public static void addPlayer(String playerName) {
        AtomicBoolean isOfflineUUID = new AtomicBoolean(true);
        Utils.getOnlineUuid(playerName.toLowerCase())
                .flatMap(jsonObject -> Utils.parseJson(jsonObject.get("id"), String.class))
                .ifPresent(playerUniqueID -> {
                    playerUUIDs.add(playerUniqueID);
                    isOfflineUUID.set(false); // if this fails, will move on to if offline.
                    // FIXME: Probably gets the "real" player when logging in on offline server.
                });
        if (isOfflineUUID.get()) {
            playerUUIDs.add(UuidUtils.generateOfflinePlayerUuid(playerName).toString());
        }
    }

    public static void removePlayer(String playerName) {
        AtomicBoolean isOfflineUUID = new AtomicBoolean(true);
        Utils.getOnlineUuid(playerName.toLowerCase())
                .flatMap(jsonObject -> Utils.parseJson(jsonObject.get("id"), String.class))
                .ifPresent(playerUniqueID -> {
                    playerUUIDs.remove(playerUniqueID);
                    isOfflineUUID.set(false); // if this fails, will move on to if offline.
                    // FIXME: Probably gets the "real" player when logging in on offline server.
                });
        if (isOfflineUUID.get()) {
            playerUUIDs.remove(UuidUtils.generateOfflinePlayerUuid(playerName).toString());
        }
    }

    public static HashSet<String> getPlayerUUIDs() {
        return playerUUIDs;
    }

    public static HashSet<String> getPlayerNames() {
        HashSet<String> playerNames = new HashSet<>();
        for (String playerUUID : playerUUIDs) {
            Utils.getPlayerNameFromUUID(playerUUID)
                    .flatMap(jsonObject -> Utils.parseJson(jsonObject.get("name"), String.class))
                    .ifPresent(playerNames::add);
        }
        return playerNames;
    }

}
