package report;

import java.io.IOException;
import java.io.OutputStream;

public interface Report {
    byte[] asBytes();
    void writeTo(OutputStream os) throws IOException;
}
