package phase2.moel;

import Controllers.EmployeePerformanceController;
import Interfaces.DAO.IBillFileHandler;
import Interfaces.DAO.IItemFileHandler;
import Interfaces.DAO.IUserFileHandler;
import Models.Bill;
import Models.User;
import Views.EmployeePerformanceView;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class CalculateSeriesWeeklyCoverageTest {

    @BeforeAll
    static void initJavaFx() throws Exception {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown);
            assertTrue(latch.await(3, TimeUnit.SECONDS));
        } catch (IllegalStateException ignored) {
            // already started
        }
    }

    @Test
    void calculateSeriesWeekly_coverage() throws Exception {
        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);

        ObservableList<Bill> bills = FXCollections.observableArrayList();
        bills.add(makeBill(monday, 50.0, "cashierA"));
        bills.add(makeBill(monday, 25.0, "cashierA"));                 // aggregation
        bills.add(makeBill(monday.plusDays(1), 0.0, "cashierB"));      // valid but ignored
        bills.add(makeBill(monday.plusDays(2), -10.0, "cashierC"));    // invalid -> exception -> ignored
        bills.add(makeBill(monday.minusDays(1), 100.0, "cashierD"));   // outside week -> ignored

        IBillFileHandler billHandler = fakeBillHandler(bills);
        IUserFileHandler userHandler = fakeUnused(IUserFileHandler.class);
        IItemFileHandler itemHandler = fakeUnused(IItemFileHandler.class);

        EmployeePerformanceView view = new EmployeePerformanceView();
        User user = makeDummyUser();

        EmployeePerformanceController controller =
                new EmployeePerformanceController(user, view, userHandler, itemHandler, billHandler, false);

        view.getSeriesWeekly().getData().clear();

        Method m = EmployeePerformanceController.class.getDeclaredMethod("calculateSeriesWeekly");
        m.setAccessible(true);

        @SuppressWarnings("unchecked")
        XYChart.Series<String, Number> series = (XYChart.Series<String, Number>) m.invoke(controller);

        assertNotNull(series);
        assertEquals(7, series.getData().size());

        // Monday should be 50 + 25
        assertEquals(75.0, series.getData().get(0).getYValue().doubleValue(), 0.0001);

        // Others should be 0 for this dataset
        for (int i = 1; i < 7; i++) {
            assertEquals(0.0, series.getData().get(i).getYValue().doubleValue(), 0.0001);
        }
    }

    // ---------------- helpers ----------------

    private static IBillFileHandler fakeBillHandler(ObservableList<Bill> bills) {
        return (IBillFileHandler) java.lang.reflect.Proxy.newProxyInstance(
                IBillFileHandler.class.getClassLoader(),
                new Class[]{IBillFileHandler.class},
                (proxy, method, args) -> method.getName().equals("getBills") ? bills : null
        );
    }

    private static <T> T fakeUnused(Class<T> clazz) {
        return clazz.cast(java.lang.reflect.Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                (proxy, method, args) -> null
        ));
    }

    private static Bill makeBill(LocalDate date, double amount, String username) throws Exception {
        Bill bill = instantiate(Bill.class);

        smartSet(bill, "setDateOfSale", "dateOfSale", date);
        smartSet(bill, "setTotalAmount", "totalAmount", amount);
        smartSet(bill, "setUsername", "username", username);

        return bill;
    }

    private static <T> T instantiate(Class<T> cls) throws Exception {
        for (Constructor<?> c : cls.getDeclaredConstructors()) {
            c.setAccessible(true);
            try {
                Object[] args = new Object[c.getParameterCount()];
                return cls.cast(c.newInstance(args));
            } catch (Exception ignored) {}
        }
        throw new RuntimeException("No usable constructor for " + cls.getName());
    }

    private static void smartSet(Object obj, String setterName, String fieldName, Object value) throws Exception {
        Method setter = findSetter(obj.getClass(), setterName, value);
        if (setter != null) {
            setter.setAccessible(true);
            setter.invoke(obj, value);
            return;
        }

        Field f = obj.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        Object current = f.get(obj);

        if (current instanceof StringProperty) {
            ((StringProperty) current).set(String.valueOf(value));
            return;
        }
        if (current instanceof DoubleProperty) {
            ((DoubleProperty) current).set(((Number) value).doubleValue());
            return;
        }
        if (current instanceof IntegerProperty) {
            ((IntegerProperty) current).set(((Number) value).intValue());
            return;
        }
        if (current instanceof LongProperty) {
            ((LongProperty) current).set(((Number) value).longValue());
            return;
        }
        if (current instanceof BooleanProperty) {
            ((BooleanProperty) current).set((Boolean) value);
            return;
        }
        if (current instanceof ObjectProperty) {
            @SuppressWarnings("unchecked")
            ObjectProperty<Object> p = (ObjectProperty<Object>) current;
            p.set(value);
            return;
        }

        f.set(obj, value);
    }

    private static Method findSetter(Class<?> cls, String setterName, Object value) {
        for (Method m : cls.getMethods()) {
            if (!m.getName().equals(setterName)) continue;
            if (m.getParameterCount() != 1) continue;

            Class<?> param = m.getParameterTypes()[0];
            if (value == null) return m;
            if (param.isAssignableFrom(value.getClass())) return m;

            if (param.isPrimitive()) {
                if (param == double.class && value instanceof Double) return m;
                if (param == int.class && value instanceof Integer) return m;
                if (param == long.class && value instanceof Long) return m;
                if (param == boolean.class && value instanceof Boolean) return m;
            }
        }
        return null;
    }

    private static User makeDummyUser() throws Exception {
        return instantiate(User.class);
    }
}
