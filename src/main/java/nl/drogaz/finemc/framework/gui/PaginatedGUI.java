package nl.drogaz.finemc.framework.gui;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class PaginatedGUI implements InventoryHolder {

    @Getter private final String title;
    @Getter private final int maxItemsPerPage;
    private int currentPage;
    private int totalPages;
    private Inventory[] pages;
    private int size;
    private InventoryType type;
    protected final Player player;

    public PaginatedGUI(String title, int maxItemsPerPage, Player player) {
        this.title = title;
        this.maxItemsPerPage = maxItemsPerPage;
        this.currentPage = 0;
        this.player = player;

        calculateSize();
        this.pages = new Inventory[totalPages];
        createInventories();
    }

    private void calculateSize() {
        int totalItems = getContent().length;
        this.totalPages = (int) Math.ceil((double) totalItems / maxItemsPerPage);
        this.size = totalPages * 9;
    }

    public void open() {
        if (totalPages == 0) {
            player.sendMessage("No content to display.");
            return;
        }

        player.openInventory(pages[currentPage]);
    }

    public void nextPage() {
        if (currentPage < totalPages - 1) {
            currentPage++;
            player.openInventory(pages[currentPage]);
        }
    }

    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            player.openInventory(pages[currentPage]);
        }
    }

    public void refresh() {
        calculateSize();
        createInventories();
        open();
    }

    protected void createInventories() {
        for (int i = 0; i < totalPages; i++) {
            pages[i] = Bukkit.createInventory(this, size, title + " - Page " + (i + 1));
        }
        populateInventories();
    }

    protected void populateInventories() {
        ItemStack[] content = getContent();
        for (int i = 0; i < totalPages; i++) {
            int startIndex = i * maxItemsPerPage;
            int endIndex = Math.min(startIndex + maxItemsPerPage, content.length);
            Inventory inventory = pages[i];
            inventory.clear();
            for (int j = startIndex; j < endIndex; j++) {
                int slot = j - (i * maxItemsPerPage);
                inventory.setItem(slot, content[j]);
            }
            drawNavigationButtons(inventory, i);
        }
    }

    private void drawNavigationButtons(Inventory inventory, int page) {
        ItemStack prevPageItem = new ItemStack(Material.ARROW);
        ItemMeta prevPageMeta = prevPageItem.getItemMeta();
        prevPageMeta.setDisplayName("Previous Page");
        prevPageItem.setItemMeta(prevPageMeta);

        ItemStack nextPageItem = new ItemStack(Material.ARROW);
        ItemMeta nextPageMeta = nextPageItem.getItemMeta();
        nextPageMeta.setDisplayName("Next Page");
        nextPageItem.setItemMeta(nextPageMeta);

        if (page > 0) {
            inventory.setItem(size - 9, prevPageItem);
        }

        if (page < totalPages - 1) {
            inventory.setItem(size - 1, nextPageItem);
        }
    }

    protected abstract ItemStack[] getContent();

    @Override
    public Inventory getInventory() {
        return pages[currentPage];
    }

    protected abstract void handleClickAction(InventoryClickEvent event);

    protected abstract void handleCloseAction(InventoryCloseEvent event);
}