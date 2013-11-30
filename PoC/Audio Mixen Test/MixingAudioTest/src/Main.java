import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 29.11.13
 * Time: 13:12
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Was machen Sachen?\n[1] Verketten\n[2] Mixen\n[3] Manuelles Verketten");
        //int i = -23;
        //int li = i & 0xffff;
        //System.out.println("TEst" + (li));
        int choice = scan.nextInt();

        switch (choice)
        {
            case 1:
                try
                {
                    Mixer.concatenate();
                    System.out.println("Verketten erfolgreich!");
                }
                catch(IOException | UnsupportedAudioFileException ex)
                {
                    System.out.println("Fehler:\n" + ex.getMessage());
                }
                break;
            case 2:
                try {
                    Mixer.manuelMixing();
                    System.out.println("Mixen erfolgreich!");
                } catch (IOException | UnsupportedAudioFileException ex) {
                    System.out.println("Fehler:\n" + ex.getMessage());
                }
                break;
            case 3:
                try {
                    Mixer.manuelConcatenate();
                    System.out.println("Verketten erfolgreich!");
                } catch (Exception ex) {
                    System.out.println("Fehler:\n" + ex.getMessage());
                }
                break;
        }
    }
}