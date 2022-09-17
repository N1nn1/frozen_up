package com.ninni.frozenup.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.ResourceOrTagLocationArgument;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.LocateCommand;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocateCommand.class)
public class LocateCommandMixin {
    private static final SimpleCommandExceptionType IGLOO_NULL = new SimpleCommandExceptionType(Component.translatable("snowed_over.commands.locate.revamped_igloo"));

    @Inject(at = @At("HEAD"), method = "locateStructure")
    private static void execute(CommandSourceStack stack, ResourceOrTagLocationArgument.Result<Structure> result, CallbackInfoReturnable<Integer> cir)  throws CommandSyntaxException {
        if (result.test(Structures.IGLOO)) {
            throw IGLOO_NULL.create();
        }
    }


}
