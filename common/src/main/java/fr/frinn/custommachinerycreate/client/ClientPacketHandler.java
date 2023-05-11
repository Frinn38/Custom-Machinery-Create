package fr.frinn.custommachinerycreate.client;

import fr.frinn.custommachinery.api.machine.MachineTile;
import fr.frinn.custommachinerycreate.Registration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ClientPacketHandler {

    public static void handleUpdateFakeTilePacket(BlockPos pos, CompoundTag nbt) {
        if(Minecraft.getInstance().level == null)
            return;
        BlockEntity be = Minecraft.getInstance().level.getBlockEntity(pos);
        if(be instanceof MachineTile machine) {
            machine.getComponentManager()
                    .getComponent(Registration.CONTRAPTION_MACHINE_COMPONENT.get())
                    .ifPresent(component -> {
                        component.getFakeTile().readClient(nbt);
                        if(nbt.contains("cm_generated_speed", Tag.TAG_FLOAT))
                            component.getFakeTile().setGeneratedSpeed(nbt.getFloat("cm_generated_speed"));
                        if(nbt.contains("cm_stress_capacity", Tag.TAG_FLOAT))
                            component.getFakeTile().setStressCapacity(nbt.getFloat("cm_stress_capacity"));
                        if(nbt.contains("cm_stress_impact", Tag.TAG_FLOAT))
                            component.getFakeTile().setStressImpact(nbt.getFloat("cm_stress_impact"));
                    });
        }
    }
}
