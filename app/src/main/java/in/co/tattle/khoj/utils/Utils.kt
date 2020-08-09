package `in`.co.tattle.khoj.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.FileUtils
import android.provider.OpenableColumns
import com.google.android.gms.common.util.IOUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Utils {

    companion object {
        @JvmStatic
        fun startURLActivity(context: Context, url: String) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
                )
            )
        }

        @JvmStatic
        fun prepareFilePart(
            partName: String,
            fileUri: Uri,
            context: Context
        ): MultipartBody.Part {
            val parcelFileDescriptor =
                context.contentResolver.openFileDescriptor(fileUri, "r", null)
            val inputStream = FileInputStream(parcelFileDescriptor!!.fileDescriptor)
            val file = File(context.cacheDir, getFileName(context, fileUri))
            val outputStream = FileOutputStream(file)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                FileUtils.copy(inputStream, outputStream)
                FileUtils.closeQuietly(inputStream)
            } else {
                IOUtils.copyStream(inputStream, outputStream)
                IOUtils.closeQuietly(inputStream)
            }
            val requestFile: RequestBody = file
                .asRequestBody(context.contentResolver.getType(fileUri)!!.toMediaTypeOrNull())
            // MultipartBody.Part is used to send also the actual file name
            return MultipartBody.Part.createFormData(partName, file.name, requestFile)
        }

        private fun getFileName(context: Context, fileUri: Uri): String {

            var name = ""
            val returnCursor = context.contentResolver.query(
                fileUri,
                null, null, null, null
            )
            if (returnCursor != null) {
                val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                returnCursor.moveToFirst()
                name = returnCursor.getString(nameIndex)
                returnCursor.close()
            }
            return name
        }

        @SuppressLint("SimpleDateFormat")
        @JvmStatic
        fun getDateFromDateTime(dateStr: String): String {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val parsedDate =
                    LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME)
                parsedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            } else {
                val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val formatter = SimpleDateFormat("dd-MM-yyyy")
                formatter.format(parser.parse(dateStr)!!)
            }
        }
    }
}