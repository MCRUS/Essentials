package com.earth2me.essentials.metrics;

import com.earth2me.essentials.User;
import java.util.logging.Level;
import net.ess3.api.IEssentials;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class MetricsListener implements Listener
{
	private final transient Server server;
	private final transient IEssentials ess;
	private final transient MetricsStarter starter;

	public MetricsListener(final IEssentials parent, final MetricsStarter starter)
	{
		this.ess = parent;
		this.server = parent.getServer();
		this.starter = starter;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(final PlayerJoinEvent event)
	{
		final User player = ess.getUser(event.getPlayer());
		if (ess.getSettings().isMetricsEnabled() == false && (player.isAuthorized("essentials.essentials") || player.isAuthorized("bukkit.broadcast.admin")))
		{
			player.sendMessage("PluginMetrics собирает минимальную статистическую информацию. Запуск примерно через 5 минут.");
			player.sendMessage("Для запуска opt out, используйте /essentials opt-out");
			ess.getLogger().log(Level.INFO, "[Metrics] Администратор вошел - Начало 5 минутного opt-out периода.");
			ess.getSettings().setMetricsEnabled(true);
			ess.runTaskLaterAsynchronously(starter, 5 * 1200);
		}
	}
}
