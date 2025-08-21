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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fintern.ourbudgeting.R
import com.fintern.ourbudgeting.data.calendar.CategoryType
import com.fintern.ourbudgeting.data.calendar.TransactionWithId
import com.fintern.ourbudgeting.ui.calendar.extensions.toFormatterDate
import com.fintern.ourbudgeting.ui.common.model.TransactionType
import com.fintern.ourbudgeting.ui.user.UserViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CategoryItemListItem(
    item: TransactionWithId,
    categoryType: CategoryType?,
    userViewModel: UserViewModel = hiltViewModel()
) {

    val nickname by userViewModel.nickname.collectAsStateWithLifecycle()

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
                text = stringResource(categoryType!!.labelRes),
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            val amountColor =
                if (item.transaction.type == TransactionType.INCOME.name) Color.Red else Color.Blue
            val amountPrefix =
                if (item.transaction.type == TransactionType.INCOME.name) stringResource(R.string.prefix_plus) else stringResource(
                    R.string.prefix_minus
                )

            val formatter = NumberFormat.getNumberInstance(Locale.getDefault())
            val formattedAmount = formatter.format(item.transaction.amount)

            Text(
                text = stringResource(R.string.item_amount, amountPrefix, formattedAmount),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = amountColor
            )
            Text(
                text = item.transaction.description,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = nickname,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = item.transaction.date?.toFormatterDate() ?: "",
                fontSize = 11.sp,
                color = Color.Gray
            )
        }
    }
}