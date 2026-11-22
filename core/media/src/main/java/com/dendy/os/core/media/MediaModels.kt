package com.dendy.os.core.media

data class MediaTrack(
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val durationSeconds: Int,
    val currentPositionSeconds: Int,
    val isPlaying: Boolean = false,
    val coverGradientIndex: Int = 0
)

data class MediaPlaybackState(
    val currentTrack: MediaTrack = MediaTrack(
        id = "1",
        title = "Midnight Drive (AWD)",
        artist = "DENDY Soundscape",
        album = "Automotive Session 01",
        durationSeconds = 245,
        currentPositionSeconds = 85,
        isPlaying = true,
        coverGradientIndex = 0
    ),
    val volumePercent: Int = 65,
    val isMuted: Boolean = false,
    val playlist: List<MediaTrack> = listOf(
        MediaTrack("1", "Midnight Drive (AWD)", "DENDY Soundscape", "Automotive Session 01", 245, 85, true, 0),
        MediaTrack("2", "Night City Cruise", "Electro Omoda", "Future Horizon", 198, 0, false, 1),
        MediaTrack("3", "Almaty Rain Lofi", "Kazakh Beats", "Silk Road Vibes", 215, 0, false, 2),
        MediaTrack("4", "Steppe Wind Symphony", "Nomad Echoes", "Expedition", 310, 0, false, 3)
    )
)

interface MediaControllerRepository {
    val playbackState: kotlinx.coroutines.flow.StateFlow<MediaPlaybackState>
    fun play()
    fun pause()
    fun togglePlayPause()
    fun nextTrack()
    fun previousTrack()
    fun seekTo(seconds: Int)
    fun setVolume(volume: Int)
}
