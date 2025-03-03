package fr.frinn.custommachinerycreate.mixin;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import fr.frinn.custommachinerycreate.CustomMachineryCreate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GeneratingKineticBlockEntity.class)
public class GeneratingKineticBlockEntityMixin {

    @Redirect(method = "setSource", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private BlockEntity cmcreate$replaceMachineTileByFakeTile(Level level, BlockPos pos) {
        return CustomMachineryCreate.getFakeTile(level, pos);
    }
}
