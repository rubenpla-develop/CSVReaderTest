package com.example.rubenpla.csvreadertest

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val disposables : CompositeDisposable = CompositeDisposable()
    private lateinit var paginator: PublishProcessor<String>
    private var result : String? = null

    override fun onClick(v: View?) {
        load()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        paginator = PublishProcessor.create()

        csv_button.setOnClickListener(this)

        val disposable = paginator.onBackpressureDrop()
                .doOnNext { Toast.makeText(this, "CSV Requested", Toast.LENGTH_LONG).show()}
                .concatMap { getCsvFile() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result = it
                    Log.i("MainActivity", "Result : $it")
                }, {
                    Log.e("MainActivity", it.localizedMessage)
                } )

        disposables.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    fun load() {
        paginator.onNext(1.toString())
    }

    fun getCsvFile(): Flowable<String?>? {
        val csvRepository  = CsvRepository(CsvApi.getInstance())

        return Flowable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { _ -> csvRepository.getCsv() }
    }

/*    private class MakeRequestAsyncTask : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void?): String? {
            var result : String?

            val csvRepository  = CsvRepository(CsvApi.getInstance())

            result = csvRepository.getCsv()

            return result
        }

        override fun onPostExecute(result: String?) {
            Log.d("AsyncTask", "AsyncTaskResult : $result")
        }
    }*/
}
