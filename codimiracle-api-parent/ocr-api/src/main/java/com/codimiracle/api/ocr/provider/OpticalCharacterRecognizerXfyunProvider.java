package com.codimiracle.api.ocr.provider;

import com.codimiracle.api.contract.invoker.Invoker;
import com.codimiracle.api.contract.invoker.impl.HttpRequestInvokerHttpURLConnectionImpl;
import com.codimiracle.api.ocr.contract.OcrApiResultXfyun;
import com.codimiracle.api.ocr.contract.config.OcrApiProperties;
import com.codimiracle.api.contract.invoker.exception.ApiInvokingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class OpticalCharacterRecognizerXfyunProvider extends AbstractOpticalCharacterRecognizerProvider {
    private static final String API_BASE_URL = "webapi.xfyun.cn/v1/service/v1/ocr/general";
    private OcrApiProperties properties;

    public OpticalCharacterRecognizerXfyunProvider(OcrApiProperties properties) {
        this.properties = properties;
    }

    protected Map<String, String> getRequestHeaders() throws UnsupportedEncodingException {
        long currentTime = System.currentTimeMillis() / 1000L;
        String rawParam = "{\"location\":\"" + properties.isResolvingLocation() + "\",\"language\":\"" + properties.getRecognizedLanguages() + "\"}";
        String paramHeaderValue = Base64.encodeBase64String(rawParam.getBytes(StandardCharsets.UTF_8));
        String checkSum = DigestUtils.md5Hex(properties.getApiKey() + currentTime + paramHeaderValue);

        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        header.put("X-Param", paramHeaderValue);
        header.put("X-CurTime", currentTime + "");
        header.put("X-CheckSum", checkSum);
        header.put("X-Appid", properties.getAppId());
        return header;
    }

    @Override
    protected OcrApiResultXfyun sendRequest(InputStream pictureInputStream) throws Throwable {
        String apiUrl = (properties.isUsingSSL() ? "https://" : "http://") + API_BASE_URL;

        String body = String.format("image=%s", URLEncoder.encode(Base64.encodeBase64String(IOUtils.toByteArray(pictureInputStream)), "UTF-8"));

        try (Invoker invoker = new HttpRequestInvokerHttpURLConnectionImpl(apiUrl, "post", body, getRequestHeaders())) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
            return mapper.readValue(invoker.invokeToInputStream(), OcrApiResultXfyun.class);
        }
    }
}
