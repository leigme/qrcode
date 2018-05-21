package me.leig.baselibrary.util

/**
 *
 *
 * @author leig
 * @version 20180301
 *
 */
interface DownloadCallBack {
    fun downloadStart()
    fun downloading(num: Int, total: Int)
    fun downloadEnd(path: String)
}