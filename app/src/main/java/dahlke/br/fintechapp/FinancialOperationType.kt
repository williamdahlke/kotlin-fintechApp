package dahlke.br.fintechapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class FinancialOperationType : Parcelable {
    CREDITO, DEBITO
}