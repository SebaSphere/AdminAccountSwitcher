package dev.sebastianb.adminaccountswitcher;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Utils {


    /**
     * Gets the username of a player from an entered subdomain.
     *
     * Use https://regex101.com/ to test the regex.
     *
     * @param subdomain the subdomain of the player
     * @return if the subdomain is valid, the username of the player, otherwise null
     */
    public static String getPlayerNameFromSubdomain(String subdomain) {
        final String regex = "(?<=^player-)(.*?)(?=\\.)";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(subdomain);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null; // invalid subdomain, do checking on method call
        }
    }

    public static Optional<JsonObject> getPlayerNameFromUUID(String UUID) {
        try {
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + UUID);
            URLConnection urlConnection = url.openConnection();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                return parseJson(bufferedReader.lines().collect(Collectors.joining()), JsonObject.class);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<JsonObject> getOnlineUuid(String username) {
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

    public static <T> Optional<T> parseJson(String json, Class<T> type) {
        try {
            return parseJson(JsonParser.parseString(json), type);
        } catch (RuntimeException ex) {
            return Optional.empty();
        }
    }

    public static <T> Optional<T> parseJson(JsonElement jsonElement, Class<T> type) {
        try {
            return Optional.of(new Gson().fromJson(jsonElement, type));
        } catch (RuntimeException ex) {
            return Optional.empty();
        }
    }

}
