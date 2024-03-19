package fr.frinn.custommachinerycreate.components;

import fr.frinn.custommachinery.api.codec.NamedCodec;
import fr.frinn.custommachinery.api.component.ComponentIOMode;
import fr.frinn.custommachinery.api.component.IMachineComponentManager;
import fr.frinn.custommachinery.api.component.IMachineComponentTemplate;
import fr.frinn.custommachinery.api.component.ISideConfigComponent;
import fr.frinn.custommachinery.api.component.ITickableComponent;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.machine.MachineStatus;
import fr.frinn.custommachinery.impl.component.AbstractMachineComponent;
import fr.frinn.custommachinery.impl.component.config.SideConfig;
import fr.frinn.custommachinerycreate.Registration;
import fr.frinn.custommachinerycreate.util.FakeGeneratingKineticBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ContraptionMachineComponent extends AbstractMachineComponent implements ITickableComponent, ISideConfigComponent {

    private final float baseStressImpact;
    private final SideConfig config;
    private final FakeGeneratingKineticBlockEntity fakeTile;

    private float speed;
    private float stressCapacity;
    private float stressImpact;
    private int resetTimer = 0;

    public ContraptionMachineComponent(IMachineComponentManager manager) {
        this(manager, 0, SideConfig.Template.DEFAULT_ALL_BOTH);
    }

    public ContraptionMachineComponent(IMachineComponentManager manager, float baseStressImpact, SideConfig.Template config) {
        super(manager, ComponentIOMode.BOTH);
        this.baseStressImpact = baseStressImpact;
        this.config = config.build(this);
        BlockEntity be = manager.getTile();
        this.fakeTile = new FakeGeneratingKineticBlockEntity(be.getType(), be.getBlockPos(), be.getBlockState(), this);
        if(manager.getLevel() != null)
            this.fakeTile.setLevel(manager.getLevel());
        this.fakeTile.reActivateSource = true;
    }

    public FakeGeneratingKineticBlockEntity getFakeTile() {
        return this.fakeTile;
    }

    public void set(float speed, float stressCapacity, float stressApplied) {
        this.speed = speed;
        this.stressCapacity = stressCapacity;
        this.stressImpact = stressApplied;
        applyChanges();
        this.resetTimer = -1;
    }

    public void markForReset() {
        this.resetTimer = 2;
    }

    private void applyChanges() {
        this.fakeTile.setGeneratedSpeed(this.speed);
        this.fakeTile.setStressCapacity(this.stressCapacity);
        this.fakeTile.setStressImpact(this.stressImpact);
        this.fakeTile.reActivateSource = true;
    }

    @Override
    public MachineComponentType<ContraptionMachineComponent> getType() {
        return Registration.CONTRAPTION_MACHINE_COMPONENT.get();
    }

    @Override
    public SideConfig getConfig() {
        return this.config;
    }

    @Override
    public String getId() {
        return "contraption";
    }

    @Override
    public void serverTick() {
        if(this.resetTimer >= 0 && this.resetTimer-- == 0) {
            this.speed = 0.0F;
            this.stressCapacity = 0.0F;
            this.stressImpact = this.baseStressImpact;
            applyChanges();
        }
        this.fakeTile.tick();
    }

    @Override
    public void onStatusChanged(MachineStatus oldStatus, MachineStatus newStatus, Component message) {
        if(newStatus != MachineStatus.RUNNING) {
            this.fakeTile.setGeneratedSpeed(0);
            this.fakeTile.setStressCapacity(0);
            this.fakeTile.reActivateSource = true;
        } else {
            applyChanges();
        }
    }

    @Override
    public void init() {
        this.fakeTile.setLevel(getManager().getLevel());
    }

    @Override
    public void onRemoved() {
        if(getManager().getTile().isUnloaded())
            this.fakeTile.onChunkUnloaded();
        this.fakeTile.setRemoved();
    }

    public static class Template implements IMachineComponentTemplate<ContraptionMachineComponent> {

        public static final NamedCodec<Template> CODEC = NamedCodec.record(templateInstance ->
                templateInstance.group(
                        NamedCodec.floatRange(0, Float.MAX_VALUE).optionalFieldOf("stress_impact", 0.0F).forGetter(template -> template.stressImpact),
                        SideConfig.Template.CODEC.optionalFieldOf("config", SideConfig.Template.DEFAULT_ALL_BOTH).forGetter(template -> template.config)
                ).apply(templateInstance, Template::new), "Contraption machine component"
        );

        private final float stressImpact;
        private final SideConfig.Template config;

        private Template(float stressImpact, SideConfig.Template config) {
            this.stressImpact = stressImpact;
            this.config = config;
        }

        public float getStressImpact() {
            return this.stressImpact;
        }

        @Override
        public MachineComponentType<ContraptionMachineComponent> getType() {
            return Registration.CONTRAPTION_MACHINE_COMPONENT.get();
        }

        @Override
        public String getId() {
            return "contraption";
        }

        @Override
        public boolean canAccept(Object ingredient, boolean isInput, IMachineComponentManager manager) {
            return false;
        }

        @Override
        public ContraptionMachineComponent build(IMachineComponentManager manager) {
            return new ContraptionMachineComponent(manager, this.stressImpact, this.config);
        }
    }
}
