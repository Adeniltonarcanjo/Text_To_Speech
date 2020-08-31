package adeniltonmorais.android.texttospeech

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this, this)



        btnFalar.setOnClickListener {
            var texto=editTextTextMultiLine.text.toString()
            if (texto.isNullOrEmpty()){
                falar("Texto nao Digitado")
            }else {
                falar(texto)
            }

             }




    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.getDefault())

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {

                Toast.makeText(applicationContext, "Falha ", Toast.LENGTH_LONG).show()

            } else {
                tts!!.speak("Bem vindo ao aplicativo", TextToSpeech.QUEUE_FLUSH, null, null)
            }
        } else {
            Toast.makeText(applicationContext, "Falha ", Toast.LENGTH_LONG).show()
        }
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun falar(str :String) {

        if (str.isNullOrEmpty()){
            tts!!.speak("Não foi digitado texto", TextToSpeech.QUEUE_FLUSH, null,null)
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts!!.speak(str, TextToSpeech.QUEUE_FLUSH, null, "")
        } else {
            @Suppress("DEPRECATION")
            tts!!.speak(str, TextToSpeech.QUEUE_FLUSH, null)
        }
    }




    override fun onDestroy() {
        // Shut down TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        super.onDestroy()
    }


}
