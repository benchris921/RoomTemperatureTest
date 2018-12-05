package app.com.roomtemperature.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.com.roomtemperature.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.init()

        selectTabRoomUI()

        circleRoomTemperature.apply {
            setBarColor(ContextCompat.getColor(context!!, R.color.colorTemperatureGradientStartColor),
                            ContextCompat.getColor(context!!, R.color.colorTemperatureGradientEndColor))
        }

        linearLayoutTabRoom.setOnClickListener {
            selectTabRoomUI()
        }

        linearLayoutTabBedRoom.setOnClickListener {
            selectTabBedRoomUI()
        }

        viewModel.getTemperature().observe(this, Observer {
            showTemperatureValue(it ?: 20f)
        })

        viewModel.getFanSpeedLevel().observe(this, Observer {
            showFanSpeedLevel(it ?: 1f)
        })
    }

    private fun selectTabRoomUI() {
        deselectTabs()
        frameLayoutTabRoomIndicator.visibility = View.VISIBLE
    }

    private fun selectTabBedRoomUI() {
        deselectTabs()
        frameLayoutTabBedRoomIndicator.visibility = View.VISIBLE
    }

    private fun deselectTabs() {
        frameLayoutTabRoomIndicator.visibility = View.INVISIBLE
        frameLayoutTabBedRoomIndicator.visibility = View.INVISIBLE
    }

    private fun showTemperatureValue(value: Float) {
        var progress = 0f

        if (value < 0) { progress = 0f }
        else if (value > MainViewModel.maxTemperature) { progress = 75f }
        else {
            progress = (value / MainViewModel.maxTemperature) * 75f
        }

        textViewTemperature.text = "${value.toInt()}°C"
        circleRoomTemperature.setValueAnimated(progress)
    }

    private fun showFanSpeedLevel(value: Float) {
        var fanSpeedText = getString(R.string.low)
        var progress = 0f
        when {
            value <= 1f -> {
                fanSpeedText = getString(R.string.low)
                progress = 1f
            }
            value <= 2f -> {
                fanSpeedText = getString(R.string.med)
                progress = 2f
            }
            value <= 3f -> {
                fanSpeedText = getString(R.string.high)
                progress = 3f
            }
            else -> {
                fanSpeedText = getString(R.string.high)
                progress = 1f
            }
        }

        textViewFanSpeed.text = fanSpeedText
        circleFanSpeed.setValue(progress)
    }

}
