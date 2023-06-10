package fr.frinn.custommachinerycreate.client;

import com.simibubi.create.content.equipment.goggles.GogglesItem;
import com.simibubi.create.content.kinetics.base.IRotate.StressImpact;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.LangBuilder;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.simibubi.create.infrastructure.config.CKinetics;
import dev.architectury.event.events.client.ClientTooltipEvent;
import fr.frinn.custommachinery.common.init.CustomMachineItem;
import fr.frinn.custommachinerycreate.components.ContraptionMachineComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ClientHandler {

    public static void init() {
        ClientTooltipEvent.ITEM.register(ClientHandler::onItemTooltip);
    }

    private static void onItemTooltip(ItemStack stack, List<Component> lines, TooltipFlag flag) {
        CustomMachineItem.getMachine(stack)
                .flatMap(machine -> machine.getComponentTemplates().stream().filter(template -> template instanceof ContraptionMachineComponent.Template).findFirst())
                .map(template -> ((ContraptionMachineComponent.Template)template).getStressImpact())
                .filter(stressImpact -> stressImpact != 0)
                .ifPresent(impact -> addKineticStatsTooltip(impact, lines));
    }

    private static void addKineticStatsTooltip(float impact, List<Component> list) {
        CKinetics config = AllConfigs.server().kinetics;
        LangBuilder rpmUnit = Lang.translate("generic.unit.rpm");
        boolean hasGoggles = GogglesItem.isWearingGoggles(Minecraft.getInstance().player);

        Lang.translate("tooltip.stressImpact")
                .style(ChatFormatting.GRAY)
                .addTo(list);

        StressImpact impactId = impact >= config.highStressImpact.get() ? StressImpact.HIGH
                : (impact >= config.mediumStressImpact.get() ? StressImpact.MEDIUM : StressImpact.LOW);
        LangBuilder builder = Lang.builder()
                .add(Lang.text(TooltipHelper.makeProgressBar(3, impactId.ordinal() + 1))
                        .style(impactId.getAbsoluteColor()));

        if (hasGoggles)
            builder.add(Lang.number(impact))
                    .text("x ")
                    .add(rpmUnit)
                    .addTo(list);
        else
            builder.translate("tooltip.stressImpact." + Lang.asId(impactId.name()))
                    .addTo(list);
    }
}
