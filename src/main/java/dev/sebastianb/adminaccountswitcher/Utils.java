package dev.sebastianb.adminaccountswitcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
}
