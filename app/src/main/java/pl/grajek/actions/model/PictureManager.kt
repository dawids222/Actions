package pl.grajek.actions.model

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import pl.grajek.actions.util.APPLICATION_DIRECTORY
import pl.grajek.actions.util.pdf
import java.io.File
import java.io.FileOutputStream
import java.util.Date

class PictureManager {

    fun getBitmap(v: View): Bitmap {
        val viewBitmap = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.RGB_565)
        val viewCanvas = Canvas(viewBitmap)
        val backgroundDrawable = v.background

        if (backgroundDrawable != null) {
            backgroundDrawable.draw(viewCanvas)
        } else {
            viewCanvas.drawColor(Color.WHITE)
            v.draw(viewCanvas)
        }

        return viewBitmap
    }

    fun save(context: Context, bitmap: Bitmap) {
        val fileStamp = pdf.format(Date())

        try {
            val folder = File(Environment.getExternalStorageDirectory(), APPLICATION_DIRECTORY)
            if (!folder.exists())
                folder.mkdirs()

            val imgFile = File(
                folder.absolutePath,
                "$fileStamp.jpg"
            )

            val outputStream = FileOutputStream(imgFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputStream)
            outputStream.close()

            addImageToGallery(imgFile.absolutePath, context)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun addImageToGallery(filePath: String, context: Context) {
        val values = ContentValues()

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        values.put(MediaStore.MediaColumns.DATA, filePath)

        context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }
}