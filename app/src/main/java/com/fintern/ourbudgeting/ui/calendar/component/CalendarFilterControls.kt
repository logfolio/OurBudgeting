package com.fintern.ourbudgeting.ui.calendar.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarFilterControls(
    nickname: String,
    filterType: FilterType,
    onFilterTypeSelected: (FilterType) -> Unit,
    modifier: Modifier = Modifier
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden,
        skipHiddenState = false
    )
    val scope = rememberCoroutineScope()

    CalendarTransactionFilter(
        nickname = nickname,
        filterType = filterType,
        onFilterClick = {
            showBottomSheet = true
            scope.launch {
                sheetState.show()
            }
        },
        modifier = modifier
    )

    if (showBottomSheet) {
        FilterBottomSheet(
            sheetState = sheetState,
            currentFilterType = filterType,
            onFilterSelected = { selectedFilter ->
                onFilterTypeSelected(selectedFilter)
                scope.launch { sheetState.hide() }
                showBottomSheet = false
            },
            onDismissRequest = {
                scope.launch { sheetState.hide() }
                showBottomSheet = false
            }
        )
    }
}