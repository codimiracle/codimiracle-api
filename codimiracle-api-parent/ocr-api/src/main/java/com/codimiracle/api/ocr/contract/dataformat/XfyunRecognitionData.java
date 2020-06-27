package com.codimiracle.api.ocr.contract.dataformat;

import lombok.Data;

import java.util.List;

@Data
public class XfyunRecognitionData {
    private List<Block> block;

    @Data
    public static class Word {
        private String content;
    }

    @Data
    public static class Line {
        public static final String TYPE_TEXT = "text";

        private float confidence;
        private List<Word> word;
    }

    @Data
    public static class Block {
        private String type;
        private List<Line> line;
    }
}
