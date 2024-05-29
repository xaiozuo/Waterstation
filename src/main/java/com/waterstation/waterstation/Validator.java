package com.waterstation.waterstation;

import java.io.IOException;
import org.apache.http.client.methods.CloseableHttpResponse;

/**
 * @author xy-peng
 */
public interface Validator {

    boolean validate(CloseableHttpResponse response) throws IOException;

}
