package com.iram.thegalleryapp.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import com.iram.thegalleryapp.R

/**
 * Util class for handling permissions
 * Created by: Iram Khan
 * Email: khan.iram02@gmail.com
 * Date: 4th Feb 2025
 */

object PermissionUtil {

    fun isStoragePermissionGranted(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(context,
                        Manifest.permission.READ_MEDIA_VIDEO
                    ) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_MEDIA_AUDIO
                    ) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun requestStoragePermissions(
        launcher: ActivityResultLauncher<Array<String>>
    ) {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_AUDIO
            )
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        launcher.launch(permissions)
    }

    fun shouldShowPermissionRationale(activity: Activity): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES) ||
                    activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_VIDEO) ||
                    activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_AUDIO)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            false
        }
    }

    fun showPermissionRationaleDialog(
        context: Context,
        retryCallback: () -> Unit
    ) {
        AlertDialog.Builder(context)
            .setTitle(R.string.permission_required)
            .setMessage(R.string.permission_detail)
            .setPositiveButton(R.string.try_again) { _, _ -> retryCallback() }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    fun showSettingsDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle(R.string.permission_required)
            .setMessage(R.string.permission_denied)
            .setPositiveButton(R.string.open_settings) { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts(context.packageName, context.packageName, null)
                }
                context.startActivity(intent)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
}
