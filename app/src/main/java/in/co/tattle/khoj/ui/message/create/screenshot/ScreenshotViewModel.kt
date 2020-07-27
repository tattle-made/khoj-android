package `in`.co.tattle.khoj.ui.message.create

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class ScreenshotViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    val screenshots: MutableLiveData<ArrayList<Uri>> by lazy {
        MutableLiveData<ArrayList<Uri>>()
    }

    init {
        screenshots.value = getScreenshots()
    }

    private fun getScreenshots(): ArrayList<Uri>? {
        //get the media ID and display name from media store
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME
        )

        val selection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            "${MediaStore.Images.Media.RELATIVE_PATH} like ? "
        } else {
            "${MediaStore.Images.Media.DATA} like ? "
        }

        //access the screenshots folder
        val selectionArgs = arrayOf(
            "%Screenshots%"
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val mCursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        when (mCursor?.count) {
            null -> {
                mCursor?.close()
                return null
            }
            0 -> {
                mCursor?.close()
                return arrayListOf()
            }
            else -> {
                //get the screenshots and display to the user
                val imageList: ArrayList<Uri> = arrayListOf()
                lateinit var uri: Uri
                while (mCursor.moveToNext()) {
                    uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        mCursor.getLong(mCursor.getColumnIndex(MediaStore.Images.Media._ID))
                    )
                    imageList.add(uri)
                }
                return imageList
            }
        }
    }
}