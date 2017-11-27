package jolangloys.optimize;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.collect.Streams;

public class Organizer {

	private static final Pattern pattern = Pattern.compile("^[0-9]*$");

	/**
	 * Méthode d'entrée de l'algorythme
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {

		Arrays.stream(args)
				.filter(s -> pattern.matcher(s)
						.matches())
				.map(Organizer::stringToItemList)
				.map(Packager::new)
				.map(packager -> Streams.stream(packager)
						.map(Organizer::packageToString)
						.collect(Collectors.joining("/")))
				.forEach(System.out::println);
	}

	/**
	 * conversion de l'entrée en liste d'objets
	 * 
	 * @param sequence
	 *            l'entrée sous forme de séquence de chiffres
	 * @return
	 */
	private static List<Integer> stringToItemList(String sequence) {
		return Arrays.stream(sequence.split(""))
				.map(Integer::valueOf)
				.collect(Collectors.toList());
	}

	/**
	 * Convertion d'un package en string
	 * 
	 * @param thePackage
	 * @return
	 */
	private static String packageToString(Package thePackage) {
		return thePackage.stream()
				.map(i -> i.toString())
				.collect(Collectors.joining());
	}

}
