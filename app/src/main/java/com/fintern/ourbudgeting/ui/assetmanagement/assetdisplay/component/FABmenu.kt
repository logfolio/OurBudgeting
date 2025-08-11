package com.fintern.ourbudgeting.ui.assetmanagement.assetdisplay.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fintern.ourbudgeting.R

@Composable
fun FABMenu(
    modifier: Modifier = Modifier,
    onAddAssetTypeClick: () -> Unit = {},
    onEditAssetTypeClick: () -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }

    if (isExpanded) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
                .clickable { isExpanded = false }
        )
    }

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        // 메뉴 아이템들 (확장되었을 때만 표시)
        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FABMenuItem(
                    onClick = {
                        onAddAssetTypeClick()
                        isExpanded = false
                    },
                    text = stringResource(R.string.add_asset_type),
                    iconRes = R.drawable.ic_add
                )
                FABMenuItem(
                    onClick = {
                        onEditAssetTypeClick()
                        isExpanded = false
                    },
                    text = stringResource(R.string.edit_asset_type),
                    iconRes = R.drawable.ic_edit
                )
            }
        }
        FloatingActionButton(
            onClick = { isExpanded = !isExpanded },
            modifier = Modifier.size(56.dp),
            containerColor = Color(0xFF964BFF),
            contentColor = Color.White
        ) {
            Icon(
                imageVector = if (isExpanded) Icons.Default.Close else Icons.Default.Add,
                contentDescription = if (isExpanded) "Close" else "Open menu",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}