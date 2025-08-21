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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fintern.ourbudgeting.data.calendar.CategoryList
import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun CategoryListSection(
    categories: List<CategoryList>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        categories.forEach { categoryList ->
            item {
                Column {
                    Text(
                        text = stringResource(categoryList.category.labelRes),
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
                    categoryType = categoryList.category
                )
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

fun toTimestamp(dateString: String): Timestamp {
    val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
    val localDate = LocalDate.parse(dateString, formatter)
    val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    return Timestamp(date)
}