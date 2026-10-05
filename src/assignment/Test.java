package assignment;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Test {




        private static final Scanner scanner = new Scanner(System.in);
        private static final Library library = new Library();

        public static void main(String[] args) {

            boolean running = true;

            while (running) {

                printMenu();

                int choice = readInt("Enter choice: ");

                switch (choice) {

                    case 1 -> addItem();

                    case 2 -> addMember();

                    case 3 -> borrowItem();

                    case 4 -> returnItem();

                    case 5 -> library.listCatalog();

                    case 6 -> library.printReport();

                    case 7 -> {
                        running = false;
                        System.out.println("Goodbye!");
                    }

                    default ->
                            System.out.println(
                                    "Invalid choice. " +
                                            "Please enter a number from 1 to 7."
                            );
                }

                System.out.println();
            }

            scanner.close();
        }

        private static void printMenu() {

            System.out.println("===== Library Lending System =====");

            System.out.println("1. Add Item");
            System.out.println("2. Add Member");
            System.out.println("3. Borrow Item");
            System.out.println("4. Return Item");
            System.out.println("5. List Catalog");
            System.out.println("6. Report");
            System.out.println("7. Exit");
        }

        private static void addItem() {

            System.out.println("--- Add Item ---");

            System.out.println("1. Book");
            System.out.println("2. Magazine");
            System.out.println("3. DVD");

            int type = readInt("Choose item type: ");

            String title = readNonEmptyString("Title: ");

            try {

                LibraryItem item;

                switch (type) {

                    case 1 -> {

                        String author =
                                readNonEmptyString("Author: ");

                        int pages =
                                readPositiveInt("Pages: ");

                        item = new Book(
                                title,
                                author,
                                pages
                        );
                    }

                    case 2 -> {

                        int issueNumber =
                                readPositiveInt("Issue number: ");

                        item = new Magazine(
                                title,
                                issueNumber
                        );
                    }

                    case 3 -> {

                        int runtime =
                                readPositiveInt(
                                        "Runtime minutes: "
                                );

                        item = new DVD(
                                title,
                                runtime
                        );
                    }

                    default -> {

                        System.out.println(
                                "Invalid item type."
                        );

                        return;
                    }
                }

                library.addItem(item);

                System.out.println(
                        "Item added successfully: " +
                                item.getId()
                );

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Could not add item: " +
                                e.getMessage()
                );
            }
        }

        private static void addMember() {

            System.out.println("--- Add Member ---");

            String memberId =
                    readNonEmptyString("Member id: ");

            String name =
                    readNonEmptyString("Name: ");

            int maxAllowed =
                    readPositiveInt(
                            "Maximum allowed items: "
                    );

            try {

                Member member =
                        new Member(
                                memberId,
                                name,
                                maxAllowed
                        );

                library.addMember(member);

                System.out.println(
                        "Member added successfully: " +
                                member.getMemberId()
                );

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Could not add member: " +
                                e.getMessage()
                );
            }
        }

        private static void borrowItem() {

            String memberId =
                    readNonEmptyString("Member id: ");

            String itemId =
                    readNonEmptyString("Item id: ");

            try {

                library.borrowItem(
                        memberId,
                        itemId
                );

                System.out.println(
                        "Borrowed " +
                                itemId +
                                " to " +
                                memberId +
                                "."
                );

            } catch (LibraryException e) {

                System.out.println(
                        "Could not borrow: " +
                                e.getMessage()
                );
            }
        }

        private static void returnItem() {

            String memberId =
                    readNonEmptyString("Member id: ");

            String itemId =
                    readNonEmptyString("Item id: ");

            try {

                library.returnItem(
                        memberId,
                        itemId
                );

                System.out.println(
                        "Returned " +
                                itemId +
                                " from " +
                                memberId +
                                "."
                );

            } catch (LibraryException e) {

                System.out.println(
                        "Could not return: " +
                                e.getMessage()
                );
            }
        }

        private static int readInt(String prompt) {

            while (true) {

                System.out.print(prompt);

                try {

                    int value = scanner.nextInt();

                    scanner.nextLine();

                    return value;

                } catch (InputMismatchException e) {

                    System.out.println(
                            "Invalid input. " +
                                    "Please enter a whole number."
                    );

                    scanner.nextLine();
                }
            }
        }

        private static int readPositiveInt(String prompt) {

            while (true) {

                int value = readInt(prompt);

                if (value > 0) {
                    return value;
                }

                System.out.println(
                        "Value must be greater than 0."
                );
            }
        }

        private static String readNonEmptyString(
                String prompt
        ) {

            while (true) {

                System.out.print(prompt);

                String value =
                        scanner.nextLine().trim();

                if (!value.isEmpty()) {
                    return value;
                }

                System.out.println(
                        "Value cannot be empty."
                );
            }
        }
    }

