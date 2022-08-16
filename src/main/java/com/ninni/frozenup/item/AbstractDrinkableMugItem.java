package com.ninni.frozenup.item;

import com.ninni.frozenup.init.FrozenUpItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public abstract class AbstractDrinkableMugItem extends BlockItem {

    public AbstractDrinkableMugItem(Block block, Properties settings) { super(block, settings); }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        super.finishUsingItem(stack, world, user);
        if (!stack.isEdible() && user instanceof Player player) {
            if (!player.isCreative()) stack.shrink(1);
        }
        return stack.isEmpty() ? new ItemStack(FrozenUpItems.EMPTY_MUG.get()) : stack;
    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(world, player, hand);
    }

}
