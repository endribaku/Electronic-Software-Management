package stubs;

import Views.EmployeesListView;

public class CapturingEmployeesListView extends EmployeesListView {
    public String lastErrorTitle;
    public String lastErrorMessage;

    public void showError(String title, String message) {
        lastErrorTitle = title;
        lastErrorMessage = message;
    }

    public void showInfo(String title, String message) {
        // no-op for tests that don't assert success messages
    }
}
