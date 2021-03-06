package captureTheFlag.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import captureTheFlag.main.Main;
import captureTheFlag.utils.TeamColor;

public class CTFCommands implements CommandExecutor {
	private Main plugin;
	
	private EndGameCommand endGameCommand;
	private SetFlagPointCommand setFlagPointCommand;
	private SetSpawnPointCommand setSpawnPointCommand;
	private SetSpawnCommand setSpawnCommand;
	private SelectTeamCommand selectTeamCommand;
	private SpawnCommand spawnCommand;
	private JoinTeamCommand joinTeamCommand;
	private RemoveFlagPointCommand removeFlagPointCommand;
	private GetKitCommand getKitCommand;
	private StartGameCommand startGameCommand;
	
	
	public CTFCommands(Main plugin) {
		this.plugin = plugin;
		endGameCommand = new EndGameCommand(plugin);
		setFlagPointCommand = new SetFlagPointCommand(plugin);
		setSpawnPointCommand = new SetSpawnPointCommand(plugin);
		setSpawnCommand = new SetSpawnCommand(plugin);
		selectTeamCommand = new SelectTeamCommand();
		spawnCommand = new SpawnCommand(plugin);
		joinTeamCommand = new JoinTeamCommand(plugin);
		removeFlagPointCommand = new RemoveFlagPointCommand(plugin);
		getKitCommand = new GetKitCommand(plugin);
		startGameCommand = new StartGameCommand(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length>0) {
			//Player Commands
			if(sender instanceof Player) {
				Player player = (Player) sender;
				//Op Commands
				if(player.isOp()) {
					//setFlagPoint Command
					if(args[0].equalsIgnoreCase("setFlagPoint")) {
						if(args.length==2) {
							try {
								TeamColor color = TeamColor.valueOf(args[1].toUpperCase());
								setFlagPointCommand.executeSetFlagPointCommand(sender, command, label, args, color, player);
								player.sendMessage(Main.PREFIX + "Du hast erfolgreich den FlagPoint f�r Team " + color.getColorCode() + " gesetzt!" ); return(true);
							} catch(Exception e) {
								player.sendMessage(Main.PREFIX + "Bitte nutze /CTF setFlagPoint �9blue�r/�cred�r"); return(false);
							}
						} else
							player.sendMessage(Main.PREFIX + "Bitte nutze /CTF setFlagPoint [color]"); return(false);
					//setSpawnPoint Command
					} else if(args[0].equalsIgnoreCase("setSpawnPoint")) {
						if(args.length==2) {
							try {
								TeamColor color = TeamColor.valueOf(args[1].toUpperCase());
								setSpawnPointCommand.executeSetSpawnPointCommand(sender, command, label, args, color, player);
								player.sendMessage(Main.PREFIX + "Du hast erfolgreich den SpawnPoint f�r Team " + color.getColorCode() + "�r gesetzt!" ); return(true);
							} catch(Exception e) {
								player.sendMessage(Main.PREFIX + "Bitte nutze /CTF setSpawnPoint �9blue�r/�cred�r"); return(false);
							}
						} else
							player.sendMessage(Main.PREFIX + "Bitte nutze /CTF setSpawnPoint [color]"); return(false);
					//setSpawn Command
					} else if(args[0].equalsIgnoreCase("setSpawn")) {
						if(args.length==1) {
							setSpawnCommand.executeSetSpawnCommand(player);
							player.sendMessage(Main.PREFIX + "Du hast erfolgreich den Spawn gesetzt.");
						} else
							player.sendMessage(Main.PREFIX + "Bitte nutze /CTF setSpawn"); return(false);
					//startGame Command
					} else if(args[0].equalsIgnoreCase("startGame")) {
						if(args.length==1) {
							startGameCommand.executeStartGameCommand();
							player.sendMessage(Main.PREFIX + "Du hast erfolgreich das Game gestartet."); return(true);
						} else
							player.sendMessage(Main.PREFIX + "Bitte nutze /CTF startGame"); return(false);
					//endGame Command
					} else if(args[0].equalsIgnoreCase("endGame")) {
						if(args.length==1) {
							endGameCommand.executeEndGameCommand();
							player.sendMessage(Main.PREFIX + "Du hast erfolgreich das Game beendet."); return(true);
						} else
							player.sendMessage(Main.PREFIX + "Bitte nutze /CTF endGame"); return(false);
					//empty Command
					} else if(args[0].equalsIgnoreCase("")) {
						if(args.length==2) {
							//TODO
						} else
							player.sendMessage(Main.PREFIX + "Bitte nutze /CTF empty"); return(false);
					}
				//Normal Player Commands
				}
				//joinTeam Command
				if(args[0].equalsIgnoreCase("joinTeam")) {
					if(args.length==2) {
						try {
							TeamColor color = TeamColor.valueOf(args[1].toUpperCase());
							joinTeamCommand.executeJoinTeamCommand(player, color);
							return(true);
						} catch(Exception e) {
							player.sendMessage(Main.PREFIX + "Bitte nutze /CTF joinTeam [color]"); return(false);
						}
					} else
						player.sendMessage(Main.PREFIX + "Bitte nutze /CTF [color]"); return(false);
				//getKit Command
				} else if(args[0].equalsIgnoreCase("getKit")) {
					if(args.length==1) {
						if(plugin.game.searchForPlayer(player)) {
							getKitCommand.executeGetKitCommand(player);
							player.sendMessage("Du hast dein CTF-Kit erhalten!"); return(true);
						} else {
							player.sendMessage(Main.PREFIX + "Nur CTF-Spieler k�nnen diesen Befehl nutzen. Nutze /CTF joinTeam um einem Team deiner wahl beizutreten."); return(false);
						}
					} else
						player.sendMessage(Main.PREFIX + "Bitte nutze /CTF getKit"); return(false);
				//spawn Command
				} else if(args[0].equalsIgnoreCase("spawn")) {
					if(args.length==1) {
						spawnCommand.executeSpawnCommand(player);
					} else
						player.sendMessage(Main.PREFIX + "Bitte nutze /CTF spawn"); return(false);
				//selectTeam Command
				} else if(args[0].equalsIgnoreCase("selectTeam")) {
					if(args.length==1) {
						selectTeamCommand.executeSelectTeamCommand(player);
						player.sendMessage(Main.DEBUGPREFIX + "Bitte w�hle dein Team aus."); return(true);
					} else
						player.sendMessage(Main.PREFIX + "Bitte nutze /CTF selectTeam"); return(false);
				//empty Command
				} else if(args[0].equalsIgnoreCase("")) {
					if(args.length==2) {
						//TODO
					} else
						player.sendMessage(Main.PREFIX + "Bitte nutze /CTF empty"); return(false);
				}
			//Console only Commands
			} else {
				//empty Command
				if(args[0].equalsIgnoreCase("")) {
					if(args.length==2) {
						//TODO
					} else
						sender.sendMessage(Main.PREFIX + "Bitte nutze /CTF empty"); return(false);
				}
			}
			//removeFlagPoint Command
			if(args[0].equalsIgnoreCase("removeFlagPoint")) {
				if(args.length==2) {
					try {
						int index = Integer.parseInt(args[1]);
						if(plugin.game.getFlagPoints().size()>=index && index>0) {
							removeFlagPointCommand.executeRemoveFlagPointCommand(sender, index);
							sender.sendMessage(Main.PREFIX + "Du hast erfolgreich den "+index+"ten FlagPoint removed."); return(true);
						} else {
							sender.sendMessage(Main.PREFIX + "Bitte w�hle einen der "+plugin.game.getFlagPoints().size()+" vorhanden FlagPoints aus."); return(false);
						}
					} catch(Exception e) {
						sender.sendMessage(Main.PREFIX + "Bitte nutze /CTF removeFlagPoint [index]"); return(false);
					}
				} else
					sender.sendMessage(Main.PREFIX + "Bitte nutze /CTF removeFlagPoint [index]"); return(false);
			//spawnFlag Command
			} else if(args[0].equalsIgnoreCase("spawnFlag")) {
				if(args.length==2) {
					try {
						int index = Integer.parseInt(args[1]);
						if(plugin.game.getFlagPoints().size()>=index && index>0) {
							SpawnFlagCommand.executeSpawnFlagCommand(plugin.game.getFlagPoints().get(index-1));
							sender.sendMessage(Main.PREFIX + "Du hast erfolgreich eine Flag auf dem "+index+"ten FlagPoint erzeugt."); return(true);
						} else {
							sender.sendMessage(Main.PREFIX + "Bitte w�hle einen der "+plugin.game.getFlagPoints().size()+" vorhanden FlagPoints aus."); return(false);
						}
					} catch(Exception e) {
						sender.sendMessage(Main.PREFIX + "Bitte nutze /CTF spawnFlag [index]"); return(false);
					}
				} else
				sender.sendMessage(Main.PREFIX + "Bitte nutze /CTF spawnFlag [index]"); return(false);
			//removeFlag Command
			} else if(args[0].equalsIgnoreCase("removeFlag")) {
				if(args.length==2) {
					try {
						int index = Integer.parseInt(args[1]);
						if(plugin.game.getFlagPoints().size()>=index && index>0) {
							RemoveFlagCommand.executeRemoveFlagCommand(plugin.game.getFlagPoints().get(index-1));
							sender.sendMessage(Main.PREFIX + "Du hast erfolgreich die Flag auf dem "+index+"ten FlagPoint entfernt."); return(true);
						} else {
							sender.sendMessage(Main.PREFIX + "Bitte w�hle einen der "+plugin.game.getFlagPoints().size()+" vorhanden FlagPoints aus."); return(false);
						}
					} catch(Exception e) {
						sender.sendMessage(Main.PREFIX + "Bitte nutze /CTF removeFlag [index]"); return(false);
					}
				} else
				sender.sendMessage(Main.PREFIX + "Bitte nutze /CTF removeFlag [index]"); return(false);
			//empty Command
			} else if(args[0].equalsIgnoreCase("")) {
				if(args.length==2) {
					//TODO
				} else
				sender.sendMessage(Main.PREFIX + "Bitte nutze /CTF empty"); return(false);
			}
		}
		sender.sendMessage(Main.PREFIX + "Benutze /CTF help f�r mehr Information."); return(false);
	}
	
}
