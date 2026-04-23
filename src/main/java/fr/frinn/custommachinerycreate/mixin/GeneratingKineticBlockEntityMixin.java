package fr.frinn.custommachinerycreate.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import fr.frinn.custommachinerycreate.CustomMachineryCreate;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GeneratingKineticBlockEntity.class)
public class GeneratingKineticBlockEntityMixin {

    @ModifyExpressionValue(method = "setSource", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private BlockEntity cmcreate$replaceMachineBEByFakeBE(BlockEntity original) {
        return CustomMachineryCreate.getFakeBE(original);
    }
}
