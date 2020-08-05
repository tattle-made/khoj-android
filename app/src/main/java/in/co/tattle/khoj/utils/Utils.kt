package `in`.co.tattle.khoj.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


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
            val file: File = FileUtils.getFile(context, fileUri)!!

            // create RequestBody instance from file
            val requestFile: RequestBody = RequestBody.create(
                MediaType.parse(context.getContentResolver().getType(fileUri)),
                file
            )

            // MultipartBody.Part is used to send also the actual file name
            return MultipartBody.Part.createFormData(partName, file.getName(), requestFile)
        }

    }
}