package fr.frinn.custommachinerycreate.util;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import fr.frinn.custommachinerycreate.components.ContraptionMachineComponent;
import fr.frinn.custommachinerycreate.network.SUpdateFakeKineticTilePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FakeGeneratingKineticBlockEntity extends GeneratingKineticBlockEntity {

    private final ContraptionMachineComponent component;

    private float generatedSpeed;
    private float stressCapacity;
    private float stressImpact;

    public FakeGeneratingKineticBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, ContraptionMachineComponent component) {
        super(type, pos, state);
        this.component = component;
    }

    public void setGeneratedSpeed(float generatedSpeed) {
        this.generatedSpeed = generatedSpeed;
    }

    public void setStressCapacity(float stressCapacity) {
        this.stressCapacity = stressCapacity;
    }

    public void setStressImpact(float stressImpact) {
        this.stressImpact = stressImpact;
    }

    @Override
    public float calculateAddedStressCapacity() {
        return this.stressCapacity;
    }

    @Override
    public float calculateStressApplied() {
        return this.stressImpact;
    }

    @Override
    public void sendData() {
        if(this.level != null && !this.level.isClientSide()) {
            CompoundTag nbt = this.writeClient(new CompoundTag());
            nbt.putFloat("cm_generated_speed", this.generatedSpeed);
            nbt.putFloat("cm_stress_capacity", this.stressCapacity);
            nbt.putFloat("cm_stress_impact", this.stressImpact);
            new SUpdateFakeKineticTilePacket(this.worldPosition, nbt).sendToLevel((ServerLevel)this.level);
        }
    }

    @Override
    public float getGeneratedSpeed() {
        return this.generatedSpeed;
    }
}
