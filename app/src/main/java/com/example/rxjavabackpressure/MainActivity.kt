package com.example.rxjavabackpressure

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = "MainActivity"

    private lateinit var mSubscription: Subscription

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.button1).setOnClickListener(this)
        findViewById<View>(R.id.button2).setOnClickListener(this)
        findViewById<View>(R.id.button3).setOnClickListener(this)
        findViewById<View>(R.id.button4).setOnClickListener(this)
        findViewById<View>(R.id.button5).setOnClickListener(this)
        findViewById<View>(R.id.button6).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button1 -> {

                Flowable
                    .create(FlowableOnSubscribe<Int> { e ->
                        val threadName = Thread.currentThread().id.toString()
                        Log.d(TAG, "被观察者线程id: " + threadName + "  开始发射数据" + System.currentTimeMillis())
                        for (i in 0..150) {
                            Log.d(TAG, "被观察者线程id: " + threadName + "  发射---->" + i)
                            e.onNext(i)
                            try {
                                Thread.sleep(10)//每隔10毫秒发射一次数据
                            } catch (ex: Exception) {
                                e.onError(ex)
                            }

                        }
                        Log.d(TAG, "被观察者线程id: " + threadName + "  发射数据结束" + System.currentTimeMillis())
                        e.onComplete()

                    }, BackpressureStrategy.MISSING)
                    .subscribe(object : Subscriber<Int> {
                        override fun onSubscribe(s: Subscription) {
//                            s.request(130)            //注意此处，暂时先这么设置
                            mSubscription = s
                        }

                        override fun onNext(integer: Int?) {
                            try {
                                Thread.sleep(20)//每隔20毫秒接收一次数据
                            } catch (ignore: InterruptedException) {
                            }

                            Log.d(TAG, "观察者线程id: " + Thread.currentThread().id.toString() + "  接收---->" + integer)
                        }

                        override fun onError(t: Throwable) {
                            Log.d(
                                TAG,
                                "观察者线程id: " + Thread.currentThread().id.toString() + "  onError:---->" + t
                            )
                        }

                        override fun onComplete() {

                            Log.d(TAG, "观察者线程id: " + Thread.currentThread().id.toString() + "  接收----> 完成")
                        }
                    })

            }

            R.id.button2 -> {
                mSubscription.request(20)
            }

            R.id.button3 -> {
                Flowable
                    .create(FlowableOnSubscribe<Int> { e ->
                        val threadName = Thread.currentThread().id.toString()
                        Log.d(TAG, "被观察者线程id: " + threadName + "  开始发射数据" + System.currentTimeMillis())
                        for (i in 0..150) {
                            Log.d(TAG, "被观察者线程id: " + threadName + "  发射---->" + i)
                            e.onNext(i)
                            try {
                                Thread.sleep(10)//每隔10毫秒发射一次数据
                            } catch (ex: Exception) {
                                e.onError(ex)
                            }

                        }
                        Log.d(TAG, "被观察者线程id: " + threadName + "  发射数据结束" + System.currentTimeMillis())
                        e.onComplete()

                    }, BackpressureStrategy.LATEST)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Subscriber<Int> {
                        override fun onSubscribe(s: Subscription) {
                            s.request(130)            //注意此处，暂时先这么设置
                            mSubscription = s
                        }

                        override fun onNext(integer: Int?) {
                            try {
                                Thread.sleep(20)//每隔20毫秒接收一次数据
                            } catch (ignore: InterruptedException) {
                            }

                            Log.d(TAG, "观察者线程id: " + Thread.currentThread().id.toString() + "  接收---->" + integer)
                        }

                        override fun onError(t: Throwable) {
                            Log.d(
                                TAG,
                                "观察者线程id: " + Thread.currentThread().id.toString() + "  onError: ---->" + t
                            )
                        }

                        override fun onComplete() {

                            Log.d(TAG, "观察者线程id: " + Thread.currentThread().id.toString() + "  接收----> 完成")
                        }
                    })
            }

            R.id.button4 -> {
                Flowable
                    .create(FlowableOnSubscribe<Int> { e ->
                        val threadName = Thread.currentThread().id.toString()
                        Log.d(TAG, "被观察者线程id: " + threadName + "  开始发射数据" + System.currentTimeMillis())
                        for (i in 1..150) {
                            Log.d(TAG, "被观察者线程id: " + threadName + "  发射---->" + i)
                            e.onNext(i)
                            try {
                                Thread.sleep(10)//每隔10毫秒发射一次数据
                            } catch (ex: Exception) {
                                e.onError(ex)
                            }

                        }
                        Log.d(TAG, "被观察者线程id: " + threadName + "  发射数据结束" + System.currentTimeMillis())
                        e.onComplete()

                    }, BackpressureStrategy.DROP)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Subscriber<Int> {
                        override fun onSubscribe(s: Subscription) {
                            s.request(135)            //注意此处，暂时先这么设置
                            mSubscription = s
                        }

                        override fun onNext(integer: Int?) {
                            try {
                                Thread.sleep(15)//每隔20毫秒接收一次数据
                            } catch (ignore: InterruptedException) {
                            }

                            Log.d(TAG, "观察者线程id: " + Thread.currentThread().id.toString() + "  接收---->" + integer)
                        }

                        override fun onError(t: Throwable) {
                            Log.d(
                                TAG,
                                "观察者线程id: " + Thread.currentThread().id.toString() + "  onError: ---->" + t
                            )
                        }

                        override fun onComplete() {

                            Log.d(TAG, "观察者线程id: " + Thread.currentThread().id.toString() + "  接收----> 完成")
                        }
                    })
            }

            R.id.button5 -> {

                Flowable.create(FlowableOnSubscribe<Int> { emitter ->
                    Log.d(TAG, "观察者可接收事件数量 = " + emitter.requested())
                    var flag: Boolean //设置标记位控制

                    // 被观察者一共需要发送500个事件
                    for (i in 0..499) {
                        flag = false

                        // 若requested() == 0则不发送
                        while (emitter.requested() == 0L) {
                            if (!flag) {
                                Log.d(TAG, "不再发送")
                                flag = true
                            }
                        }
                        // requested() ≠ 0 才发送
                        Log.d(TAG, "发送了事件" + i + "，观察者可接收事件数量 = " + emitter.requested())
                        emitter.onNext(i)


                    }
                }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()) // 设置被观察者在io线程中进行
                    .observeOn(AndroidSchedulers.mainThread()) // 设置观察者在主线程中进行
                    .subscribe(object : Subscriber<Int> {
                        override fun onSubscribe(s: Subscription) {
                            Log.d(TAG, "onSubscribe")
                            mSubscription = s
                            // 初始状态 = 不接收事件；通过点击按钮接收事件
                        }

                        override fun onNext(integer: Int?) {
                            Log.d(TAG, "接收到了事件" + integer!!)
                        }

                        override fun onError(t: Throwable) {
                            Log.w(TAG, "onError: ", t)
                        }

                        override fun onComplete() {
                            Log.d(TAG, "onComplete")
                        }
                    })

            }
            R.id.button6 -> {
                mSubscription.request(47)
            }
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
