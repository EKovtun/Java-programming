package report;

import java.io.IOException;
import java.io.OutputStream;

public class ReportCSV implements Report {
    private final byte[] data;

    ReportCSV(byte[] data) {
        this.data = data;
    }

    @Override
    public byte[] asBytes() {
        return data;
    }

    @Override
    public void writeTo(OutputStream os) throws IOException {
        os.write(data);
    }
}
