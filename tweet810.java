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

import javax.swing.plaf.DesktopIconUI;

public class tweet810 {
    public static void main(String[] args) {
        Random rand = new Random();
        List<String> words = new ArrayList<>();
        words = collectWords();

        if (words.isEmpty()) {
            System.err.println("エラー：words.txtが空または存在しません。");
            return;
        }

        // ツイートする語録を決める
        // System.out.println(words.size());
        int tweetIndex = rand.nextInt(words.size());
        String tweetWord = words.get(tweetIndex);

        // URLエンコード
        String encodedTweet = URLEncoder.encode(tweetWord, StandardCharsets.UTF_8);

        tweet(encodedTweet);
    }

    static List<String> collectWords() {
        List<String> words = new ArrayList<>();
        try {
            BufferedReader br = Files.newBufferedReader(Paths.get("words.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    static void tweet(String tweetWord) {
        String url = "https://x.com/intent/post?text=" + tweetWord;
        Desktop desktop = Desktop.getDesktop();

        try {
            URI uri = new URI(url);
            desktop.browse(uri);
        } catch (Exception e) {
            System.err.println("ツイート失敗");
            e.printStackTrace();
        }
    }
}