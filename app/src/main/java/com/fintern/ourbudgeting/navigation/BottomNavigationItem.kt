package com.fintern.ourbudgeting.navigation

import androidx.annotation.DrawableRes
import com.fintern.ourbudgeting.R

enum class BottomNavigationItem(
    val label: String,
    @DrawableRes val icon: Int,
    val contentDescription: String
) {
    HOME("홈", R.drawable.ic_home, "홈"),
    CALENDAR("캘린더", R.drawable.ic_calendar, "캘린더"),
    STATISTICS("통계", R.drawable.ic_statistic, "통계"),
    ASSETMANAGEMENT("자산", R.drawable.ic_asset, "자산"),
    SETTING("설정", R.drawable.ic_setting, "설정"),
}