package es.hulk.core.utils.menu;

import es.hulk.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class MenuListener implements Listener {

    public MenuListener() {
        Bukkit.getPluginManager().registerEvents(this, Core.getInstance());
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onButtonPress(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Menu openInventory = (Menu) Menu.currentlyOpenedMenus.get(player.getName());
        if (openInventory != null) {
            if (event.getSlot() != event.getRawSlot()) {
                if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
                    event.setCancelled(true);
                }

                return;
            }

            if (openInventory.getButtons().containsKey(event.getSlot())) {
                Button button = (Button) openInventory.getButtons().get(event.getSlot());
                boolean cancel = button.shouldCancel(player, event.getSlot(), event.getClick());
                if (!cancel && (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT)) {
                    event.setCancelled(true);
                    if (event.getCurrentItem() != null) {
                        player.getInventory().addItem(new ItemStack[]{event.getCurrentItem()});
                    }
                } else {
                    event.setCancelled(cancel);
                }

                button.clicked(player, event.getSlot(), event.getClick(), event.getHotbarButton());
                if (Menu.currentlyOpenedMenus.containsKey(player.getName())) {
                    Menu newInventory = (Menu) Menu.currentlyOpenedMenus.get(player.getName());
                    if (newInventory == openInventory) {
                        boolean buttonUpdate = button.shouldUpdate(player, event.getSlot(), event.getClick());
                        if (buttonUpdate) {
                            openInventory.setClosedByMenu(true);
                            newInventory.openMenu(player);
                        }
                    }
                } else if (button.shouldUpdate(player, event.getSlot(), event.getClick())) {
                    openInventory.setClosedByMenu(true);
                    openInventory.openMenu(player);
                }

                if (event.isCancelled()) {
                    Bukkit.getScheduler().runTaskLater(Core.getInstance(), player::updateInventory, 1L);
                }
            } else {
                if (event.getCurrentItem() != null) {
                    event.setCancelled(true);
                }

                if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onClose(InventoryCloseEvent event) {
        if (ChatColor.stripColor(event.getInventory().getTitle()).equalsIgnoreCase("Kit Editor") || ChatColor.stripColor(event.getInventory().getTitle()).contains("Editing")) {
            Player player = (Player) event.getPlayer();
            Menu openMenu = Menu.currentlyOpenedMenus.get(player.getName());
            if (openMenu != null) {
                openMenu.onClose(player);
                Menu.currentlyOpenedMenus.remove(player.getName());
            }
        }
    }
}
