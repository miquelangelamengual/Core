package es.hulk.core.utils.aquamenu.slots.pages;

import lombok.RequiredArgsConstructor;
import es.hulk.core.utils.ItemBuilder;
import es.hulk.core.utils.aquamenu.menu.SwitchableMenu;
import es.hulk.core.utils.aquamenu.slots.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class PageSlot extends Slot {

  private final SwitchableMenu switchableMenu;
  private final int slot;

  @Override
  public ItemStack getItem(Player player) {
    ItemBuilder item = new ItemBuilder(Material.BOOK);
    item.name(switchableMenu.getPagesTitle(player));

    item.addLoreLine("");
    item.addLoreLine("&fCurrent page&7: &e" + switchableMenu.getPage());
    item.addLoreLine("&fMax pages&7: &e" + switchableMenu.getPages(player));
    item.addLoreLine("");

    return item.build();
  }

  @Override
  public int getSlot() {
    return slot;
  }

  @Override
  public int[] getSlots() {
    return new int[] { 40 };
  }
}
