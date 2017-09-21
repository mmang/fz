package com.mangmang.fz.adapter

/**
 * Created by mangmang on 2017/9/21.
 */
interface IExpandable<T> {
    fun isExpanded(): Boolean
    fun setExpanded(expanded: Boolean)
    fun getSubItems(): List<T>
    fun getLevel(): Int
}