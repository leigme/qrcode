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
    fun downloading(total: Int, num: Int)
    fun downloadEnd(path: String)
}