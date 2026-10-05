package assignment;

import java.util.ArrayList;
import java.util.List;

public class Member {

    private String memberId;
    private String name;
    private int maxAllowed;

    private final List<LibraryItem> borrowedItems;

    public Member(String memberId, String name, int maxAllowed) {
        setMemberId(memberId);
        setName(name);
        setMaxAllowed(maxAllowed);

        borrowedItems = new ArrayList<>();
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Member ID cannot be null or empty."
            );
        }

        this.memberId = memberId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Member name cannot be null or empty."
            );
        }

        this.name = name.trim();
    }

    public int getMaxAllowed() {
        return maxAllowed;
    }

    public void setMaxAllowed(int maxAllowed) {
        if (maxAllowed <= 0) {
            throw new IllegalArgumentException(
                    "Maximum allowed items must be greater than 0."
            );
        }

        this.maxAllowed = maxAllowed;
    }

    public List<LibraryItem> getBorrowedItems() {
        return List.copyOf(borrowedItems);
    }

    public int getBorrowedCount() {
        return borrowedItems.size();
    }

    public boolean canBorrowMore() {
        return borrowedItems.size() < maxAllowed;
    }

    public void addBorrowedItem(LibraryItem item) {
        if (item == null) {
            throw new IllegalArgumentException(
                    "Borrowed item cannot be null."
            );
        }

        borrowedItems.add(item);
    }

    public boolean removeBorrowedItem(String itemId) {

        for (int i = 0; i < borrowedItems.size(); i++) {

            if (borrowedItems.get(i).getId().equals(itemId)) {
                borrowedItems.remove(i);
                return true;
            }
        }

        return false;
    }

    public boolean hasBorrowedItem(String itemId) {

        for (LibraryItem item : borrowedItems) {

            if (item.getId().equals(itemId)) {
                return true;
            }
        }

        return false;
    }
}
