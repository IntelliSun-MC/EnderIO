package crazypants.enderio.base.handler.darksteel;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.enderio.core.api.client.gui.IAdvancedTooltipProvider;
import com.enderio.core.client.handlers.SpecialTooltipHandler;
import com.enderio.core.common.util.ItemUtil;

import crazypants.enderio.api.upgrades.IDarkSteelUpgrade;
import crazypants.enderio.base.EnderIO;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry.Impl;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AbstractUpgrade extends Impl<IDarkSteelUpgrade> implements IAdvancedTooltipProvider, IDarkSteelUpgrade {

  public static final @Nonnull String KEY_UPGRADE_PREFIX = "enderio.darksteel.upgrade.";

  private static final @Nonnull String KEY_VARIANT = "level";

  protected final int levelCost, variant;
  protected final @Nonnull String id;
  protected final @Nonnull String unlocName;

  protected @Nonnull ItemStack upgradeItem;

  protected AbstractUpgrade(@Nonnull String id, @Nonnull String unlocName, @Nonnull ItemStack upgradeItem, int levelCost) {
    this(id, 0, unlocName, upgradeItem, levelCost);
  }

  protected AbstractUpgrade(@Nonnull String domain, @Nonnull String id, @Nonnull String unlocName, @Nonnull ItemStack upgradeItem, int levelCost) {
    this(domain, id, 0, unlocName, upgradeItem, levelCost);
  }

  protected AbstractUpgrade(@Nonnull String id, int variant, @Nonnull String unlocName, @Nonnull ItemStack upgradeItem, int levelCost) {
    this(EnderIO.DOMAIN, id, variant, unlocName, upgradeItem, levelCost);
  }

  protected AbstractUpgrade(@Nonnull String domain, @Nonnull String id, int variant, @Nonnull String unlocName, @Nonnull ItemStack upgradeItem, int levelCost) {
    this.id = KEY_UPGRADE_PREFIX + id;
    this.unlocName = unlocName;
    this.upgradeItem = upgradeItem;
    this.levelCost = levelCost;
    this.variant = variant;
    setRegistryName(domain, id + (variant != 0 ? variant : ""));
  }

  @Override
  public @Nonnull ItemStack getUpgradeItem() {
    return upgradeItem;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addCommonEntries(@Nonnull ItemStack itemstack, @Nullable EntityPlayer entityplayer, @Nonnull List<String> list, boolean flag) {
    SpecialTooltipHandler.addCommonTooltipFromResources(list, getUnlocalizedName());
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addBasicEntries(@Nonnull ItemStack itemstack, @Nullable EntityPlayer entityplayer, @Nonnull List<String> list, boolean flag) {
    list.add(TextFormatting.DARK_AQUA + EnderIO.lang.localizeExact(getUnlocalizedName() + ".name"));
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addDetailedEntries(@Nonnull ItemStack itemstack, @Nullable EntityPlayer entityplayer, @Nonnull List<String> list, boolean flag) {
    list.add(TextFormatting.DARK_AQUA + EnderIO.lang.localizeExact(getUnlocalizedName() + ".name"));
    SpecialTooltipHandler.addDetailedTooltipFromResources(list, getUnlocalizedName());
  }

  @Override
  public int getLevelCost() {
    return levelCost;
  }

  @Override
  public @Nonnull String getUnlocalizedName() {
    return unlocName;
  }

  @Override
  public boolean hasUpgrade(@Nonnull ItemStack stack) {
    final NBTTagCompound tagCompound = stack.getTagCompound();
    return tagCompound != null && tagCompound.hasKey(id) && tagCompound.getCompoundTag(id).getInteger(KEY_VARIANT) == variant;
  }

  @Override
  public void addToItem(@Nonnull ItemStack stack) {
    NBTTagCompound upgradeRoot = new NBTTagCompound();
    upgradeRoot.setInteger(KEY_VARIANT, variant);
    NBTTagCompound stackRoot = ItemUtil.getOrCreateNBT(stack);
    stackRoot.setTag(id, upgradeRoot);
    stack.setTagCompound(stackRoot);
  }

  public @Nonnull NBTTagCompound getUpgradeNBT(@Nonnull ItemStack stack) {
    NBTTagCompound tagCompound = stack.getTagCompound();
    if (tagCompound == null) {
      stack.setTagCompound(tagCompound = new NBTTagCompound());
    }
    if (!tagCompound.hasKey(id)) {
      tagCompound.setTag(id, new NBTTagCompound());
    }
    return tagCompound.getCompoundTag(id);
  }

}
