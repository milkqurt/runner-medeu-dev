package com.mygdx.game;

import com.badlogic.gdx.Gdx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

public class HighScore {
    public static String topPlayerName = "";
    public static int topPlayerScore = 0;

    public static void loadTable() {
        BufferedReader br = null;
        try {
            br = Gdx.files.local("score.txt").reader(8192);
            String data = br.readLine();
            topPlayerName = data.split(" ")[0];
            topPlayerScore = Integer.parseInt(data.split(" ")[1]);
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }


    public static void updateTable(String name, int score) {
        if (score > topPlayerScore) {
            Writer writer = null;
            try {
                writer = Gdx.files.local("score.txt").writer(false);
                writer.write(name + " " + score);
            } catch (IOException exception) {
                exception.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
        loadTable();
    }

    public static void createTable() {
        if (Gdx.files.local("score.txt").exists()) return;
        Writer writer = null;
        try {
            writer = Gdx.files.local("score.txt").writer(false);
            writer.write("Unknown 0");
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
