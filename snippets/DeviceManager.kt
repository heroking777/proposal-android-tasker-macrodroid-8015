import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SiteAutoCirculationManager(private val context: Context) {

    private val TAG = "SiteAutoCirculationManager"

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun performSiteCheck(url: String, authHeader: String): Boolean {
        if (!isNetworkAvailable()) {
            Log.e(TAG, "No network available")
            return false
        }

        try {
            val siteUrl = URL(url)
            with(siteUrl.openConnection() as HttpURLConnection) {
                requestMethod = "GET"
                setRequestProperty("Authorization", authHeader)

                val responseCode: Int = responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) { // success
                    Log.d(TAG, "Site check successful")
                    return true
                } else {
                    Log.e(TAG, "Site check failed with response code $responseCode")
                    return false
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception during site check", e)
            return false
        }
    }

    fun fetchContent(url: String): String? {
        if (!isNetworkAvailable()) {
            Log.e(TAG, "No network available")
            return null
        }

        try {
            val siteUrl = URL(url)
            with(siteUrl.openConnection() as HttpURLConnection) {
                requestMethod = "GET"

                val inputStream = inputStream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var inputLine: String?
                while (bufferedReader.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
                bufferedReader.close()

                return response.toString()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception during content fetch", e)
            return null
        }
    }
}