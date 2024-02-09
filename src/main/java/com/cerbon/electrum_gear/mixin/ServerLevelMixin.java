package com.cerbon.electrum_gear.mixin;

import com.cerbon.electrum_gear.item.EGArmorMaterials;
import com.cerbon.electrum_gear.item.EGItems;
import com.cerbon.electrum_gear.util.EGUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {

    @Inject(method = "findLightningTargetAround", at = @At(value = "INVOKE", target = "Ljava/util/Optional;isPresent()Z"), cancellable = true)
    private void eg_findLightningTargetAround(BlockPos pos, CallbackInfoReturnable<BlockPos> cir) {
        ServerLevel serverLevel = (ServerLevel) (Object) this;

        List<ServerPlayer> serverPlayers = serverLevel.getPlayers(player -> EGUtils.hasAnyCorrectArmonOn(EGArmorMaterials.ELECTRUM, player) || EGUtils.hasAnyCorrectItemsInHand(EGItems.ITEMS.getEntries().stream().toList(), player));
        ServerPlayer serverPlayer = serverPlayers.isEmpty() ? null : serverPlayers.get(serverLevel.random.nextInt(serverPlayers.size()));
        if (serverPlayer == null) return;

        cir.setReturnValue(serverPlayer.blockPosition());
    }
}
