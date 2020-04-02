package galstyan.hayk.exchangerates.ui

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import galstyan.hayk.exchangerates.model.Bank
import galstyan.hayk.exchangerates.model.Rate
import galstyan.hayk.exchangerates.model.Rates


fun bankDiffer(): DiffUtil.ItemCallback<Bank> = SimpleDiffer()
fun rateDiffer(): DiffUtil.ItemCallback<Rates> = SimpleDiffer()

fun stringDiffer(): DiffUtil.ItemCallback<String> = SimpleDiffer()
fun Class<String>.differ(): DiffUtil.ItemCallback<String> = stringDiffer()

open class SimpleDiffer<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(old: T, new: T): Boolean = old === new

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(old: T, new: T): Boolean = old == new
}