package com.ninni.frozenup.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ninni.frozenup.init.FrozenUpItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class PineconeLootModifier extends LootModifier {
    public static final Codec<PineconeLootModifier> CODEC = RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, PineconeLootModifier::new));

    public PineconeLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        if (state != null && state.is(Blocks.SPRUCE_LEAVES)) {
            ItemStack ctxTool = context.getParamOrNull(LootContextParams.TOOL);
            RandomSource random = context.getRandom();
            if (ctxTool != null) {
                if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, ctxTool) > 0 || ctxTool.getItem() instanceof ShearsItem) {
                    return generatedLoot;
                }
            }
            float[] chances = new float[]{0.05F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F};
            int i = ctxTool != null ? EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, ctxTool) : 0;
            if (random.nextFloat() < chances[Math.min(i, chances.length - 1)]) {
                generatedLoot.add(new ItemStack(FrozenUpItems.PINECONE.get()));
            }
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

}
