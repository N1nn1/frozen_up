package com.ninni.frozenup.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.argument.RegistryPredicateArgumentType;
import net.minecraft.server.command.LocateCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.Structures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocateCommand.class)
public class LocateCommandMixin {
    private static final SimpleCommandExceptionType IGLOO_NULL = new SimpleCommandExceptionType(Text.translatable("frozenup.commands.locate.revamped_igloo"));

    @Inject(at = @At("HEAD"), method = "executeLocateStructure")
    private static void execute(ServerCommandSource source, RegistryPredicateArgumentType.RegistryPredicate<Structure> predicate, CallbackInfoReturnable<Integer> cir)  throws CommandSyntaxException {
        if (predicate.test(Structures.IGLOO)) {
            throw IGLOO_NULL.create();
        }
    }


}
