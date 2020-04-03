package galstyan.hayk.exchangerates.app

import android.annotation.SuppressLint
import android.net.Uri
import galstyan.hayk.exchangerates.domain.*
import galstyan.hayk.exchangerates.repository.BranchRepository
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.json.JSONObject
import java.net.URL


private const val API = "http://rate.am/ws/mobile/v2/branches.ashx"

private const val KEY_QUERY_BANK_ID = "id"

private const val KEY_BRANCHES = "list"


/**
 * rate.am API Bank branches implementation
 * Using mix of custom deserialization since dynamic keys are not supported
 * by the most serialization libraries
 */
class BranchRepositoryImpl() : BranchRepository() {


    /**
     * Simple cache of branches by bank id
     */
    private val cache: MutableMap<String, List<Branch>> = HashMap()


    /**
     * @return cached branch if it exists since they are not changed frequently
     * otherwise fetches from API
     */
    @SuppressLint("DefaultLocale") // seems like a lint bug
    override suspend fun getBranchesOf(bankId: String, language: Language): List<Branch> {

        // returning cached list if it exists
        cache[bankId]?.let { return it }

        val url = Uri.parse(API)
            .buildUpon()
            .appendQueryParameter(KEY_QUERY_BANK_ID, bankId)
            .toString()

        val response = URL(url).readText()
        return deserializeBranches(
            language.name.toLowerCase(),
            JSONObject(response).get(KEY_BRANCHES) as JSONObject
        ).also { cache[bankId] = it }
    }


    private fun deserializeBranches(lang: String, response: JSONObject): List<Branch> {
        val branchIds = response.keys()
        val result = mutableListOf<Branch>()
        var headBranch: Branch? = null

        branchIds.forEach { id ->
            val branch = response.get(id) as JSONObject
            if (branch["head"] as Int == 1) {
                headBranch = deserializeBranch(id, branch, lang, true)
            } else result.add(deserializeBranch(id, branch, lang, false))
        }

        headBranch?.let { result.add(it) }
        return result.reversed()
    }


    private fun deserializeBranch(
        id: String,
        branch: JSONObject,
        lang: String,
        isHead: Boolean
    ): Branch {
        val location = branch["location"] as JSONObject
        return Branch(
            id = id,
            title = (branch["title"] as JSONObject)[lang].toString(),
            address = (branch["address"] as JSONObject)[lang].toString(),
            contact = branch["contacts"].toString(),
            location = Location(
                lat = location["lat"].toString().toDouble(),
                long = location["lng"].toString().toDouble()
            ),
            isHead = isHead
        )
    }
}