package dahlke.br.fintechapp

import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ExtratoActivity : AppCompatActivity() {

    private lateinit var financialOpList : ArrayList<FinancialOperation>
    private lateinit var lvFinancialOp : ListView
    private val arrayListKey : String = "FINANCIAL_OPERATION_LIST"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_extrato)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bundle = intent.extras
        if (bundle != null){
            val list = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                bundle.getParcelableArrayList(arrayListKey, FinancialOperation::class.java)
            } else{
                bundle.getParcelableArrayList(arrayListKey)
            }

            financialOpList = list!!
        }

        lvFinancialOp = findViewById(R.id.lvFinancialOp)
        val arrayAdapter: ArrayAdapter<FinancialOperation> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            financialOpList
        )
        lvFinancialOp.adapter = arrayAdapter
    }
}