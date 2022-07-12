package es.hulk.core.utils.aquamenu.slots.pages;

import lombok.RequiredArgsConstructor;
import es.hulk.core.utils.CC;
import es.hulk.core.utils.ItemBuilder;
import es.hulk.core.utils.aquamenu.menu.SwitchableMenu;
import es.hulk.core.utils.aquamenu.slots.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class NextPageSlot extends Slot {

  private final SwitchableMenu switchableMenu;

  @Override
  public ItemStack getItem(Player player) {
    ItemBuilder item = new ItemBuilder(Material.HOPPER);
    item.name("&6Next page");
    item.addLoreLine(" ");

    if (this.switchableMenu.getPage() < this.switchableMenu.getPages(player)) {
      item.addLoreLine("&fClick to head");
      item.addLoreLine("&fover to next page.");
      item.addLoreLine(" ");
      item.addLoreLine(
        "&6Page: &7(&e" +
        this.switchableMenu.getPage() +
        "&7/&e" +
        this.switchableMenu.getPages(player) +
        "&7)"
      );
    } else {
      item.addLoreLine("&6There is no next page.");
      item.addLoreLine("&6You're on the last page.");
      item.addLoreLine(" ");
      item.addLoreLine(
        "&6Page: &7(&e" +
        this.switchableMenu.getPage() +
        "&7/&e" +
        this.switchableMenu.getPages(player) +
        "&7)"
      );
    }
    item.addLoreLine(" ");

    return item.build();
  }

  @Override
  public void onClick(Player player, int slot, ClickType clickType) {
    if (this.switchableMenu.getPage() >= this.switchableMenu.getPages(player)) {
      player.sendMessage(CC.translate("&cYou're on the last page of the menu!"));
      return;
    }
    this.switchableMenu.changePage(player, 1);
  }

  @Override
  public int getSlot() {
    return 26;
  }

  @Override
  public int[] getSlots() {
    return null;
  }
}
