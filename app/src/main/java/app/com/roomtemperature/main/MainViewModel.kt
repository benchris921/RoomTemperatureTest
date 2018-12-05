package app.com.roomtemperature.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Handler
import java.util.*

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val temperature = MutableLiveData<Float>()

    private val fanSpeedLevel = MutableLiveData<Float>()

    fun init() {
        temperature.value = 29f
        fanSpeedLevel.value = 1f
        runTemperatureRandomValueChanger()
        runFanSpeedLevelRandomValueChanger()
    }

    fun getTemperature(): LiveData<Float> {
        return temperature
    }

    fun getFanSpeedLevel(): LiveData<Float> {
        return fanSpeedLevel
    }

    private fun runTemperatureRandomValueChanger() {
        Handler().postDelayed({
            temperature.value = Random().nextFloat() * maxTemperature
            runTemperatureRandomValueChanger()
        }, 2000)
    }

    private fun runFanSpeedLevelRandomValueChanger() {
        Handler().postDelayed({
            fanSpeedLevel.value = Random().nextFloat() * maxFanSpeedLevel
            runFanSpeedLevelRandomValueChanger()
        }, 2500)
    }

    companion object {
        const val maxTemperature = 40f
        const val maxFanSpeedLevel = 3
    }
}
