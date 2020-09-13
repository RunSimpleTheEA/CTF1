package captureTheFlag.events;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

import captureTheFlag.main.Main;
import captureTheFlag.utils.ItemBuilder;

public class EntityShootBowListener implements Listener {
	private Main plugin;
	
	public EntityShootBowListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityShootBowEvent(EntityShootBowEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(plugin.game.searchForPlayer(player)) {
				if(!event.getBow().getType().equals(Material.CROSSBOW)) {
					event.setCancelled(true);
					player.sendMessage(plugin.PREFIX+"Du kannst nur einen "+plugin.PREFIX+"X-BOW benutzten um Pfeile zu verschießen");
				} else {
					player.getInventory().setItem(8, new ItemBuilder(Material.ARROW).setAmount(1).setName(plugin.PREFIX+"ARROW").setLore("The arrow of real infinity!", "Let your enemys die into a storm of arrows.").addEnchantment(Enchantment.ARROW_INFINITE, 10).addEnchantment(Enchantment.ARROW_FIRE, 5).addEnchantment(Enchantment.LOYALTY, 10).build());
					//player.getItemInHand().setDurability((short) 326);
				}
			} else {
				if(!player.isOp()) {
					event.setCancelled(true);
					player.sendMessage(plugin.PREFIX+"Du hast nicht die Berechtigung einen Bogen zu benutzten.");
				}
			}
		}
	}
}
