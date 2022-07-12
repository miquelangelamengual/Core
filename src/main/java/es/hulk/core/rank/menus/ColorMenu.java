package es.hulk.core.rank.menus;

import com.google.common.collect.Lists;
import es.hulk.core.rank.Rank;
import es.hulk.core.utils.ItemBuilder;
import es.hulk.core.utils.WoolUtil;
import es.hulk.core.utils.aquamenu.menu.AquaMenu;
import es.hulk.core.utils.aquamenu.slots.Slot;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ColorMenu extends AquaMenu {

    private final Rank rank;

    public ColorMenu(Rank rank) {
        this.rank = rank;
    }

    @Override
    public List<Slot> getSlots(Player player) {
        List<Slot> slots = Lists.newArrayList();
        int i = 0;
        for (ChatColor color : ChatColor.values()) {
            i++;
            int finalI = i;
            Slot slot = new Slot() {
                @Override
                public ItemStack getItem(Player player) {
                    return new ItemBuilder(Material.WOOL).data(WoolUtil.convertChatColorToWoolData(color)).name(color + color.name().replace("_", " ")).build();
                }

                @Override
                public int getSlot() {
                    return finalI;
                }

                @Override
                public void onClick(Player player, int slot, ClickType clickType) {
                    rank.setColor(color.name().toUpperCase());
                    player.closeInventory();
                    player.sendMessage("cambiao tonto");
                }
            };
            slots.add(slot);
        }
        return slots;
    }

    @Override
    public String getName(Player player) {
        return "Menu color";
    }
}
