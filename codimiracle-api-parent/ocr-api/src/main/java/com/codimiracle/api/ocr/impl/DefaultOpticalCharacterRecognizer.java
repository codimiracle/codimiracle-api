package com.codimiracle.api.ocr.impl;

import com.codimiracle.api.ocr.OpticalCharacterRecognizer;
import com.codimiracle.api.ocr.contract.OcrApiResult;
import com.codimiracle.api.ocr.provider.OpticalCharacterRecognizerProvider;
import com.codimiracle.api.ocr.provider.OpticalCharacterRecognizerXfyunProvider;

import java.io.File;
import java.io.InputStream;

public class DefaultOpticalCharacterRecognizer implements OpticalCharacterRecognizer {
    private OpticalCharacterRecognizerProvider provider;

    public DefaultOpticalCharacterRecognizer(OpticalCharacterRecognizerXfyunProvider provider) {
        this.provider = provider;
    }

    @Override
    public OcrApiResult recognize(InputStream pictureInputStream) throws Throwable {
        return provider.recognize(pictureInputStream);
    }

    @Override
    public String recognizeAsString(String pictureBase64) {
        return provider.recognizeAsString(pictureBase64);
    }

    @Override
    public InputStream recognizeAsStream(InputStream pictureInputStream) {
        return provider.recognizeAsStream(pictureInputStream);
    }

    @Override
    public String recognizeAsString(File picture) {
        return provider.recognizeAsString(picture);
    }

    @Override
    public InputStream recognizeAsStream(File picture) {
        return provider.recognizeAsStream(picture);
    }
}
