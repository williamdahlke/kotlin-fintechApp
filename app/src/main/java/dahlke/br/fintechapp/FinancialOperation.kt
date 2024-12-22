package dahlke.br.fintechapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FinancialOperation (
    val description : String,
    val operationValue : Float,
    val operationType : FinancialOperationType) : Parcelable {
}