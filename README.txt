Create a file in resources/config.json for configuration.
Config is as follows:

{
	token: String,
	prefix: String,
	guildID: Long,
	infectedID: Long,
	deadID: Long,
	curedID: Long,
	susceptibleIDs: [Long],
	privilegedIDs: [Long],
	killHours: Long,
	infectMinutes: Long
}

Note that if token is not found, it is read from env var $TOKEN
