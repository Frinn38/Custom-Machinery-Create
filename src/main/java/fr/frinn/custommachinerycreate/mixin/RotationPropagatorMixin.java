package fr.frinn.custommachinerycreate.mixin;

import com.simibubi.create.content.kinetics.RotationPropagator;
import fr.frinn.custommachinerycreate.CustomMachineryCreate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RotationPropagator.class)
public class RotationPropagatorMixin {

    @Redirect(method = "findConnectedNeighbour", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private static BlockEntity cmcreate$replaceMachineTileByFakeTile(Level level, BlockPos pos) {
        return CustomMachineryCreate.getFakeTile(level, pos);
    }

    @Redirect(method = "propagateMissingSource", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private static BlockEntity cmcreate$replaceMachineTileByFakeTile2(Level level, BlockPos pos) {
        return CustomMachineryCreate.getFakeTile(level, pos);
    }

    @Redirect(method = "handleRemoved", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private static BlockEntity cmcreate$replaceMachineTileByFakeTile3(Level level, BlockPos pos) {
        return CustomMachineryCreate.getFakeTile(level, pos);
    }
}
