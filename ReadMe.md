# LightCycle (WORK IN PROGRESS)

[![License](https://img.shields.io/static/v1?color=blue&label=License&message=MIT)](https://github.com/g000sha256/lightcycle-compose-android/blob/master/License)

```kotlin
val lightcycle = Lightcycle(name = "Feature")
```

```kotlin
internal interface FeatureViewModel { .... }
```

```kotlin
internal class FeatureViewModelImpl(
    backRegistry: BackRegistry,
    lifecycleRegistry: LifecycleRegistry,
    ....
) : FeatureViewModel {

    init {
        backRegistry.setBackCallback { .... }
        lifecycleRegistry.setStartCallback { .... }
        lifecycleRegistry.setStopCallback { .... }
        lifecycleRegistry.setDestroyCallback {
            backRegistry.setBackCallback(null)
            lifecycleRegistry.setDestroyCallback(null)
            lifecycleRegistry.setStartCallback(null)
            lifecycleRegistry.setStopCallback(null)
            ....
        }
        ....
    }

}
```

```kotlin
interface FeatureView {

    @Composable
    fun Draw(modifier: Modifier)

}
```

```kotlin
@Composable
internal fun FeatureUi(featureViewModel: FeatureViewModel, modifier: Modifier) {
    ....
}
```

```kotlin
interface FeatureController : BackCallback, DestroyCallback, FeatureView
```

```kotlin
internal class FeatureControllerImpl(
    private val featureViewModel: FeatureViewModel,
    private val visibilityCallback: VisibilityCallback,
    backCallback: BackCallback,
    destroyCallback: DestroyCallback
) : BackCallback by backCallback, DestroyCallback by destroyCallback, FeatureController {

    @Composable
    override fun Draw(modifier: Modifier) {
        VisibilityHandler(visibilityCallback)
        FeatureUi(featureViewModel, modifier)
    }

}
```

```kotlin
val featureViewModel: FeatureViewModel = FeatureViewModelImpl(lightcycle, lightcycle, ....)
```

```kotlin
val featureController: FeatureController = FeatureControllerImpl(featureViewModel, lightcycle, lightcycle, lightcycle)
```