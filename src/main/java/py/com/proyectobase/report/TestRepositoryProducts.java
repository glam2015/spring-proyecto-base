package py.com.proyectobase.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ar.com.fdvs.dj.util.SortUtils;

public class TestRepositoryProducts {

	public static List getDummyCollection() {

		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.applyPattern("dd/MM/yyyy");

		List col = new ArrayList();

		col.add(new Product(new Long("1"), "Libro", "Harry Potter 7",
				"Asuncion", "Palma", new Long("2500"), new Float("10000")));
		col.add(new Product(new Long("1"), "Farmaco", "Amoxicilina",
				"Asuncion", "Eusebio Ayala", new Long("1400"), new Float(
						"2831.32")));
		col.add(new Product(new Long("1"), "Farmaco", "Penicilina", "Asuncion",
				"Eusebio Ayala", new Long("1400"), new Float("2831.32")));
		// The collection is ordered by State, Branch and Product Line
		col.add(new Product(new Long("1"), "book", "Harry Potter 7", "Florida",
				"Main Street", new Long("2500"), new Float("10000")));
		col.add(new Product(new Long("1"), "book", "Harry Potter 7", "Florida",
				"Railway Station", new Long("1400"), new Float("2831.32")));
		col.add(new Product(new Long("1"), "book", "Harry Potter 7", "Florida",
				"Baseball Stadium", new Long("4000"), new Float("38347")));
		col.add(new Product(new Long("1"), "book", "Harry Potter 7", "Florida",
				"Shopping Center", new Long("3000"), new Float("9482.4")));
		col.add(new Product(new Long("1"), "book", "Harry Potter 7",
				"New York", "Main Street", new Long("2500"), new Float(
						"27475.5")));
		col.add(new Product(new Long("1"), "book", "Harry Potter 7",
				"New York", "Railway Station", new Long("1400"), new Float(
						"3322")));
		col.add(new Product(new Long("1"), "book", "Harry Potter 7",
				"New York", "Baseball Stadium", new Long("4000"), new Float(
						"78482")));
		col.add(new Product(new Long("1"), "book", "Harry Potter 7",
				"New York", "Shopping Center", new Long("3000"), new Float(
						"5831.32")));
		col.add(new Product(new Long("2"), "book", "The Sum of All Fears",
				"New York", "Main Street", new Long("1500"),
				new Float("8329.2")));
		col.add(new Product(new Long("2"), "book", "The Sum of All Fears",
				"New York", "Railway Station", new Long("2500"), new Float(
						"27475.5")));
		col.add(new Product(new Long("3"), "book", "The Pelican Brief,",
				"New York", "Main Street", new Long("2500"), new Float("38347")));
		col.add(new Product(new Long("3"), "book", "The Pelican Brief,",
				"New York", "Railway Station", new Long("1400"), new Float(
						"9482.4")));
		col.add(new Product(new Long("3"), "book", "The Pelican Brief,",
				"New York", "Baseball Stadium", new Long("1500"), new Float(
						"8329.2")));
		col.add(new Product(new Long("3"), "book", "The Pelican Brief,",
				"New York", "Shopping Center", new Long("2500"), new Float(
						"27475.5")));
		col.add(new Product(new Long("3"), "book", "The Pelican Brief,",
				"Washington", "Main Street", new Long("1400"),
				new Float("3322")));
		col.add(new Product(new Long("3"), "book", "The Pelican Brief,",
				"Washington", "Railway Station", new Long("4000"), new Float(
						"78482")));
		col.add(new Product(new Long("3"), "book", "The Pelican Brief,",
				"Washington", "Baseball Stadium", new Long("3000"), new Float(
						"5831.32")));
		col.add(new Product(new Long("3"), "book", "The Pelican Brief,",
				"Washington", "Shopping Center", new Long("4000"), new Float(
						"78482")));
		col.add(new Product(new Long("3"), "book", "The Pelican Brief,",
				"Arizona", "Main Street", new Long("3000"),
				new Float("2831.32")));
		col.add(new Product(new Long("3"), "book", "The Pelican Brief,",
				"Arizona", "Railway Station", new Long("1500"), new Float(
						"38347")));

		col.add(new Product(new Long("3"), "book", "The Pelican Brief,",
				"Arizona", "Shopping Center", new Long("1400"), new Float(
						"8329.2")));
		col.add(new Product(new Long("4"), "dvd", "Titanic", "Florida",
				"Main Street", new Long("4000"), new Float("27475.5")));
		col.add(new Product(new Long("4"), "dvd", "Titanic", "Florida",
				"Railway Station", new Long("3000"), new Float("3322")));
		col.add(new Product(new Long("4"), "dvd", "Titanic", "Florida",
				"Baseball Stadium", new Long("1500"), new Float("78482")));
		col.add(new Product(new Long("4"), "dvd", "Titanic", "Florida",
				"Shopping Center", new Long("2500"), new Float("5831.32")));
		col.add(new Product(new Long("4"), "dvd", "Titanic", "New York",
				"Main Street", new Long("1400"), new Float("78482")));
		col.add(new Product(new Long("4"), "dvd", "Titanic", "New York",
				"Railway Station", new Long("1500"), new Float("2831.32")));
		col.add(new Product(new Long("4"), "dvd", "Titanic", "New York",
				"Baseball Stadium", new Long("2500"), new Float("38347")));
		col.add(new Product(new Long("4"), "dvd", "Titanic", "New York",
				"Shopping Center", new Long("1400"), new Float("9482.4")));
		col.add(new Product(new Long("4"), "dvd", "Titanic", "Washington",
				"Main Street", new Long("4000"), new Float("8329.2")));
		col.add(new Product(new Long("4"), "dvd", "Titanic", "Washington",
				"Railway Station", new Long("3000"), new Float("27475.5")));
		col.add(new Product(new Long("4"), "dvd", "Titanic", "Washington",
				"Baseball Stadium", new Long("4000"), new Float("3322")));
		col.add(new Product(new Long("4"), "dvd", "Titanic", "Washington",
				"Shopping Center", new Long("3000"), new Float("78482")));
		col.add(new Product(new Long("4"), "dvd", "Titanic", "Arizona",
				"Main Street", new Long("1500"), new Float("5831.32")));
		col.add(new Product(new Long("4"), "dvd", "Titanic", "Arizona",
				"Railway Station", new Long("8400"), new Float("3322")));
		col.add(new Product(new Long("4"), "dvd", "Titanic", "Arizona",
				"Shopping Center", new Long("4000"), new Float("5831.32")));
		col.add(new Product(new Long("6"), "dvd", "Monsters Inc", "New York",
				"Railway Station", new Long("1400"), new Float("78482")));
		col.add(new Product(new Long("6"), "dvd", "Monsters Inc", "New York",
				"Baseball Stadium", new Long("4000"), new Float("2831.32")));
		col.add(new Product(new Long("6"), "dvd", "Monsters Inc", "New York",
				"Shopping Center", new Long("3000"), new Float("38347")));
		col.add(new Product(new Long("6"), "dvd", "Monsters Inc", "Washington",
				"Main Street", new Long("4000"), new Float("9482.4")));
		col.add(new Product(new Long("6"), "dvd", "Monsters Inc", "Washington",
				"Railway Station", new Long("3000"), new Float("8329.2")));
		col.add(new Product(new Long("7"), "magazine", "Sports Illustrated",
				"New York", "Main Street", new Long("1400"), new Float("3322")));
		col.add(new Product(new Long("7"), "magazine", "Sports Illustrated",
				"New York", "Railway Station", new Long("4000"), new Float(
						"78482")));
		col.add(new Product(new Long("7"), "magazine", "Sports Illustrated",
				"New York", "Baseball Stadium", new Long("3000"), new Float(
						"5831.32")));
		col.add(new Product(new Long("10"), "food", "snickers", "New York",
				"Main Street", new Long("1400"), new Float("3322")));
		col.add(new Product(new Long("10"), "food", "snickers", "New York",
				"Railway Station", new Long("1500"), new Float("78482")));
		col.add(new Product(new Long("10"), "food", "snickers", "New York",
				"Baseball Stadium", new Long("2500"), new Float("5831.32")));
		return col;
	}

