package ru.g000sha256.lightcycle

interface LifecycleRegistry {

    fun setDestroyCallback(destroyCallback: DestroyCallback?)

    fun setStartCallback(startCallback: StartCallback?)

    fun setStopCallback(stopCallback: StopCallback?)

}