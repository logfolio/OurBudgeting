package com.fintern.ourbudgeting.ui.calendar.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fintern.ourbudgeting.data.calendar.CategoryList
import com.fintern.ourbudgeting.data.calendar.FoodItem

@Composable
fun CategoryListSection() {
    val categories = remember {
        listOf(
            CategoryList(
                name = "식비",
                items = listOf(
                    FoodItem(
                        id = "1",
                        amount = 2000,
                        description = "햄버거",
                        date = "2025/07/29",
                        userName = "짱구"
                    ),
                    FoodItem(
                        id = "2",
                        amount = -2000,
                        description = "피자",
                        date = "2025/07/29",
                        userName = "짱구"
                    )
                )
            )
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        categories.forEach { categoryItemData ->
            item {
                Column {
                    Text(
                        text = categoryItemData.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Modifier.padding(horizontal = 16.dp)
                }
            }
            items(categoryItemData.items, key = { it.id }) { item ->
                CategoryItemListItem(item = item)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}