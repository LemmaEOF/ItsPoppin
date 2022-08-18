package gay.lemmaeof.itspoppin.mixin;

import com.google.common.collect.ImmutableList;
import gay.lemmaeof.itspoppin.init.PoppinBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.structure.processor.*;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.RandomBlockMatchRuleTest;
import net.minecraft.util.Holder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(StructureProcessorLists.class)
public class MixinStructureProcessorLists {
	private static ThreadLocal<String> idLocal = new ThreadLocal<>();

	@Inject(method = "register", at = @At("HEAD"))
	private static void setIdLocal(String id, ImmutableList<StructureProcessor> processorList, CallbackInfoReturnable<Holder<StructureProcessorList>> info) {
		if (idLocal == null) idLocal = new ThreadLocal<>();
		idLocal.set(id);
	}

	@ModifyArg(method = "register", at = @At(value = "INVOKE", target = "net/minecraft/structure/processor/StructureProcessorList.<init>(Ljava/util/List;)V"))
	private static List<StructureProcessor> hookProcessors(List<StructureProcessor> orig) {
		if (PoppinBlocks.ADD_CORN_TO.contains(idLocal.get())) {
			List<StructureProcessor> processors = new ArrayList<>(orig);
			//this is kinda gross and hardcoded but oh well
			StructureProcessor first = processors.get(0);
			if (first instanceof RuleStructureProcessor rule) {
				List<StructureProcessorRule> rules = new ArrayList<>(((RuleStructureProcessorAccessor)rule).getRules());
				rules.add(
						new StructureProcessorRule(
								new RandomBlockMatchRuleTest(Blocks.WHEAT, 0.3F), AlwaysTrueRuleTest.INSTANCE, PoppinBlocks.CORN.getDefaultState()
						)
				);
				processors.set(0, new RuleStructureProcessor(ImmutableList.copyOf(rules)));
			}
			return ImmutableList.copyOf(processors);
		}
		return orig;
	}
}
