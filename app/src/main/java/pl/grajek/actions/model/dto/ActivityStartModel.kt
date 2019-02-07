package pl.grajek.actions.model.dto

import android.os.Bundle

data class ActivityStartModel(
    val type: Class<*>,
    val bundle: Bundle
)