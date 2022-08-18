package gay.lemmaeof.itspoppin.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import gay.lemmaeof.itspoppin.init.PoppinItems;
import gay.lemmaeof.itspoppin.init.PoppinRecipes;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public class SaltingRecipe extends ShapelessRecipe {
	public SaltingRecipe(Identifier identifier, String string, ItemStack itemStack, DefaultedList<Ingredient> defaultedList) {
		super(identifier, string, itemStack, defaultedList);
	}

	@Override
	public ItemStack craft(CraftingInventory inv) {
		ItemStack result = super.craft(inv);
		for (int i = 0; i < inv.size(); i++) {
			ItemStack stack = inv.getStack(i);
			if (stack.getItem() == PoppinItems.POPCORN_SALT) {
				Potion pot = PotionUtil.getPotion(stack);
				PotionUtil.setPotion(result, pot);
				return result;
			}
		}
		return result;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return PoppinRecipes.SALTING;
	}

	public static class Serializer implements RecipeSerializer<SaltingRecipe> {

		@Override
		public SaltingRecipe read(Identifier id, JsonObject json) {
			String string = JsonHelper.getString(json, "group", "");
			DefaultedList<Ingredient> defaultedList = getIngredients(JsonHelper.getArray(json, "ingredients"));
			if (defaultedList.isEmpty()) {
				throw new JsonParseException("No ingredients for shapeless recipe");
			} else if (defaultedList.size() > 9) {
				throw new JsonParseException("Too many ingredients for shapeless recipe");
			} else {
				ItemStack itemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));
				return new SaltingRecipe(id, string, itemStack, defaultedList);
			}
		}

		private static DefaultedList<Ingredient> getIngredients(JsonArray json) {
			DefaultedList<Ingredient> defaultedList = DefaultedList.of();

			for(int i = 0; i < json.size(); ++i) {
				Ingredient ingredient = Ingredient.fromJson(json.get(i));
				if (!ingredient.isEmpty()) {
					defaultedList.add(ingredient);
				}
			}

			return defaultedList;
		}

		@Override
		public SaltingRecipe read(Identifier id, PacketByteBuf buf) {
			String string = buf.readString();
			int i = buf.readVarInt();
			DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);

			for(int j = 0; j < defaultedList.size(); ++j) {
				defaultedList.set(j, Ingredient.fromPacket(buf));
			}

			ItemStack itemStack = buf.readItemStack();
			return new SaltingRecipe(id, string, itemStack, defaultedList);
		}

		@Override
		public void write(PacketByteBuf buf, SaltingRecipe recipe) {
			buf.writeString(recipe.getGroup());
			buf.writeVarInt(recipe.getIngredients().size());

			for(Ingredient ingredient : recipe.getIngredients()) {
				ingredient.write(buf);
			}

			buf.writeItemStack(recipe.getOutput());
		}
	}
}
