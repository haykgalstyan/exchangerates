package galstyan.hayk.exchangerates.app

import galstyan.hayk.exchangerates.model.Bank
import galstyan.hayk.exchangerates.model.CurrencyRate
import galstyan.hayk.exchangerates.model.Rate
import galstyan.hayk.exchangerates.model.RateInfo
import galstyan.hayk.exchangerates.repository.RatesRepository


class RatesMockRepositoryImpl : RatesRepository() {

    override suspend fun getRates(): List<Bank> {
        val list: MutableList<Bank> = mutableListOf()
        repeat(10) {
            list.add(
                Bank(
                    id = it.toString(),
                    title = "Bank $it",
                    image = "247e6cb5ede645579a2a6dba26dc6200.jpg",
                    branches = null,
                    rateInfo = RateInfo(
                        date = System.currentTimeMillis(),
                        rates = listOf(
                            CurrencyRate(
                                currency = "USD",
                                cash = Rate(
                                    buy = 1000.toDouble(),
                                    sell = 2000.toDouble()
                                ),
                                nonCash = Rate(
                                    buy = 500.toDouble(),
                                    sell = 700.toDouble()
                                )
                            )
                        )
                    )
                )
            )
        }
        return list
    }
}