package com.ninni.frozenup.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.argument.RegistryPredicateArgumentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.command.LocateCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LocateCommand.class)
public class LocateCommandMixin {
    private static final SimpleCommandExceptionType IGLOO_NULL = new SimpleCommandExceptionType(Text.translatable("frozenup.commands.locate.revamped_igloo"));

    @Inject(at = @At("HEAD"), method = "executeLocateStructure")
    private static void FU$executeLocateStructure(ServerCommandSource source, RegistryPredicateArgumentType.RegistryPredicate<Structure> predicate, CallbackInfoReturnable<Integer> cir)  throws CommandSyntaxException {
        Optional<RegistryKey<Structure>> optional = predicate.getKey().left();
        if (optional.isPresent() && optional.get().getValue().equals(new Identifier("igloo"))) {
            throw IGLOO_NULL.create();
        }
    }


}
