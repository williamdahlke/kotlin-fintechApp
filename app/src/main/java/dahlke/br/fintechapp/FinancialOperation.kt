package dahlke.br.fintechapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FinancialOperation (
    val description : String,
    val operationValue : Double,
    val operationType : FinancialOperationType) : Parcelable {

    override fun toString(): String {
        return "Description: $description, Type: ${operationType.toString().lowercase()}, Value: $operationValue"
    }
}
