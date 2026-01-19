package stubs;

import Views.EmployeesListView;

public class CapturingEmployeesListView extends EmployeesListView {
    public String lastErrorTitle;
    public String lastErrorMessage;

    @Override
    public void showError(String title, String message) {
        lastErrorTitle = title;
        lastErrorMessage = message;
    }

    @Override
    public void showInfo(String title, String message) {
        // no-op for tests that don't assert success messages
    }
}
