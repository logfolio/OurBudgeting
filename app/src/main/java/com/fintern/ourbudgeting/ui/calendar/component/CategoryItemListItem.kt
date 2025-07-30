package com.fintern.ourbudgeting.ui.calendar.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fintern.ourbudgeting.data.calendar.CategoryDefinition
import com.fintern.ourbudgeting.data.calendar.CategoryItemData
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CategoryItemListItem(
    item: CategoryItemData,
    categoryDefinition: CategoryDefinition?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(1.dp, Color(0xFF964BFF), RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
                Text(
                    text = categoryDefinition?.emoji ?: "❓",
                    fontSize = 16.sp
                )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            val amountColor = if (item.amount > 0) Color.Red else Color.Blue
            val amountPrefix = if (item.amount > 0) "+" else ""

            val formatter = NumberFormat.getNumberInstance(Locale.getDefault())
            val formattedAmount = formatter.format(item.amount)

            Text(
                text = "$amountPrefix ${formattedAmount}원",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = amountColor
            )
            Text(
                text = item.description,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = item.userName,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = item.date,
                fontSize = 11.sp,
                color = Color.Gray
            )
        }
    }
}