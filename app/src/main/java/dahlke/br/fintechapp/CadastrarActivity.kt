package dahlke.br.fintechapp

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CadastrarActivity : AppCompatActivity() {
    private val arrayListKey = "FINANCIAL_OPERATION_LIST"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastrar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun teste(){
        val meuObjeto = FinancialOperation("Gasolina", 200.0f, FinancialOperationType.DEBITO)
        val lista = arrayListOf(meuObjeto)
        val resultIntent = Intent()

        resultIntent.putExtra(arrayListKey, lista)
        this.setResult(RESULT_OK, resultIntent)
        finish()
    }
}

