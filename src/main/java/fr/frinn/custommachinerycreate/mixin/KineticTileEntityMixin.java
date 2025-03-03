package fr.frinn.custommachinerycreate.mixin;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import fr.frinn.custommachinerycreate.CustomMachineryCreate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(KineticBlockEntity.class)
public class KineticTileEntityMixin {

    @Redirect(method = "validateKinetics", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private BlockEntity cmcreate$replaceMachineTileByFakeTile(Level level, BlockPos pos) {
        return CustomMachineryCreate.getFakeTile(level, pos);
    }

    @Redirect(method = "setSource", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private BlockEntity cmcreate$replaceMachineTileByFakeTile2(Level level, BlockPos pos) {
        return CustomMachineryCreate.getFakeTile(level, pos);
    }
}
