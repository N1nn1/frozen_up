package teamdraco.frozenup.item;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import teamdraco.frozenup.util.EffectUtil;

public class HotChocolateMugItem extends AbstractDrinkableMugItem {
    public HotChocolateMugItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void applyEffects(ItemStack stack, World world, LivingEntity user) {
        EffectUtil.removeEffects(user, instance -> instance.getEffectType().getType() == StatusEffectType.HARMFUL);
        if (!world.isClient()) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 500, 0));
        }
        if (user instanceof PlayerEntity) {
            HungerManager hunger = ((PlayerEntity) user).getHungerManager();
            hunger.add(8, 8);
        }
    }
}
