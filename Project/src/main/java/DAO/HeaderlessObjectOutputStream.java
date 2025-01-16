package DAO;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class HeaderlessObjectOutputStream extends ObjectOutputStream {
    public HeaderlessObjectOutputStream(OutputStream os) throws IOException {
        super(os);
    }

    @Override
    protected void writeStreamHeader() throws IOException {

    }
}
