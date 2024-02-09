package com.cerbon.electrum_gear.misc;

import com.cerbon.electrum_gear.ElectrumGear;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class EGRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(Registries.RECIPE_SERIALIZER, ElectrumGear.MOD_ID);

    public static RegistryObject<RecipeSerializer<?>> ELECTRUM_SHIELD_DECORATION_RECIPE = RECIPES.register("crafting_special_electrum_shield_decoration",
            () -> new SimpleCraftingRecipeSerializer<>(ElectrumShieldDecorationRecipe::new));

    public static void register(IEventBus eventBus) {
        RECIPES.register(eventBus);
    }
}
