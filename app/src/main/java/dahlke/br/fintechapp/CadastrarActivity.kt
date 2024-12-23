package dahlke.br.fintechapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import java.util.ArrayList

class CadastrarActivity : AppCompatActivity() {
    private val arrayListKey = "FINANCIAL_OPERATION_LIST"
    private lateinit var etDescription : EditText
    private lateinit var etValue : EditText
    private lateinit var spOperation : Spinner
    private lateinit var btnBack : Button
    private lateinit var btnSave : Button
    private lateinit var selectedOperationType : FinancialOperationType
    private lateinit var tvResumeValue : TextView
    private lateinit var tvResumeCoin : TextView
    private val financialOpList = ArrayList<FinancialOperation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastrar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etDescription = findViewById(R.id.etDescription)
        etValue = findViewById(R.id.etValue)
        spOperation = findViewById(R.id.spOperation)
        btnBack = findViewById(R.id.btnBack)
        btnSave = findViewById(R.id.btnSave)
        tvResumeValue = findViewById(R.id.tvResumeValue)
        tvResumeCoin = findViewById(R.id.tvResumeCoin)

        hideResumeFields()

        val options = FinancialOperationType.entries.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spOperation.adapter = adapter

        spOperation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedOperationType = options[position]
                setResumeValue()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        btnBack.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra(arrayListKey, financialOpList)
            this.setResult(RESULT_OK, resultIntent)
            finish()
        }

        btnSave.setOnClickListener {
            if (validateBeforeSave()){
                saveItem()
            } else{
                Toast.makeText(this, getString(R.string.messageSaveError), Toast.LENGTH_LONG).show()
            }
        }

        etValue.addTextChangedListener{
            setResumeValue()
        }
    }

    private fun hideResumeFields(){
        tvResumeValue.visibility = View.GONE
        tvResumeCoin.visibility = View.GONE
    }

    private fun showResumeFields(){
        tvResumeValue.visibility = View.VISIBLE
        tvResumeCoin.visibility = View.VISIBLE
    }

    private fun setResumeValue(){

        val value = etValue.text.toString().toDoubleOrNull()
        if (value != null){
            showResumeFields()

            if (selectedOperationType == FinancialOperationType.DEBITO){
                (value * -1).toString().also { tvResumeValue.text = it }
                tvResumeValue.setTextColor(Color.RED)
                tvResumeCoin.setTextColor(Color.RED)
            } else{
                value.toString().also { tvResumeValue.text = it }
                tvResumeValue.setTextColor(Color.BLACK)
                tvResumeCoin.setTextColor(Color.BLACK)
            }
        }
    }

    private fun validateBeforeSave() : Boolean{
        return validateDescription() && validateValue()
    }

    private fun validateDescription() : Boolean{
        return !etDescription.text.isNullOrEmpty() && !etValue.text.isNullOrEmpty()
    }

    private fun validateValue() : Boolean{
        return etValue.text.toString().toDoubleOrNull()!! > 0
    }

    private fun saveItem(){
        financialOpList.add(FinancialOperation(etDescription.text.toString(), etValue.text.toString().toDouble(), selectedOperationType))
        Toast.makeText(this, getString(R.string.messageSave), Toast.LENGTH_SHORT).show()
        clearFields()
    }

    private fun clearFields(){
        hideResumeFields()
        etDescription.text.clear()
        etValue.text.clear()
        spOperation.setSelection(0)
    }
}

