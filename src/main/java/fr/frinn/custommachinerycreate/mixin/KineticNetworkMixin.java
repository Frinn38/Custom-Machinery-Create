package fr.frinn.custommachinerycreate.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.content.kinetics.KineticNetwork;
import fr.frinn.custommachinerycreate.CustomMachineryCreate;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(KineticNetwork.class)
public class KineticNetworkMixin {

    /***Redirect not good because conflict with Create Aeronautics which does the same***/

    @ModifyExpressionValue(method = "calculateCapacity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private BlockEntity cmcreate$replaceMachineBEByFakeBE(BlockEntity original) {
        return CustomMachineryCreate.getFakeBE(original);
    }

    @ModifyExpressionValue(method = "calculateStress", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private BlockEntity cmcreate$replaceMachineBEByFakeBE2(BlockEntity original) {
        return CustomMachineryCreate.getFakeBE(original);
    }
}
