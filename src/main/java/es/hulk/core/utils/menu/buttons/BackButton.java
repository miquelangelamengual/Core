package es.hulk.core.utils.menu.buttons;

import es.hulk.core.utils.aquamenu.menu.SwitchableMenu;
import es.hulk.core.utils.menu.Button;
import es.hulk.core.utils.menu.Menu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BackButton extends Button {

  private Menu back;
  private SwitchableMenu backSwitchable;

  @Override
  public ItemStack getButtonItem(Player player) {
    ItemStack itemStack = new ItemStack(Material.BED);
    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.setDisplayName(ChatColor.RED + "Go back");
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  @Override
  public void clicked(Player player, int i, ClickType clickType, int hb) {
    Button.playNeutral(player);
    this.back.openMenu(player);
    this.backSwitchable.open(player);
  }

  public BackButton(Menu back) {
    this.back = back;
  }

  public BackButton(SwitchableMenu backSwitchable) {
    this.backSwitchable = backSwitchable;
  }
}
