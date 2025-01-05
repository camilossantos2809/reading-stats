package io.readingstats.android.view.readingProgress

import androidx.lifecycle.ViewModel
import io.readingstats.android.domain.ReadingProgress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ReadingProgressViewModel:ViewModel() {
    private val _progress = MutableStateFlow(ReadingProgress())
    val progress get() = _progress.asStateFlow()
}