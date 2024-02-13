package com.cerbon.electrum_gear.event;

import com.cerbon.electrum_gear.ElectrumGear;
import com.cerbon.electrum_gear.capability.TimerProvider;
import com.cerbon.electrum_gear.config.EGConfigs;
import com.cerbon.electrum_gear.item.EGArmorMaterials;
import com.cerbon.electrum_gear.item.custom.*;
import com.cerbon.electrum_gear.util.EGUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ElectrumGear.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void onAttachCapabilityItemStack(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject() == null) return;

        if (event.getObject().getItem() instanceof ElectrumPickaxeItem)
            event.addCapability(new ResourceLocation(ElectrumGear.MOD_ID, "timer"), new TimerProvider());

        if (event.getObject().getItem() instanceof ElectrumAxeItem)
            event.addCapability(new ResourceLocation(ElectrumGear.MOD_ID, "timer"), new TimerProvider());

        if (event.getObject().getItem() instanceof ElectrumHoeItem)
            event.addCapability(new ResourceLocation(ElectrumGear.MOD_ID, "timer"), new TimerProvider());

        if (event.getObject().getItem() instanceof ElectrumShovelItem)
            event.addCapability(new ResourceLocation(ElectrumGear.MOD_ID, "timer"), new TimerProvider());

        if (event.getObject().getItem() instanceof ElectrumSwordItem)
            event.addCapability(new ResourceLocation(ElectrumGear.MOD_ID, "timer"), new TimerProvider());
    }

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
                attacker.hurt(player.damageSources().thorns(player), EGConfigs.ARMOR_THUNDER_DAMAGE.get());
                player.level().playSound(null, player.blockPosition(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.PLAYERS);
                player.getInventory().armor.forEach(armor -> armor.getOrCreateTag().putBoolean("IsCharged", false));
            }
    }

    @SubscribeEvent
    public static void onShieldBlock(ShieldBlockEvent event) {
        if (event.getEntity().getUseItem().getItem() instanceof ElectrumShieldItem) {
            ItemStack shield = event.getEntity().getUseItem();
            shield.getOrCreateTag().putInt("Hit", shield.getOrCreateTag().getInt("Hit") + 1);

            if (shield.getOrCreateTag().getInt("Hit") > EGConfigs.HITS_TO_CHARGE_SHIELD.get() && event.getDamageSource().getDirectEntity() instanceof LivingEntity attacker) {
                attacker.hurt(event.getEntity().damageSources().thorns(event.getEntity()), EGConfigs.SHIELD_THUNDER_DAMAGE.get());
                event.getEntity().level().playSound(null, event.getEntity().blockPosition(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.PLAYERS);
                shield.getOrCreateTag().putInt("Hit", 0);
            }
        }
    }
}
