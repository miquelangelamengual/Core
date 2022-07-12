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
public class PreviousPageSlot extends Slot {

  private final SwitchableMenu switchableMenu;

  @Override
  public ItemStack getItem(Player player) {
    ItemBuilder item = new ItemBuilder(Material.HOPPER);
    item.name("&6Previous Page");
    item.addLoreLine(" ");

    if (this.switchableMenu.getPage() != 1) {
      item.addLoreLine("&7Click to head over");
      item.addLoreLine("&7to previous page.");
      item.addLoreLine(" ");
      item.addLoreLine(
        "&6Page: &7(&e" +
        this.switchableMenu.getPage() +
        "&7/&e" +
        this.switchableMenu.getPages(player) +
        "&7)"
      );
    } else {
      item.addLoreLine("&eThere is no previous page.");
      item.addLoreLine("&eYou're on the first page.");
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
    if (this.switchableMenu.getPage() == 1) {
      player.sendMessage(CC.translate("&cYou're on the first page of the menu!"));
      return;
    }
    this.switchableMenu.changePage(player, -1);
  }

  @Override
  public int getSlot() {
    return 18;
  }

  @Override
  public int[] getSlots() {
    return null;
  }
}
