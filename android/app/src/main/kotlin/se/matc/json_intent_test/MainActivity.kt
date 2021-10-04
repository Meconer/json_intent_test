package se.matc.json_intent_test

import android.content.Intent
import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall

class MainActivity: FlutterActivity() {

    private var sharedText: String? = null
    private val CHANNEL = "se.matc.dataChannel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val action = intent.action
        val type = intent.type
        android.util.Log.d("MainActivity", "onCreate: intent.type = " + type)
        android.util.Log.d("MainActivity", "onCreate: intent.action = " + action.toString())

        if (Intent.ACTION_VIEW == action && type != null) {
            handleSendText(intent) // Handle text being sent
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val intent = intent
        val action = intent.action
        val type = intent.type
        android.util.Log.d("MainActivity", "onNewIntent: intent.type = " + type)
        android.util.Log.d("MainActivity", "onNewIntent: intent.action = " + action.toString())
        handleSendText(intent)
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
                .setMethodCallHandler { call: MethodCall, result: MethodChannel.Result ->
                    if (call.method.contentEquals("getSharedText")) {
                        result.success(sharedText)
                        sharedText = null
                    }
                }
    }



    fun handleSendText(intent: Intent) {
        // sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        // android.util.Log.d("MainActivity", "handleSend: intent.getStringExtra = " + sharedText)
        val data = intent.getData()
        val uri = intent.data
        if ( uri != null ) {
            val inputStream = this.contentResolver.openInputStream(uri)
            android.util.Log.d("MainActivity", "inputstream: " + inputStream.toString())
            val extraData = inputStream?.readBytes()
            sharedText = String(extraData!!)
            android.util.Log.d("MainActivity", "extraData: " + sharedText)
        }
        //android.util.Log.d("MainActivity", "handleSend: intent = " + sharedText)
        //android.util.Log.d("MainActivity", "uri: " + uri.toString())

    }
}
