package jolangloys.optimize;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.OptionalInt;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * Un objet Package est cr�� � partir d'une liste d'objets (repr�sent� par des
 * entiers correspondant � la taille de l'objet). Il permet d'it�rer sur les
 * cartons g�n�r�s � partir de cette liste d'objets.
 * 
 * Les objets sont class�s par taille dans un {@link Multiset}, qui est vid� au
 * fur et � mesure des appels � la m�thode de lecture {@link Iterator#next()}
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
	 * Les "�tag�res". On regroupe les cartons par taille, en associant leur
	 * quantit� via un multiset guava. Si on ne souhaite pas utiliser guava, on
	 * peut le remplacer par un HashMap<Integer, Integer>
	 */
	private final Multiset<Integer> shelves;

	/**
	 * Constructeur prenant une taille de carton en param�tre
	 * 
	 * @param items
	 *            la liste des objets � mettre en carton
	 * @param maxPackageSize
	 *            La taille maximum que peut contenir un carton
	 * 
	 */
	public Packager(Iterable<Integer> items, int maxPackageSize) {
		super();
		this.maxPackageSize = maxPackageSize;

		this.shelves = HashMultiset.create(items);

		// V�rification d'un cas impossible � r�soudre: avoir des objets plus
		// volumineux que la taille des cartons.
		if (this.shelves.elementSet()
				.stream()
				.mapToInt(i -> i)
				.max()
				.orElse(0) > maxPackageSize) {
			throw new IllegalArgumentException("Les objets ne doivent pas �tre plus volumineux que les cartons");
		}
	}

	/**
	 * Constructeur se basant sur la taille standart de carton.
	 * {@link Packager#DEFAULT_MAX_PACKAGE_SIZE}
	 * 
	 * @param items
	 *            la liste des objets � mettre en carton
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
	 * Calcul de la composition du prochain carton � remplir.
	 * 
	 * @return La composition du prochain carton � remplir.
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
	 * D�termine le prochain objet � prendre dans les rayons � partir de la
	 * composition th�orique du prochain carton. La logique est simple: on prend
	 * le plus grand objet disponible qui peut rentrer dans le carton.
	 * 
	 * Le prochain carton, fourni en param�tre, n'est pas encore retir� des
	 * �tag�res. Il est donc pris en compte dans la comptabilisation de la
	 * disponibilit� des tailles.
	 * 
	 * @param nextPlannedPackage
	 *            la composition pr�vue du prochain carton
	 * 
	 * @return un optionel contenant la taille du prochain objet � prendre en
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
	 * V�rifie dans les rangements si un objet d'une taille donn�e est
	 * disponible, en prenant en compte les objets que l'on compte d�j� prendre
	 * dans le prochain carton, pour qu'ils ne soient pas comptabilis�s
	 * 
	 * @param itemSize
	 *            Taille de l'objet � v�rifier
	 * @param nextPlannedPackage
	 *            la composition actuelle du prochain carton pr�vu (les objets
	 *            correspondant sont toujours dans les rayons
	 * 
	 * @return vrai si un objet de la taille indiqu� est encore disponible dans
	 *         les rayons. Faux sinon.
	 */
	private boolean isItemSizeAvailable(int itemSize, Package nextPlannedPackage) {
		return shelves.count(itemSize) > nextPlannedPackage.stream()
				.filter(size -> size.intValue() == itemSize)
				.count();
	}
}
