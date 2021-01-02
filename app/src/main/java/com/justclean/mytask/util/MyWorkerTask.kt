package com.justclean.mytask.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.justclean.mytask.ui.main.post.PostViewModel
import java.lang.Exception

class MyWorkerTask (context: Context,workerParameters: WorkerParameters):
    CoroutineWorker(context,workerParameters){
    override suspend fun doWork(): Result {

        return try {
            try {
                makingNetworkCall()
            }catch (e:Exception){
                Result.failure()
            }
        }catch (e:Exception){
            Result.failure()
        }
    }

    private suspend fun makingNetworkCall() :Result{
      Handler(Looper.getMainLooper()).postDelayed({

      },3000)


        return Result.success()

    }


}
