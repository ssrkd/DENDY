package com.dendy.os.core.media

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DefaultMediaRepository(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : MediaControllerRepository {

    private val _playbackState = MutableStateFlow(MediaPlaybackState())
    override val playbackState: StateFlow<MediaPlaybackState> = _playbackState.asStateFlow()

    init {
        // Real-time track progression ticker
        scope.launch {
            while (true) {
                delay(1000)
                val state = _playbackState.value
                if (state.currentTrack.isPlaying) {
                    val nextPos = state.currentTrack.currentPositionSeconds + 1
                    if (nextPos >= state.currentTrack.durationSeconds) {
                        nextTrack()
                    } else {
                        _playbackState.value = state.copy(
                            currentTrack = state.currentTrack.copy(currentPositionSeconds = nextPos)
                        )
                    }
                }
            }
        }
    }

    override fun play() {
        val current = _playbackState.value
        _playbackState.value = current.copy(
            currentTrack = current.currentTrack.copy(isPlaying = true)
        )
    }

    override fun pause() {
        val current = _playbackState.value
        _playbackState.value = current.copy(
            currentTrack = current.currentTrack.copy(isPlaying = false)
        )
    }

    override fun togglePlayPause() {
        if (_playbackState.value.currentTrack.isPlaying) pause() else play()
    }

    override fun nextTrack() {
        val state = _playbackState.value
        val currentIndex = state.playlist.indexOfFirst { it.id == state.currentTrack.id }
        val nextIndex = (currentIndex + 1) % state.playlist.size
        val next = state.playlist[nextIndex].copy(isPlaying = true, currentPositionSeconds = 0)
        _playbackState.value = state.copy(currentTrack = next)
    }

    override fun previousTrack() {
        val state = _playbackState.value
        val currentIndex = state.playlist.indexOfFirst { it.id == state.currentTrack.id }
        val prevIndex = if (currentIndex <= 0) state.playlist.size - 1 else currentIndex - 1
        val prev = state.playlist[prevIndex].copy(isPlaying = true, currentPositionSeconds = 0)
        _playbackState.value = state.copy(currentTrack = prev)
    }

    override fun seekTo(seconds: Int) {
        val state = _playbackState.value
        val clamped = seconds.coerceIn(0, state.currentTrack.durationSeconds)
        _playbackState.value = state.copy(
            currentTrack = state.currentTrack.copy(currentPositionSeconds = clamped)
        )
    }

    override fun setVolume(volume: Int) {
        _playbackState.value = _playbackState.value.copy(
            volumePercent = volume.coerceIn(0, 100)
        )
    }
}
