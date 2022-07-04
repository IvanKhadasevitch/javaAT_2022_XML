package by.khadasevich.xml.view;

import by.khadasevich.xml.controller.command.CommandName;

import java.util.NoSuchElementException;
import java.util.Scanner;

public final class TopMenu {
    /**
     * String format used to menu item display.
     */
    private static final String MENU_ITEM_FORMAT = "%2d.%s%n";
    /**
     * Message for choosing menu item.
     */
    private static final String ENTER_MENU_ITEM =
            "Enter menu Item (for example:0):";
    /**
     * Message as invalid menu item is chosen.
     */
    private static final String INVALID_MENU_ITEM = "Invalid menu Item";
    // utility class, no realisation needed
    private TopMenu() { }
    /**
     * Present possible work variants.
     * @return CommandName to find and execute command.
     */
    public static CommandName getMenuItem() {
        printMenuItems();
        CommandName[] commandNames = CommandName.values();
        int item = 1;
        boolean isValuableItem = false;
        System.out.print(ENTER_MENU_ITEM);
        do {
            try {
                Scanner scanner = new Scanner(System.in);
                item = scanner.nextInt();
                if (isValuableMenuItem(item, commandNames.length)) {
                    isValuableItem = true;
                } else {
                    System.out.println(INVALID_MENU_ITEM);
                }
            } catch (NoSuchElementException exp) {
                System.out.println(INVALID_MENU_ITEM);
            }
        } while (!isValuableItem);
        return commandNames[item];
    }
    private static void printMenuItems() {
        CommandName[] commandNames = CommandName.values();
        for (int i = 0; i < commandNames.length; i++) {
            String message = commandNames[i].getTitle();
            System.out.printf(MENU_ITEM_FORMAT, i, message);
        }
    }
    private static boolean isValuableMenuItem(final int menuItemNumber,
                                              final int quantityMenuItems) {
        return 0 <= menuItemNumber && menuItemNumber < quantityMenuItems;
    }
}
