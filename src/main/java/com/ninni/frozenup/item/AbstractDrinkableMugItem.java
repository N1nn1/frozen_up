package com.ninni.frozenup.item;

import com.ninni.frozenup.sound.FrozenUpSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public abstract class AbstractDrinkableMugItem extends BlockItem {

    public AbstractDrinkableMugItem(Block block, Settings settings) { super(block, settings); }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        if (!stack.isFood() && user instanceof PlayerEntity) {
            if (!((PlayerEntity) user).isCreative()) stack.decrement(1);
        }
        return stack.isEmpty() ? new ItemStack(FrozenUpItems.EMPTY_MUG) : stack;
    }

    @Override public int getMaxUseTime(ItemStack stack) { return 32; }

    @Override public UseAction getUseAction(ItemStack stack) { return UseAction.DRINK; }

    @Override
    public SoundEvent getDrinkSound() {
        return FrozenUpSoundEvents.ITEM_MUG_DRINK;
    }

    @Override
    public SoundEvent getEatSound() {
        return FrozenUpSoundEvents.ITEM_MUG_DRINK;
    }

    @Override public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) { return ItemUsage.consumeHeldItem(world, user, hand); }
}
