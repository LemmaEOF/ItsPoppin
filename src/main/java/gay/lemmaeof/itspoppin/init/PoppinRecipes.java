package gay.lemmaeof.itspoppin.init;

import gay.lemmaeof.itspoppin.ItsPoppin;
import gay.lemmaeof.itspoppin.recipe.SaltingRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.registry.Registry;

public class PoppinRecipes {
	public static final RecipeSerializer<SaltingRecipe> SALTING = new SaltingRecipe.Serializer();

	public static void init() {
		ItsPoppin.AUTOREG.autoRegister(Registry.RECIPE_SERIALIZER, PoppinRecipes.class, RecipeSerializer.class);
	}
}
