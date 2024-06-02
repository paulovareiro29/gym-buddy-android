package ipvc.gymbuddy.app.extensions

import com.google.gson.Gson
import ipvc.gymbuddy.api.models.Exercise
import ipvc.gymbuddy.api.models.PlanExercise
import ipvc.gymbuddy.api.models.TrainingPlan
import ipvc.gymbuddy.database.entities.DBPlanExercise

fun PlanExercise.toDatabaseModel(): DBPlanExercise{
    return DBPlanExercise(
        id,
        Gson().toJson(plan),
        Gson().toJson(exercise),
        repetitions,
        sets,
        rest_between_sets,
        day
    )
}

fun DBPlanExercise.toAPIModel(): PlanExercise {
    return PlanExercise(
        id,
        Gson().fromJson(plan, TrainingPlan::class.java),
        Gson().fromJson(exercise, Exercise::class.java),
        repetitions,
        sets,
        rest_between_sets,
        day
    )
}