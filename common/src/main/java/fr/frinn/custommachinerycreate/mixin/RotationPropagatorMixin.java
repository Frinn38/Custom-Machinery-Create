package fr.frinn.custommachinerycreate.mixin;

import com.simibubi.create.content.contraptions.RotationPropagator;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import fr.frinn.custommachinery.api.machine.MachineTile;
import fr.frinn.custommachinerycreate.Registration;
import fr.frinn.custommachinerycreate.components.ContraptionMachineComponent;
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
        return getFakeTile(level, pos);
    }

    @Redirect(method = "propagateMissingSource", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private static BlockEntity cmcreate$replaceMachineTileByFakeTile2(Level level, BlockPos pos) {
        return getFakeTile(level, pos);
    }

    @Redirect(method = "handleRemoved", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private static BlockEntity cmcreate$replaceMachineTileByFakeTile3(Level level, BlockPos pos) {
        return getFakeTile(level, pos);
    }

    private static BlockEntity getFakeTile(Level level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if(be instanceof MachineTile machine) {
            KineticTileEntity fakeTile = machine.getComponentManager()
                    .getComponent(Registration.CONTRAPTION_MACHINE_COMPONENT.get())
                    .map(ContraptionMachineComponent::getFakeTile)
                    .orElse(null);
            if(fakeTile != null)
                return fakeTile;
        }
        return be;
    }
}
