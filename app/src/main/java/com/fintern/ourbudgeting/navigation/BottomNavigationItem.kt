package com.fintern.ourbudgeting.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.fintern.ourbudgeting.R

enum class BottomNavigationItem(
    @param:StringRes val label: Int,
    @param:DrawableRes val icon: Int,
    @param:StringRes val contentDescription: Int
) {
    HOME(R.string.home, R.drawable.ic_home, R.string.home),
    CALENDAR(R.string.calendar, R.drawable.ic_calendar, R.string.calendar),
    STATISTICS(R.string.statistics, R.drawable.ic_statistic, R.string.statistics),
    ASSETMANAGEMENT(R.string.asset, R.drawable.ic_asset, R.string.asset),
    SETTING(R.string.setting, R.drawable.ic_setting, R.string.setting),
}