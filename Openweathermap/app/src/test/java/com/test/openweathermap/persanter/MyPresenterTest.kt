package com.test.openweathermap.persanter

import com.test.openweathermap.MyContract
import com.test.openweathermap.WeatherData
import com.test.openweathermap.container.Callback
import com.test.openweathermap.data.datasource.repository.MyRepository
import com.test.openweathermap.data.model.Coord
import com.test.openweathermap.data.model.Weather
import com.test.openweathermap.utils.Utils
import junit.framework.TestCase
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MyPresenterTest : TestCase() {

    @Mock
    private lateinit var repository: MyRepository

    @Mock
    private lateinit var view: MyContract.View

    private lateinit var weatherData: WeatherData

    @Captor
    private lateinit var captor: ArgumentCaptor<Callback<WeatherData>>

    private lateinit var presenter: MyPresenter

    public override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        presenter = MyPresenter(view, repository)
    }

    fun testOnGetWeatherUpdateResponse() {
        presenter.onGetWeatherUpdate()
        verify(repository).getWeatherUpdate(MockitoHelper.capture(captor))
        captor.value.onResponse(testData())
        verify(view).getWeatherUpdate(
            Utils.LAT + 30.0f + "\n" + Utils.LON + 20.0f,
            Utils.DISCRIPTION + "test"
        )
    }

    fun testOnGetWeatherUpdateError() {
        presenter.onGetWeatherUpdate()
        verify(repository).getWeatherUpdate(MockitoHelper.capture(captor))
        captor.value.onError(anyString(), anyString())
        verify(view).getDataError(anyString(), anyString())
    }

    object MockitoHelper {
        fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()
    }

    private fun testData(): WeatherData {
        val weather = Weather(1, "main", "test")
        var mutableList: MutableList<Weather> = mutableListOf<Weather>()
        val coord = Coord(20.0f, 30.0f)
        mutableList.add(weather)
        weatherData = WeatherData(mutableList, coord)
        return weatherData
    }

}