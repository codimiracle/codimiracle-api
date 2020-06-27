package com.codimiracle.api.ocr;


import com.codimiracle.api.ocr.contract.OcrApiResult;

import java.io.File;
import java.io.InputStream;

/**
 * @author Codimiracle
 */
public interface OpticalCharacterRecognizer {


    OcrApiResult recognize(InputStream pictureInputStream) throws Throwable;

    /**
     * invoking ocr api by using picture file.
     *
     * @param pictureBase64 picture encoded with base64 url
     * @return recognized content
     */
    String recognizeAsString(String pictureBase64);

    /**
     * invoking ocr api by using picture file.
     *
     * @param pictureInputStream picture input stream
     * @return recognized content
     */
    InputStream recognizeAsStream(InputStream pictureInputStream);

    /**
     * invoking ocr api by using picture file.
     *
     * @param picture picture file
     * @return recognized content
     */
    String recognizeAsString(File picture);

    /**
     * invoking ocr api by using picture file.
     *
     * @param picture picture file
     * @return recognized content
     */
    InputStream recognizeAsStream(File picture);
}
