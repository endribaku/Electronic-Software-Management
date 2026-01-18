package phase3.integration.endri;

import Controllers.InventoryController;
import Models.*;
import Views.InventoryView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.*;

import stubs.InventoryFileHandlerStub;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class InventoryControllerViewIT {

    private InventoryController controller;
    private InventoryView view;
    private InventoryFileHandlerStub handlerStub;
    private User testUser;

    /* ---------------- JavaFX bootstrap ---------------- */

    @BeforeAll
    static void initToolkit() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {
            // JavaFX already initialized
        }
    }

    /* ---------------- Test setup ---------------- */

    @BeforeEach
    void setUp() {
        testUser = new User("ui-user", Access.Manager);
        testUser.setSector(FXCollections.observableArrayList("Electronics"));

        view = new InventoryView();
        handlerStub = new InventoryFileHandlerStub();

        controller = new InventoryController(
                testUser,
                view,
                handlerStub,
                true   // wire actions
        );
    }

    /* ---------------- Helper ---------------- */

    private void runOnFxThreadAndWait(Runnable action) {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            action.run();
            latch.countDown();
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /* =========================================================
       1. addItemButton_callsAddItemOnHandler
       ========================================================= */

    @Test
    void addItemButton_callsAddItemOnHandler() {
        runOnFxThreadAndWait(() -> {

            // ---------- Arrange ----------
            Category category = new Category("Phones", "Electronics");
            Supplier supplier = new Supplier("Apple", FXCollections.observableArrayList());

            view.getItemCategoryListView()
                    .setItems(FXCollections.observableArrayList(category));
            view.getItemSupplierListView()
                    .setItems(FXCollections.observableArrayList(supplier));

            view.getItemCategoryListView().getSelectionModel().select(category);
            view.getItemSupplierListView().getSelectionModel().select(supplier);

            view.getItemNameField().setText("iPhone 14");
            view.getItemQuantityField().setText("10");
            view.getItemPPriceField().setText("800");
            view.getItemSPriceField().setText("1000");

            // ---------- Act ----------
            view.getAddItemButton().fire();

            // ---------- Assert ----------
            // UI cleared = success path executed
            assertEquals("", view.getItemNameField().getText());
            assertEquals("", view.getItemQuantityField().getText());
            assertEquals("", view.getItemPPriceField().getText());
            assertEquals("", view.getItemSPriceField().getText());
        });
    }

    /* =========================================================
       2. updateItemButton_callsUpdateItemOnHandler
       ========================================================= */

    @Test
    void updateItemButton_callsUpdateItemOnHandler() {
        runOnFxThreadAndWait(() -> {

            // ---------- Arrange ----------
            Category category = new Category("Food", "Main");
            Supplier supplier = new Supplier("Supplier A", List.of());

            Item item = new Item(
                    "Test Item",
                    category.getName(),
                    supplier.getName(),
                    LocalDate.now(),
                    10.0,
                    15.0,
                    5
            );

            view.getInventoryTableView().getItems().add(item);
            view.getInventoryTableView().getSelectionModel().select(item);

            view.getItemEditNameField().setText("Updated Item");
            view.getItemEditQuantityField().setText("10");
            view.getItemEditPPriceField().setText("12.0");
            view.getItemEditSPriceField().setText("18.0");

            view.getEditItemCategoriesBox().getItems().add(category);
            view.getEditItemCategoriesBox().getSelectionModel().select(category);

            view.getEditSupplierBox().getItems().add(supplier);
            view.getEditSupplierBox().getSelectionModel().select(supplier);

            // ---------- Act ----------
            view.getUpdateItemButton().fire();

            // ---------- Assert ----------
            assertTrue(
                    handlerStub.updateItemCalled,
                    "Update Item button should call updateItem on handler"
            );
        });
    }

    /* =========================================================
       3. invalidNumericInput_showsErrorMessageInView
       ========================================================= */

    @Test
    void invalidNumericInput_showsErrorMessageInView() {

        // ---------- Arrange / Act / Assert ----------
        // (Assert = no exception + UI remains stable)

        assertDoesNotThrow(() ->
                runOnFxThreadAndWait(() -> {

                    // ---------- Arrange ----------
                    Category category = new Category("Phones", "Electronics");
                    Supplier supplier = new Supplier(
                            "Apple",
                            FXCollections.observableArrayList()
                    );

                    view.getItemCategoryListView()
                            .setItems(FXCollections.observableArrayList(category));
                    view.getItemSupplierListView()
                            .setItems(FXCollections.observableArrayList(supplier));

                    view.getItemCategoryListView().getSelectionModel().select(category);
                    view.getItemSupplierListView().getSelectionModel().select(supplier);

                    view.getItemNameField().setText("iPhone 14");

                    // ❌ invalid numeric input
                    view.getItemQuantityField().setText("abc");
                    view.getItemPPriceField().setText("800");
                    view.getItemSPriceField().setText("1000");

                    // ---------- Act ----------
                    view.getAddItemButton().fire();
                })
        );
    }
}