	public static List getDummyCollectionSmall() {

		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.applyPattern("dd/MM/yyyy");

		List col = new ArrayList();

		// The collection is ordered by State, Branch and Product Line
		col.add(new Product(new Long("1"), "book", "Harry Potter 7", "Florida",
				"Main Street", new Long("250"), new Float("10000"), true));
		col.add(new Product(new Long("1"), "book", "Harry Potter 7", "Florida",
				"Railway Station", new Long("400"), new Float("2831.32"), true));
		col.add(new Product(new Long("1"), "book", "Harry Potter 7", "Florida",
				"Baseball Stadium", new Long("440"), null, false));
		col.add(new Product(new Long("1"), "book", "Harry Potter 7", "Florida",
				"Shopping Center", new Long("300"), new Float("9482.4"), false));
		col.add(new Product(new Long("1"), "book", "Harry Potter 7",
				"New York", "Main Street", new Long("500"),
				new Float("27475.5"), true));
		col.add(new Product(new Long("1"), "book", "Harry Potter 7",
				"New York", "Railway Station", new Long("640"), new Float(
						"3322"), true));
		col.add(new Product(new Long("1"), "book", "Harry Potter 7",
				"New York", "Baseball Stadium", new Long("100"), new Float(
						"78482"), false));
		col.add(new Product(new Long("1"), "book", "Harry Potter 7",
				"New York", "Shopping Center", new Long("70"), new Float(
						"5831.32"), false));
		col.add(new Product(new Long("3"), "book", "The Pelican Brief,",
				"Washington", "Main Street", null, new Float("3322"), true));
		col.add(new Product(new Long("3"), "book", "The Pelican Brief,",
				"Washington", "Railway Station", new Long("98"), new Float(
						"78482"), true));
		col.add(new Product(new Long("3"), "book", "The Pelican Brief,",
				"Washington", "Baseball Stadium", new Long("613"), new Float(
						"5831.32"), false));
		col.add(new Product(new Long("3"), "book", "The Pelican Brief,",
				"Washington", "Shopping Center", new Long("87"), null, false));
		col.add(new Product(new Long("3"), "book", "The Pelican Brief,",
				"Arizona", "Main Street", new Long("250"),
				new Float("2831.32"), true));
		col.add(new Product(new Long("3"), "book", "The Pelican Brief,",
				"Arizona", "Railway Station", new Long("550"), new Float(
						"38347"), false));

		return col;
	}

	public static List getDummyCollectionSorted1() {

		List list = getDummyCollection();
		return SortUtils.sortCollection(list, new String[] { "state", "branch",
				"item" });

	}

	public static void main(String[] args) {

		System.out.println(getDummyCollectionSorted1());
	}
}
