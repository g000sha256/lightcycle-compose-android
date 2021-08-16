package ru.g000sha256.lightcycle

interface Lightcycle : BackCallback, BackRegistry, DestroyCallback, LifecycleRegistry, VisibilityCallback

fun Lightcycle(name: String, logger: Logger? = null): Lightcycle {
    return LightcycleImpl(name, logger)
}

private class LightcycleImpl(private val name: String, private val logger: Logger?) : Lightcycle {

    private var backCallback: BackCallback? = null
    private var isDestroyed: Boolean? = null
    private var isStarted: Boolean? = null
    private var destroyCallback: DestroyCallback? = null
    private var startCallback: StartCallback? = null
    private var stopCallback: StopCallback? = null

    init {
        log("init")
    }

    override fun onBack() {
        if (isDestroyed == true) return
        if (isStarted == false) return
        log("back")
        backCallback?.onBack()
    }

    override fun setBackCallback(backCallback: BackCallback?) {
        this.backCallback = backCallback
    }

    override fun onDestroy() {
        onStop()
        if (isDestroyed == true) return
        isDestroyed = true
        log("destroy")
        destroyCallback?.onDestroy()
    }

    override fun setDestroyCallback(destroyCallback: DestroyCallback?) {
        this.destroyCallback = destroyCallback
        if (isDestroyed == true) {
            destroyCallback?.onDestroy()
        }
    }

    override fun setStartCallback(startCallback: StartCallback?) {
        this.startCallback = startCallback
        if (isStarted == true) {
            startCallback?.onStart()
        }
    }

    override fun setStopCallback(stopCallback: StopCallback?) {
        this.stopCallback = stopCallback
        if (isStarted == false) {
            stopCallback?.onStop()
        }
    }

    override fun onStart() {
        if (isDestroyed == true) return
        if (isStarted == true) return
        isStarted = true
        log("start")
        startCallback?.onStart()
    }

    override fun onStop() {
        if (isDestroyed == true) return
        if (isStarted == false) return
        isStarted = false
        log("stop")
        stopCallback?.onStop()
    }

    private fun log(message: String) {
        logger?.log("$name -> $message")
    }

}