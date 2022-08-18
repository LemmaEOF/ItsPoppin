package gay.lemmaeof.itspoppin.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PopcornSaltItem extends Item {
	public PopcornSaltItem(Settings settings) {
		super(settings);
	}

	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		PotionUtil.buildTooltip(stack, tooltip, 1.0F);
	}
}
