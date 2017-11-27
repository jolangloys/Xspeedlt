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
 * spécifiques à nos traitements.
 * 
 * @author Johann Langloys
 */
public class Package implements List<Integer> {

	private final List<Integer> origin;

	public Package(List<Integer> origin) {
		this.origin = origin;
	}

	public Package() {
		this.origin = new ArrayList<>();
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
		origin.forEach(action);
	}

	@Override
	public int size() {
		return origin.size();
	}

	@Override
	public boolean isEmpty() {
		return origin.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return origin.contains(o);
	}

	@Override
	public Iterator<Integer> iterator() {
		return origin.iterator();
	}

	@Override
	public Object[] toArray() {
		return origin.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return origin.toArray(a);
	}

	@Override
	public boolean add(Integer e) {
		return origin.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return origin.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return origin.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Integer> c) {
		return origin.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Integer> c) {
		return origin.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return origin.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return origin.retainAll(c);
	}

	@Override
	public void replaceAll(UnaryOperator<Integer> operator) {
		origin.replaceAll(operator);
	}

	@Override
	public boolean removeIf(Predicate<? super Integer> filter) {
		return origin.removeIf(filter);
	}

	@Override
	public void sort(Comparator<? super Integer> c) {
		origin.sort(c);
	}

	@Override
	public void clear() {
		origin.clear();
	}

	@Override
	public boolean equals(Object o) {
		return origin.equals(o);
	}

	@Override
	public int hashCode() {
		return origin.hashCode();
	}

	@Override
	public Integer get(int index) {
		return origin.get(index);
	}

	@Override
	public Integer set(int index, Integer element) {
		return origin.set(index, element);
	}

	@Override
	public void add(int index, Integer element) {
		origin.add(index, element);
	}

	@Override
	public Stream<Integer> stream() {
		return origin.stream();
	}

	@Override
	public Integer remove(int index) {
		return origin.remove(index);
	}

	@Override
	public Stream<Integer> parallelStream() {
		return origin.parallelStream();
	}

	@Override
	public int indexOf(Object o) {
		return origin.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return origin.lastIndexOf(o);
	}

	@Override
	public ListIterator<Integer> listIterator() {
		return origin.listIterator();
	}

	@Override
	public ListIterator<Integer> listIterator(int index) {
		return origin.listIterator(index);
	}

	@Override
	public List<Integer> subList(int fromIndex, int toIndex) {
		return origin.subList(fromIndex, toIndex);
	}

	@Override
	public Spliterator<Integer> spliterator() {
		return origin.spliterator();
	}

}
