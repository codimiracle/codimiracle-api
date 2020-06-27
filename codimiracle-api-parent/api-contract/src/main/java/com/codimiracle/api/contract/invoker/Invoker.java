package com.codimiracle.api.contract.invoker;

import java.io.IOException;
import java.io.InputStream;

public interface Invoker extends AutoCloseable {
    String invokeToString() throws IOException;

    InputStream invokeToInputStream() throws IOException;
}
