package com.codimiracle.api.ocr.provider;

import com.codimiracle.api.ocr.contract.OcrApiResult;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public abstract class AbstractOpticalCharacterRecognizerProvider implements OpticalCharacterRecognizerProvider {

    protected abstract OcrApiResult sendRequest(InputStream pictureInputStream) throws Throwable;

    @Override
    public OcrApiResult recognize(InputStream pictureInputStream) throws Throwable {
        return sendRequest(pictureInputStream);
    }

    private static final String BASE64_IMAGE_SEPARATOR = ";base64,";
    @Override
    public String recognizeAsString(String pictureBase64) {
        String rawData = pictureBase64;
        int dataIndex = pictureBase64.indexOf(BASE64_IMAGE_SEPARATOR);
        if (dataIndex > -1) {
            rawData = pictureBase64.substring(dataIndex + BASE64_IMAGE_SEPARATOR.length());
        }
        try {
            OcrApiResult ocrApiResult = recognize(new ByteArrayInputStream(Base64.decodeBase64(rawData)));
            return ocrApiResult.getDataAsString();
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public InputStream recognizeAsStream(InputStream pictureInputStream) {
        try {
            OcrApiResult ocrApiResult = recognize(pictureInputStream);
            return IOUtils.toInputStream(ocrApiResult.getData().toString());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    @Override
    public String recognizeAsString(File picture) {
        try {
            OcrApiResult ocrApiResult = recognize(FileUtils.openInputStream(picture));
            return ocrApiResult.getDataAsString();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    @Override
    public InputStream recognizeAsStream(File picture) {
        try {
            OcrApiResult ocrApiResult = recognize(FileUtils.openInputStream(picture));
            return ocrApiResult.getDataAsStream();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
}
