package fr.frinn.custommachinerycreate.client.builder;

import com.simibubi.create.AllBlocks;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.client.screen.BaseScreen;
import fr.frinn.custommachinery.client.screen.creation.MachineEditScreen;
import fr.frinn.custommachinery.client.screen.creation.component.ComponentBuilderPopup;
import fr.frinn.custommachinery.client.screen.creation.component.ComponentConfigBuilderWidget;
import fr.frinn.custommachinery.client.screen.creation.component.IMachineComponentBuilder;
import fr.frinn.custommachinery.client.screen.popup.PopupScreen;
import fr.frinn.custommachinery.client.screen.widget.FloatSlider;
import fr.frinn.custommachinery.impl.component.config.ToggleSideConfig;
import fr.frinn.custommachinerycreate.Registration;
import fr.frinn.custommachinerycreate.components.ContraptionMachineComponent;
import fr.frinn.custommachinerycreate.components.ContraptionMachineComponent.Template;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ContraptionComponentBuilder implements IMachineComponentBuilder<ContraptionMachineComponent, Template> {

    @Override
    public MachineComponentType<ContraptionMachineComponent> type() {
        return Registration.CONTRAPTION_MACHINE_COMPONENT.get();
    }

    @Override
    public PopupScreen makePopup(MachineEditScreen parent, @Nullable Template template, Consumer<Template> onFinish) {
        return new ContraptionComponentBuilderPopup(parent, template, onFinish);
    }

    @Override
    public void render(GuiGraphics graphics, int x, int y, int width, int height, Template template) {
        graphics.renderFakeItem(AllBlocks.COGWHEEL.asItem().getDefaultInstance(), x, y + height / 2 - 8);
        graphics.drawString(Minecraft.getInstance().font, "type: " + template.getType().getId().getPath(), x + 25, y + 5, 0, false);
    }

    public static class ContraptionComponentBuilderPopup extends ComponentBuilderPopup<Template> {

        private FloatSlider stressImpact;
        private ToggleSideConfig.Template config;

        public ContraptionComponentBuilderPopup(BaseScreen parent, @Nullable Template template, Consumer<Template> onFinish) {
            super(parent, template, onFinish, Component.translatable("custommachinerycreate.gui.creation.components.contraption"));
        }

        @Override
        public Template makeTemplate() {
            return new Template(this.stressImpact.floatValue(), this.config);
        }

        @Override
        protected void init() {
            super.init();

            //Stress impact
            this.stressImpact = this.propertyList.add(Component.translatable("custommachinerycreate.gui.creation.components.contraption.stress_impact"), FloatSlider.builder().decimalsToShow(2).bounds(0, 128).displayOnlyValue().create(0, 0, 160, 20, Component.translatable("custommachinerycreate.gui.creation.components.contraption.stress_impact")));
            this.baseTemplate().ifPresentOrElse(template -> this.stressImpact.setValue(template.stressImpact()), () -> this.stressImpact.setValue(0F));

            //Config
            this.baseTemplate().ifPresentOrElse(template -> this.config = template.config(), () -> this.config = ToggleSideConfig.Template.DEFAULT_ALL_ENABLED);
            this.propertyList.add(Component.translatable("custommachinery.gui.config.component"), ComponentConfigBuilderWidget.make(0, 0, 160, 20, Component.translatable("custommachinery.gui.config.component"), this.parent, () -> this.config, template -> this.config = template));
        }
    }
}
