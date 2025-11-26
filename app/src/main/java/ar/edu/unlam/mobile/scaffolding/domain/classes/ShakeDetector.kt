package ar.edu.unlam.mobile.scaffolding.domain.classes

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

class ShakeDetector(
    private val onShake: () -> Unit,
) : SensorEventListener {
    @Suppress("ktlint:standard:property-naming")
    private val SHAKE_THRESHOLD_GRAVITY = 3.3F

    @Suppress("ktlint:standard:property-naming")
    private val SHAKE_SLOP_TIME_MS = 500
    private var mShakeTimestamp: Long = 0

    override fun onAccuracyChanged(
        sensor: Sensor?,
        accuracy: Int,
    ) {}

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val gForce = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat() / SensorManager.GRAVITY_EARTH

        if (gForce > SHAKE_THRESHOLD_GRAVITY) {
            val now = System.currentTimeMillis()

            if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                return
            }
            mShakeTimestamp = now

            onShake.invoke()
        }
    }
}

@Composable
fun ShakeDetectorComposable(onShake: () -> Unit) {
    val context = LocalContext.current
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    // instancia del detector con el callback de navegación
    val detector = remember { ShakeDetector(onShake) }

    // Ciclo de Vida con DisposableEffect
    DisposableEffect(sensorManager, accelerometer) {
        // Se ejecuta cuando el Composable entra en la composición (se muestra)
        sensorManager.registerListener(
            detector,
            accelerometer,
            SensorManager.SENSOR_DELAY_UI, // Baja latencia para UI
        )

        // Se ejecuta cuando el Composable sale de la composición o la clave cambia
        onDispose {
            sensorManager.unregisterListener(detector)
        }
    }
}
