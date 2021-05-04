package teamdraco.frozenup.item;

import teamdraco.frozenup.init.FrozenUpItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class HotChocolateItem extends Item {
	public HotChocolateItem(Settings builder) {
		super(builder);
	}

	public ItemStack finishUsing(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.BLINDNESS);
		if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.HUNGER);
		if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.MINING_FATIGUE);
		if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.NAUSEA);
		if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.POISON);
		if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.SLOWNESS);
		if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.UNLUCK);
		if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.WEAKNESS);
		if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.WITHER);

		if (!worldIn.isClient) entityLiving.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 500, 0));

		if (entityLiving instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)entityLiving;
			Criteria.CONSUME_ITEM.trigger(serverplayerentity, stack);
			serverplayerentity.incrementStat(Stats.USED.getOrCreateStat(this));
		}

		if (entityLiving instanceof PlayerEntity) {
			int newFoodLevel = ((PlayerEntity)entityLiving).getHungerManager().getFoodLevel() + 8;
			((PlayerEntity) entityLiving).getHungerManager().setFoodLevel(newFoodLevel);
		}

		if (entityLiving instanceof PlayerEntity && !((PlayerEntity)entityLiving).abilities.creativeMode) {
			stack.decrement(1);
		}

		return stack.isEmpty() ? new ItemStack(FrozenUpItems.EMPTY_MUG) : stack;
	}

	public int getMaxUseTime(ItemStack stack) {
		return 32;
	}

	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}

	public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		return ItemUsage.consumeHeldItem(worldIn, playerIn, handIn);
	}
}