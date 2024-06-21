package ipvc.gymbuddy.api.core

import com.google.gson.Gson
import retrofit2.Response

class ResponseParser {

    companion object {
        fun <T> Success(response: Response<T>): RequestResult.Success<*> {
            return response.body() as RequestResult.Success<*>
        }

        fun <T> Error(response: Response<T>): RequestResult.Error {
            return Gson().fromJson(response.errorBody()!!.string(), RequestResult.Error::class.java)
        }

        inline fun <reified T : Any> payload (obj: Any): T {
            if (obj is RequestResult.Success<*>) {
                if (obj.data is T) {
                    return obj.data
                }
            }
            throw Exception("Unsafe object cast from ${obj::class.simpleName} to Success<${T::class.simpleName}>")
        }
    }
}