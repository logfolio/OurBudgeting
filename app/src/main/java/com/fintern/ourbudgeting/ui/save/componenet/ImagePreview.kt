package com.fintern.ourbudgeting.ui.save.componenet

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.fintern.ourbudgeting.R

@Composable
fun ImagePreview(
    photoUri: Uri?,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (photoUri != null) {
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(photoUri)
                .build()
        )

        Box(
            modifier = modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            Image(
                painter = painter,
                contentDescription = stringResource(R.string.image),
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
            )

            IconButton(
                onClick = onRemove,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.delete_image),
                    tint = Color.White
                )
            }
        }
    }
}