package net.demengel.refactoring.vrs.it;

import static net.demengel.refactoring.vrs.xxx.FakeCustomerTable.newCustomer;
import static net.demengel.refactoring.vrs.xxx.FakeMovieTable.newMovie;
import static net.demengel.refactoring.vrs.xxx.FakeReferentialTable.insertPropertyAndValueIntoReferentialTable;
import net.demengel.refactoring.vrs.xxx.FakeDb;

import org.joda.time.LocalDate;

public class TestDatabase {

    public static void init() {
        FakeDb.truncateAllTables();

        LocalDate now = new LocalDate();

        newMovie("BEKINDREWI2008").title("Be Kind Rewind").country("US").releaseDate("2008/01/20").rentalStart("2008/04/26").genres("Comedy", "Drama")
                .duration(102).director("Michel Gondry").writers("Michel Gondry").cast("Jack Black", "Mos Def", "Danny Glover").ownedQuantity(5).insert();
        newMovie("THEDARKKNI2012").title("The Dark Knight Rises").country("US").releaseDate("2012/07/16").rentalStart(now.minusMonths(2))
                .genres("Action", "Crime", "Thriller").duration(165).director("Christopher Nolan").writers("Jonathan Nolan", "Christopher Nolan")
                .cast("Christian Bale", "Tom Hardy", "Anne Hathaway").ownedQuantity(21).insert();
        newMovie("AVENGERS2012").title("Avengers").country("US").releaseDate("2012/04/11").rentalStart(now.minusMonths(5)).genres("Action").duration(143)
                .director("Joss Whedon").writers("Zak Penn", "Joss Whedon").cast("Robert Downey Jr.", "Chris Evans", "Scarlett Johansson").ownedQuantity(17)
                .insert();
        newMovie("SOULKITCH2009").title("Soul Kitchen").country("DE").releaseDate("2009/12/25").rentalStart("2010/08/01").genres("Comedy", "Drama")
                .duration(99).director("Fatih Akin").writers("Fatih Akin", "Adam Bousdoukos").cast("Adam Bousdoukos", "Moritz Bleibtreu", "Birol Ünel")
                .ownedQuantity(2).insert();
        newMovie("LASOUPEAUX1981").title("La soupe aux choux").country("FR").releaseDate("1981/12/02").rentalStart("1985/11/05").genres("Comedy", "Sci-Fi")
                .duration(98).director("Jean Girault").writers("Louis de Funès", "René Fallet").cast("Louis de Funès", "Jean Carmet", "Jacques Villeret")
                .ownedQuantity(1).forcedPrice(1.2).insert();

        newCustomer("11111").name("John Doe").birthDate("1980/07/13").creditCardNumber("1111-2222-3333-4444").credits(65).addressLine1("221B Baker Street")
                .zipCode("W1U 6RS").city("LONDON").phoneNumber("+33 6 67 67 89 89").insert();
        newCustomer("22222").name("Arthur Dent").birthDate("1976/02/20").creditCardNumber("5555-6666-7777-8888").addressLine1("Sector ZZ9")
                .addressLine2("Plural Z Alpha").zipCode("ZZ9").city("Betelgeuse Seven").credits(12).phoneNumber("+89 1011 1213").insert();
        newCustomer("22333").name("Zaphod Beeblebox").birthDate("1976/02/20").creditCardNumber("5555-6666-7777-8888").addressLine1("Sector ZZ9")
                .addressLine2("Plural Z Alpha").zipCode("ZZ9").city("Betelgeuse Seven").credits(12).phoneNumber("+89 1011 1213").insert();

        insertPropertyAndValueIntoReferentialTable("MAX_RENTAL_DAYS", "7");
        insertPropertyAndValueIntoReferentialTable("PRICE_FOR_MOVIES_NO_OLDER_THAN_3_MONTHS", "42");
        insertPropertyAndValueIntoReferentialTable("PRICE_FOR_MOVIES_NO_OLDER_THAN_1_YEAR", "17.2");
        insertPropertyAndValueIntoReferentialTable("PRICE_FOR_MOVIES_OLDER_THAN_1_YEAR", "1.3");
    }
}
