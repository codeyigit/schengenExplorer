package hr.algebra.schengenexplorer.api

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SchengenWorker(private val context: Context,workerParameters:WorkerParameters):
    Worker(context,workerParameters) {
    override fun doWork(): Result {
        SchengenFetcher(context).fetchItems(10)
        return Result.success()
    }


}