package br.com.alexander.jokenpo

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class CustomObserver: DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        Log.d("Lifecycle", "Owner: ${owner.javaClass.simpleName} - Event: onCreate")
        super.onCreate(owner)
    }

    override fun onStart(owner: LifecycleOwner) {
        Log.d("Lifecycle", "Owner: ${owner.javaClass.simpleName} - Event: onStart")
        super.onStart(owner)
    }

    override fun onResume(owner: LifecycleOwner) {
        Log.d("Lifecycle", "Owner: ${owner.javaClass.simpleName} - Event: onResume")
        super.onResume(owner)
    }

    override fun onPause(owner: LifecycleOwner) {
        Log.d("Lifecycle", "Owner: ${owner.javaClass.simpleName} - Event: onPause")
        super.onPause(owner)
    }

    override fun onStop(owner: LifecycleOwner) {
        Log.d("Lifecycle", "Owner: ${owner.javaClass.simpleName} - Event: onStop")
        super.onStop(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Log.d("Lifecycle", "Owner: ${owner.javaClass.simpleName} - Event: onDestroy")
        super.onDestroy(owner)
    }

}