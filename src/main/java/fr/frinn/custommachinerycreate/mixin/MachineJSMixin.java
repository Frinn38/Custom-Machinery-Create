package fr.frinn.custommachinerycreate.mixin;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import fr.frinn.custommachinery.common.init.CustomMachineTile;
import fr.frinn.custommachinery.common.integration.kubejs.function.MachineJS;
import fr.frinn.custommachinerycreate.Registration;
import fr.frinn.custommachinerycreate.components.BlazeBurnerComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = MachineJS.class, remap = false)
public class MachineJSMixin {

    @Final
    @Shadow(remap = false)
    private CustomMachineTile internal;

    /** BLAZE BURNER **/

    public HeatLevel getBlazeBurnerHeat() {
        return this.internal.getComponentManager().getComponent(Registration.BLAZE_BURNER_MACHINE_COMPONENT.get())
                .map(BlazeBurnerComponent::getHeatLevel)
                .orElse(HeatLevel.NONE);
    }

    /** STRESS **/

    public float getStressApplied() {
        return this.internal.getComponentManager().getComponent(Registration.CONTRAPTION_MACHINE_COMPONENT.get())
                .map(component -> component.getFakeTile().calculateStressApplied())
                .orElse(0.0F);
    }

    public float getStressCapacity() {
        return this.internal.getComponentManager().getComponent(Registration.CONTRAPTION_MACHINE_COMPONENT.get())
                .map(component -> component.getFakeTile().calculateAddedStressCapacity())
                .orElse(0.0F);
    }

    public void setStressImpact(float stressImpact) {
        this.internal.getComponentManager().getComponent(Registration.CONTRAPTION_MACHINE_COMPONENT.get())
                .ifPresent(component -> {
                    component.getFakeTile().setStressImpact(stressImpact);
                    component.getFakeTile().reActivateSource = true;
                });
    }

    public void setStressCapacity(float stressCapacity) {
        this.internal.getComponentManager().getComponent(Registration.CONTRAPTION_MACHINE_COMPONENT.get())
                .ifPresent(component -> {
                    component.getFakeTile().setStressCapacity(stressCapacity);
                    component.getFakeTile().reActivateSource = true;
                });
    }

    /** SPEED **/

    public float getCurrentSpeed() {
        return this.internal.getComponentManager().getComponent(Registration.CONTRAPTION_MACHINE_COMPONENT.get())
                .map(component -> component.getFakeTile().getSpeed())
                .orElse(0.0F);
    }

    public float getGeneratedSpeed() {
        return this.internal.getComponentManager().getComponent(Registration.CONTRAPTION_MACHINE_COMPONENT.get())
                .map(component -> component.getFakeTile().getGeneratedSpeed())
                .orElse(0.0F);
    }

    public void setSpeed(float speed) {
        this.internal.getComponentManager().getComponent(Registration.CONTRAPTION_MACHINE_COMPONENT.get())
                .ifPresent(component -> {
                    component.getFakeTile().setGeneratedSpeed(speed);
                    component.getFakeTile().reActivateSource = true;
                });
    }
}
