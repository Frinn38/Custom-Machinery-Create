package fr.frinn.custommachinerycreate.mixin;

import com.simibubi.create.api.schematic.requirement.SpecialBlockItemRequirement;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;
import fr.frinn.custommachinery.api.machine.MachineTile;
import fr.frinn.custommachinery.common.init.CustomMachineBlock;
import fr.frinn.custommachinery.common.init.CustomMachineItem;
import fr.frinn.custommachinerycreate.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(CustomMachineBlock.class)
public abstract class CustomMachineBlockMixin implements IRotate, SpecialBlockItemRequirement {
    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return Optional.ofNullable(world.getBlockEntity(pos))
                .filter(be -> be instanceof MachineTile)
                .flatMap(be -> ((MachineTile) be).getComponentManager().getComponent(Registration.CONTRAPTION_MACHINE_COMPONENT.get()))
                .map(component -> !component.getConfig().getDirectionMode(face).isDisabled())
                .orElse(false);
    }

    @Override
    public Axis getRotationAxis(BlockState state) {
        return Axis.Y;
    }

    @Override
    public ItemRequirement getRequiredItems(BlockState state, @Nullable BlockEntity blockEntity) {
        if (blockEntity instanceof MachineTile machine) {
            return new ItemRequirement(new ItemRequirement.StrictNbtStackRequirement(
                    CustomMachineItem.makeMachineItem(machine.getMachine().getId()), ItemRequirement.ItemUseType.CONSUME));
        } else {
            return ItemRequirement.INVALID;
        }
    }
}
