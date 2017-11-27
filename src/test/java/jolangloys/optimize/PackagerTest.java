package jolangloys.optimize;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Streams;

/**
 * Classe de tests JUnit
 * 
 * @author Johann Langloys
 */
public class PackagerTest {

	@Test
	public void test1() {
		final int maxPackageSize = 10;
		final List<Integer> items = Arrays.asList(1, 2, 5, 8, 9, 5, 4, 6, 3, 8, 2, 1, 4, 8, 6, 8, 6, 4, 2, 7, 1, 1, 3,
				4);

		final Packager packager = new Packager(items, maxPackageSize);
		final List<Package> result = Streams.stream(packager)
				.collect(Collectors.toList());

		verifyResult(maxPackageSize, items, result);
	}

	@Test
	public void test2() {
		final int maxPackageSize = 20;
		final List<Integer> items = Arrays.asList(1, 2, 5, 8, 9, 5, 4, 6, 3, 12, 2, 1, 4, 8, 6, 8, 6, 16, 2, 7, 13, 3,
				4);

		final Packager packager = new Packager(items, maxPackageSize);
		final List<Package> result = Streams.stream(packager)
				.collect(Collectors.toList());

		verifyResult(maxPackageSize, items, result);
	}

	private void verifyResult(final int maxPackageSize, final List<Integer> items, final List<Package> result) {
		// On vérifie qu'aucun paquet ne dépasse une taille de 10
		Assert.assertTrue(result.stream()
				.allMatch(p -> p.stream()
						.mapToInt(i -> i)
						.sum() <= maxPackageSize));

		// On vérifie qu'on retrouve bien tout les objets passés en entrée du
		// packager
		Assert.assertEquals(items.stream()
				.sorted()
				.collect(Collectors.toList()),
				result.stream()
						.flatMap(p -> p.stream())
						.sorted()
						.collect(Collectors.toList()));
	}

}
