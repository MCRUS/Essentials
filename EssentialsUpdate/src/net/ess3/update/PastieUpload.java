package net.ess3.update;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PastieUpload
{
	private final PostToUrl connection;
	private final Pattern pattern = Pattern.compile("(?s).*\\?key=([a-z0-9]+).*");

	public PastieUpload() throws MalformedURLException
	{
		connection = new PostToUrl(new URL("http://pastie.org/pastes"));
	}

	public String send(final String data) throws IOException
	{
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("paste[parser_id]", "19");
		map.put("paste[authorization]", "burger");
		map.put("paste[body]", data);
		map.put("paste[restricted]", "1");
		final String html = connection.send(map);
		final Matcher matcher = pattern.matcher(html);
		if (matcher.matches())
		{
			final String key = matcher.group(1);
			return "http://pastie.org/private/" + key;
		}
		else
		{
			throw new IOException("Failed to upload to pastie.org");
		}
	}
}
