package `in`.co.tattle.khoj.utils

import okhttp3.RequestBody
import okio.*
import java.io.IOException


class ProgressRequestBody(
    private val requestBody: RequestBody,
    private val mediaUploadCallbacks: MediaUploadCallbacks
) : RequestBody() {
    override fun contentType() = requestBody.contentType()

    @Throws(IOException::class)
    override fun contentLength() = requestBody.contentLength()

    override fun writeTo(sink: BufferedSink) {
        val countingSink = CountingSink(sink, this, mediaUploadCallbacks)
//        val bufferedSink = Okio.buffer(countingSink)

//        requestBody.writeTo(bufferedSink)
//        bufferedSink.flush()
    }

    class CountingSink(
        sink: Sink,
        private val requestBody: RequestBody,
        private val mediaUploadCallbacks: MediaUploadCallbacks
    ) : ForwardingSink(sink) {
        private var bytesWritten = 0L

        override fun write(source: Buffer, byteCount: Long) {
            super.write(source, byteCount)

            bytesWritten += byteCount
            mediaUploadCallbacks.onProgressUpdate((bytesWritten / requestBody.contentLength() * 100).toInt())
        }
    }
}

interface MediaUploadCallbacks {
    fun onProgressUpdate(percentage: Int)
}
