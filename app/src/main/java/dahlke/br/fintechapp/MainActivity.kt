package dahlke.br.fintechapp
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var btnSave : Button
    private lateinit var btnExtract : Button
    private lateinit var btnExit : Button
    private val financialOpList = ArrayList<FinancialOperation>()
    private val arrayListKey = "FINANCIAL_OPERATION_LIST"

    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                //método novo para os SDK mais novos
                result.data?.getParcelableArrayListExtra(arrayListKey, FinancialOperation::class.java)
            } else{
                //método deprecated para os SDK mais antigos
                result.data?.getParcelableArrayListExtra<FinancialOperation>(arrayListKey)
            }
            userData?.let {
                financialOpList.addAll(userData)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnSave = findViewById(R.id.btnSave)
        btnExtract = findViewById(R.id.btnExtract)
        btnExit = findViewById(R.id.btnSair)

        btnSave.setOnClickListener {
            openSaveActivity()
        }

        btnExtract.setOnClickListener {
            openExtractActivity()
        }

        btnExit.setOnClickListener {
            finish()
        }
    }

    private fun openExtractActivity() {
        val intent = Intent(this, ExtratoActivity::class.java)
        intent.putExtra("financialOpList", financialOpList)
        startActivity(intent)
    }

    private fun openSaveActivity(){
        val intent = Intent(this, CadastrarActivity::class.java)

        activityResultLauncher.launch(intent)
    }
}