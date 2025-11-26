package ar.edu.unlam.mobile.scaffolding.ui.screens.map
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import javax.inject.Inject

class MapsRepository
    @Inject
    constructor() {
        suspend fun getRoute(
            origin: LatLng,
            destination: LatLng,
            apiKey: String,
        ): List<LatLng> =
            withContext(Dispatchers.IO) {
                val url =
                    "https://maps.googleapis.com/maps/api/directions/json?" +
                        "origin=${origin.latitude},${origin.longitude}" +
                        "&destination=${destination.latitude},${destination.longitude}" +
                        "&key=$apiKey"

                val client = OkHttpClient()
                val request = Request.Builder().url(url).build()

                val response = client.newCall(request).execute()
                val body = response.body?.string()

                if (body == null) return@withContext emptyList()

                Log.d("MAP_ROUTE", body)

                val json = JSONObject(body)
                val routes = json.getJSONArray("routes")
                if (routes.length() == 0) return@withContext emptyList()

                val polyline =
                    routes
                        .getJSONObject(0)
                        .getJSONObject("overview_polyline")
                        .getString("points")

                decodePolyline(polyline)
            }

        private fun decodePolyline(encoded: String): List<LatLng> {
            val poly = ArrayList<LatLng>()
            var index = 0
            val len = encoded.length
            var lat = 0
            var lng = 0

            while (index < len) {
                var b: Int
                var shift = 0
                var result = 0
                do {
                    b = encoded[index++].code - 63
                    result = result or ((b and 0x1f) shl shift)
                    shift += 5
                } while (b >= 0x20)
                val dlat = if ((result and 1) != 0) (result shr 1).inv() else result shr 1
                lat += dlat

                shift = 0
                result = 0
                do {
                    b = encoded[index++].code - 63
                    result = result or ((b and 0x1f) shl shift)
                    shift += 5
                } while (b >= 0x20)
                val dlng = if ((result and 1) != 0) (result shr 1).inv() else result shr 1
                lng += dlng

                poly.add(LatLng(lat / 1E5, lng / 1E5))
            }
            return poly
        }
    }
