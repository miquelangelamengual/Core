package es.hulk.core.utils.aquamenu;

import com.google.common.collect.Maps;
import es.hulk.core.Core;
import es.hulk.core.utils.aquamenu.menu.AquaMenu;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
public class MenuManager {

  private final Core plugin;

  public Map<UUID, AquaMenu> openedMenus = Maps.newHashMap();
  public Map<UUID, AquaMenu> lastOpenedMenus = Maps.newHashMap();

  public MenuManager(Core plugin) {
    this.plugin = plugin;
  }
}
