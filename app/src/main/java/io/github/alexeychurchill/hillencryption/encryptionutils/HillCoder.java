package io.github.alexeychurchill.hillencryption.encryptionutils;

import java.util.LinkedList;
import java.util.List;

import io.github.alexeychurchill.hillencryption.matrixutils.IntMatrixUtils;
import io.github.alexeychurchill.hillencryption.matrixutils.NumberUtils;

/**
 * Hill cypher encoder/decoder
 */

public class HillCoder {
    private Alphabet alphabet = new UkrainianAlphabet();
    private String key = null;
    private int[][] keyMatrix = null;
    private int keyMatrixSize = 3;
    private char paddingChar = '~';

    public char getPaddingChar() {
        return paddingChar;
    }

    public void setPaddingChar(char paddingChar) {
        this.paddingChar = paddingChar;
    }

    public int getKeyMatrixSize() {
        return keyMatrixSize;
    }

    public void setKeyMatrixSize(int keyMatrixSize) {
        if (keyMatrixSize < 2) {
            return;
        }
        this.keyMatrixSize = keyMatrixSize;
        buildKey();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        buildKey();
    }

    public String encode(String source) {
        if (keyMatrix == null) {
            return null;
        }
        if (source == null) {
            return null;
        }
        int det = IntMatrixUtils.det(keyMatrix);
        if (det == 0 || !NumberUtils.isInverseByModExists(det, alphabet.length())) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        List<String> chunks = splitString(source);
        for (String chunk : chunks) {
            int[][] chunkMatrix = chunkToMatrix(chunk);
            int[][] encodedChunkMatrix = IntMatrixUtils.multiply(keyMatrix, chunkMatrix, alphabet.length());
            String encodedChunk = matrixToChunk(encodedChunkMatrix);
            builder.append(encodedChunk);
        }
        return builder.toString();
    }

    public String decode(String source) {
        if (keyMatrix == null) {
            return null;
        }
        if (source == null) {
            return null;
        }
        int[][] decodeMatrix = decodeMatrix(keyMatrix);
        if (decodeMatrix == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        List<String> chunks = splitString(source);
        for (String chunk : chunks) {
            int[][] chunkMatrix = chunkToMatrix(chunk);
            int[][] decodedChunkMatrix = IntMatrixUtils.multiply(decodeMatrix, chunkMatrix, alphabet.length());
            String decodedChunk = matrixToChunk(decodedChunkMatrix);
            builder.append(decodedChunk);
        }
        return builder.toString().replace(String.valueOf(paddingChar), "");
    }

    private int[][] decodeMatrix(int[][] keyMatrix) {
        return IntMatrixUtils.inverseByMod(keyMatrix, alphabet.length());
    }

    private String matrixToChunk(int[][] encodedChunkMatrix) {
        StringBuilder chunkBuilder = new StringBuilder();
        for (int[] anEncodedChunkMatrix : encodedChunkMatrix) {
            chunkBuilder.append(alphabet.character(anEncodedChunkMatrix[0]));
        }
        return chunkBuilder.toString();
    }

    private int[][] chunkToMatrix(String chunk) {
        int[][] chunkMatrix = IntMatrixUtils.getColumnMatrix(chunk.length());
        for (int i = 0; i < chunk.length(); i++) {
            chunkMatrix[i][0] = alphabet.position(chunk.charAt(i));
        }
        return chunkMatrix;
    }

    private void buildKey() {
        if (key == null) {
            return;
        }
        keyMatrix = new int[keyMatrixSize][keyMatrixSize];
        for (int i = 0; i < keyMatrixSize; i++) {
            for (int j = 0; j < keyMatrixSize; j++) {
                int keyPosition = i * keyMatrixSize + j;
                keyMatrix[i][j] = alphabet.position(key.charAt(keyPosition % key.length()));
            }
        }
    }

    private List<String> splitString(String source) {
        List<String> chunks = new LinkedList<>();
        int chunksCount = (source.length() / keyMatrixSize) + ((source.length() % keyMatrixSize > 0) ? 1 : 0);
        for (int chunkI = 0; chunkI < chunksCount; chunkI++) {
            int start = chunkI * keyMatrixSize;
            int end = chunkI * keyMatrixSize + keyMatrixSize;
            end = (end >= source.length()) ? source.length() : end;
            String subString = source.substring(start, end);
            if (subString.length() < keyMatrixSize) {
                int paddingCount = keyMatrixSize - subString.length();
                StringBuilder paddingBuilder = new StringBuilder();
                for (int i = 0; i < paddingCount; i++) {
                    paddingBuilder.append(paddingChar);
                }
                subString = subString.concat(paddingBuilder.toString());
            }
            chunks.add(subString);
        }
        return chunks;
    }
}
