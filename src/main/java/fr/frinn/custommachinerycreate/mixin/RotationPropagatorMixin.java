package fr.frinn.custommachinerycreate.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.content.kinetics.RotationPropagator;
import fr.frinn.custommachinerycreate.CustomMachineryCreate;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RotationPropagator.class)
public class RotationPropagatorMixin {

    @ModifyExpressionValue(method = "findConnectedNeighbour", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private static BlockEntity cmcreate$replaceMachineBEByFakeBE(BlockEntity original) {
        return CustomMachineryCreate.getFakeBE(original);
    }

    @ModifyExpressionValue(method = "propagateMissingSource", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private static BlockEntity cmcreate$replaceMachineBEByFakeBE2(BlockEntity original) {
        return CustomMachineryCreate.getFakeBE(original);
    }

    @ModifyExpressionValue(method = "handleRemoved", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private static BlockEntity cmcreate$replaceMachineBEByFakeBE3(BlockEntity original) {
        return CustomMachineryCreate.getFakeBE(original);
    }
}
