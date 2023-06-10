package fr.frinn.custommachinerycreate.mixin;

import com.simibubi.create.content.kinetics.base.IRotate;
import fr.frinn.custommachinery.api.machine.MachineTile;
import fr.frinn.custommachinery.common.init.CustomMachineBlock;
import fr.frinn.custommachinerycreate.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(CustomMachineBlock.class)
public abstract class CustomMachineBlockMixin implements IRotate {
    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return Optional.ofNullable(world.getBlockEntity(pos))
                .filter(be -> be instanceof MachineTile)
                .flatMap(be -> ((MachineTile) be).getComponentManager().getComponent(Registration.CONTRAPTION_MACHINE_COMPONENT.get()))
                .map(component -> !component.getConfig().getSideMode(face).isNone())
                .orElse(false);
    }

    @Override
    public Axis getRotationAxis(BlockState state) {
        return Axis.Y;
    }
}
