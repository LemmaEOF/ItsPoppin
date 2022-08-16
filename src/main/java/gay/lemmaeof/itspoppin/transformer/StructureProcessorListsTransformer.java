package gay.lemmaeof.itspoppin.transformer;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import gay.lemmaeof.itspoppin.init.PoppinBlocks;
import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

import net.minecraft.block.Blocks;
import net.minecraft.structure.processor.RuleStructureProcessor;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorRule;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.RandomBlockMatchRuleTest;

@Patch.Class("net/minecraft/structure/processor/StructureProcessorLists")
public class StructureProcessorListsTransformer extends MiniTransformer {
	@Patch.Method("register(Ljava/lang/String;Lcom/google/common/collect/ImmutableList;)Lnet/minecraft/util/Holder;")
	public void patchCornCrops(PatchContext ctx) {
		ctx.jumpToStart();
		ctx.add(
				ALOAD(0),
				ALOAD(1),
				INVOKESTATIC("gay/lemmaeof/itspoppin/transformer/StructureProcessorListsTransformer$Hooks", "addExtraProcessors", "(Ljava/lang/String;Lcom/google/common/collect/ImmutableList;)Lcom/google/common/collect/ImmutableList;"),
				ASTORE(1)
		);
	}

	public static class Hooks {
		public static ImmutableList<StructureProcessor> addExtraProcessors(String name, ImmutableList<StructureProcessor> orig) {
			if (PoppinBlocks.ADD_CORN_TO.contains(name)) {
				List<StructureProcessor> processors = new ArrayList<>(orig);
				//this is kinda gross and hardcoded but oh well
				StructureProcessor first = processors.get(0);
				if (first instanceof RuleStructureProcessor rule) {
					List<StructureProcessorRule> rules = new ArrayList<>(rule.rules);
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
}
