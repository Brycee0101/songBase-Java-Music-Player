/**
 *
 * @author Bryce Stephen Halnin
 */
import java.io.FileInputStream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class threads {
        private PlayerThread currentPlaybackThread;

    public void stopPlayback() {
        if (currentPlaybackThread != null) {
            currentPlaybackThread.stopPlayback();
            currentPlaybackThread = null;
        }
    }

    public void pausePlayback() {
        System.out.println(currentPlaybackThread);
        if (currentPlaybackThread != null) {
            currentPlaybackThread.pausePlayback();
        }
    }

    public void resumePlayback() {
        if (currentPlaybackThread != null) {
            currentPlaybackThread.resumePlayback();
        }
    }

    public class PlayerThread extends Thread {
        private String songFilePath;
        private boolean loop;
        private AdvancedPlayer player;
        private FileInputStream fis;
        private long totalSongLength;
        private boolean isPaused = false;
        private long pauseLocation = 0;

        public PlayerThread(String songFilePath, boolean loop) {
            this.songFilePath = songFilePath;
            this.loop = loop;
        }

        public void stopPlayback() {
            if (player != null) {
                player.stop();
            }
        }

        public void pausePlayback() {
            if (player != null) {
                try {
                    isPaused = true;
                    pauseLocation = fis.available();
                    player.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void resumePlayback() {
            try {
                if (isPaused) {
                    fis = new FileInputStream(songFilePath);
                    long skip = ((totalSongLength - pauseLocation)+3000);
                    fis.skip(skip);
                    player = new AdvancedPlayer(fis);
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                player.play();
                            } catch (JavaLayerException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }.start();
                    isPaused = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void close() {
            loop = false;
            if (player != null) {
                player.close();
            }
            this.interrupt();
        }

        public void run() {
            try {
                fis = new FileInputStream(songFilePath);
                player = new AdvancedPlayer(fis);
                totalSongLength = fis.available();

                // Create a PlaybackListener to handle playback events
                PlaybackListener listener = new PlaybackListener() {
                    @Override
                    public void playbackFinished(PlaybackEvent event) {
                        if (loop) {
                            try {
                                // Reset the stream to replay the song
                                fis = new FileInputStream(songFilePath);
                                player = new AdvancedPlayer(fis);
                                player.play();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };

                // Set the playback listener for the player
                player.setPlayBackListener(listener);

                // Start playback
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
