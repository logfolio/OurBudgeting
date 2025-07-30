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
import com.fintern.ourbudgeting.data.calendar.CategoryDefinition
import com.fintern.ourbudgeting.data.calendar.CategoryItemData
import com.fintern.ourbudgeting.data.calendar.CategoryList

@Composable
fun CategoryListSection() {
    val categories = remember {
        listOf(
            CategoryList(
                category = CategoryDefinition(
                    id = "food",
                    emoji = "🍔",
                    displayName = "식비"
                ),
                items = listOf(
                    CategoryItemData(
                        id = "1",
                        amount = 2000,
                        description = "햄버거",
                        date = "2025/07/29",
                        userName = "짱구",
                        categoryId = "food"
                    ),
                    CategoryItemData(
                        id = "2",
                        amount = -2000,
                        description = "피자",
                        date = "2025/07/29",
                        userName = "짱구",
                        categoryId = "food"
                    )
                )
            )
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        categories.forEach { categoryList ->
            item {
                Column {
                    Text(
                        text = categoryList.category.displayName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Modifier.padding(horizontal = 16.dp)
                }
            }
            items(categoryList.items, key = { it.id }) { item ->
                CategoryItemListItem(
                    item = item,
                    categoryDefinition = categoryList.category)
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