package com.codimiracle.api.ocr.contract;

import com.codimiracle.api.ocr.contract.dataformat.XfyunRecognitionData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.Objects;

/**
 * @author Codimiracle
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OcrApiResultXfyun extends OcrApiResult<XfyunRecognitionData> {
    private String sid;

    public String getDesc() {
        return this.getMessage();
    }

    public void setDesc(String desc) {
        this.setMessage(desc);
    }

    @Override
    public String getDataAsString() {
        if (Objects.isNull(getData())) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (XfyunRecognitionData.Block block : getData().getBlock()) {
            if (XfyunRecognitionData.Line.TYPE_TEXT.equals(block.getType())) {
                for (XfyunRecognitionData.Line line : block.getLine()) {
                    for (XfyunRecognitionData.Word word : line.getWord()) {
                        builder.append(word.getContent());
                    }
                    builder.append("\n");
                }
            }
        }
        return builder.toString();
    }

    @Override
    public InputStream getDataAsStream() {
        return IOUtils.toInputStream(getDataAsString());
    }
}
