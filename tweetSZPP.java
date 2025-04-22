import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Desktop;

public class tweetSZPP {
    public static void main(String[] args) {
        Random rand = new Random();
        List<String> comments = new ArrayList<>();
        List<String> catchPhrase = new ArrayList<>();

        comments = collectWords("comments.txt");
        catchPhrase = collectWords("catch_phrase.txt");

        if (comments.isEmpty() || catchPhrase.isEmpty()) {
            System.err.println("エラー：comments.txtまたはcatch_phrase.txtが空または存在しません。");
            return;
        }

        String[] tweetWord = new String[2];
        int tweetIndex;

        tweetIndex = rand.nextInt(comments.size());
        tweetWord[0] = comments.get(tweetIndex);

        tweetIndex = rand.nextInt(catchPhrase.size());
        tweetWord[1] = catchPhrase.get(tweetIndex);

        // URLエンコード
        String[] encodedTweet = new String[2];

        for (int i = 0; i < encodedTweet.length; i++) {
            encodedTweet[i] = URLEncoder.encode(tweetWord[i], StandardCharsets.UTF_8);
        }

        tweet(encodedTweet);
    }

    static List<String> collectWords(String fileName) {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                words.add(line);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    static void tweet(String[] tweetWord) {
        String link = "https://x.com/intent/post?text=";
        String word = "【SZPP開発発表会】";
        String tweetLink = link.concat(word);
        StringBuilder sb = new StringBuilder(tweetLink);

        for (int i = 0; i < tweetWord.length; i++) {
            sb.append("%0A" + tweetWord[i]);
        }
        sb.append("%0A%0Aおいでよ、SZPPへ。");
        tweetLink = sb.toString();

        Desktop desktop = Desktop.getDesktop();

        try {
            URI uri = new URI(tweetLink);
            desktop.browse(uri);
        } catch (Exception e) {
            System.err.println("ツイート失敗");
            e.printStackTrace();
        }
    }
}