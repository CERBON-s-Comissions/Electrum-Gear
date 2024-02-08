package com.cerbon.electrum_gear.event;

import com.cerbon.electrum_gear.ElectrumGear;
import com.cerbon.electrum_gear.item.EGArmorMaterials;
import com.cerbon.electrum_gear.util.EGUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ElectrumGear.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void onHitByLightning(EntityStruckByLightningEvent event) {
        if (event.getEntity() instanceof Player player)
            if (EGUtils.hasFullSuitOfArmorOn(player) && EGUtils.hasCorrectArmorOn(EGArmorMaterials.ELECTRUM, player)) {
                player.getInventory().armor.forEach(armor -> armor.getOrCreateTag().putBoolean("IsCharged", true));
                event.setCanceled(true);
            }
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player && event.getSource().getDirectEntity() instanceof LivingEntity attacker)
            if (EGUtils.hasFullSuitOfArmorOn(player) && EGUtils.hasCorrectArmorOn(EGArmorMaterials.ELECTRUM, player) && player.getInventory().armor.stream().allMatch(armor -> armor.getOrCreateTag().getBoolean("IsCharged"))) {
                attacker.hurt(player.damageSources().thorns(player), 7);
                player.level().playSound(null, player.blockPosition(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.PLAYERS);
                player.getInventory().armor.forEach(armor -> armor.getOrCreateTag().putBoolean("IsCharged", false));
            }
    }
}
