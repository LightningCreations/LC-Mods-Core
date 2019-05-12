package github.chorman0773.gac14.util;

import java.util.Comparator;
import java.util.function.Function;

import net.minecraft.util.ResourceLocation;

public interface Comparators {
	static final Comparator<ResourceLocation> stringOrder = Comparators.with(String.CASE_INSENSITIVE_ORDER, ResourceLocation::toString);

	static <T,U> Comparator<T> with(Comparator<? super U> comparator, Function<? super T,? extends U> func) {
		// TODO Auto-generated method stub
		return Comparator.comparing(func, comparator);
	}
}
