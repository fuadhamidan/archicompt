package `in`.jejak.android.data.network

import android.util.Log
import com.firebase.jobdispatcher.Job
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.firebase.jobdispatcher.RetryStrategy

/**
 * Created by fuado on 2018/10/13.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */
class AppFirebaseJobService : JobService() {

    /**
     * The entry point to your Job. Implementations should offload work to another thread of
     * execution as soon as possible.
     *
     *
     * This is called by the Job Dispatcher to tell us we should start our job. Keep in mind this
     * method is run on the application's main thread, so we need to offload work to a background
     * thread.
     *
     * @return whether there is more work remaining.
     */
    override fun onStartJob(jobParameters: JobParameters): Boolean {
        Log.d(LOG_TAG, "Job service started")

        // TODO Finish this method when instructed. Will eventually call the fetchWeather code

        jobFinished(jobParameters, false)

        return true
    }

    /**
     * Called when the scheduling engine has decided to interrupt the execution of a running job,
     * most likely because the runtime constraints associated with the job are no longer satisfied.
     *
     * @return whether the job should be retried
     * @see Job.Builder.setRetryStrategy
     * @see RetryStrategy
     */
    override fun onStopJob(jobParameters: JobParameters): Boolean {
        return true
    }

    companion object {
        private val LOG_TAG = AppFirebaseJobService::class.java.simpleName
    }
}