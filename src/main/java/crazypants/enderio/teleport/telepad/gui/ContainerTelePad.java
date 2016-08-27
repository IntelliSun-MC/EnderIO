package crazypants.enderio.teleport.telepad.gui;

import java.awt.Point;
import java.util.List;

import javax.annotation.Nullable;

import com.enderio.core.client.gui.widget.GhostBackgroundItemSlot;
import com.enderio.core.client.gui.widget.GhostSlot;

import crazypants.enderio.EnderIO;
import crazypants.enderio.teleport.telepad.TelepadTarget;
import crazypants.enderio.teleport.telepad.TileTelePad;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerTelePad extends com.enderio.core.common.ContainerEnderCap<TileTelePad> {

  private int slotno;

  public ContainerTelePad(InventoryPlayer playerInv, TileTelePad itemHandler) {
    super(playerInv, itemHandler);
  }

  @Override
  public Point getPlayerInventoryOffset() {
    return new Point(8, 138);
  }

  @Override
  protected void addSlots() {
    int x = 149;
    int y = 47;
    slotno = inventorySlots.indexOf(addSlotToContainer(new SlotItemHandler(getItemHandler(), 0, x, y) {
      @Override
      public boolean isItemValid(@Nullable ItemStack itemStack) {
        return TelepadTarget.readFromNBT(itemStack) != null;
      }
    }));
    y = 84;
    addSlotToContainer(new SlotItemHandler(getItemHandler(), 1, x, y));
  }

  public void createGhostSlots(List<GhostSlot> slots) {
    slots.add(new GhostBackgroundItemSlot(EnderIO.itemlocationPrintout, inventorySlots.get(slotno)));
  }

}
