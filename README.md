
# Retain MVP
  
MVP implementation with stateful presenters for Android `Activity` in Kotlin that survive orientation changes.

## How?
`Retain MVP` uses retained fragment behind the scenes (`Fragment::setRetainInstance` set to `true`).

## Aren't retained Fragments a bad thing?
Not necessarily, unless they're misused. Some projects set their UI fragments as retained instances. This can indeed cause issues if one is not careful and promotes incorrect handling of configuration changes. However, the reason for introducing retained fragments was never for UI fragments to survive configuration changes, rather the idea was to use them to retain instance of resource heavy calculations, or asynchronous operations, such as `AsyncTask`, so that these operations are not leaking context or do survive configuration changes. This is what `Retain MVP` is effectively leveraging. `Retain MVP` allows presenters to survive configuration changes, thus allowing for making resource heavy computations or asynchronous operations without having to worry about configuration changes of your `Activity`. Even Android's Architectural components library is based on this approach.

## But what about Activities getting destroyed by system?
I'm glad you should ask! Even Android's Architectural components library suffers from one issue, that might be very easy to overlook! It doesn't handle saved instance state properly! What does it mean? In some situations, the operating system might decide to kill your app process and with it all your activities. However, the system does allow for saving state of your activities in such cases, so that when user returns to your app, it can look like the user starts where he left of the last time he was using your app. A lot of apps actually don't handle saved instance state correctly and it's easy to forget about it. Especially if you're using ViewModels or Presenters that survive configuration changes! So... 

## How does `Retain MVP` help me here? 
Another brilliant question! In order to use `Retain MVP`, you're **forced** to implement certain classes, that take care of converting saved instance state to your own representation of `Activity` state and back into saved instance state. You don't even have to override `onSaveInstanceState()` in your `Activity`, it's taken care of for you by the library. All that and your `Activity` doesn't even need to inherit from any type from the library!

The idea is, that all of your screen state is represented in your `Presenter` using a single state property (that's already defined for you). Since you're representing the whole state using this property to reset your `Activity` after configuration changes, your app will behave exactly the same way even if your process is killed and you return to your app from the "Recent Apps" menu. The state will be restored.

## There is one more thing!
This library also helps you with starting activities with parameters. If you use the same keys for `Bundle` values in both saved instance state bundle and also in the `Intent::extras` `Bundle`,  and start an activity with such `Intent`, the library will accept these parameters and initialize your `Presenter` with these values. This is a nice way of moving through screens, sending parameters along the way, without having to resort to any singleton parameter holder, also helping with the boilerplate resulting from parsing parameters from `Intent::extras` a little bit.

## Sounds dope! How do I use it?!
At the moment, there's unfortunately no maven artifact that you can use to add this library to your project yet. You'll need to copy/clone the library to your project and add dependency on it to your `build.gradle` file.

To use `Retain MVP`, your presenters need to inherit from `Presenter` class. This is a generic class that requires you to define two generic parameters. `View` (which shouldn't be surprising for MVP) and `StoredState`. `StoredState` should be a simple data class, that represents state of the `View`. 

**Typical implementation of custom presenter might look like this:**

```kotlin
data class CustomViewState(val someValue: Int = 0)

interface CustomView {
    fun updateSomeViewUsingValue(value: Int)
}
class CustomPresenter : Presenter<CustomView, CustomViewState> {
    override val defaultStoredState: CustomViewState // State to be used as default  
        get() = CustomViewState()

    /**
     * This method is used to tell view how to be drawn using stored state
     */
    override fun updateView(view: CustomView) {
        view.updateSomeViewUsingValue(storedState.someValue)
    }

    fun methodThatUpdatesState(newValue: Int) {
        storedState = storedState.copy(someValue = newValue)
        updateView() // Call this method to redraw screen using new stored state
    }
}
```

To attach presenter to activity, you need to define two more classes. A factory for the presenter (this factory can be injected using DI framework of your choice) and a converter class that converts from/to `Bundle` to/from instance of your Stored State data class.

**Example presenter factory:**
```kotlin
class CustomPresenterFactory : PresenterFactory<CustomView, CustomViewState, CustomPresenter> {  
    override fun create() = CustomPresenter()  
}
```

**Example stored state converter:**
```kotlin
const val CustomValueBundleKey = "ValueBundleKey"  
  
class CustomViewStateConverter : StoredStateConverter<CustomViewState> {  
    override fun convertToStoredState(bundle: Bundle) = CustomViewState(  
            bundle.getInt(CustomValueBundleKey, 0)  
    )  
  
    override fun convertStoredStateToBundle(bundle: Bundle, storedState: CustomViewState) =  
            bundle.apply { putInt(CustomValueBundleKey, storedState.someValue) }  
}
```

Having these two classes defined, attaching presenter to your custom `Activity` is simple. 

**Example of custom `Activity`:**

```kotlin
class CustomActivity : AppCompatActivity(), CustomView {  
    override fun onCreate(savedInstanceState: Bundle?) {  
        super.onCreate(savedInstanceState)  
        setContentView(R.layout.activity_main)  
  
        val presenter = Presenters.getPresenterForActivity(  
                this,  
                CustomPresenterFactory(),  
                CustomViewStateConverter(),  
                this,  
                intent,  
                savedInstanceState  
        )  
  
        someButton.setOnClickListener { presenter.methodThatUpdatesState() }
  }  
  
    override fun updateSomeViewUsingValue(value: Int) {  
        someView.text = value
    }
}
```

## Ideas for improvement
* Add documentation into MVP classes
* Make passing `Intent` optional
* Create Maven artifact
* Implement this for Fragments

## Contribution
Feel free to drop a pull request with changes, or open an issue if you find some problem.

## License
```
Copyright 2018 lukas1

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```