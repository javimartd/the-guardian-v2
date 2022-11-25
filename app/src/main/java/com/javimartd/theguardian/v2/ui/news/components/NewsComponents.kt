package com.javimartd.theguardian.v2.ui.news.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberAsyncImagePainter
import com.javimartd.theguardian.v2.R

@Composable
fun NewsItem(content: @Composable () -> Unit) {
    Card(
        backgroundColor = Color.White,
        elevation = dimensionResource(id = R.dimen.cardView_elevation),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.medium_margin))
    ) {
        content.invoke()
    }
}

@Composable
fun Thumbnail(
    modifier: Modifier = Modifier,
    thumbnail: String
) {
    Image(
        contentDescription = stringResource(id = R.string.image_content_description),
        contentScale = ContentScale.FillBounds,
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.image_height)),
        painter = rememberAsyncImagePainter(thumbnail)
    )
}

@Composable
fun Title(title: String) {
    Text(
        maxLines = 3,
        modifier = Modifier.padding(
            end = dimensionResource(id = R.dimen.medium_margin),
            start = dimensionResource(id = R.dimen.medium_margin),
            top = dimensionResource(id = R.dimen.medium_margin)
        ),
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.h1,
        text = title
    )
}

@Composable
fun Section(
    date: String,
    name: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_margin)),
        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.medium_margin)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.small_margin)))
                .background(color = colorResource(id = R.color.blue_500))
                .padding(horizontal = dimensionResource(id = R.dimen.small_margin)),
            style = MaterialTheme.typography.body1
                .copy(color = Color.White),
            text = name
        )
        Text(
            text = date,
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
fun Body(body: String) {
    Text(
        maxLines = 5,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.medium_margin)),
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.body1,
        text = body,
        textAlign = TextAlign.Justify
    )
}

@Composable
fun ReadMore(webUrl: String) {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(webUrl)) }
    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = dimensionResource(id = R.dimen.medium_margin),
                end = dimensionResource(id = R.dimen.medium_margin)
            ),
        onClick = { context.startActivity(intent) },
        style = MaterialTheme.typography.body1
            .copy(
                color = colorResource(id = R.color.blue_500),
                textAlign = TextAlign.End
            ),
        text = AnnotatedString(stringResource(id = R.string.news_screen_item_button)),
    )
}