package jolangloys.optimize;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.collect.Streams;

/**
 * Classe point d'entre de l'application. Elle permet de cr�er des
 * {@link Packager} � partir de cha�nes de chiffres et de les ex�cuter, pour
 * obtenir en retour les cha�nes organis�es en package
 * 
 * @author Johann Langloys
 *
 */
public final class Organizer {

	private static final Pattern pattern = Pattern.compile("^[1-9]*$");

	/** Constructeur priv� car classe "statique" non instanciable */
	private Organizer() {

	}

	/**
	 * M�thode d'entr�e de l'algorythme
	 * 
	 * @param args
	 *            une ou plusieures cha�nes de chiffres � interpr�ter
	 */
	public static void main(final String[] args) {

		Arrays.stream(args)
				.map(input -> organize(input))
				.forEach(System.out::println);
	}

	/**
	 * Interpr�tation d'une s�quence en entr�e
	 * 
	 * @param sequence
	 *            la chaine de chiffre � interpr�ter
	 * @return La chaine repr�sentant les packages
	 */
	private static String organize(String sequence) {
		if (!pattern.matcher(sequence)
				.matches()) {
			throw new IllegalArgumentException(
					String.format("Le param�tre %s n'est pas une chaine de num�ro valide", sequence));
		}

		List<Integer> items = stringToItemList(sequence);
		Packager packager = new Packager(items);
		return Streams.stream(packager)
				.map(p -> packageToString(p))
				.collect(Collectors.joining("/"));
	}

	/**
	 * conversion de l'entr�e sous forme de {@link String} en liste "d'objets"
	 * (liste d'entier)
	 * 
	 * @param sequence
	 *            l'entr�e sous forme de s�quence de chiffres
	 * @return
	 */
	private static List<Integer> stringToItemList(String sequence) {
		return Arrays.stream(sequence.split(""))
				.map(Integer::valueOf)
				.collect(Collectors.toList());
	}

	/**
	 * Convertion d'un package en string en concat�nant les tailles des objets.
	 * 
	 * @param thePackage
	 *            le carton � convertir en string � des fins d'affichage
	 * @return Un chaine de chiffres correspondant au contenu du carton
	 */
	private static String packageToString(Package thePackage) {
		return thePackage.stream()
				.map(i -> i.toString())
				.collect(Collectors.joining());
	}

}
