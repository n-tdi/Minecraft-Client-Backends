package rip.athena.athenasleeper.services;

import java.io.IOException;
import java.io.InputStream;

public interface FileServingService {
    void storeCosmeticFile(final InputStream p_inputStream, final int p_cosmeticId) throws IOException;
    void storeCosmeticAnimatedFile(final InputStream p_inputStream, final int p_cosmeticId) throws IOException;
    void storeRankIconFile(final InputStream p_inputStream, final int p_rankId) throws IOException;
    byte[] getByteInfoOfStoreCosmeticImage(final int p_cosmeticId) throws IOException;
    byte[] getByteInfoOfStoreCosmeticAnimated(final int p_cosmeticId) throws IOException;
    byte[] getByteInfoOfStoreRankImage(final int p_cosmeticId) throws IOException;
}
