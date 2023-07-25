package rip.athena.athenasleeper.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
@Slf4j
public class FileServingServiceImpl implements FileServingService {

    @Value("${downloadFolder}")
    private String m_downloadFolder;

    @Override
    public void storeCosmeticFile(final InputStream p_inputStream, final int p_cosmeticId) throws IOException {
        createFile(p_inputStream.readAllBytes(), p_cosmeticId + ".png", "cosmetics");
    }

    @Override
    public void storeCosmeticAnimatedFile(final InputStream p_inputStream, final int p_cosmeticId) throws IOException {
        createFile(p_inputStream.readAllBytes(), p_cosmeticId + ".gif", "cosmetics");
    }

    @Override
    public void storeRankIconFile(final InputStream p_inputStream, final int p_rankId) throws IOException {
        createFile(p_inputStream.readAllBytes(), p_rankId + ".png", "ranks");
    }

    private void createFile(final byte[] p_byteBuffer, final String p_fileName, final String p_subName) throws IOException {
        final String targetFileName = m_downloadFolder + File.separator + p_subName + File.separator + p_fileName;
        final File pathCosmeticsStorage = new File(targetFileName);

        createParentFolderIfNotExists(pathCosmeticsStorage);

        try (OutputStream outputStream = new FileOutputStream(pathCosmeticsStorage)) {
            outputStream.write(p_byteBuffer);
        }
    }

    @SuppressWarnings("all")
    private void createParentFolderIfNotExists(final File p_path) throws IOException {
        System.out.println(p_path);
        p_path.getParentFile().mkdirs();
        p_path.createNewFile();
    }

    @Override
    public byte[] getByteInfoOfStoreCosmeticImage(final int p_cosmeticId) throws IOException {
        return getByteOfStoredFile("png", "cosmetics", p_cosmeticId);
    }

    @Override
    public byte[] getByteInfoOfStoreCosmeticAnimated(final int p_cosmeticId) throws IOException {
        return getByteOfStoredFile("gif", "cosmetics", p_cosmeticId);
    }

    @Override
    public byte[] getByteInfoOfStoreRankImage(final int p_cosmeticId) throws IOException {
        return getByteOfStoredFile("png", "ranks", p_cosmeticId);
    }

    private byte[] getByteOfStoredFile(final String p_formatName, final String p_subName, final int p_cosmeticId) throws IOException {
        BufferedImage bImage;
        try {
            bImage = ImageIO.read(new File(m_downloadFolder + File.separator + p_subName + File.separator + p_cosmeticId + "." + p_formatName));
        } catch (IOException ignored) {
            return new byte[0];
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, p_formatName, bos);

        return bos.toByteArray();
    }
}
