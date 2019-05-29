package fr.maxlego08.list.server;

public class ServerInfos {

	private final ServerVersion version;
	private final ServerPlayers players;

	public ServerInfos(ServerVersion version, ServerPlayers players) {
		super();
		this.version = version;
		this.players = players;
	}

	/**
	 * @return the version
	 */
	public ServerVersion getVersion() {
		return version;
	}

	/**
	 * @return the players
	 */
	public ServerPlayers getPlayers() {
		return players;
	}

	public static class ServerVersion
	{
		private final String name;
		private final String protocol;

		public ServerVersion(String name, String protocol)
		{
			this.name = name;
			this.protocol = protocol;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the protocol
		 */
		public String getProtocol() {
			return protocol;
		}
		
	}

	public static class ServerPlayers
	{
		private final int online;
		private final int max;

		public ServerPlayers(int online, int max)
		{
			this.online = online;
			this.max = max;
		}

		/**
		 * @return the online
		 */
		public int getOnline() {
			return online;
		}

		/**
		 * @return the max
		 */
		public int getMax() {
			return max;
		}
		
	}
	
}
