package com.N2Project.androidml


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import java.io.File
import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.N2Project.androidml.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ImageHandler() {

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
        fun classifyProcess(context: Context, img: Uri?, isClassified: Boolean):FloatArray ? {
            var output by remember { mutableStateOf<FloatArray?>(null) }

            Button(
                onClick = {
                    output = classify(context = context, image = img)
                },
                modifier = Modifier.size(150.dp, 70.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green,
                    contentColor = Color.White
                ),
                enabled = isClassified
            ) {
                Text(
                    text = if (isClassified) "Classified" else "Classify"
                )
            }

            return output
        }

        private fun classify(context: Context, image: Uri?): FloatArray? {
            val model = Model.newInstance(context)

            // mengubah image uri menjadi bitmap
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, image)
            val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
            val byteBuffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3)
            byteBuffer.order(ByteOrder.nativeOrder())
            val intValues = IntArray(224 * 224)
            resizedBitmap.getPixels(intValues, 0, 224, 0, 0, 224, 224)

            for (pixelVal in intValues) {
                byteBuffer.putFloat(((pixelVal shr 16 and 0xFF) / 255.0f)) // R -> memindahkan 16 bit ke kanan dan menyisakan nilai R di bagian paling kanan
                byteBuffer.putFloat(((pixelVal shr 8 and 0xFF) / 255.0f))  // G -> memindahkan 8 bit ke kanan dan menyisakan nilai G di bagian paling kanan
                byteBuffer.putFloat(((pixelVal and 0xFF) / 255.0f))        // B -> menyisakan nilai B di bagian paling kanan
            }

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 1, 1, 1), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray
            model.close()

            return outputFeature0
        }
    }
}