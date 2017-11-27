package jolangloys.optimize;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.OptionalInt;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * Un objet Package est créé à partir d'une liste d'objets (représenté par des
 * entiers correspondant à la taille de l'objet). Il permet d'itérer sur les
 * cartons générés à partir de cette liste d'objets.
 * 
 * Les objets sont classés par taille dans un {@link Multiset}, qui est vidé au
 * fur et à mesure des appels à la méthode de lecture {@link Iterator#next()}
 * 
 * @author Johann Langloys
 */
public final class Packager implements Iterator<Package> {

	public static final int DEFAULT_MAX_PACKAGE_SIZE = 10;

	/**
	 * Taille maximale d'un package
	 */
	private final int maxPackageSize;

	/**
	 * Les "étagères". On regroupe les cartons par taille, en associant leur
	 * quantité via un multiset guava. Si on ne souhaite pas utiliser guava, on
	 * peut le remplacer par un HashMap<Integer, Integer>
	 */
	private final Multiset<Integer> shelves;

	/**
	 * Constructeur prenant une taille de carton en paramètre
	 * 
	 * @param items
	 *            la liste des objets à mettre en carton
	 * @param maxPackageSize
	 *            La taille maximum que peut contenir un carton
	 * 
	 */
	public Packager(Iterable<Integer> items, int maxPackageSize) {
		super();
		this.maxPackageSize = maxPackageSize;

		this.shelves = HashMultiset.create(items);

		// Vérification d'un cas impossible à résoudre: avoir des objets plus
		// volumineux que la taille des cartons.
		if (this.shelves.elementSet()
				.stream()
				.mapToInt(i -> i)
				.max()
				.orElse(0) > maxPackageSize) {
			throw new IllegalArgumentException("Les objets ne doivent pas être plus volumineux que les cartons");
		}
	}

	/**
	 * Constructeur se basant sur la taille standart de carton.
	 * {@link Packager#DEFAULT_MAX_PACKAGE_SIZE}
	 * 
	 * @param items
	 *            la liste des objets à mettre en carton
	 */
	public Packager(Iterable<Integer> items) {
		this(items, DEFAULT_MAX_PACKAGE_SIZE);
	}

	@Override
	public boolean hasNext() {
		return !this.shelves.isEmpty();
	}

	@Override
	public Package next() {
		if (!this.hasNext()) {
			throw new NoSuchElementException();
		}
		Package nextPackage = this.getNextPackage();
		nextPackage.forEach(shelves::remove);
		return nextPackage;
	}

	/**
	 * Calcul de la composition du prochain carton à remplir.
	 * 
	 * @return La composition du prochain carton à remplir.
	 */
	private Package getNextPackage() {
		final Package nextPackage = new Package();

		OptionalInt nextItem = OptionalInt.empty();
		do {
			nextItem = this.getNextItem(nextPackage);
			nextItem.ifPresent(nextPackage::add);
		} while (nextItem.isPresent());

		return nextPackage;
	}

	/**
	 * Détermine le prochain objet à prendre dans les rayons à partir de la
	 * composition théorique du prochain carton. La logique est simple: on prend
	 * le plus grand objet disponible qui peut rentrer dans le carton.
	 * 
	 * Le prochain carton, fourni en paramètre, n'est pas encore retiré des
	 * étagères. Il est donc pris en compte dans la comptabilisation de la
	 * disponibilité des tailles.
	 * 
	 * @param nextPlannedPackage
	 *            la composition prévue du prochain carton
	 * 
	 * @return un optionel contenant la taille du prochain objet à prendre en
	 *         rayon si un tel objet existe, sinon un optionel vide.
	 */
	private OptionalInt getNextItem(Package nextPlannedPackage) {

		for (int itemSize = this.maxPackageSize - nextPlannedPackage.itemsSize(); itemSize > 0; itemSize--) {

			if (this.isItemSizeAvailable(itemSize, nextPlannedPackage)) {
				return OptionalInt.of(itemSize);
			}
		}
		return OptionalInt.empty();
	}

	/**
	 * Vérifie dans les rangements si un objet d'une taille donnée est
	 * disponible, en prenant en compte les objets que l'on compte déjà prendre
	 * dans le prochain carton, pour qu'ils ne soient pas comptabilisés
	 * 
	 * @param itemSize
	 *            Taille de l'objet à vérifier
	 * @param nextPlannedPackage
	 *            la composition actuelle du prochain carton prévu (les objets
	 *            correspondant sont toujours dans les rayons
	 * 
	 * @return vrai si un objet de la taille indiqué est encore disponible dans
	 *         les rayons. Faux sinon.
	 */
	private boolean isItemSizeAvailable(int itemSize, Package nextPlannedPackage) {
		return shelves.count(itemSize) > nextPlannedPackage.stream()
				.filter(size -> size.intValue() == itemSize)
				.count();
	}
}
