package com.ninni.frozenup.client.screen;

import com.ninni.frozenup.entity.ReindeerEntity;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ReindeerInventoryMenu extends AbstractContainerMenu {
    private final Container horseContainer;
    private final ReindeerEntity horse;

    public ReindeerInventoryMenu(int id, Inventory inventory, Container container, final ReindeerEntity reindeer) {
        super(null, id);
        this.horseContainer = container;
        this.horse = reindeer;
        container.startOpen(inventory.player);
        this.addSlot(new Slot(container, 0, 8, 18) {
            public boolean mayPlace(ItemStack stack) {
                return stack.is(Items.SADDLE) && !this.hasItem() && reindeer.isSaddleable();
            }
            public boolean isActive() {
                return reindeer.isSaddleable();
            }
        });
        this.addSlot(new Slot(container, 1, 8, 36) {
            public boolean mayPlace(ItemStack stack) {
                return reindeer.isArmor(stack);
            }

            public boolean isActive() {
                return reindeer.canWearArmor();
            }

            public int getMaxStackSize() {
                return 1;
            }
        });

        for(int i1 = 0; i1 < 3; ++i1) {
            for(int k1 = 0; k1 < 9; ++k1) {
                this.addSlot(new Slot(inventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
            }
        }

        for(int j1 = 0; j1 < 9; ++j1) {
            this.addSlot(new Slot(inventory, j1, 8 + j1 * 18, 142));
        }

    }

    public boolean stillValid(Player player) {
        return !this.horse.hasInventoryChanged(this.horseContainer) && this.horseContainer.stillValid(player) && this.horse.isAlive() && this.horse.distanceTo(player) < 8.0F;
    }

    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            int i = this.horseContainer.getContainerSize();
            if (index < i) {
                if (!this.moveItemStackTo(itemstack1, i, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(1).mayPlace(itemstack1) && !this.getSlot(1).hasItem()) {
                if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(0).mayPlace(itemstack1)) {
                if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (i <= 2 || !this.moveItemStackTo(itemstack1, 2, i, false)) {
                int j = i + 27;
                int k = j + 9;
                if (index >= j && index < k) {
                    if (!this.moveItemStackTo(itemstack1, i, j, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < j) {
                    if (!this.moveItemStackTo(itemstack1, j, k, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(itemstack1, j, j, false)) {
                    return ItemStack.EMPTY;
                }

                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    public void removed(Player player) {
        super.removed(player);
        this.horseContainer.stopOpen(player);
    }
}
