package net.ess3.xmpp;

import net.ess3.api.IEssentials;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

//TODO: port 2.9 changes and verify thread safety.
class EssentialsXMPPPlayerListener implements Listener
{
	private final IEssentials ess;

	EssentialsXMPPPlayerListener(final IEssentials ess)
	{
		super();
		this.ess = ess;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(final PlayerJoinEvent event)
	{
		sendMessageToSpyUsers("Player " + event.getPlayer().getDisplayName() + " joined the game");
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerChat(final AsyncPlayerChatEvent event)
	{
		sendMessageToSpyUsers(String.format(event.getFormat(), event.getPlayer().getDisplayName(), event.getMessage()));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(final PlayerQuitEvent event)
	{
		sendMessageToSpyUsers("Player " + event.getPlayer().getDisplayName() + " left the game");
	}

	private void sendMessageToSpyUsers(final String message)
	{
		try
		{
			for (String address : EssentialsXMPP.getInstance().getSpyUsers())
			{
				EssentialsXMPP.getInstance().sendMessage(address, message);
			}
		}
		catch (Exception ex)
		{
			// Ignore exceptions
		}
	}
}
