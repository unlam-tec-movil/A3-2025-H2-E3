package ar.edu.unlam.mobile.scaffolding.ui.components

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView

fun View.getBitmapFromComposable(
    width: Int,
    height: Int,
    content: @Composable () -> Unit,
): Bitmap {
    // Crea una ComposeView temporal
    val composeView =
        ComposeView(context).apply {
            // Establece el contenido de Compose
            setContent(content)
            // Mide y hace layout de la vista
            layout(0, 0, width, height)
            measure(
                View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY),
            )
        }

    // Crea el Bitmap y dibuja la vista en él
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(bitmap)
    composeView.draw(canvas)
    return bitmap
}
