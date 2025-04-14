package com.blummock.core.repository

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.blummock.core.serialize.PointDtoFile
import com.blummock.domain.entity.Point
import com.blummock.domain.logger.Logger
import com.blummock.domain.repository.FileSystemRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FileSystemRepositoryImpl @Inject constructor(
    private val json: Json,
    private val logger: Logger,
    @ApplicationContext private val context: Context,
) : FileSystemRepository {

    override suspend fun saveGraphic(points: List<Point>): String = suspendCoroutine {
        try {
            val content = json.encodeToString(points.map { p -> PointDtoFile(p.x, p.y) })
            val fileName = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + ".json"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val resolver = context.contentResolver

                val contentValues = ContentValues().apply {
                    put(MediaStore.Downloads.DISPLAY_NAME, fileName)
                    put(MediaStore.Downloads.MIME_TYPE, "application/json")
                    put(MediaStore.Downloads.IS_PENDING, 1)
                }

                val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

                uri?.let { link ->
                    resolver.openOutputStream(link)?.bufferedWriter()?.use { writer ->
                        writer.write(content)
                    }
                    contentValues.clear()
                    contentValues.put(MediaStore.Downloads.IS_PENDING, 0)
                    resolver.update(link, contentValues, null, null)
                }
            } else {
                val downloadsDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                if (!downloadsDir.exists()) downloadsDir.mkdirs()

                val file = File(downloadsDir, fileName)
                FileOutputStream(file).bufferedWriter().use { writer -> writer.write(content) }
                context.sendBroadcast(
                    Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file))
                )
            }
            it.resume(fileName)
            logger.info("file saved: $fileName")
        } catch (e: Exception) {
            it.resumeWithException(e)
            logger.error(e, "saving file")
        }
    }
}