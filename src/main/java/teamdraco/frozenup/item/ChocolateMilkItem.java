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

public class ChocolateMilkItem extends Item {
   public ChocolateMilkItem(Settings builder) {
      super(builder);
   }

   public ItemStack finishUsing(ItemStack stack, World worldIn, LivingEntity entityLiving) {
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.BLINDNESS);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.ABSORPTION);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.DOLPHINS_GRACE);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.BAD_OMEN);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.CONDUIT_POWER);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.FIRE_RESISTANCE);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.GLOWING);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.HASTE);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.HEALTH_BOOST);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.HUNGER);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.INVISIBILITY);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.JUMP_BOOST);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.LEVITATION);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.LUCK);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.MINING_FATIGUE);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.NAUSEA);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.NIGHT_VISION);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.POISON);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.RESISTANCE);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.SATURATION);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.SLOW_FALLING);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.SLOWNESS);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.STRENGTH);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.UNLUCK);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.WATER_BREATHING);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.WEAKNESS);
      if (!worldIn.isClient) entityLiving.removeStatusEffect(StatusEffects.WITHER);

      if (!worldIn.isClient) entityLiving.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0));
      if (!worldIn.isClient) entityLiving.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 1));
      

      if (entityLiving instanceof PlayerEntity) {
    	int newFoodLevel = ((PlayerEntity)entityLiving).getHungerManager().getFoodLevel() + 3;
    	((PlayerEntity) entityLiving).getHungerManager().setFoodLevel(newFoodLevel);
      }
      
      if (entityLiving instanceof ServerPlayerEntity) {
         ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)entityLiving;
         Criteria.CONSUME_ITEM.trigger(serverplayerentity, stack);
         serverplayerentity.incrementStat(Stats.USED.getOrCreateStat(this));
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