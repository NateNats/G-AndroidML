package com.N2Project.androidml

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.androidbrowserhelper.trusted.PermissionStatus

class ImageHandler {

    companion object {
        var isClassified: Boolean = false
        var isCancel: Boolean = false

        @Composable
        fun ImagePicker(onImageSelected: (Uri) -> Unit) {
            val imagePickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent(),
                onResult = {uri ->
                    uri?.let {
                        onImageSelected(it)
                    }
                }
            )

            IconButton(
                onClick = {
                    imagePickerLauncher.launch("image/*")
                },
                modifier = Modifier.size(100.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.folder),
                    contentDescription = "Image Picker"
                )
            }
        }

        @Composable
        fun CameraPicker(onImageCaptured: (Uri) -> Unit) {
            val context = LocalContext.current
            var imageUri by remember { mutableStateOf<Uri?>(null) }

            // Fungsi untuk membuat URI untuk menyimpan gambar
            fun createImageUri(): Uri {
                val file = File(context.filesDir, "photo_${System.currentTimeMillis()}.jpg")
                return FileProvider.getUriForFile(context, "com.yourapp.fileprovider", file)
            }

            // Permission State untuk kamera
//             val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

            // Launcher untuk mengambil gambar dari kamera
            val takePictureLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.TakePicture(),
                onResult = { isSuccess ->
                    if (isSuccess && imageUri != null) {
                        onImageCaptured(imageUri!!)
                    }
                }
            )

            // Tombol untuk mengambil gambar
            IconButton(
                onClick = {
//                    if (cameraPermissionState.status == PermissionStatus.ALLOW) {
//                        imageUri = createImageUri()
//                        takePictureLauncher.launch(imageUri!!)
//                    } else {
//                        cameraPermissionState.launchPermissionRequest()
//                    }
                },
                modifier = Modifier.size(100.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.camera),
                    contentDescription = "Camera Picker"
                )
            }
        }

        @Composable
        fun CancelProcess(onClick: () -> Unit) {
            Button(
                onClick = {
                    onClick()
                },
                modifier = Modifier.size(150.dp, 70.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                enabled = isCancel
            ) {
                Text(
                    text = "Cancel"
                )
            }
        }

        @Composable
        fun ClassifyProcess(onClick: () -> Unit) {
            Button(
                onClick = {
                    onClick()
                },
                modifier = Modifier.size(150.dp, 70.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green,
                    contentColor = Color.White
                ),
                enabled = isClassified
            ) {
                Text(
                    text = "Classify"
                )
            }
        }
    }
}