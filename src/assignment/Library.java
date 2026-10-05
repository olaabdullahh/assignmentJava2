package assignment;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Library {

    private final Map<String, LibraryItem> catalog;
    private final Map<String, Member> members;
    private final Set<String> borrowedIds;

    public Library() {
        catalog = new LinkedHashMap<>();
        members = new LinkedHashMap<>();
        borrowedIds = new LinkedHashSet<>();
    }

    public void addItem(LibraryItem item) {

        if (item == null) {
            throw new IllegalArgumentException(
                    "Item cannot be null."
            );
        }

        if (catalog.containsKey(item.getId())) {
            throw new IllegalArgumentException(
                    "Item ID already exists: " + item.getId()
            );
        }

        catalog.put(item.getId(), item);
    }

    public void addMember(Member member) {

        if (member == null) {
            throw new IllegalArgumentException(
                    "Member cannot be null."
            );
        }

        if (members.containsKey(member.getMemberId())) {
            throw new IllegalArgumentException(
                    "Member ID already exists: " + member.getMemberId()
            );
        }

        members.put(member.getMemberId(), member);
    }

    public void borrowItem(
            String memberId,
            String itemId
    ) throws LibraryException {

        Member member = members.get(memberId);

        if (member == null) {
            throw new LibraryException(
                    "member " + memberId + " does not exist."
            );
        }

        LibraryItem item = catalog.get(itemId);

        if (item == null) {
            throw new LibraryException(
                    "item " + itemId + " does not exist."
            );
        }

        if (item.isBorrowed()) {
            throw new LibraryException(
                    "item " + itemId + " is already out."
            );
        }

        if (!member.canBorrowMore()) {
            throw new LibraryException(
                    "member " + memberId +
                            " has reached the borrowing limit of " +
                            member.getMaxAllowed() + "."
            );
        }

        item.markBorrowed();

        member.addBorrowedItem(item);

        borrowedIds.add(item.getId());
    }

    public void returnItem(
            String memberId,
            String itemId
    ) throws LibraryException {

        Member member = members.get(memberId);

        if (member == null) {
            throw new LibraryException(
                    "member " + memberId + " does not exist."
            );
        }

        LibraryItem item = catalog.get(itemId);

        if (item == null) {
            throw new LibraryException(
                    "item " + itemId + " does not exist."
            );
        }

        if (!member.hasBorrowedItem(itemId)) {
            throw new LibraryException(
                    "member " + memberId +
                            " does not have item " + itemId + "."
            );
        }

        member.removeBorrowedItem(itemId);

        item.markReturned();

        borrowedIds.remove(itemId);
    }

    public void listCatalog() {

        if (catalog.isEmpty()) {
            System.out.println("Catalog is empty.");
            return;
        }

        for (LibraryItem item : catalog.values()) {

            item.displayInfo();
        }
    }

    public void printReport() {

        Map<String, Integer> itemsByType = new TreeMap<>();

        for (LibraryItem item : catalog.values()) {

            itemsByType.merge(
                    item.getType(),
                    1,
                    Integer::sum
            );
        }

        System.out.println("---------- REPORT ----------");

        System.out.println(
                "Total items : " + catalog.size()
        );

        System.out.println(
                "Currently out : " + borrowedIds.size()
        );

        System.out.println(
                "Borrowed ids : " + borrowedIds
        );

        System.out.println(
                "Items by type : " + itemsByType
        );

        System.out.println(
                "Total created : " +
                        LibraryItem.getTotalItemsCreated()
        );

        System.out.println("----------------------------");
    }
}
