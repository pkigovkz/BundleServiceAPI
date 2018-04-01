package kz.gov.pki.osgi.layer.api

import org.json.JSONObject
import kotlin.jvm.JvmStatic

data class NCALayerJSON(val version: String, val info: String, val updurl: String, val syspkgs: String, val disturls: List<DistUrlJSON>, val bundles: List<BundleJSON>) {
	companion object {
		@JvmStatic fun parseJSON(json: String): NCALayerJSON {
			val jo = JSONObject(json).getJSONObject("ncalayer")
			val jbl = jo.getJSONArray("bundles")
			val bundleList = mutableListOf<BundleJSON>()
			for (jb in jbl) {
				with(jb as JSONObject) {
					bundleList.add(BundleJSON(getString("name"), getString("symname"), getString("version"), optBoolean("required", false), optBoolean("hidden", false), optBoolean("banned", false), getString("info"), getString("url"), getString("hash"), getString("csernum"), getString("chash")))
				}
			}
			val jdul = jo.getJSONArray("disturls")
			val disturlList = mutableListOf<DistUrlJSON>()
			for (jdu in jdul) {
				with(jdu as JSONObject) {
					disturlList.add(DistUrlJSON(getString("type"), getString("url"), getString("hash")))
				}
			}
			return NCALayerJSON(jo.getString("version"), jo.getString("info"), jo.getString("updurl"), jo.getString("syspkgs"), disturlList, bundleList)
		}
	}

	private val requiredSymNames = bundles.filter { it.required }.map { it.symname }.toSet()
	fun listRequiredSymNames() = requiredSymNames
	private val bannedBundles = bundles.filter { it.banned }
	fun listBannedBundles() = bannedBundles
	private val hiddenBundles = bundles.filter { it.hidden }
	fun listHiddenBundles() = hiddenBundles
}

data class BundleJSON(val name: String, val symname: String, val version: String, val required: Boolean, val hidden: Boolean, val banned: Boolean, val info: String, val url: String, val hash: String, val csernum: String, val chash: String)

data class DistUrlJSON(val type: String, val url: String, val hash: String)