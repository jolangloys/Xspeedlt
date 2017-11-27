package jolangloys.optimize;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.collect.Streams;

/**
 * Classe point d'entre de l'application. Elle permet de créer des
 * {@link Packager} à partir de chaînes de chiffres et de les exécuter, pour
 * obtenir en retour les chaînes organisées en package
 * 
 * @author Johann Langloys
 *
 */
public final class Organizer {

	private static final Pattern pattern = Pattern.compile("^[1-9]*$");

	/** Constructeur privé car classe "statique" non instanciable */
	private Organizer() {

	}

	/**
	 * Méthode d'entrée de l'algorythme
	 * 
	 * @param args
	 *            une ou plusieures chaînes de chiffres à interpréter
	 */
	public static void main(final String[] args) {

		Arrays.stream(args)
				.map(input -> organize(input))
				.forEach(System.out::println);
	}

	/**
	 * Interprétation d'une séquence en entrée
	 * 
	 * @param sequence
	 *            la chaine de chiffre à interpréter
	 * @return La chaine représentant les packages
	 */
	private static String organize(String sequence) {
		if (!pattern.matcher(sequence)
				.matches()) {
			throw new IllegalArgumentException(
					String.format("Le paramètre %s n'est pas une chaine de numéro valide", sequence));
		}

		List<Integer> items = stringToItemList(sequence);
		Packager packager = new Packager(items);
		return Streams.stream(packager)
				.map(p -> packageToString(p))
				.collect(Collectors.joining("/"));
	}

	/**
	 * conversion de l'entrée sous forme de {@link String} en liste "d'objets"
	 * (liste d'entier)
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
	 * Convertion d'un package en string en concaténant les tailles des objets.
	 * 
	 * @param thePackage
	 *            le carton à convertir en string à des fins d'affichage
	 * @return Un chaine de chiffres correspondant au contenu du carton
	 */
	private static String packageToString(Package thePackage) {
		return thePackage.stream()
				.map(i -> i.toString())
				.collect(Collectors.joining());
	}

}
