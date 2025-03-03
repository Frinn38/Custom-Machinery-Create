package fr.frinn.custommachinerycreate.client;

import com.simibubi.create.content.equipment.goggles.GogglesItem;
import com.simibubi.create.content.kinetics.base.IRotate.StressImpact;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.utility.CreateLang;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.simibubi.create.infrastructure.config.CKinetics;
import fr.frinn.custommachinery.common.init.CustomMachineItem;
import fr.frinn.custommachinerycreate.CustomMachineryCreate;
import fr.frinn.custommachinerycreate.components.ContraptionMachineComponent;
import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

@EventBusSubscriber(modid = CustomMachineryCreate.MODID, bus = Bus.GAME, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onItemTooltip(final ItemTooltipEvent event) {
        CustomMachineItem.getMachine(event.getItemStack())
                .flatMap(machine -> machine.getComponentTemplates().stream().filter(template -> template instanceof ContraptionMachineComponent.Template).findFirst())
                .map(template -> ((ContraptionMachineComponent.Template)template).stressImpact())
                .filter(stressImpact -> stressImpact != 0)
                .ifPresent(impact -> addKineticStatsTooltip(impact, event.getToolTip()));
    }

    private static void addKineticStatsTooltip(float impact, List<Component> list) {
        CKinetics config = AllConfigs.server().kinetics;
        LangBuilder rpmUnit = CreateLang.translate("generic.unit.rpm");
        boolean hasGoggles = GogglesItem.isWearingGoggles(Minecraft.getInstance().player);

        CreateLang.translate("tooltip.stressImpact")
                .style(ChatFormatting.GRAY)
                .addTo(list);

        StressImpact impactId = impact >= config.highStressImpact.get() ? StressImpact.HIGH
                : (impact >= config.mediumStressImpact.get() ? StressImpact.MEDIUM : StressImpact.LOW);
        LangBuilder builder = CreateLang.builder()
                .add(CreateLang.text(TooltipHelper.makeProgressBar(3, impactId.ordinal() + 1))
                        .style(impactId.getAbsoluteColor()));

        if (hasGoggles)
            builder.add(CreateLang.number(impact))
                    .text("x ")
                    .add(rpmUnit)
                    .addTo(list);
        else
            builder.translate("tooltip.stressImpact." + Lang.asId(impactId.name()))
                    .addTo(list);
    }
}
