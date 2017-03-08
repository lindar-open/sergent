package lindar.sergent.impl.exp;

/**
 *
 * @author iulian.dafinoiu
 */
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import lombok.extern.slf4j.Slf4j;
import lindar.sergent.Sergent;
import lindar.sergent.util.ClasspathUtil;

@Slf4j
public class Seed {

    private final static String[] noiseAudioSeedFiles = {"soundfile.au", "soundfile.wav", "soundfile.aiff"};

    public Seed() {
    }

    /**
     * Gets a array of random bytes used for seeding RNGs.
     *
     * @param length the length of the seed in bytes
     * @return
     * @throws java.io.IOException
     */
    public byte[] getSeed(int length) throws IOException, NoSuchAlgorithmException {
        byte[] seed = new byte[length];
        int randomFileIndex = new Sergent().randInt(1, noiseAudioSeedFiles.length);
        try (RandomBitInputStream rbis = new RandomBitInputStream(ClasspathUtil.getFileFromResources(noiseAudioSeedFiles[randomFileIndex - 1]).getAbsolutePath())) {
            for (int i = 0; i < seed.length; i++) {
                seed[i] = rbis.readSkewCorrectedRandomByte();
            }
        }
        return seed;
    }

}
