package ipvc.gymbuddy.app.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.models.Exercise
import ipvc.gymbuddy.api.models.requests.exercise.CreateExerciseRequest
import ipvc.gymbuddy.api.services.ExerciseService
import ipvc.gymbuddy.app.core.AsyncData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExerciseDataStore(context: Context) : BaseDataStore(context) {

    @SuppressLint("StaticFieldLeak")
    companion object{
        @Volatile private var instance: ExerciseDataStore? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: ExerciseDataStore(context).also { instance = it }
        }
    }

    var exercises = MutableLiveData<AsyncData<List<Exercise>>>(AsyncData(listOf()))
    var post = MutableLiveData<AsyncData<CreateExerciseRequest>>(AsyncData())

    fun getExercises() {
        exercises.postValue(AsyncData(exercises.value?.data ?: listOf(), AsyncData.Status.LOADING))
        coroutine.launch {
            when(val response = ExerciseService().getExercises())  {
                is RequestResult.Success -> {
                    exercises.postValue(AsyncData(response.data.exercises, AsyncData.Status.SUCCESS))
                }
                is RequestResult.Error -> {
                    exercises.postValue(AsyncData(exercises.value?.data ?: listOf(), AsyncData.Status.ERROR))
                }
            }
        }
    }

    fun createExercise(name: String, photo: String?, machineId: String, categories: List<String>) {
        val entity = CreateExerciseRequest(name, photo, machineId, categories)

        post.postValue(AsyncData(entity, AsyncData.Status.LOADING))
        coroutine.launch {
            when(ExerciseService().createExercise(entity))  {
                is RequestResult.Success -> post.postValue(AsyncData(null, AsyncData.Status.SUCCESS))
                is RequestResult.Error -> post.postValue(AsyncData(null, AsyncData.Status.ERROR))
            }

            delay(2500)
            post.postValue(AsyncData(null, AsyncData.Status.IDLE))
        }
    }
}