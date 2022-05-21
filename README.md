# AdminAccountSwitcher
A plugin that allows you to login to any valid account from the subdomain.

# Usage and installation
![image](https://user-images.githubusercontent.com/27737877/164373408-9f94c33a-732f-4cfe-a3af-3e3c5f60e057.png)

Before installing the plugin, make sure you have a DNS record on your domain with a wildcard. You can find more information about this here: https://en.wikipedia.org/wiki/Wildcard_DNS_record

After a wildcard has been setup and you've added your player name as a admin, you'll be able to join the server using the subdomain.

You will need to have the prefix `player-PLAYER_NAME` before your domain. For example, if you had your server connected to the domain `sebastianb.dev` and wanted to login as `SebaSphere`, you will need to enter `player-SebaSphere.sebastianb.dev` as the server you're connecting to.

# Commands
- `/al | /adminlogin` : Base command, does nothing
- `/al add (player_name)` : Allows the added player to login to any account from the subdomain
- `/al remove (player_name) : Removes a specific player from being able to login to any account
- `/al list` : Displays a list of all players that have access to join with other accounts.

# W.I.P.
Currently, there's some bugs I need to fix as of the time of writing. You can find a list of things to be aware about below:
- `/al list` resets on server restart. I need to properly save this data.
- When the server has online mode disabled, this plugin currently won't work. I will investigate this later, however not a priority.

Pull requests are always welcome! If you want to PR any specific feature, please contact me at my Discord `SebaSphere#0001`

I am not responsible for any misuse of this plugin for malicious intents. Please don't abuse this plugin.
