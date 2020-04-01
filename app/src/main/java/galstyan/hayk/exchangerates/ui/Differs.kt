package galstyan.hayk.exchangerates.ui

import androidx.recyclerview.widget.DiffUtil
import galstyan.hayk.exchangerates.model.Bank
import galstyan.hayk.exchangerates.model.CurrencyRate


class RateDiffer : DiffUtil.ItemCallback<CurrencyRate>() {
    override fun areItemsTheSame(
        old: CurrencyRate,
        new: CurrencyRate
    ): Boolean {
        return old == new
    }

    override fun areContentsTheSame(
        old: CurrencyRate,
        new: CurrencyRate
    ): Boolean {
        return true
    }
}


class BankDiffer : DiffUtil.ItemCallback<Bank>() {
    override fun areItemsTheSame(
        old: Bank,
        new: Bank
    ): Boolean {
        return old.title == new.title
    }

    override fun areContentsTheSame(
        old: Bank,
        new: Bank
    ): Boolean {
        return old == new
    }
}