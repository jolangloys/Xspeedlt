package jolangloys.optimize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Classe décoratrice pour une liste, permettant d'ajouter des méthodes
 * spécifiques à nos traitements. Représente un carton avec la taille des objets
 * qu'il contient
 * 
 * @author Johann Langloys
 */
public class Package implements List<Integer> {

	/**
	 * La liste décorée par l'objet.
	 */
	private final List<Integer> list;

	/** Constructeur à partir d'une liste fournise */
	public Package(List<Integer> origin) {
		this.list = origin;
	}

	/** Constructeur simple, se base sur une {@link ArrayList} */
	public Package() {
		this(new ArrayList<>());
	}

	/**
	 * Indique la somme des tailles des éléments du carton
	 * 
	 * @param thePackage
	 * @return
	 */
	public int itemsSize() {
		return this.stream()
				.reduce(0, (a, b) -> a + b);
	}

	// A partir de ce point, il n'y a plus que des méthode déléguées
	@Override
	public void forEach(Consumer<? super Integer> action) {
		list.forEach(action);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public Iterator<Integer> iterator() {
		return list.iterator();
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	@Override
	public boolean add(Integer e) {
		return list.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return list.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Integer> c) {
		return list.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Integer> c) {
		return list.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	@Override
	public void replaceAll(UnaryOperator<Integer> operator) {
		list.replaceAll(operator);
	}

	@Override
	public boolean removeIf(Predicate<? super Integer> filter) {
		return list.removeIf(filter);
	}

	@Override
	public void sort(Comparator<? super Integer> c) {
		list.sort(c);
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public boolean equals(Object o) {
		return list.equals(o);
	}

	@Override
	public int hashCode() {
		return list.hashCode();
	}

	@Override
	public Integer get(int index) {
		return list.get(index);
	}

	@Override
	public Integer set(int index, Integer element) {
		return list.set(index, element);
	}

	@Override
	public void add(int index, Integer element) {
		list.add(index, element);
	}

	@Override
	public Stream<Integer> stream() {
		return list.stream();
	}

	@Override
	public Integer remove(int index) {
		return list.remove(index);
	}

	@Override
	public Stream<Integer> parallelStream() {
		return list.parallelStream();
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<Integer> listIterator() {
		return list.listIterator();
	}

	@Override
	public ListIterator<Integer> listIterator(int index) {
		return list.listIterator(index);
	}

	@Override
	public List<Integer> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	@Override
	public Spliterator<Integer> spliterator() {
		return list.spliterator();
	}

}
