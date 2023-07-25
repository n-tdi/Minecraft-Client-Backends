package rip.athena.athenasleeper.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;

@Service
@Slf4j
@AllArgsConstructor
public class FileServingServiceImpl implements FileServingService {

    @Value("${download.folder}")
    private Path m_downloadFolder;

    @Override
    public void storeCosmeticFile(final InputStream p_inputStream, final int p_cosmeticId) throws IOException {
        createFile(p_inputStream.readAllBytes(), p_cosmeticId + ".png", "cosmetics");
    }

    @Override
    public void storeRankIconFile(final InputStream p_inputStream, final int p_rankId) throws IOException {
        createFile(p_inputStream.readAllBytes(), p_rankId + ".png", "ranks");
    }

    public void createFile(final byte[] p_byteBuffer, final String p_fileName, final String p_subName) throws IOException {
        final String targetFileName = m_downloadFolder + File.separator + p_subName + File.separator + p_fileName;
        final File pathCosmeticsStorage = new File(targetFileName);

        createParentFolderIfNotExists(pathCosmeticsStorage);

        try (OutputStream outputStream = new FileOutputStream(pathCosmeticsStorage)) {
            outputStream.write(p_byteBuffer);
        }
    }

    @SuppressWarnings("all")
    public void createParentFolderIfNotExists(final File p_path) throws IOException {
        p_path.mkdirs();
        p_path.createNewFile();
    }
}
